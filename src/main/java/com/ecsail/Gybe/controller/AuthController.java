package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.service.*;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	private final EmailService emailService;
	SendMailService service;
	RosterService rosterService;
	MembershipService membershipService;
	AdminService adminService;


	@Value("${spring.mail.username}")
	private String fromEmail;


	@Autowired
	public AuthController(
			SendMailService service,
			RosterService rosterService,
			AdminService adminService,
			MembershipService membershipService,
			EmailService emailService) {
		this.service = service;
		this.rosterService = rosterService;
		this.adminService = adminService;
		this.membershipService = membershipService;
		this.emailService = emailService;
	}

	@GetMapping("/renew")
	public String greetingForm(Model model) {
		AuthDTO authDTO = new AuthDTO();
		model.addAttribute("authDTO", authDTO);
		return "auth";
	}

	@PostMapping("/renew")
	public String greetingSubmit(@ModelAttribute AuthDTO authDTO, Model model) throws MessagingException {
		MailDTO mailDTO = emailService.processEmailSubmission(authDTO);
		if(authDTO.getExists())
			System.out.println(mailDTO);
//			service.sendHTMLMail(mailDTO, fromEmail);
		model.addAttribute("authDTO", authDTO);
		// makes hash and returns page
		return emailService.returnCorrectPage(authDTO);
	}

	@GetMapping("/notfound")
	public String showNotFound(@ModelAttribute AuthDTO authDTO, Model model) {
		model.addAttribute("authDTO", authDTO);
		return "notfound";
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

	@GetMapping("/form-request")
	public String getFormRequests(Model model, @RequestParam(required = false) Integer year) {
		if (year == null) year = Year.now().getValue(); // Get the current year if 'year' is not provided
		List<FormHashRequestDTO> formHashRequestDTOS = adminService.getFormRequests(year);
		model.addAttribute("formRequests", formHashRequestDTOS);
		return "form-request";
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
		List<MembershipListDTO> membershipList = rosterService.getRoster(year, rb, sort, searchParams);
		model.addAttribute("list", membershipList);
		model.addAttribute("listSize", membershipList.size());
		return "lists";
	}
}
