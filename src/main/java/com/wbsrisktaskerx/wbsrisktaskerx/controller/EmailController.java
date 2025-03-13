package com.wbsrisktaskerx.wbsrisktaskerx.controller;

import com.wbsrisktaskerx.wbsrisktaskerx.common.contants.EmailContant;
import com.wbsrisktaskerx.wbsrisktaskerx.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public void sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String content) throws MessagingException, IOException {
        Map<String, String> placeholders = Map.of(
                EmailContant.PLACEHOLDER_CONTENT, content,
                EmailContant.PLACEHOLDER_SUBJECT, subject
        );

        String templatePath = EmailContant.PLACEHOLDER_TEMPLATES_EMAIL;
        emailService.sendEmail(to, subject, templatePath, placeholders);
    }
}
