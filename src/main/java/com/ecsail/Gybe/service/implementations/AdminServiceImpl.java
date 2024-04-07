package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.FormHashRequestDTO;
import com.ecsail.Gybe.dto.FormRequestSummaryDTO;
import com.ecsail.Gybe.dto.PersonDTO;
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

    @Autowired
    public AdminServiceImpl(HashRepository hashRepository, EmailRepository emailRepository, PersonRepository personRepository) {
        this.hashRepository = hashRepository;
        this.emailRepository = emailRepository;
        this.personRepository = personRepository;
    }
    @Override
    public List<FormHashRequestDTO> getFormRequests(int year) {
        return hashRepository.getFormHashRequests(year);
    }

    public List<FormRequestSummaryDTO> getFormSummaries(Integer year) {
        return hashRepository.getFormRequestSummariesForYear(year);
    }
    @Override
    public PersonDTO getPersonByEmail(String email) {
        return personRepository.getPersonByEmail(email);
    }
    
}
