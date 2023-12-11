package com.ecsail.Gybe.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
public class FormRequestController {

    @GetMapping("register")
    public String redirectToForm(@RequestParam String member) throws URISyntaxException {
//        String url = emailService.buildLinkWithParameters(member);
        return member;
    }

}
