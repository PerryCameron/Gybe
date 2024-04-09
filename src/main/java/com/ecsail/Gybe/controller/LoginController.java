package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.MailDTO;
import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.service.interfaces.AdminService;
import com.ecsail.Gybe.service.interfaces.SendMailService;
import com.ecsail.Gybe.utils.ApiKeyGenerator;
import com.ecsail.Gybe.utils.ForgotPasswordHTML;
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
        MailDTO mailDTO = adminService.generateCredentialsEmail(email);
        sendMailService.sendHTMLMail(mailDTO, fromEmail);
        model.addAttribute("message", "An email has been sent to your address with further instructions. If you don't receive it shortly, please check your spam or junk folder. For any assistance, feel free to <a href=\"mailto:register@ecsail.org?subject=Login%20Help\">contact the administrator</a>.");
        return "message";
    }

    @GetMapping("/update_creds")
    public String updateCredentials(@RequestParam String key, @RequestParam String status) {
        System.out.println("Key: " + key);
        System.out.println("Status: " + status);
        return "login";
    }
}
