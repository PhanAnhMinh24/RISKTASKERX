package com.wbsrisktaskerx.wbsrisktaskerx.controller.otp;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointUtil;
import com.wbsrisktaskerx.wbsrisktaskerx.service.otp.AdminEmailService;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(EndpointUtil.API)
public class SendOTPController {

    private final AdminEmailService adminEmailService;

    public SendOTPController(AdminEmailService adminEmailService) {
        this.adminEmailService = adminEmailService;
    }

    @PostMapping(EndpointUtil.EMAIL + EndpointUtil.SEND)
    public void sendOtpEmail(@RequestParam String to) throws MessagingException, IOException {
        adminEmailService.sendOtpEmail(to);
    }
}
