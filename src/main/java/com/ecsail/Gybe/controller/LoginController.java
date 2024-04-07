package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.service.interfaces.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final AdminService adminService;

    @Autowired
    public LoginController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Name of the HTML file for the login page
    }

    @PostMapping("/useradd")
    public String register(@RequestParam String email, Model model) {
        System.out.println(adminService.getPersonByEmail(email));
        return "login";
    }
}
