package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.service.implementations.*;
import com.ecsail.Gybe.service.interfaces.FeeService;
import com.ecsail.Gybe.service.interfaces.SendMailService;
import com.ecsail.Gybe.wrappers.BoardOfDirectorsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class MembershipController {
	private static final Logger logger = LoggerFactory.getLogger(MembershipController.class);
	private final EmailServiceImpl emailServiceImpl;
	private final GeneralServiceImpl generalService;
	private final FeeService feeService;
	SendMailService service;
	RosterServiceImpl rosterServiceImpl;
	MembershipServiceImpl membershipService;
	AdminServiceImpl adminServiceImpl;


	@Value("${spring.mail.username}")
	private String fromEmail;


	@Autowired
	public MembershipController(
			SendMailService service,
			RosterServiceImpl rosterService,
			AdminServiceImpl adminService,
			MembershipServiceImpl membershipService,
			EmailServiceImpl emailService,
			GeneralServiceImpl generalService,
			FeeService feeService) {
		this.service = service;
		this.rosterServiceImpl = rosterService;
		this.adminServiceImpl = adminService;
		this.membershipService = membershipService;
		this.emailServiceImpl = emailService;
		this.generalService = generalService;
		this.feeService = feeService;
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

	@GetMapping("/lists")
	public String getHomePage(Model model,
							  @RequestParam(required = false) Integer year,
							  @RequestParam(defaultValue = "active") String rb,
							  @RequestParam(defaultValue = "byId") String sort,
							  @RequestParam Map<String, String> allParams) {
		// Set the year to current year if it's not provided
		if (year == null) {
			year = Year.now().getValue();
		}
		// Extract searchParams from allParams
		List<String> searchParams = allParams.entrySet().stream()
				.filter(e -> e.getKey().startsWith("param"))
				.map(Map.Entry::getValue)
				.collect(Collectors.toList());
		List<MembershipListDTO> membershipList = rosterServiceImpl.getRoster(year, rb, sort, searchParams);
		model.addAttribute("list", membershipList);
		model.addAttribute("listSize", membershipList.size());
		return "lists";
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

	@GetMapping("/rb_bod")
	public ResponseEntity<BoardOfDirectorsResponse> getBoardOfDirectors(@RequestParam(defaultValue = "#{T(java.time.LocalDate).now().getYear()}") Integer year) {
		BoardOfDirectorsResponse bodResponse = membershipService.getBodResponse(year);
		return ResponseEntity.ok(bodResponse);
	}

	@GetMapping("/slip-wait-list")
	public String getSlipWaitList(Model model) {
		List<MembershipListDTO> membershipListDTOS = rosterServiceImpl.getSlipWait();
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



}
