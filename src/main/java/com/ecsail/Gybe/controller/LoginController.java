package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.service.interfaces.AdminService;
import com.ecsail.Gybe.service.interfaces.SendMailService;
import com.ecsail.Gybe.wrappers.MailWrapper;
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
        MailWrapper mailWrapper = adminService.generateCredentialsEmail(email);
        if(mailWrapper.sendEmail()) sendMailService.sendHTMLMail(mailWrapper.getMailDTO(), fromEmail);
        model.addAttribute("message", mailWrapper.getMessage());
        model.addAttribute("button",!mailWrapper.sendEmail());
        return "message";
    }

    @GetMapping("/update_creds")
    public String updateCredentials(@RequestParam String key,
                                    @RequestParam String status,
                                    @RequestParam String email, Model model) {
    if(adminService.isValidKey(key)) {
        // if(status.equals("EXISTING") return "set-pass";
        // if(status.equals("NEW_ACCOUNT") do something else
        model.addAttribute("email", email);
        model.addAttribute("key", key);
        model.addAttribute("status", status);
        return "set-pass";
    } else
        model.addAttribute("message", "Your password reset has expired. You only have 10 minutes to complete the process.");
        model.addAttribute("button",true);
        return "message";
    }

    @PostMapping("/update_password")
    public String updatePassword(@RequestParam String key,
                                 @RequestParam String status,
                                 @RequestParam String email,
                                 @RequestParam String password1,
                                 Model model) {
        // Your logic to update the password

        // For now, just print the received data
        System.out.println("Key: " + key);
        System.out.println("Status: " + status);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password1);
        // Redirect to a confirmation page or display a message
        return "login";
    }


}
