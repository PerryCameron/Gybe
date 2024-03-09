package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.repository.interfaces.*;
import com.ecsail.Gybe.service.interfaces.MembershipService;
import com.ecsail.Gybe.wrappers.BoardOfDirectorsResponse;
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
        MembershipListDTO membership = membershipRepository.getMembershipByMsId(msId, selectedYear);
        membership.setPersonDTOS((ArrayList<PersonDTO>) personRepository.getActivePeopleByMsId(msId));
        membership.getPersonDTOS().forEach(personDTO -> {
            personDTO.setPhones((ArrayList<PhoneDTO>) phoneRepository.getPhoneByPid(personDTO.getpId()));
            personDTO.setEmail((ArrayList<EmailDTO>) emailRepository.getEmail(personDTO.getpId()));
            personDTO.setAwards((ArrayList<AwardDTO>) awardRepository.getAwards(personDTO));
            personDTO.setOfficer((ArrayList<OfficerDTO>) officerRepository.getOfficer(personDTO));
        });
        membership.setBoatDTOS((ArrayList<BoatDTO>) boatRepository.getBoatsByMsId(msId));
        membership.setMembershipIdDTOS((ArrayList<MembershipIdDTO>) membershipIdRepository.getIds(msId));
        membership.setNotesDTOS((ArrayList<NotesDTO>) notesRepository.getMemosByMsId(msId));
        return membership;
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
    public ThemeDTO getTheme(Integer year) {
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


}
