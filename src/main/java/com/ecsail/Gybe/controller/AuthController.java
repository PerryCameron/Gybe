package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.AuthDTO;
import com.ecsail.Gybe.dto.MembershipListDTO;
import com.ecsail.Gybe.dto.SqlAuthDTO;
import com.ecsail.Gybe.service.EmailService;
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

import java.util.List;


@Controller
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	EmailService emailService;
	SendMailService service;

	@Value("${spring.mail.username}")
	private String fromEmail;

	RosterService rosterService;
	@Autowired
	public AuthController(SendMailService service, EmailService emailService, RosterService rosterService) {
		this.service = service;
		this.emailService = emailService;
		this.rosterService = rosterService;
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

	@GetMapping("/lists")
	public String getHomePage(Model model, @RequestParam int year, @RequestParam String rb, @RequestParam String sort) {
		List<MembershipListDTO> membershipList = rosterService.getRoster(year,rb,sort);
		model.addAttribute("list", membershipList);
		model.addAttribute("listSize", membershipList.size());
		return "lists";
	}

//	@GetMapping("/lists") // http://localhost:8080/lists?year=2023&sort=byId&rb=option1
//	public String getHomePage(Model model,
//							  @RequestParam(name = "year", required = false, defaultValue = "2023") int year,
//							  @RequestParam(name = "rb", required = false, defaultValue = "option1") String rb,
//							  @RequestParam(name = "sort", required = false, defaultValue = "byId") String sort) {
//
//		List<MembershipListDTO> membershipList = rosterService.getRoster(year, rb, sort);
//		model.addAttribute("membershipList", membershipList);
//		return "lists";
//	}




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
