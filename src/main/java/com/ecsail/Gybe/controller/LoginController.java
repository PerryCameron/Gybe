package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.MailDTO;
import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.service.interfaces.AdminService;
import com.ecsail.Gybe.service.interfaces.SendMailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final AdminService adminService;
    private final SendMailService sendMailService;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    public LoginController(AdminService adminService, SendMailService sendMailService) {
        this.adminService = adminService;
        this.sendMailService = sendMailService;
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Name of the HTML file for the login page
    }

    @PostMapping("/upsert_user")
    public String register(@RequestParam String email, Model model) throws MessagingException {
        MailDTO mailDTO = adminService.getPersonByEmail(email);
        sendMailService.sendHTMLMail(mailDTO, fromEmail);
        return "login";
    }
}
