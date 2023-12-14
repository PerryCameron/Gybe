package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.service.*;
import com.ecsail.Gybe.service.implementations.AdminServiceImpl;
import com.ecsail.Gybe.service.interfaces.SendMailService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	AdminServiceImpl adminServiceImpl;


	@Value("${spring.mail.username}")
	private String fromEmail;


	@Autowired
	public AuthController(
			SendMailService service,
			RosterService rosterService,
			AdminServiceImpl adminServiceImpl,
			MembershipService membershipService,
			EmailService emailService) {
		this.service = service;
		this.rosterService = rosterService;
		this.adminServiceImpl = adminServiceImpl;
		this.membershipService = membershipService;
		this.emailService = emailService;
	}

	@GetMapping("/renew")
	public String greetingForm(Model model) {
		String email = ""; // Initialize with an empty string or a default value
		model.addAttribute("email", email);
		return "email_auth_form";
	}

	@PostMapping("/renew")
	public String greetingSubmit(@RequestParam String email, Model model) throws MessagingException {
		if (email == null || email.isEmpty()) {
			logger.error("Email is null or Empty");
			return "error"; // Replace with your error page view name
		}
		MailDTO mailDTO = emailService.processEmailSubmission(email);
		if (mailDTO == null) return "error"; // Replace with your error page view name
		model.addAttribute("email", mailDTO); // Update the model with the email
		return emailService.returnCorrectPage(mailDTO); // Adjust this method to accept a string email
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
		List<FormHashRequestDTO> formHashRequestDTOS = adminServiceImpl.getFormRequests(year);
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
