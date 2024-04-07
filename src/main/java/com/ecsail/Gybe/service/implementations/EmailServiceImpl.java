package com.ecsail.Gybe.service.implementations;


import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.repository.interfaces.EmailRepository;
import com.ecsail.Gybe.repository.interfaces.GeneralRepository;
import com.ecsail.Gybe.repository.interfaces.HashRepository;
import com.ecsail.Gybe.service.interfaces.EmailService;
import com.ecsail.Gybe.service.interfaces.SettingsService;
import com.ecsail.Gybe.utils.RegisterHtml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Year;

@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final HashRepository hashRepository;
    private final EmailRepository emailRepository;
    private final GeneralRepository generalRepository;
    private final SettingsService settingsService;


    public EmailServiceImpl(HashRepository hashRepository,
                            EmailRepository emailRepository,
                            GeneralRepository generalRepository,
                            SettingsService settingsService) {
        this.hashRepository = hashRepository;
        this.emailRepository = emailRepository;
        this.generalRepository = generalRepository;
        this.settingsService = settingsService;
    }
    @Override
    public MailDTO sendPasswordReset(String email) {
        return new MailDTO(email,"ECSC Password Reset", "email body");
    }

    @Override
    public MailDTO processEmailSubmission(String email) {
        MailDTO mailDTO = null;
        if (emailRepository.emailFromActiveMembershipExists(email, settingsService.getSelectedYear())) {
            UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                    .scheme(settingsService.getScheme().getValue())
                    .host(settingsService.getHostName().getValue())
                    .path("/register");

            String port = settingsService.getAppPort().getValue();
            if (port != null && !port.isEmpty()) {
                builder.port(port);
            }
            // this fills the dto with correct values
            AuthDTO authDTO = emailRepository.getAuthDTOFromEmail(Year.now().getValue(), email);
            // creates a new hash or loads an existing hash
            HashDTO hashDTO = createHash(authDTO);
            // create link
            builder.queryParam("member", String.valueOf(hashDTO.getHash()));
            // log it
            logger.info("link created: " + builder.toUriString());
                hashRepository.insertHashRequestHistory(new FormHashRequestDTO(
                        0,
                        authDTO.getFullName(),
                        "register?member=" + hashDTO.getHash(),
                        authDTO.getMsId(),
                        authDTO.getEmail()));
            mailDTO = new MailDTO(authDTO.getEmail(),"ECSC Registration",
                    RegisterHtml.createEmailWithHtml(authDTO.getfName(), builder.toUriString(), settingsService));
            mailDTO.setAuthDTO(authDTO);
            logger.info("Created Mail for: " + mailDTO.getRecipient());
            mailDTO.getAuthDTO().setExists(true);
        }
        return mailDTO;
    }
    @Override
    public HashDTO createHash(AuthDTO authDTO) {
        HashDTO hashDTO;
        // see if a hash already exists for this membership
        if(generalRepository.recordExists("form_msid_hash","MS_ID", authDTO.getMsId())) {
            // if it does exist we won't add another entry
            hashDTO = hashRepository.getHashDTOFromMsid(authDTO.getMsId());
            logger.info("Hash exists, no need to create. hash=" + hashDTO.getHash());
        }
        // it doesn't exist so we will create one
        else {
            // creates new hash as object
            hashDTO = new HashDTO(0, authDTO.getMsId(), authDTO.getEmail());
            // put our hash object into the database
            hashRepository.insertHash(hashDTO);
            logger.info("Created hash=" + hashDTO.getHash() + " for ms_id=" + hashDTO.getMsId());
        }
        return hashDTO;
    }

}
