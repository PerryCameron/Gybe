package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.FormHashRequestDTO;
import com.ecsail.Gybe.dto.FormRequestSummaryDTO;
import com.ecsail.Gybe.dto.MailDTO;
import com.ecsail.Gybe.service.implementations.AdminServiceImpl;
import com.ecsail.Gybe.service.implementations.AuthenticationServiceImpl;
import com.ecsail.Gybe.service.implementations.EmailServiceImpl;
import com.ecsail.Gybe.service.implementations.SendMailServiceImpl;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Year;
import java.util.List;


@Controller
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	private final EmailServiceImpl emailServiceImpl;
	private final SendMailServiceImpl sendMailService;
	private final AdminServiceImpl adminServiceImpl;
	private final AuthenticationServiceImpl authenticationService;


	@Value("${spring.mail.username}")
	private String fromEmail;

	@Autowired
	public AuthController(
			AdminServiceImpl adminServiceImpl,
			EmailServiceImpl emailServiceImpl,
			SendMailServiceImpl sendMailService,
			AuthenticationServiceImpl authenticationService) {
		this.adminServiceImpl = adminServiceImpl;
		this.emailServiceImpl = emailServiceImpl;
		this.sendMailService = sendMailService;
		this.authenticationService = authenticationService;
	}

	@GetMapping("/renew")
	public String greetingForm(Model model) {
		String email = ""; // Initialize with an empty string or a default value
		model.addAttribute("email", email);
		return "email_auth_form";
	}

	@PostMapping("/renew")
	public String greetingSubmit(@RequestParam String email, Model model) throws MessagingException {
		MailDTO mailDTO = emailServiceImpl.processEmailSubmission(email);
		if (mailDTO != null && mailDTO.getAuthDTO() != null && Boolean.TRUE.equals(mailDTO.getAuthDTO().getExists())) {
			model.addAttribute("email", mailDTO); // Update the model with the email
			sendMailService.sendHTMLMail(mailDTO, fromEmail);
			return "result";
		} else {
			model.addAttribute("errorMessage", email + " was not found in our system."); // Add custom error message
			logger.error(email + " was not found in our system.");
			return "email-error";
		}
	}
	// I think this one is deprecated as all  functionality here is included in form-request-summary
	@GetMapping("/email-login")
	public String getFormRequests(Model model, @RequestParam(required = false) Integer year) {
		if (year == null) year = Year.now().getValue(); // Get the current year if 'year' is not provided
		List<FormHashRequestDTO> formHashRequestDTOS = adminServiceImpl.getFormRequests(year);
		model.addAttribute("emailLogins", formHashRequestDTOS);
		return "email-login";
	}

	@GetMapping("/form-request-summary")
	public String getJotFormRequests(Model model, @RequestParam(required = false) Integer year) {
		if (year == null) year = Year.now().getValue(); // Get the current year if 'year' is not provided
		List<FormRequestSummaryDTO> formRequestSummaryDTOS = adminServiceImpl.getFormSummaries(year);
		model.addAttribute("formSummaries", formRequestSummaryDTOS);
		return "form-request-summary";
	}

	@GetMapping("/admin")
	public String getAdminPage() {
		return "admin";
	}


}
