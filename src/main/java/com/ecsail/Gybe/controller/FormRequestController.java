package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.service.EmailService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class FormRequestController {

    EmailService emailService;

    public FormRequestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("register")
    public ResponseEntity<Object> redirectToForm(@RequestParam String member) throws URISyntaxException {
        String url = emailService.buildLinkWithParameters(member);
        URI jotform = new URI(url);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(jotform);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

}
