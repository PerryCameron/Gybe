package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.service.interfaces.AdminService;
import com.ecsail.Gybe.service.interfaces.AuthenticationService;
import com.ecsail.Gybe.service.interfaces.SendMailService;
import com.ecsail.Gybe.wrappers.MailWrapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class LoginController {
    private final AdminService adminService;
    private final SendMailService sendMailService;
    private final AuthenticationService authenticationService;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    public LoginController(AdminService adminService,
                           SendMailService sendMailService, 
                           AuthenticationService authenticationService) {
        this.adminService = adminService;
        this.sendMailService = sendMailService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Name of the HTML file for the login page
    }

    // When you submit an email, this checks to make sure it is on the system, etc..
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
                                 Model model,
                                 HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        System.out.println("Session ID (/update_password): " + sessionId);
        adminService.setUserPass(key, status, email, password1);
        // For now, just print the received data
        System.out.println("Key: " + key);
        System.out.println("Status: " + status);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password1);

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        // Get the session from the request
        HttpSession session2 = attr.getRequest().getSession();
        String sessionId2 = session2.getId();
        System.out.println("Session ID (setUserPass()): " + sessionId2);
        // Redirect to a confirmation page or display a message
        return "redirect:/";
    }


}
