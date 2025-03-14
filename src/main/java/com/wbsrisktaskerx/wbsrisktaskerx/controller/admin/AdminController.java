package com.wbsrisktaskerx.wbsrisktaskerx.controller.admin;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EmailConstant;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointUtil;
import com.wbsrisktaskerx.wbsrisktaskerx.service.otp.AdminOtpService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(EndpointUtil.REQUEST_MAPPING)
public class AdminController {

    @Autowired
    private AdminOtpService adminOtpService;

    @PostMapping(EndpointUtil.SEND)
    public void sendEmail(@RequestParam String to) throws MessagingException, IOException {

        Map<String, String> placeholders = new HashMap<>();

        String templatePath = EmailConstant.PLACEHOLDER_TEMPLATES_EMAIL;
        adminOtpService.sendEmail(to, templatePath, placeholders);
    }
}
