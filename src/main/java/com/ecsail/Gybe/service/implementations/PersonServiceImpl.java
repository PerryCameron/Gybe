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
    private final PhoneRepository phoneRepository;

    public PersonServiceImpl(HashRepository hashRepository,
                             EmailRepository emailRepository,
                             PersonRepository personRepository,
                             GeneralRepository generalRepository,
                             PhoneRepository phoneRepository) {
        this.personRepository = personRepository;
        this.emailRepository = emailRepository;
        this.generalRepository = generalRepository;
        this.phoneRepository = phoneRepository;
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
        int result = 0;
        if(phoneDTOList.size() == 1) result = phoneRepository.update(phoneDTOList.get(0));
        else if(phoneDTOList.size() > 1) result = phoneRepository.batchUpdate(phoneDTOList);
        return result > 0;
    }

    @Override
    public int insertNewPhoneRow(PhoneDTO phoneDTO) {
        return phoneRepository.insert(phoneDTO);
    }

    @Override
    public boolean deletePhoneRow(PhoneDTO phoneDTO) {
        int success = phoneRepository.delete(phoneDTO);
        if (success > 0) return true;
        return false;
    }


}
