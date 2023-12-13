package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.FormHashRequestDTO;
import com.ecsail.Gybe.dto.FormSettingsDTO;
import com.ecsail.Gybe.service.AdminService;
import com.ecsail.Gybe.service.FormRequestService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class FormRequestController {

    private final AdminService adminService;
    FormRequestService formRequestService;

    public FormRequestController(FormRequestService formRequestService,
                                 AdminService adminService) {
        this.formRequestService = formRequestService;
        this.adminService = adminService;
    }

    @GetMapping("register")
    public ResponseEntity<Object> redirectToForm(@RequestParam String member) throws URISyntaxException {
        String url = formRequestService.openForm(member);
        URI jotform = new URI(url);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(jotform);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }


}
