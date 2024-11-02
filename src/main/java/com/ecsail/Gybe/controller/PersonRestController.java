package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.service.interfaces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PersonRestController {

    private static final Logger logger = LoggerFactory.getLogger(PersonRestController.class);
    private final PersonService personService;

    SendMailService service;
    RosterService rosterService;
    MembershipService membershipService;
    AdminService adminService;


    @Value("${spring.mail.username}")
    private String fromEmail;


    @Autowired
    public PersonRestController(
            SendMailService service,
            RosterService rosterService,
            AdminService adminService,
            MembershipService membershipService,
            PersonService personService) {
        this.service = service;
        this.rosterService = rosterService;
        this.adminService = adminService;
        this.membershipService = membershipService;
        this.personService = personService;
    }

    @PostMapping("api/update-person")
    @PreAuthorize("hasRole('ROLE_MEMBERSHIP')") // Adjust the role as needed
    public ResponseEntity<Map<String, String>> updatePerson(@RequestBody PersonDTO personDTO) {
        boolean updateSuccess = personService.updatePerson(personDTO);

        Map<String, String> response = new HashMap<>();
        if (updateSuccess) {
            response.put("message", "Person updated successfully.");
            return ResponseEntity.ok(response); // Return JSON response
        } else {
            response.put("message", "Failed to update person.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
