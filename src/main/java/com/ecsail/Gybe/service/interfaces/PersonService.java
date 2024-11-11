package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.*;

import java.util.List;

public interface PersonService {
    boolean updatePerson(PersonDTO personDTO);
    int insertNewPhoneRow(PhoneDTO phoneDTO);
    int insertNewPositionRow(OfficerDTO officerDTO);
    int insertNewEmailRow(EmailDTO emailDTO);
    int insertNewAwardRow(AwardDTO awardDTO);
    boolean deletePhoneRow(PhoneDTO phoneDTO);
    boolean deletePositionRow(OfficerDTO officerDTO);
    boolean deleteEmailRow(EmailDTO emailDTO);
    boolean deleteAwardRow(AwardDTO awardDTO);
    boolean batchUpdateEmail(List<EmailDTO> emailDTOList);
    boolean batchUpdatePosition(List<OfficerDTO> officerDTOList);
    boolean batchUpdateAwards(List<AwardDTO> awardDTOS);
    boolean batchUpdatePhones(List<PhoneDTO> phoneDTOList);
}
