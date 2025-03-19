package com.wbsrisktaskerx.wbsrisktaskerx.service.otp;

import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ForgotPasswordRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ResetPasswordRequest;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AdminEmailService {
    ResponseEntity<String> sendOtpEmail(String to) throws MessagingException, IOException;
    ResponseEntity<String> verifyOtp(ForgotPasswordRequest request);
    ResponseEntity<String> resetPassword(ResetPasswordRequest request);
}
