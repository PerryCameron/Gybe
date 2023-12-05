package com.ecsail.Gybe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login"; // Name of the HTML file for the login page
    }
}
