package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.dto.PhoneDTO;

import java.util.List;

public interface PersonService {
    boolean updatePerson(PersonDTO personDTO);
    boolean batchUpdatePhones(List<PhoneDTO> phoneDTOList);
    int insertNewPhoneRow(PhoneDTO phoneDTO);
    boolean deletePhoneRow(PhoneDTO phoneDTO);
}
