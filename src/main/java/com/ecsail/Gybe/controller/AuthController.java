package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.AuthDTO;
import com.ecsail.Gybe.dto.BoardPositionDTO;
import com.ecsail.Gybe.dto.MembershipListDTO;
import com.ecsail.Gybe.dto.SqlAuthDTO;
import com.ecsail.Gybe.service.EmailService;
import com.ecsail.Gybe.service.MembershipService;
import com.ecsail.Gybe.service.RosterService;
import com.ecsail.Gybe.service.SendMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	EmailService emailService;
	SendMailService service;
	RosterService rosterService;
	MembershipService membershipService;


	@Value("${spring.mail.username}")
	private String fromEmail;


	@Autowired
	public AuthController(
			SendMailService service,
			EmailService emailService,
			RosterService rosterService,
			MembershipService membershipService) {
		this.service = service;
		this.emailService = emailService;
		this.rosterService = rosterService;
		this.membershipService = membershipService;
	}


	@GetMapping("/")
	public String greetingForm(Model model) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		logger.info("Current authentication: {}", authentication);
		AuthDTO authDTO = new AuthDTO();
		model.addAttribute("authDTO", authDTO);
		return "auth";
	}

//	@PostMapping("/")
//	public String greetingSubmit(@ModelAttribute AuthDTO authDTO, Model model) throws MessagingException {
//		MailDTO mailDTO = emailService.processEmailSubmission(authDTO);
//		if(authDTO.getExists())
//			service.sendHTMLMail(mailDTO, fromEmail);
//		model.addAttribute("authDTO", authDTO);
//		// makes hash and returns page
//		return emailService.returnCorrectPage(authDTO);
//	}

	@GetMapping("/admin")
	public String initForm(Model model) {
		SqlAuthDTO sqlAuthDTO = new SqlAuthDTO();
		model.addAttribute("sqlAuthDTO", sqlAuthDTO);
		return "admin";
	}

	@GetMapping("/notfound")
	public String showNotFound(@ModelAttribute AuthDTO authDTO, Model model) {
		model.addAttribute("authDTO", authDTO);
		return "notfound";
	}

//	@GetMapping("register")
//	public ResponseEntity<Object> redirectToForm(@RequestParam String member) throws URISyntaxException {
//		String url = emailService.buildLinkWithParameters(member);
//		URI jotform = new URI(url);
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setLocation(jotform);
//		return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
//	}


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

		List<MembershipListDTO> membershipList = rosterService.getRoster(year, rb, sort, searchParams);
		model.addAttribute("list", membershipList);
		model.addAttribute("listSize", membershipList.size());
		return "lists";
	}





//	@GetMapping("/person/{pid}")
//	@ResponseBody
//		public PersonDTO getStudent(@PathVariable("pid") Integer pid) {
//			return PEOPLE.stream().filter(person -> pid.equals(person.getP_id()))
//					.findFirst()
//					.orElseThrow(() -> new IllegalStateException("person " + pid + " does not exist"));
//		}
//
//	@GetMapping("/user/{pid}")
//	@ResponseBody
//	public PersonDTO getStudent2(@PathVariable("pid") Integer pid) {
//		return PEOPLE.stream().filter(person -> pid.equals(person.getP_id()))
//				.findFirst()
//				.orElseThrow(() -> new IllegalStateException("person " + pid + " does not exist"));
//	}
}
