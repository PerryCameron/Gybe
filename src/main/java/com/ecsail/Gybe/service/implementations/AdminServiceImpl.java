package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.enums.AccountStatus;
import com.ecsail.Gybe.repository.interfaces.AuthenticationRepository;
import com.ecsail.Gybe.repository.interfaces.EmailRepository;
import com.ecsail.Gybe.repository.interfaces.HashRepository;
import com.ecsail.Gybe.repository.interfaces.PersonRepository;
import com.ecsail.Gybe.service.interfaces.AdminService;
import com.ecsail.Gybe.service.interfaces.AuthenticationService;
import com.ecsail.Gybe.utils.ApiKeyGenerator;
import com.ecsail.Gybe.utils.ForgotPasswordHTML;
import com.ecsail.Gybe.wrappers.MailWrapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Enumeration;
import java.util.List;
import java.util.Set;

@Service
public class AdminServiceImpl implements AdminService {


    @Value("${app.url}")
    private String appURL;
    private final AuthenticationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final EmailRepository emailRepository;
    private final HashRepository hashRepository;
    private final PersonRepository personRepository;
    private final AuthenticationRepository authenticationRepository;

    @Autowired
    public AdminServiceImpl(HashRepository hashRepository,
                            EmailRepository emailRepository,
                            PersonRepository personRepository,
                            AuthenticationRepository authenticationRepository,
                            AuthenticationManager authenticationManager,
                            AuthenticationService authenticationService) {
        this.hashRepository = hashRepository;
        this.emailRepository = emailRepository;
        this.personRepository = personRepository;
        this.authenticationRepository = authenticationRepository;
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }

    @Override
    public List<FormHashRequestDTO> getFormRequests(int year) {
        return hashRepository.getFormHashRequests(year);
    }

    public List<FormRequestSummaryDTO> getFormSummaries(Integer year) {
        return hashRepository.getFormRequestSummariesForYear(year);
    }

    @Override
    public boolean isValidKey(String passKey) {
        return hashRepository.isValidKey(passKey);
    }

    // this checks if email is on the system.
    @Override
    public MailWrapper generateCredentialsEmail(String email) {
        MailWrapper mailWrapper = new MailWrapper();
        PersonDTO personDTO = personRepository.getPersonByEmail(email);
        // if email does not exist in system do something
        if (!emailRepository.emailExists(email)) {
            mailWrapper.setMessage(email + " was not found in the system");
            mailWrapper.setSendEmail(false);
        } // account already exists we are here to fix a forgotten password
        else if (authenticationRepository.existsByUsername(email)) {
            mailWrapper.setMailDTO(new MailDTO(email, "ECSC Password Reset", ""));
            String generatedLink = generateLink(personDTO, email, AccountStatus.EXISTING);
            mailWrapper.getMailDTO().setMessage(ForgotPasswordHTML.createEmail(generatedLink, AccountStatus.EXISTING));
            mailWrapper.setMessage(setEmailMessage(email, "password recovery link"));
        } else {   // account does not exist, we need to create one
            mailWrapper.setMailDTO(new MailDTO(email, "New Account Creation", ""));
            String generatedLink = generateLink(personDTO, email, AccountStatus.NEW_ACCOUNT);
            mailWrapper.getMailDTO().setMessage(ForgotPasswordHTML.createEmail(generatedLink, AccountStatus.NEW_ACCOUNT));
            mailWrapper.setMessage(setEmailMessage(email, "link to create an account"));
        }
        return mailWrapper;
    }


    @Override
    public void setUserPass(String key, String status, String email, String password) {
        // using spring security 6.2.3
        PersonDTO personDTO = personRepository.getPersonByEmail(email);
        String encodedPass = authenticationService.updatePassword(password);
        if (status.equals("EXISTING")) {
            // if the password updates with success add completed timestamp
            if (authenticationRepository.updatePassword(encodedPass, personDTO.getpId()) == 1) {
                // fill in the completed column
                hashRepository.completeUserAuthRequest(personDTO.getpId());
            }
        } else {
            UserDTO userDTO = authenticationRepository
                    .saveUser(new UserDTO(0, email, encodedPass, personDTO.getpId()));
            // We successfully added a user
            if (userDTO.getUserId() > 0) {
                // add role user
                // set correct user_aut-request to completed
            }
        }
        authenticateUser(email, password);
    }

//
//    private int userId;
//    private String username;
//    private String password;
//    private int pId;
//    private Set<RoleDTO> authorities;

    private void authenticateUser(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        System.out.println("Authentication: " + authentication);
        System.out.println("Authorities: " + authentication.getAuthorities());
        System.out.println("Authenticated: " + authentication.isAuthenticated());
        System.out.println("");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Get the current HTTP request
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        // Get the session from the request
        HttpSession session = attr.getRequest().getSession();
        String sessionId = session.getId();
        System.out.println("Session ID (setUserPass()): " + sessionId);
        System.out.println("Session attributes after setting authentication: " + session.getAttributeNames());
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            System.out.println("Session attribute: " + attributeName + " = " + session.getAttribute(attributeName));
        }
        authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication);
        System.out.println("Authorities: " + authentication.getAuthorities());
        System.out.println("Authenticated: " + authentication.isAuthenticated());
    }

    private String generateLink(PersonDTO personDTO, String email, AccountStatus accountStatus) {
        String baseUrl = appURL + "/update_creds";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        // make sure there is not a good key entry < 10 minutes old and also not completed
        if (!hashRepository.existsUserAuthRequestByPidWithinTenMinutes(personDTO.getpId())) {
            // create a key
            String key = ApiKeyGenerator.generateApiKey(32);
            // we need to create a fresh new entry by add a key and a pid
            hashRepository.insertUserAuthRequest(key, personDTO.getpId());
            // let's add the key and transaction type to our link
            builder.queryParam("key", key);
        } else {
            // a good key entry already exists, we should reset the update_at field and use the existing key
            hashRepository.updateUpdatedAtTimestamp(personDTO.getpId());
            // let's get the updated object
            UserAuthRequestDTO userAuthRequestDTO = hashRepository.findUserAuthRequestByPidWithinTenMinutes(personDTO.getpId());
            // let's add the key to our link
            builder.queryParam("key", userAuthRequestDTO.getPassKey());
        }
        builder.queryParam("status", accountStatus.toString());
        builder.queryParam("email", email);
        // if not create an entry
        return builder.toUriString();
    }

    private static String setEmailMessage(String email, String altText) {
        return "An email has been sent to " + email + " with a " + altText + " . " +
                "If you don't receive it shortly, please check your spam or junk folder. For any assistance, " +
                "feel free to <a href=\"mailto:register@ecsail.org?subject=Login%20Help\" style=\"color: #add8e6;\">" +
                "contact the administrator</a>\n.";
    }
}
