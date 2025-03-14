package com.wbsrisktaskerx.wbsrisktaskerx.service.otp;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EmailConstant;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.AdminOtpJpaQueryRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class AdminOtpService implements OtpService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private AdminOtpJpaQueryRepository adminOtpJpaQueryRepository;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    public AdminOtpService(JavaMailSender javaMailSender, AdminOtpJpaQueryRepository adminOtpJpaQueryRepository) {
        this.javaMailSender = javaMailSender;
        this.adminOtpJpaQueryRepository = adminOtpJpaQueryRepository;
    }

    public void sendOtpEmail(String to) throws MessagingException, IOException {
        if (!adminOtpJpaQueryRepository.existsByEmail(to)) {
            throw new AppException(ErrorCode.EMAIL_NOT_FOUND);
        }

        Map<String, String> placeholders = new HashMap<>();
        String templatePath = EmailConstant.PLACEHOLDER_TEMPLATES_EMAIL;

        sendEmail(to, templatePath, placeholders);
    }

    public void sendEmail(String to, String templatePath, Map<String, String> placeholders)
            throws MessagingException, IOException {

        String otpCode = String.format("%04d", new Random().nextInt(10000));

        placeholders = new HashMap<>(placeholders);
        placeholders.put(EmailConstant.PLACEHOLDER_SUBJECT_OTP_CODE, otpCode);
        placeholders.put(EmailConstant.PLACEHOLDER_SUBJECT_EMAIL_SUBJECT, EmailConstant.PLACEHOLDER_SUBJECT_SEND_OTP);

        String htmlTemplate = new String(Files.readAllBytes(Paths.get(new ClassPathResource(templatePath).getURI())));

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            htmlTemplate = htmlTemplate.replace(entry.getKey(), entry.getValue());
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");

        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(EmailConstant.PLACEHOLDER_SUBJECT_SEND_OTP);
        mimeMessageHelper.setFrom(fromEmail);
        mimeMessageHelper.setText(htmlTemplate, true);

        javaMailSender.send(message);
    }
}
