package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.MembershipListDTO;
import com.ecsail.Gybe.service.interfaces.*;
import com.ecsail.Gybe.wrappers.BoardOfDirectorsResponse;
import com.ecsail.Gybe.wrappers.RosterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class MembershipRestController {

    private final EmailService emailService;
    private final GeneralService generalService;
    private final FeeService feeService;
    SendMailService service;
    RosterService rosterService;
    MembershipService membershipService;
    AdminService adminService;


    @Autowired
    public MembershipRestController(
            SendMailService service,
            RosterService rosterService,
            AdminService adminService,
            MembershipService membershipService,
            EmailService emailService,
            GeneralService generalService,
            FeeService feeService) {
        this.service = service;
        this.rosterService = rosterService;
        this.adminService = adminService;
        this.membershipService = membershipService;
        this.emailService = emailService;
        this.generalService = generalService;
        this.feeService = feeService;
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

//    @GetMapping("/lists")
//    public String getHomePage(Model model,
//                              @RequestParam(required = false) Integer year,
//                              @RequestParam(defaultValue = "active") String rb,
//                              @RequestParam(defaultValue = "byId") String sort,
//                              @RequestParam Map<String, String> allParams) {
//        // Set the year to current year if it's not provided
//        if (year == null) {
//            year = Year.now().getValue();
//        }
//        // Extract searchParams from allParams
//        List<String> searchParams = allParams.entrySet().stream()
//                .filter(e -> e.getKey().startsWith("param"))
//                .map(Map.Entry::getValue)
//                .collect(Collectors.toList());
//        List<MembershipListDTO> membershipList = rosterService.getRoster(year, rb, sort, searchParams);
//        model.addAttribute("list", membershipList);
//        model.addAttribute("listSize", membershipList.size());
//        return "lists";
//    }

}
