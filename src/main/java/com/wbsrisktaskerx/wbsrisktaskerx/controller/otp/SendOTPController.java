package com.wbsrisktaskerx.wbsrisktaskerx.controller.otp;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EmailConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointUtil;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ForgotPasswordRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ResetPasswordRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.service.otp.AdminEmailService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.annotation.security.PermitAll;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(EndpointUtil.API)
public class SendOTPController {

    private final AdminEmailService adminEmailService;

    public SendOTPController(AdminEmailService adminEmailService) {
        this.adminEmailService = adminEmailService;
    }

    @PostMapping(EndpointUtil.EMAIL + EndpointUtil.SEND_OTP)
    public void sendOtpEmail(@RequestParam String to) throws MessagingException, IOException {
        adminEmailService.sendOtpEmail(to);
    }

    @PostMapping(EndpointUtil.EMAIL + EndpointUtil.VERIFY_OTP)
    public ResponseEntity<String> verifyOtp(@RequestBody @Valid ForgotPasswordRequest request) {
        return adminEmailService.verifyOtp(request);
    }

    @PostMapping(EndpointUtil.EMAIL + EndpointUtil.FORGOT_PASSWORD)
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        return adminEmailService.resetPassword(request);
    }
}