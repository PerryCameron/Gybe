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

    public boolean updatePerson(PersonDTO personDTO) {
        System.out.println(personDTO);
//        // Check which fields are non-null and update only those fields
//        Optional<Person> personOptional = personRepository.findById(personDTO.getpId());
//        if (personOptional.isPresent()) {
//            Person person = personOptional.get();
//
//            if (personDTO.getFirstName() != null) person.setFirstName(personDTO.getFirstName());
//            if (personDTO.getLastName() != null) person.setLastName(personDTO.getLastName());
//            if (personDTO.getNickName() != null) person.setNickName(personDTO.getNickName());
//            if (personDTO.getOccupation() != null) person.setOccupation(personDTO.getOccupation());
//            if (personDTO.getBusiness() != null) person.setBusiness(personDTO.getBusiness());
//            if (personDTO.getBirthday() != null) person.setBirthday(personDTO.getBirthday());
//
//            personRepository.save(person);
//            return true;
//        }
        return true;
    }
}
