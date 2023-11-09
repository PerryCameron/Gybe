package com.ecsail.Gybe.service;


import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.repository.implementations.HashRepositoryImpl;
import com.ecsail.Gybe.repository.implementations.MembershipRepositoryImpl;
import com.ecsail.Gybe.repository.interfaces.HashRepository;
import com.ecsail.Gybe.repository.interfaces.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EmailService {

    private final HashRepository hashRepository;
    private final MembershipRepository membershipRepository;
    LinkBuilderService linkBuilder;

    @Autowired
    public EmailService(
            LinkBuilderService linkBuilder,
            HashRepositoryImpl hashRepository,
            MembershipRepositoryImpl membershipRepository) {
        this.linkBuilder = linkBuilder;
        this.hashRepository = hashRepository;
        this.membershipRepository = membershipRepository;
    }




// IMPLEMENT
//    public String buildLinkWithParameters(String hash) {
//        HashDTO hashDTO = hashRepository.getHashDTOFromHash(Long.valueOf(hash));
//        MembershipListDTO membershipListDTO = membershipRepository.getMembershipListFromMsidAndYear(new SimpleDateFormat("yyyy").format(new Date()));
//        String parameterLink = linkBuilder.createGetRequestWithParameters(
//                membershipListDTO, settingRepo.getFormSettings().getSelected_year());
//        SpinnakerPackagedApplication.logger.info(parameterLink);
//        // This DTO records a request for a jotform with filled out parameters
//        int id = genRepo.getNextAvailablePrimaryKey("form_request","form_id");
//        insertRepo.insertHashHistory(new FormRequestDTO(id,membershipListDTO.getF_name() + " "
//                + membershipListDTO.getL_name(),membershipListDTO.getMs_id(),true));
//        return parameterLink;
//    }

    /** This method is given an email address and uses that to pull up the relevant membership information,
     * It then takes that information and creates and HTML body for an email with a link to later be used
     * to open a Jotform. Essentially this is a simple way to validate who people are without making people
     * create a login.
     *
     * @param
     * @return a mailDTO that contains a nice HTML formatted email body with a link containing a hash which
     * corresponds to a membership. This hash is used in a link placed within an email to later open a
     * jotform populated with the data from the membership.
     */

    // IMPLEMENT
//    public MailDTO processEmailSubmission(AuthDTO authDTO) {
//        FormSettingsDTO fs = settingRepo.getFormSettings();
//        MailDTO mailDTO = null;
//        if (genRepo.emailFromActiveMembershipExists(authDTO.getEmail(), settingRepo.getFormSettings().getSelected_year())) {
//            // create a query builder
//            HttpUrl.Builder queryUrlBuilder = HttpUrl.get("https://"+fs.getLink()+":"+fs.getPort()+"/register").newBuilder();
//            // this fills the dto with correct values
//            objRepo.getAuthDTOFromEmail(authDTO);
//            // creates a new hash or loads an existing hash
//            HashDTO hashDTO = createHash(authDTO);
//            // create link
//            queryUrlBuilder.addQueryParameter("member", String.valueOf(hashDTO.getHash()));
//            // log it
//            SpinnakerPackagedApplication.logger.info("link created: " + queryUrlBuilder);
//            System.out.println(genRepo.getNextAvailablePrimaryKey("form_hash_request","form_hash_id"));
//            // this DTO will store a record of someone requesting a hash
//            int id = genRepo.getNextAvailablePrimaryKey("form_hash_request","form_hash_id");
//            insertRepo.insertHashRequestHistory(new FormHashRequestDTO(id,authDTO.getF_name()
//                    + " " + authDTO.getL_name(), String.valueOf(queryUrlBuilder),authDTO.getMs_id(),
//                    authDTO.getEmail()));
////             This adds the HTML body to the email
//            mailDTO = new MailDTO(authDTO.getEmail(),"ECSC Registration",
//                    RegisterHtml.createEmailWithHtml(authDTO.getF_name(),queryUrlBuilder.toString()));
//            // log to system
//            SpinnakerPackagedApplication.logger.info("Created Mail for: " + mailDTO.getRecipient());
//        } else {
//            authDTO.setExists(false);
//        }
//        return mailDTO;
//    }

    // IMPLEMENT
//    private HashDTO createHash(AuthDTO authDTO) {
//        HashDTO hashDTO;
//        // see if a hash already exists for this membership
//        if(genRepo.recordExists("form_msid_hash","MS_ID",authDTO.getMs_id())) {
//            // if it does exist we won't add another entry
//            hashDTO = objRepo.getHashDTOFromMsid(authDTO.getMs_id());
//            SpinnakerPackagedApplication.logger.info("Hash exists, no need to create. hash=" + hashDTO.getHash());
//        }
//        // it doesn't exist so we will create one
//        else {
//            // creates new hash as object
//            hashDTO = new HashDTO(genRepo.getNextAvailablePrimaryKey("form_msid_hash", "HASH_ID"), authDTO.getMs_id(), authDTO.getEmail());
//            // put our hash object into the database
//            hashRepo.insertHash(hashDTO);
//            SpinnakerPackagedApplication.logger.info("Created hash=" + hashDTO.getHash() + " for ms_id=" + hashDTO.getMs_id());
//        }
//        return hashDTO;
//    }

    // determines page to direct to and makes hash
//    public String returnCorrectPage(AuthDTO authDTO) {
//        if(authDTO.getExists())
//            authDTO.setHtmlPage("result");
//        else
//            authDTO.setHtmlPage("notfound");
//        return authDTO.getHtmlPage();
//    }
}
