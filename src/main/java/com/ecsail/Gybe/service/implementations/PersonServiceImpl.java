package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.dto.PhoneDTO;
import com.ecsail.Gybe.repository.interfaces.*;
import com.ecsail.Gybe.service.interfaces.PersonService;
import org.springframework.stereotype.Service;

import java.util.List;

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
        int success = personRepository.update(personDTO);
        return success > 0;
    }

    @Override
    public boolean batchUpdatePhones(List<PhoneDTO> phoneDTOList) {
        for(PhoneDTO phoneDTO : phoneDTOList) {
            System.out.println(phoneDTO);
        }
        return true;
    }
}
