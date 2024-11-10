package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.EmailDTO;
import com.ecsail.Gybe.dto.OfficerDTO;
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
    private final OfficerRepository officerRepository;

    public PersonServiceImpl(HashRepository hashRepository,
                             EmailRepository emailRepository,
                             PersonRepository personRepository,
                             GeneralRepository generalRepository,
                             PhoneRepository phoneRepository,
                             OfficerRepository officerRepository) {
        this.personRepository = personRepository;
        this.emailRepository = emailRepository;
        this.generalRepository = generalRepository;
        this.phoneRepository = phoneRepository;
        this.officerRepository = officerRepository;
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
        if(phoneDTOList.size() == 1) {
            result = phoneRepository.update(phoneDTOList.get(0));
            if(result == 1) {
                System.out.println("Successfully updated phone");
            }
        }
        else if(phoneDTOList.size() > 1) {
            result = phoneRepository.batchUpdate(phoneDTOList);
            if(result == 1) {
                System.out.println("Successfully updated phone batch");
            }
        }
        return result > 0;
    }

    @Override
    public int insertNewPhoneRow(PhoneDTO phoneDTO) {
        return phoneRepository.insert(phoneDTO);
    }

    @Override
    public int insertNewPositionRow(OfficerDTO officerDTO) {
        return officerRepository.insert(officerDTO);
    }

    @Override
    public int insertNewEmailRow(EmailDTO emailDTO) {
        return emailRepository.insert(emailDTO);
    }

    @Override
    public boolean deletePhoneRow(PhoneDTO phoneDTO) {
        int success = phoneRepository.delete(phoneDTO);
        if (success > 0) return true;
        return false;
    }

    @Override
    public boolean deletePositionRow(OfficerDTO officerDTO) {
        int success = officerRepository.delete(officerDTO);
        if (success > 0) return true;
        return false;
    }

    @Override
    public boolean deleteEmailRow(EmailDTO emailDTO) {
        int result = emailRepository.delete(emailDTO);
        return result > 0; // Returns true if the row was successfully deleted, false otherwise
    }

    @Override
    public boolean batchUpdateEmail(List<EmailDTO> emailDTOList) {
        int result = 0;
        if(emailDTOList.size() == 1) result = emailRepository.update(emailDTOList.get(0));
        else if(emailDTOList.size() > 1) result = emailRepository.batchUpdate(emailDTOList);
        return result > 0;
    }

    @Override
    public boolean batchUpdatePosition(List<OfficerDTO> officerDTOList) {
        int result = 0;
        if(officerDTOList.size() == 1) result = officerRepository.update(officerDTOList.get(0));
        else if(officerDTOList.size() > 1) result = officerRepository.batchUpdate(officerDTOList);
        return result > 0;
    }
}
