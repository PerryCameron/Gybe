package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.Email_InformationDTO;
import com.ecsail.Gybe.repository.interfaces.EmailRepository;
import com.ecsail.Gybe.service.interfaces.XLSService;
import com.ecsail.Gybe.utils.Xls_email_list;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class XLSServiceImpl implements XLSService {

    private final EmailRepository emailRepository;
    @Autowired
    public XLSServiceImpl(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @Override
    public void createEmailList() {
        ArrayList<Email_InformationDTO> emailInformationDTOS =
                (ArrayList<Email_InformationDTO>) emailRepository.getEmailInfo();
        Xls_email_list.createSpreadSheet(emailInformationDTOS);
    }



}
