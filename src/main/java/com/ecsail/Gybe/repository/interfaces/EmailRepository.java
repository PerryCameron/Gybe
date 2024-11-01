package com.ecsail.Gybe.repository.interfaces;

import com.ecsail.Gybe.dto.AuthDTO;
import com.ecsail.Gybe.dto.EmailDTO;
import com.ecsail.Gybe.dto.Email_InformationDTO;
import com.ecsail.Gybe.dto.PersonDTO;

import java.util.List;

public interface EmailRepository {
    List<Email_InformationDTO> getEmailInfo();
    List<EmailDTO> getEmail(int p_id);
    EmailDTO getPrimaryEmail(PersonDTO person);
    int update(EmailDTO o);
    int insert(EmailDTO emailDTO);
    int delete(EmailDTO o);
    boolean emailFromActiveMembershipExists(String email, int year);
    AuthDTO getAuthDTOFromEmail(int year, String email);
    void updateAuthDTOFromEmail(int year, AuthDTO authDTO);
    boolean emailExists(String email);
    int batchUpdate(List<EmailDTO> emailDTOList);
}
