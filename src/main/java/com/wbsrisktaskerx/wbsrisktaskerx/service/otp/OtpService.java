package com.wbsrisktaskerx.wbsrisktaskerx.service.otp;

import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.Map;

public interface OtpService {
    void sendEmail(String to, String templatePath, Map<String, String> placeholders) throws MessagingException, IOException;
}
