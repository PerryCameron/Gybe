package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.FormHashRequestDTO;
import com.ecsail.Gybe.dto.FormRequestSummaryDTO;
import com.ecsail.Gybe.dto.MailDTO;
import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.repository.interfaces.AuthenticationRepository;
import com.ecsail.Gybe.repository.interfaces.EmailRepository;
import com.ecsail.Gybe.repository.interfaces.HashRepository;
import com.ecsail.Gybe.repository.interfaces.PersonRepository;
import com.ecsail.Gybe.service.interfaces.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

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
    public MailDTO getPersonByEmail(String email) {
        MailDTO mailDTO = null;
        PersonDTO personDTO = personRepository.getPersonByEmail(email);
        if(authenticationRepository.existsByUsername(email)) {
            // account exists, email about making a new password
            mailDTO = new MailDTO(email,"ECSC Password Reset", "I heard you want to change your password");
            System.out.println("Account Exists: " + email);
        } else {
            // account does not exist, we need to create one
            mailDTO = new MailDTO(email,"New Account", "I heard you want to create an account");
            System.out.println("Account does not exist: " + email);
        }
        return mailDTO; // this will need to change
    }
    
}
