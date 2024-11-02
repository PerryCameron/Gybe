package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.repository.interfaces.*;
import com.ecsail.Gybe.service.interfaces.PersonService;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final EmailRepository emailRepository;
    private final GeneralRepository generalRepository;

    public PersonServiceImpl(HashRepository hashRepository,
                             EmailRepository emailRepository,
                             PersonRepository personRepository,
                             GeneralRepository generalRepository) {
        this.personRepository = personRepository;
        this.emailRepository = emailRepository;
        this.generalRepository = generalRepository;
    }

    @Override
    public boolean updatePerson(PersonDTO personDTO) {
        return false;
    }
}
