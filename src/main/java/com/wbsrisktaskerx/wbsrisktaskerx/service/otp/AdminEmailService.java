package com.wbsrisktaskerx.wbsrisktaskerx.service.otp;

import jakarta.mail.MessagingException;
import java.io.IOException;

public interface AdminEmailService {
    void sendOtpEmail(String to) throws MessagingException, IOException;
}
