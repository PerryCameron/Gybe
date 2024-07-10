package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.AppSettingsDTO;
import com.ecsail.Gybe.dto.BoardPositionDTO;
import com.ecsail.Gybe.dto.CommodoreMessageDTO;
import com.ecsail.Gybe.dto.MembershipInfoDTO;
import com.ecsail.Gybe.pdf.directory.PDF_Directory;
import com.ecsail.Gybe.repository.interfaces.*;
import com.ecsail.Gybe.service.interfaces.PDFService;
import com.ecsail.Gybe.wrappers.DirectoryDataWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
public class PDFServiceImpl implements PDFService {

    private final MembershipRepository membershipRepository;
    private final PersonRepository personRepository;
    private final BoatRepository boatRepository;
    private final MembershipIdRepository membershipIdRepository;
    private final PhoneRepository phoneRepository;
    private final EmailRepository emailRepository;
    private final AwardRepository awardRepository;
    private final OfficerRepository officerRepository;
    private final NotesRepository notesRepository;
    private final BoardPositionsRepository boardPositionsRepository;
    private final SettingsRepository settingsRepository;
    private static final Logger logger = LoggerFactory.getLogger(PDFServiceImpl.class);

    @Autowired
    public PDFServiceImpl(
            MembershipRepository membershipRepository,
            PersonRepository personRepository,
            BoatRepository boatRepository,
            MembershipIdRepository membershipIdRepository,
            PhoneRepository phoneRepository,
            EmailRepository emailRepository,
            AwardRepository awardRepository,
            OfficerRepository officerRepository,
            NotesRepository notesRepository,
            BoardPositionsRepository boardPositionsRepository,
            SettingsRepository settingsRepository
    ) {
        this.membershipRepository = membershipRepository;
        this.personRepository = personRepository;
        this.boatRepository = boatRepository;
        this.membershipIdRepository = membershipIdRepository;
        this.phoneRepository = phoneRepository;
        this.emailRepository = emailRepository;
        this.awardRepository = awardRepository;
        this.officerRepository = officerRepository;
        this.notesRepository = notesRepository;
        this.boardPositionsRepository = boardPositionsRepository;
        this.settingsRepository = settingsRepository;
    }

    @Override
    public void createDirectory(List<JsonNode> memberships) {
        DirectoryDataWrapper directoryData = new DirectoryDataWrapper();
        directoryData.setMembershipInfoDTOS(convertJSONToPOJO(memberships));
        directoryData.setCommodoreMessage(personRepository.getCommodoreMessageByYear(Year.now().getValue()));
        directoryData.setPositionData((ArrayList<BoardPositionDTO>) boardPositionsRepository.getPositions());
        directoryData.setAppSettingsDTOS((ArrayList<AppSettingsDTO>) settingsRepository.getAppSettingsByGroupName("directory"));
        new PDF_Directory(directoryData);
    }

    private ArrayList<MembershipInfoDTO> convertJSONToPOJO(List<JsonNode> memberships) {
        ArrayList<MembershipInfoDTO> membershipInfos = new ArrayList<>();
        try {
            // Assuming 'memberships' is a JsonNode that contains an array of membership JSON objects
            ObjectMapper objectMapper = new ObjectMapper();
                    // Convert JsonNode to a List of MembershipInfoDTO
                for (JsonNode node : memberships) {
                    MembershipInfoDTO membershipInfo = objectMapper.treeToValue(node, MembershipInfoDTO.class);
                    membershipInfos.add(membershipInfo);
                }
            // Do something with the membershipInfos list
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return membershipInfos;
    }
}
