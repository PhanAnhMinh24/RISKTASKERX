package com.wbsrisktaskerx.wbsrisktaskerx.service.otp;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ForgotPasswordRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ResetPasswordRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.VerifyOtpResponse;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AdminEmailService {
    ResponseEntity<Boolean> sendOtpEmail(String to) throws MessagingException, IOException;
    VerifyOtpResponse verifyOtp(ForgotPasswordRequest request);
    ResponseEntity<Boolean> resetPassword(ResetPasswordRequest request);
}
