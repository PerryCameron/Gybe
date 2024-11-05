package com.ecsail.Gybe.repository.interfaces;

import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.dto.PhoneDTO;

import java.util.List;

public interface PhoneRepository {
    List<PhoneDTO> getPhoneByPid(int p_id);
    List<PhoneDTO> getPhoneByPerson(PersonDTO p);
    PhoneDTO getListedPhoneByType(PersonDTO p, String type);
    PhoneDTO getPhoneByPersonAndType(int pId, String type);
    int update(PhoneDTO o);
    int delete(PhoneDTO o);
    int insert(PhoneDTO o);
    int batchUpdate(List<PhoneDTO> phoneDTOList);
}
