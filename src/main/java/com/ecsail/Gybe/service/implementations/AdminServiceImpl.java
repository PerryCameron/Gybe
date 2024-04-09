package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.enums.AccountStatus;
import com.ecsail.Gybe.repository.interfaces.AuthenticationRepository;
import com.ecsail.Gybe.repository.interfaces.EmailRepository;
import com.ecsail.Gybe.repository.interfaces.HashRepository;
import com.ecsail.Gybe.repository.interfaces.PersonRepository;
import com.ecsail.Gybe.service.interfaces.AdminService;
import com.ecsail.Gybe.utils.ApiKeyGenerator;
import com.ecsail.Gybe.utils.ForgotPasswordHTML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Value("${app.url}")
    private String appURL;

    private final EmailRepository emailRepository;
    private final HashRepository hashRepository;
    private final PersonRepository personRepository;
    private final AuthenticationRepository authenticationRepository;

    @Autowired
    public AdminServiceImpl(HashRepository hashRepository,
                            EmailRepository emailRepository,
                            PersonRepository personRepository,
                            AuthenticationRepository authenticationRepository) {
        this.hashRepository = hashRepository;
        this.emailRepository = emailRepository;
        this.personRepository = personRepository;
        this.authenticationRepository = authenticationRepository;
    }
    @Override
    public List<FormHashRequestDTO> getFormRequests(int year) {
        return hashRepository.getFormHashRequests(year);
    }

    public List<FormRequestSummaryDTO> getFormSummaries(Integer year) {
        return hashRepository.getFormRequestSummariesForYear(year);
    }
    @Override
    public MailDTO generateCredentialsEmail(String email) {
        MailDTO mailDTO;
        PersonDTO personDTO = personRepository.getPersonByEmail(email);
        if(authenticationRepository.existsByUsername(email)) {
            // account exists, email about making a new password
            mailDTO = new MailDTO(email,"ECSC Password Reset", "");
            mailDTO.setMessage(ForgotPasswordHTML.createEmail(generateLink(personDTO,AccountStatus.EXISTING)));
        } else {
            // account does not exist, we need to create one
            mailDTO = new MailDTO(email,"New Account", "I heard you want to create an account");
        }
        return mailDTO; // this will need to change
    }

    private String generateLink(PersonDTO personDTO, AccountStatus accountStatus) {
        String baseUrl = appURL + "/update_creds";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        // make sure there is not a good key entry < 10 minutes old
        if(!hashRepository.existsUserAuthRequestByPidWithinTenMinutes(personDTO.getpId())) {
            // create a key
            String key = ApiKeyGenerator.generateApiKey(32);
            // we need to create a fresh new entry by add a key and a pid
            hashRepository.insertUserAuthRequest(key,personDTO.getpId());
            // let's add the key and transaction type to our link
            builder.queryParam("key",key);
        } else {
            // a good key entry already exists, we should reset the update_at field and use the existing key
            hashRepository.updateUpdatedAtTimestamp(personDTO.getpId());
            // let's get the updated object
            UserAuthRequestDTO userAuthRequestDTO = hashRepository.findUserAuthRequestByPidWithinTenMinutes(personDTO.getpId());
            // let's add the key to our link
            builder.queryParam("key",userAuthRequestDTO.getPassKey());
        }
        builder.queryParam("status",accountStatus.toString());
        // if not create an entry
        return builder.toUriString();
    }
    
}
