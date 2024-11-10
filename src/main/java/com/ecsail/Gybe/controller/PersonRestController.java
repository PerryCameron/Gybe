package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.EmailDTO;
import com.ecsail.Gybe.dto.OfficerDTO;
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

    @PatchMapping("/api/update-phones")
    @PreAuthorize("hasRole('ROLE_MEMBERSHIP')")
    public ResponseEntity<Map<String, Object>> updatePhones(@RequestBody List<PhoneDTO> phoneDTOList) {
        boolean isUpdated = personService.batchUpdatePhones(phoneDTOList); // Update each email in a batch operation
        Map<String, Object> response = new HashMap<>();
        response.put("updated", isUpdated);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/api/update-emails")
    @PreAuthorize("hasRole('ROLE_MEMBERSHIP')")
    public ResponseEntity<Map<String, Object>> updateEmails(@RequestBody List<EmailDTO> emailDTOList) {
        boolean isUpdated = personService.batchUpdateEmail(emailDTOList); // Update each email in a batch operation
        Map<String, Object> response = new HashMap<>();
        response.put("updated", isUpdated);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/api/update-positions")
    @PreAuthorize("hasRole('ROLE_MEMBERSHIP')")
    public ResponseEntity<Map<String, Object>> updatePositions(@RequestBody List<OfficerDTO> officerDTOList) {
        boolean isUpdated = personService.batchUpdatePosition(officerDTOList); // Update each email in a batch operation
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

    @PostMapping("/api/insert-email")
    @PreAuthorize("hasRole('ROLE_MEMBERSHIP')")
    public ResponseEntity<Map<String, Object>> insertEmail(@RequestBody EmailDTO emailDTO) {
        int id = personService.insertNewEmailRow(emailDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/insert-position")
    @PreAuthorize("hasRole('ROLE_MEMBERSHIP')")
    public ResponseEntity<Map<String, Object>> insertPosition(@RequestBody OfficerDTO officerDTO) {
        int id = personService.insertNewPositionRow(officerDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/delete-position")
    @PreAuthorize("hasRole('ROLE_MEMBERSHIP')")
    public ResponseEntity<Map<String, Object>> deletePosition(@RequestBody OfficerDTO officerDTO) {
        boolean isDeleted = personService.deletePositionRow(officerDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("deleted", isDeleted);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/delete-email")
    @PreAuthorize("hasRole('ROLE_MEMBERSHIP')")
    public ResponseEntity<Map<String, Object>> deleteEmail(@RequestBody EmailDTO emailDTO) {
        boolean isDeleted = personService.deleteEmailRow(emailDTO);
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
