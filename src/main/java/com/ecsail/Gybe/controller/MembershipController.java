package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.service.interfaces.*;
import com.ecsail.Gybe.wrappers.BoatListResponse;
import com.ecsail.Gybe.wrappers.BoatResponse;
import com.ecsail.Gybe.wrappers.RosterResponse;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class MembershipController {
    private static final Logger logger = LoggerFactory.getLogger(MembershipController.class);
    private final EmailService emailService;
    private final GeneralService generalService;
    private final FeeService feeService;
    private final BoatService boatService;
    private final XLSService xlsService;
    private final FileController finalContrller;
    SendMailService service;
    RosterService rosterService;
    MembershipService membershipService;
    AdminService adminService;


    @Value("${spring.mail.username}")
    private String fromEmail;


    @Autowired
    public MembershipController(
            SendMailService service,
            RosterService rosterService,
            AdminService adminService,
            MembershipService membershipService,
            EmailService emailService,
            GeneralService generalService,
            FeeService feeService,
            BoatService boatService,
            XLSService xlsService,
            FileController fileController) {
        this.service = service;
        this.rosterService = rosterService;
        this.adminService = adminService;
        this.membershipService = membershipService;
        this.emailService = emailService;
        this.generalService = generalService;
        this.feeService = feeService;
        this.boatService = boatService;
        this.xlsService = xlsService;
        this.finalContrller = fileController;
    }

    // sets initial page AJAX updates
    @GetMapping("/Rosters")
    public String getRosters(Model model) {
        RosterResponse rosterResponse = rosterService.getDefaultRosterResponse();
        model.addAttribute("rosters", rosterResponse);
        return "rosters";
    }

    // this is to forward to ecsail.org
    @GetMapping("/bod-stripped")
    public String getBodStrippedVersion(Model model, @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().getYear()}") Integer year) {
        List<LeadershipDTO> leadershipDTOS = membershipService.getLeaderShip(year);
        ThemeDTO themeDTO = membershipService.getThemeByYear(year);
        model.addAttribute("year", year);
        model.addAttribute("bod", leadershipDTOS);
        model.addAttribute("theme", themeDTO);
        return "bod-stripped";
    }

    @GetMapping("/slip-wait-list")
    public String getSlipWaitList(Model model) {
        List<MembershipListDTO> membershipListDTOS = rosterService.getSlipWait();
        model.addAttribute("waitList", membershipListDTOS);
        return "slip-wait-list";
    }

    @GetMapping("/")
    public String getStats(Model model, HttpServletRequest request, Principal principal) {
        if (principal != null) {
            Authentication authentication = (Authentication) principal;
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            model.addAttribute("roles", authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
        }
        List<StatsDTO> statsDTOS = generalService.getStats();
        AgesDTO agesDTO = generalService.getAges();
        model.addAttribute("stats", statsDTOS);
        model.addAttribute("ages", agesDTO);
        return "main";
    }

    @GetMapping("/ecsc-pricing")
    public String getPrices(Model model) {
        List<FeeDTO> feeDTOS = feeService.getFees();
        model.addAttribute("fees", feeDTOS);
        return "ecsc-pricing";
    }

    // this is for exporting to website
    @GetMapping("/slips")
    public String getSlips(Model model) {
        List<SlipInfoDTO> slipInfoDTOS = generalService.getSlipInfo();
        List<SlipStructureDTO> slipStructureDTOS = generalService.getSlipStructure();
        model.addAttribute("slipOwners", slipInfoDTOS);
        model.addAttribute("slipStructure", slipStructureDTOS);
        return "slips";
    }

    @GetMapping("/boat_list")
    public String getBoatLit(Model model,
                             @RequestParam(defaultValue = "active_sailboats") String listType,
                             @RequestParam Map<String, String> allParams) {
        BoatListResponse boatListResponse = boatService.getBoatListResponse(listType, allParams);
        model.addAttribute("boatList", boatListResponse);
        return "boatlist";
    }

    @GetMapping("/boat")
    public String getBoat(Model model, @RequestParam String boatId) {
        BoatResponse boatResponse = boatService.getBoatResponse(boatId);
        model.addAttribute("boat", boatResponse);
        return "boat";
    }

    @GetMapping("/directory")
    public String getDirectory(Model model) {
        List<JsonNode> memberships = membershipService.getMembershipListAsJson();
        System.out.println("Number of nodes=" + memberships.size());
        return "directory";
    }

}
