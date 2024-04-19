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
import com.ecsail.Gybe.utils.PasswordValidator;
import com.ecsail.Gybe.wrappers.MailWrapper;
import com.ecsail.Gybe.wrappers.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


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
    public static Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

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
    public MessageResponse setUserPass(String key, String status, String email, String password1, String password2) {
        MessageResponse messageResponse = new MessageResponse();
        // using spring security 6.2.3
        if(!PasswordValidator.validatePassword(password1,password2)) {
            // if password failed this will give us a more refined message
            messageResponse.setMessage(PasswordValidator.passwordError(password1,password2));
            return messageResponse;
        }
        PersonDTO personDTO = personRepository.getPersonByEmail(email);
        String encodedPass = authenticationService.updatePassword(password1);
        if (status.equals("EXISTING")) {
            updateUserPassword(email, messageResponse, personDTO, encodedPass);
        } else { // This is a new account
            UserDTO userDTO = authenticationRepository
                    .saveUser(new UserDTO(0, email, encodedPass, personDTO.getpId()));
            // We successfully added a user
            if (userDTO.getUserId() > 0) {
                // add role user
                RoleDTO userRole = authenticationRepository.findByAuthority("ROLE_USER").get();
                authenticationRepository.saveUserRole(userDTO,userRole);
                // set correct user_aut-request to completed
                hashRepository.completeUserAuthRequest(personDTO.getpId());
                messageResponse.setSuccess(true);
                messageResponse.setMessage("Account created: Please log in for the first time.");
            } else messageResponse.setMessage("FAIL");
        }
        return messageResponse;
    }

    private void updateUserPassword(String email, MessageResponse messageResponse, PersonDTO personDTO, String encodedPass) {
        try {
            // Attempt to update the password
            int updateCount = authenticationRepository.updatePassword(encodedPass, personDTO.getpId());
            if (updateCount == 1) {
                // If the password updates successfully, add completed timestamp
                hashRepository.completeUserAuthRequest(personDTO.getpId());
                messageResponse.setSuccess(true);
                messageResponse.setMessage("You have successfully changed your password. Please log in.");
            } else {
                // If no rows were updated, handle accordingly
                messageResponse.setSuccess(false);
                messageResponse.setMessage("No matching record found: password not updated.");
                logger.info("During an attempt to update password for " + email
                        + " the system was unable to secure a record with a matching P_ID. ");
            }
        } catch (Exception e) {
            // Log the exception and set the failure message
            logger.error("Failed to update password for p_id: " + personDTO.getpId(), e);
            messageResponse.setSuccess(false);
            messageResponse.setMessage("Unable to update password due to an error.");
        }
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
