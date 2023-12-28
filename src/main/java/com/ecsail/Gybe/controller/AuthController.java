package com.ecsail.Gybe.controller;

import com.ecsail.Gybe.dto.AuthDTO;
import com.ecsail.Gybe.dto.FormHashRequestDTO;
import com.ecsail.Gybe.dto.MailDTO;
import com.ecsail.Gybe.service.implementations.AdminServiceImpl;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Year;
import java.util.List;


@Controller
public class AuthController {
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	private final EmailServiceImpl emailServiceImpl;
	private final SendMailServiceImpl sendMailService;
	AdminServiceImpl adminServiceImpl;


	@Value("${spring.mail.username}")
	private String fromEmail;


	@Autowired
	public AuthController(
			AdminServiceImpl adminServiceImpl,
			EmailServiceImpl emailServiceImpl,
			SendMailServiceImpl sendMailService) {
		this.adminServiceImpl = adminServiceImpl;
		this.emailServiceImpl = emailServiceImpl;
		this.sendMailService = sendMailService;
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
//		if(mailDTO.getAuthDTO().getExists())
//			sendMailService.sendHTMLMail(mailDTO, fromEmail);
		if (mailDTO == null) {
			model.addAttribute("errorMessage", "Your email was not found in the system."); // Add custom error message
			System.out.println("Returning second one");
			return "email-error"; // Replace with your error page view name
		}		model.addAttribute("email", mailDTO); // Update the model with the email
		return emailServiceImpl.returnCorrectPage(mailDTO); // Adjust this method to accept a string email
	}

	@GetMapping("/notfound")
	public String showNotFound(@ModelAttribute AuthDTO authDTO, Model model) {
		model.addAttribute("authDTO", authDTO);
		return "notfound";
	}

	@GetMapping("/form-request")
	public String getFormRequests(Model model, @RequestParam(required = false) Integer year) {
		if (year == null) year = Year.now().getValue(); // Get the current year if 'year' is not provided
		List<FormHashRequestDTO> formHashRequestDTOS = adminServiceImpl.getFormRequests(year);
		model.addAttribute("formRequests", formHashRequestDTOS);
		return "form-request";
	}
}
