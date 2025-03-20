package com.wbsrisktaskerx.wbsrisktaskerx.service.otp;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EmailConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.AdminOtp;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ForgotPasswordRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ResetPasswordRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.AdminOtpJpaQueryRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.DateTimeUtils;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class AdminEmailServiceImpl implements AdminEmailService {

    private final EmailService emailService;
    private final AdminOtpJpaQueryRepository adminOtpJpaQueryRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public AdminEmailServiceImpl(EmailService emailService, AdminOtpJpaQueryRepository adminOtpJpaQueryRepository, PasswordEncoder passwordEncoder, EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        this.emailService = emailService;
        this.adminOtpJpaQueryRepository = adminOtpJpaQueryRepository;
        this.passwordEncoder = passwordEncoder;
        this.entityManager = entityManager;
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public boolean sendOtpEmail(String to) throws MessagingException, IOException {
        if (!adminOtpJpaQueryRepository.existsByEmail(to)) {
            throw new AppException(ErrorCode.EMAIL_NOT_FOUND);
        }

        Map<String, String> placeholders = new HashMap<>();
        String templatePath = EmailConstants.PLACEHOLDER_TEMPLATES_EMAIL;

        String otpCode = String.format("%04d", new Random().nextInt(10000));

        Admin admin = adminOtpJpaQueryRepository.findAdminByEmail(to)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));

        AdminOtp adminOtp = AdminOtp.builder()
                .admin(admin)
                .verificationCode(otpCode)
                .expiresAt(DateTimeUtils.getDateTimeNow().plusMinutes(5))
                .build();

        adminOtpJpaQueryRepository.saveOtp(adminOtp);

        placeholders.put(EmailConstants.PLACEHOLDER_SUBJECT_OTP_CODE, otpCode);
        placeholders.put(EmailConstants.PLACEHOLDER_SUBJECT_EMAIL_SUBJECT, EmailConstants.PLACEHOLDER_SUBJECT_SEND_OTP);

        emailService.sendEmail(to, templatePath, placeholders);
        return true;
    }

    @Override
    @Transactional
    public boolean verifyOtp(ForgotPasswordRequest request) {
        Optional<AdminOtp> adminOtpOpt = adminOtpJpaQueryRepository
                .findValidOtpByEmail(request.getEmail(), request.getOtp());

        if (adminOtpOpt.isPresent()) {
            adminOtpJpaQueryRepository.deleteOtpByEmailAndCode(request.getEmail(), request.getOtp());
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean resetPassword(ResetPasswordRequest request) {

        Optional<AdminOtp> recentOtp = adminOtpJpaQueryRepository.findValidOtpByEmail(request.getEmail(), null);
        if (recentOtp.isPresent()) {
            throw new AppException(ErrorCode.OTP_NOT_VERIFIED);
        }

        request.validate();
        Admin admin = adminOtpJpaQueryRepository.findAdminByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_FOUND));

        // Updated password
        admin.setPassword(passwordEncoder.encode(request.getNewPassword()));
        entityManager.merge(admin);
        entityManager.flush();
        return true;
    }
}