package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.service.interfaces.*;
import com.ecsail.Gybe.wrappers.BoardOfDirectorsResponse;
import com.ecsail.Gybe.wrappers.BoatListResponse;
import com.ecsail.Gybe.wrappers.BoatResponse;
import com.ecsail.Gybe.wrappers.RosterResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@Controller
public class MembershipController {
    private static final Logger logger = LoggerFactory.getLogger(MembershipController.class);
    private final EmailService emailService;
    private final GeneralService generalService;
    private final FeeService feeService;
    private final BoatService boatService;
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

    @GetMapping("/membership")
    public String getMembership(Model model,
                                @RequestParam() Integer msId,
                                @RequestParam() Integer selectedYear) {
        MembershipListDTO membershipListDTO = membershipService.getMembership(msId, selectedYear);
        List<BoardPositionDTO> boardPositionDTOS = membershipService.getBoardPositions();
        model.addAttribute("membership", membershipListDTO);
        model.addAttribute("boardPositions", boardPositionDTOS);
        return "membership";
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
        ThemeDTO themeDTO = membershipService.getTheme(year);
        model.addAttribute("year", year);
        model.addAttribute("bod", leadershipDTOS);
        model.addAttribute("theme", themeDTO);
        return "bod-stripped";
    }

    @GetMapping("/bod")
    public String getBods(Model model, @RequestParam(defaultValue = "#{T(java.time.LocalDate).now().getYear()}") Integer year) {
        List<LeadershipDTO> leadershipDTOS = membershipService.getLeaderShip(year);
        BoardOfDirectorsResponse bodResponse = membershipService.getBodResponse(year);
        model.addAttribute("bodResponse", bodResponse);
        return "bod";
    }

    @GetMapping("/slip-wait-list")
    public String getSlipWaitList(Model model) {
        List<MembershipListDTO> membershipListDTOS = rosterService.getSlipWait();
        model.addAttribute("waitList", membershipListDTOS);
        return "slip-wait-list";
    }

    @GetMapping("/")
    public String getStats(Model model) {
        List<StatsDTO> statsDTOS = generalService.getStats();
        AgesDTO agesDTO = generalService.getAges();
        model.addAttribute("stats", statsDTOS);
        model.addAttribute("ages", agesDTO);
        return "charts";
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

    @GetMapping("/slip_chart")
    public String getSlipChart(Model model) {
        List<SlipInfoDTO> slipInfoDTOS = generalService.getSlipInfo();
        List<SlipStructureDTO> slipStructureDTOS = generalService.getSlipStructure();
        model.addAttribute("slipOwners", slipInfoDTOS);
        model.addAttribute("slipStructure", slipStructureDTOS);
        return "slip_back_end";
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
}
