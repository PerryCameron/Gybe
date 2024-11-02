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

    @PostMapping("/update-person")
    @PreAuthorize("hasRole('ROLE_USER')") // Adjust the role as needed
    public ResponseEntity<String> updatePerson(@RequestBody PersonDTO personDTO) {
        // Call the service to update only the provided fields
        boolean updateSuccess = personService.updatePerson(personDTO);

        if (updateSuccess) {
            return ResponseEntity.ok("Person updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update person.");
        }
    }
}
