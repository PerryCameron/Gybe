package com.ecsail.Gybe.service.implementations;


import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.repository.interfaces.EmailRepository;
import com.ecsail.Gybe.repository.interfaces.GeneralRepository;
import com.ecsail.Gybe.repository.interfaces.HashRepository;
import com.ecsail.Gybe.service.interfaces.EmailService;
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

    public EmailServiceImpl(HashRepository hashRepository,
                            EmailRepository emailRepository,
                            GeneralRepository generalRepository) {
        this.hashRepository = hashRepository;
        this.emailRepository = emailRepository;
        this.generalRepository = generalRepository;
    }
    @Override
    public MailDTO processEmailSubmission(String email) {
        FormSettingsDTO fs = hashRepository.getFormSettings();
        MailDTO mailDTO = null;
        if (emailRepository.emailFromActiveMembershipExists(email, hashRepository.getFormSettings().getSelected_year())) {
            // create a query builder
            UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host(fs.getLink())
//                    .port(fs.getPort())
                    .path("/register");
            // this fills the dto with correct values
            AuthDTO authDTO = emailRepository.getAuthDTOFromEmail(Year.now().getValue(), email);
            // creates a new hash or loads an existing hash
            System.out.println(authDTO);
            HashDTO hashDTO = createHash(authDTO);
            // create link
            builder.queryParam("member", String.valueOf(hashDTO.getHash()));
            // log it
            logger.info("link created: " + builder.toUriString());
            // this DTO will store a record of someone requesting a hash
//            int id = genRepo.getNextAvailablePrimaryKey("form_hash_request","form_hash_id");
            hashRepository.insertHashRequestHistory(new FormHashRequestDTO(0,authDTO.getfName()
                    + " " + authDTO.getlName(), builder.toUriString(),authDTO.getMsId(),
                    authDTO.getEmail()));
//             This adds the HTML body to the email
            mailDTO = new MailDTO(authDTO.getEmail(),"ECSC Registration","");
            mailDTO.setAuthDTO(authDTO);
            System.out.println(mailDTO);
            System.out.println(mailDTO.getAuthDTO());
//                    RegisterHtml.createEmailWithHtml(authDTO.getfName(),queryUrlBuilder.toString()));
            // log to system
//            logger.info("Created Mail for: " + mailDTO.getRecipient());
            mailDTO.getAuthDTO().setExists(true);
        } else {
            mailDTO.getAuthDTO().setExists(false);
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
    @Override
    public String returnCorrectPage(MailDTO mailDTO) {
        if (mailDTO.getAuthDTO() != null && Boolean.TRUE.equals(mailDTO.getAuthDTO().getExists())) {
            return "result";
        } else {
            return "notfound";
        }
    }

    private boolean emailExists(String email) {
        // Implement logic to check if email exists
        // This could involve a database query or other checks
        return true; // Placeholder for actual implementation
    }
}
