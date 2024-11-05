package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.EmailDTO;
import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.dto.PhoneDTO;
import com.ecsail.Gybe.service.interfaces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PersonRestController {

    private static final Logger logger = LoggerFactory.getLogger(PersonRestController.class);
    private final PersonService personService;
    private final EmailService emailService;

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
            PersonService personService,
            EmailService emailService) {
        this.service = service;
        this.rosterService = rosterService;
        this.adminService = adminService;
        this.membershipService = membershipService;
        this.personService = personService;
        this.emailService = emailService;
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

    @PatchMapping("/api/update-phones")
    @PreAuthorize("hasRole('ROLE_MEMBERSHIP')")
    public ResponseEntity<Map<String, Object>> updatePhones(@RequestBody List<PhoneDTO> phoneDTOList) {
        boolean isUpdated = personService.batchUpdatePhones(phoneDTOList); // Update each email in a batch operation
        Map<String, Object> response = new HashMap<>();
        response.put("updated", isUpdated);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/insert-phone")
    @PreAuthorize("hasRole('ROLE_MEMBERSHIP')")
    public ResponseEntity<Map<String, Object>> insertEmail(@RequestBody PhoneDTO phoneDTO) {
        int id = personService.insertNewPhoneRow(phoneDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/delete-email")
    @PreAuthorize("hasRole('ROLE_MEMBERSHIP')")
    public ResponseEntity<Map<String, Object>> deleteEmail(@RequestBody EmailDTO emailDTO) {
        boolean isDeleted = emailService.deleteEmailRow(emailDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("deleted", isDeleted);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/delete-phone")
    @PreAuthorize("hasRole('ROLE_MEMBERSHIP')")
    public ResponseEntity<Map<String, Object>> deletePhone(@RequestBody PhoneDTO phoneDTO) {
        boolean isDeleted = personService.deletePhoneRow(phoneDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("deleted", isDeleted);
        return ResponseEntity.ok(response);
    }
}
