package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.PasswordUpdateRequestDTO;
import com.ecsail.Gybe.dto.UserDTO;
import com.ecsail.Gybe.service.interfaces.AdminService;
import com.ecsail.Gybe.service.interfaces.AuthenticationService;
import com.ecsail.Gybe.service.interfaces.SendMailService;
import com.ecsail.Gybe.wrappers.MailWrapper;
import com.ecsail.Gybe.wrappers.MessageResponse;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {
    private final AdminService adminService;
    private final SendMailService sendMailService;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    public LoginController(AdminService adminService,
                           SendMailService sendMailService,
                           AuthenticationService authenticationService) {
        this.adminService = adminService;
        this.sendMailService = sendMailService;
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Name of the HTML file for the login page
    }

    @GetMapping("/logout")
    public String showLogoutPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = (UserDTO) auth.getPrincipal();
        model.addAttribute("username", user.getUsername());
        return "logout";
    }

    // When you submit an email, this checks to make sure it is on the system, etc..
    // Also sends reset password or create new Account email
    @PostMapping("/upsert_user")
    public String register(@RequestParam String email, Model model) throws MessagingException {
        MailWrapper mailWrapper = adminService.generateCredentialsEmail(email);
        if (mailWrapper.sendEmail()) sendMailService.sendHTMLMail(mailWrapper.getMailDTO(), fromEmail);
        model.addAttribute("message", mailWrapper.getMessage());
        model.addAttribute("button", !mailWrapper.sendEmail()); // this should be false
        // in the event it is true some more code here would be nice
        return "message";
    }

    // This method is called from an email
    // http://localhost:8080/update_creds?key=l0CcXf6hUfIgxQFSiaJXr2pfIrX-JlHHMJPoMpb2ylM&status=EXISTING&email=perry.lee.cameron@gmail.com
    @GetMapping("/update_creds")
    public String updateCredentials(@RequestParam String key,
                                    @RequestParam String status,
                                    @RequestParam String email, Model model) {
        if (adminService.isValidKey(key)) {
            // if(status.equals("EXISTING") return "set-pass";
            // if(status.equals("NEW_ACCOUNT") do something else
            model.addAttribute("email", email);
            model.addAttribute("key", key);
            model.addAttribute("status", status);
            return "set-pass";
        } else
            model.addAttribute("message", "Your account password reset has expired. You have either" +
                    " completed the process or it has been more than 10 minutes, since you made your request");
        model.addAttribute("button", true);
        model.addAttribute("link", "/login");
        model.addAttribute("buttonText", "Go to login");
        return "message";
    }

    // This method is called from AJAX (set-pass.html) using the double password form.
    @PostMapping("/update_password")
    @ResponseBody
    public ResponseEntity<MessageResponse> updatePassword(@RequestBody PasswordUpdateRequestDTO request) {
        // Similar logs for other fields
        MessageResponse messageResponse = adminService.setUserPass(
                request.getKey(), request.getStatus(), request.getEmail(),
                request.getPassword1(), request.getPassword2());
            return ResponseEntity.ok(messageResponse);
    }

    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("message", "You do not have permission to access this page.");
        model.addAttribute("button", false);
        model.addAttribute("link", "/"); // don't need
        model.addAttribute("buttonText", "Return Home"); // don't need
        return "message";  // Name of the template/view to be returned
    }


}
