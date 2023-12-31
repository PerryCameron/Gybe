package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.service.implementations.AdminServiceImpl;
import com.ecsail.Gybe.service.implementations.FormRequestServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class JotFormRestController {

    private final AdminServiceImpl adminServiceImpl;
    FormRequestServiceImpl formRequestServiceImpl;

    public JotFormRestController(FormRequestServiceImpl formRequestServiceImpl,
                                 AdminServiceImpl adminServiceImpl) {
        this.formRequestServiceImpl = formRequestServiceImpl;
        this.adminServiceImpl = adminServiceImpl;
    }

    @GetMapping("register")
    public ResponseEntity<Object> redirectToForm(@RequestParam String member) throws URISyntaxException {
        String url = formRequestServiceImpl.openForm(member);
        URI jotform = new URI(url);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(jotform);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }


}
