package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointUtil;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ForgotPasswordRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.ResetPasswordRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.VerifyOtpResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.service.otp.AdminEmailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(EndpointUtil.OTP)
public class AdminController {

    private final AdminEmailService adminEmailService;

    public AdminController(AdminEmailService adminEmailService) {
        this.adminEmailService = adminEmailService;
    }

    @GetMapping(EndpointUtil.EMAIL + EndpointUtil.SEND)
    public ResponseEntity<Boolean> sendOtpEmail(@RequestParam String to) throws MessagingException, IOException {
        return adminEmailService.sendOtpEmail(to);
    }

    @PostMapping(EndpointUtil.EMAIL + EndpointUtil.VERIFY)
    public VerifyOtpResponse verifyOtp(@RequestBody @Valid ForgotPasswordRequest request) {
        return adminEmailService.verifyOtp(request);
    }

    @PutMapping(EndpointUtil.EMAIL + EndpointUtil.FORGOT_PASSWORD)
    public ResponseEntity<Boolean> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        return adminEmailService.resetPassword(request);
    }
}
