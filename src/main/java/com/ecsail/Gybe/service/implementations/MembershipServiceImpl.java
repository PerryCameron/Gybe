package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.repository.interfaces.*;
import com.ecsail.Gybe.service.interfaces.MembershipService;
import com.ecsail.Gybe.wrappers.BoardOfDirectorsResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MembershipServiceImpl implements MembershipService {
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
    private static final Logger logger = LoggerFactory.getLogger(MembershipServiceImpl.class);



    @Autowired
    public MembershipServiceImpl(
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
    public MembershipListDTO getMembership(int msId, int selectedYear) {
        try {
            MembershipListDTO membership = membershipRepository.getMembershipByMsId(msId, selectedYear);
            membership.setPersonDTOS((ArrayList<PersonDTO>) personRepository.getActivePeopleByMsId(msId));
            System.out.println("List size: " + membership.getPersonDTOS().size());
            membership.getPersonDTOS().forEach(personDTO -> {
                personDTO.setPhones(phoneRepository.getPhoneByPid(personDTO.getPId()));
                personDTO.setEmails(emailRepository.getEmail(personDTO.getPId()));
                System.out.println("Number of Emails: " + personDTO.getEmails().size());
                personDTO.setAwards(awardRepository.getAwards(personDTO));
                personDTO.setOfficers(officerRepository.getOfficer(personDTO));
            });
            membership.setBoatDTOS((ArrayList<BoatDTO>) boatRepository.getBoatsByMsId(msId));
            membership.setMembershipIdDTOS((ArrayList<MembershipIdDTO>) membershipIdRepository.getIds(msId));
            membership.setNotesDTOS((ArrayList<NotesDTO>) notesRepository.getMemosByMsId(msId));
            return membership;
        } catch (Exception e) {
            System.out.println(e);
            logger.error(e.getMessage());
        }
        return null;
    }
    @Override
    public List<BoardPositionDTO> getBoardPositions() {
        return boardPositionsRepository.getPositions();
    }

    @Override
    public List<LeadershipDTO> getLeaderShip(int year) {
        return officerRepository.getLeadershipByYear(year);
    }
    @Override
    public ThemeDTO getThemeByYear(Integer year) {
        return settingsRepository.findThemeByYear(year);
    }
    @Override
    public BoardOfDirectorsResponse getBodResponse(int year) {
        BoardOfDirectorsResponse bodResponse = new BoardOfDirectorsResponse();
        bodResponse.setYear(year);
        bodResponse.setLeadership(officerRepository.getLeadershipByYear(year));
        bodResponse.setTheme(settingsRepository.findThemeByYear(year));
        return bodResponse;
    }

    @Override
    public List<LeadershipDTO> getLeadershipByYear(int year) {
        return officerRepository.getLeadershipByYear(year);
    }

    @Override
    public List<JsonNode> getMembershipListAsJson() {
        return membershipRepository.getMembershipsAsJson();
    }

    @Override
    public JsonNode getMembershipAsJson(int msId, int selectedYear) {
        return membershipRepository.getMembershipAsJSON(msId, selectedYear);
    }


}
