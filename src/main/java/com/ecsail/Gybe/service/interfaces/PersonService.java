package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.OfficerDTO;
import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.dto.PhoneDTO;

import java.util.List;

public interface PersonService {
    boolean updatePerson(PersonDTO personDTO);
    boolean batchUpdatePhones(List<PhoneDTO> phoneDTOList);
    int insertNewPhoneRow(PhoneDTO phoneDTO);
    int insertNewPositionRow(OfficerDTO officerDTO);
    boolean deletePhoneRow(PhoneDTO phoneDTO);
    boolean deletePositionRow(OfficerDTO officerDTO);
}
