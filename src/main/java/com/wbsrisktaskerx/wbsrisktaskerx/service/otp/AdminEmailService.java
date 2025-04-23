package com.wbsrisktaskerx.wbsrisktaskerx.service.otp;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ForgotPasswordRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ResetPasswordRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.VerifyOtpResponse;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface AdminEmailService {
    boolean sendOtpEmail(String to) throws MessagingException, IOException;
    VerifyOtpResponse verifyOtp(ForgotPasswordRequest request);
    boolean resetPassword(ResetPasswordRequest request);
    boolean sendTemporaryPassword(String to) throws MessagingException, IOException;
}