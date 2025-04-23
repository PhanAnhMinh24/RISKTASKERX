package com.wbsrisktaskerx.wbsrisktaskerx.service.otp;

import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.Map;

public interface EmailService {
    boolean sendEmail(String to, String subject, String templatePath, Map<String, String> placeholders) throws MessagingException, IOException;
}