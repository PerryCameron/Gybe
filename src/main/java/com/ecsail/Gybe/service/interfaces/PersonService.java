package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.*;

import java.util.List;

public interface PersonService {
    boolean updatePerson(PersonDTO personDTO);
    int insertNewPhoneRow(PhoneDTO phoneDTO);
    int insertNewPositionRow(OfficerDTO officerDTO);
    int insertNewEmailRow(EmailDTO emailDTO);
    int insertNewAwardRow(AwardDTO awardDTO);
    int insertNewMembershipId(MembershipIdDTO membershipIdDTO);
    boolean deletePhoneRow(PhoneDTO phoneDTO);
    boolean deletePositionRow(OfficerDTO officerDTO);
    boolean deleteEmailRow(EmailDTO emailDTO);
    boolean deleteAwardRow(AwardDTO awardDTO);
    boolean deleteMembershipIdRow(MembershipIdDTO membershipIdDTO);
    boolean updateEmail(List<EmailDTO> emailDTOList);
    boolean updatePositions(List<OfficerDTO> officerDTOList);
    boolean updateAwards(List<AwardDTO> awardDTOS);
    boolean batchUpdatePhones(List<PhoneDTO> phoneDTOList);
    boolean updateMembershipIds(List<MembershipIdDTO> membershipIdDTOS);
    boolean updateAddress(MembershipDTO membershipDTO);
}
