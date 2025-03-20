package com.wbsrisktaskerx.wbsrisktaskerx.service.otp;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ForgotPasswordRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ResetPasswordRequest;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface AdminEmailService {
    String sendOtpEmail(String to) throws MessagingException, IOException;
    boolean verifyOtp(ForgotPasswordRequest request);
    boolean resetPassword(ResetPasswordRequest request);
}
