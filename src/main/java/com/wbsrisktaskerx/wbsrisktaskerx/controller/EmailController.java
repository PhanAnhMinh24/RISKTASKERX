package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EmailConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.service.email.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(EndpointConstants.EMAIL)
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping(EndpointConstants.SEND)
    public void sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String content) throws MessagingException, IOException {
        Map<String, String> placeholders = Map.of(
                EmailConstants.PLACEHOLDER_CONTENT, content,
                EmailConstants.PLACEHOLDER_SUBJECT, subject
        );

        String templatePath = EmailConstants.PLACEHOLDER_TEMPLATES_EMAIL;
        emailService.sendEmail(to, subject, templatePath, placeholders);
    }
}
