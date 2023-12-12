package com.ecsail.Gybe.repository.interfaces;

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
}
