package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.SlipInfoDTO;
import com.ecsail.Gybe.dto.SlipStructureDTO;
import com.ecsail.Gybe.service.interfaces.*;
import com.ecsail.Gybe.wrappers.BoardOfDirectorsResponse;
import com.ecsail.Gybe.wrappers.BoatListResponse;
import com.ecsail.Gybe.wrappers.RosterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MembershipRestController {

    private final EmailService emailService;
    private final GeneralService generalService;
    private final FeeService feeService;
    private final BoatService boatService;
    private final SendMailService service;
    private final RosterService rosterService;
    private final MembershipService membershipService;
    private final AdminService adminService;


    @Autowired
    public MembershipRestController(
            SendMailService service,
            RosterService rosterService,
            AdminService adminService,
            MembershipService membershipService,
            EmailService emailService,
            GeneralService generalService,
            FeeService feeService,
            BoatService boatService) {
        this.service = service;
        this.rosterService = rosterService;
        this.adminService = adminService;
        this.membershipService = membershipService;
        this.emailService = emailService;
        this.generalService = generalService;
        this.feeService = feeService;
        this.boatService = boatService;
    }

    @GetMapping("/rb_bod")
    public ResponseEntity<BoardOfDirectorsResponse> getBoardOfDirectors(@RequestParam(defaultValue = "#{T(java.time.LocalDate).now().getYear()}") Integer year) {
        BoardOfDirectorsResponse bodResponse = membershipService.getBodResponse(year);
        return ResponseEntity.ok(bodResponse);
    }

    @GetMapping("/rb_roster")
    public ResponseEntity<RosterResponse> getRosterResponse(
            @RequestParam(defaultValue = "active") String type,
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().getYear()}") Integer year,
            @RequestParam Map<String, String> allParams) {
        RosterResponse rosterResponse = rosterService.getRosterResponse(type, year, allParams);
        return ResponseEntity.ok(rosterResponse);
    }

    @GetMapping("/rb_boat_list")
    public ResponseEntity<BoatListResponse> getBoatLit(
            @RequestParam(defaultValue = "active_sailboats") String type,
            @RequestParam Map<String, String> allParams) {
        BoatListResponse boatListResponse = boatService.getBoatListResponse(type, allParams);
        return ResponseEntity.ok(boatListResponse);
    }

    @GetMapping("/api/slip_chart")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Map<String, Object> getSlipChart() {
        List<SlipInfoDTO> slipInfoDTOS = generalService.getSlipInfo();
        List<SlipStructureDTO> slipStructureDTOS = generalService.getSlipStructure();
        Map<String, Object> response = new HashMap<>();
        response.put("slipOwners", slipInfoDTOS);
        response.put("slipStructure", slipStructureDTOS);
        return response;
    }


}
