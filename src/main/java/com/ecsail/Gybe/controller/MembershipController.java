package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.service.implementations.AdminServiceImpl;
import com.ecsail.Gybe.service.implementations.EmailServiceImpl;
import com.ecsail.Gybe.service.implementations.MembershipServiceImpl;
import com.ecsail.Gybe.service.implementations.RosterServiceImpl;
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
public class MembershipController {
	private static final Logger logger = LoggerFactory.getLogger(MembershipController.class);
	private final EmailServiceImpl emailServiceImpl;
	SendMailService service;
	RosterServiceImpl rosterServiceImpl;
	MembershipServiceImpl membershipServiceImpl;
	AdminServiceImpl adminServiceImpl;


	@Value("${spring.mail.username}")
	private String fromEmail;


	@Autowired
	public MembershipController(
			SendMailService service,
			RosterServiceImpl rosterServiceImpl,
			AdminServiceImpl adminServiceImpl,
			MembershipServiceImpl membershipServiceImpl,
			EmailServiceImpl emailServiceImpl) {
		this.service = service;
		this.rosterServiceImpl = rosterServiceImpl;
		this.adminServiceImpl = adminServiceImpl;
		this.membershipServiceImpl = membershipServiceImpl;
		this.emailServiceImpl = emailServiceImpl;
	}

	@GetMapping("/membership")
	public String getMembership(Model model,
								@RequestParam() Integer msId,
								@RequestParam() Integer selectedYear) {
		MembershipListDTO membershipListDTO = membershipServiceImpl.getMembership(msId, selectedYear);
		List<BoardPositionDTO> boardPositionDTOS = membershipServiceImpl.getBoardPositions();
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
}
