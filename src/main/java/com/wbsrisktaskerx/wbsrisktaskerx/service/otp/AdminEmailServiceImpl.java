package com.wbsrisktaskerx.wbsrisktaskerx.service.otp;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EmailConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.AdminOtpJpaQueryRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class AdminEmailServiceImpl implements AdminEmailService {

    private final EmailService emailService;
    private final AdminOtpJpaQueryRepository adminOtpJpaQueryRepository;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public AdminEmailServiceImpl(EmailService emailService, AdminOtpJpaQueryRepository adminOtpJpaQueryRepository) {
        this.emailService = emailService;
        this.adminOtpJpaQueryRepository = adminOtpJpaQueryRepository;
    }

    @Override
    public void sendOtpEmail(String to) throws MessagingException, IOException {
        if (!adminOtpJpaQueryRepository.existsByEmail(to)) {
            throw new AppException(ErrorCode.EMAIL_NOT_FOUND);
        }

        Map<String, String> placeholders = new HashMap<>();
        String templatePath = EmailConstants.PLACEHOLDER_TEMPLATES_EMAIL;

        String otpCode = String.format("%04d", new Random().nextInt(10000));

        placeholders.put(EmailConstants.PLACEHOLDER_SUBJECT_OTP_CODE, otpCode);
        placeholders.put(EmailConstants.PLACEHOLDER_SUBJECT_EMAIL_SUBJECT, EmailConstants.PLACEHOLDER_SUBJECT_SEND_OTP);

        emailService.sendEmail(to, templatePath, placeholders);
    }
}
