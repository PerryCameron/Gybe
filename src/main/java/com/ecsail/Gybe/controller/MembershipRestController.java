package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.service.interfaces.*;
import com.ecsail.Gybe.wrappers.BoardOfDirectorsResponse;
import com.ecsail.Gybe.wrappers.BoatListResponse;
import com.ecsail.Gybe.wrappers.RosterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Year;
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
    private final XLSService xlsService;


    @Autowired
    public MembershipRestController(
            SendMailService service,
            RosterService rosterService,
            AdminService adminService,
            MembershipService membershipService,
            EmailService emailService,
            GeneralService generalService,
            FeeService feeService,
            BoatService boatService,
            XLSService xlsService) {
        this.service = service;
        this.rosterService = rosterService;
        this.adminService = adminService;
        this.membershipService = membershipService;
        this.emailService = emailService;
        this.generalService = generalService;
        this.feeService = feeService;
        this.boatService = boatService;
        this.xlsService = xlsService;
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

    @GetMapping("/api/gybe_chart_data")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Map<String, Object> getGybeChartData() {
        // Replace with actual service calls to fetch data
        List<StatsDTO> statsDTOS = generalService.getStats();
        AgesDTO agesDTO = generalService.getAges();
        Map<String, Object> response = new HashMap<>();
        response.put("stats", statsDTOS);
        response.put("ages", agesDTO);
        return response;
    }

    @GetMapping("/api/bod")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Map<String, Object> getBods(Model model, @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().getYear()}") Integer year) {
        List<LeadershipDTO> leadershipDTOS = membershipService.getLeadershipByYear(year);
        ThemeDTO themeDTO = membershipService.getThemeByYear(year);
        Map<String, Object> response = new HashMap<>();
        response.put("boardOfDirectors", leadershipDTOS);
        response.put("theme", themeDTO);
        response.put("year", year);
        return response;
    }

    @GetMapping("/api/publicity")
    @PreAuthorize("hasRole('ROLE_PUBLICITY')")
    public ResponseEntity<InputStreamResource> getPublicity(Model model, @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().getYear()}") Integer year) {
        xlsService.createEmailList(); // this creates an xlsx file
        String filePath = System.getProperty("user.home") + "/Email_List.xlsx";
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("File not found: " + filePath);
        }
        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Email_List.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(file.length())
                    .body(resource);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found", e);
        }
    }

    @GetMapping("/api/form-request-summary")
    @PreAuthorize("hasRole('ROLE_MEMBERSHIP')")
    public Map<String, Object> getFormRequest(@RequestParam(defaultValue = "#{T(java.time.LocalDate).now().getYear()}") Integer year) {
        if (year == null) year = Year.now().getValue(); // Get the current year if 'year' is not provided
        List<FormRequestSummaryDTO> formRequestSummaryDTOS = adminService.getFormSummaries(year);
        Map<String, Object> response = new HashMap<>();
        response.put("formSummaryData", formRequestSummaryDTOS);
        response.put("year", year);
        return response;
    }

}
