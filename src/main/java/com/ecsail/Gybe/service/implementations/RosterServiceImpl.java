package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.MembershipListDTO;
import com.ecsail.Gybe.repository.interfaces.MembershipRepository;
import com.ecsail.Gybe.service.interfaces.RosterService;
import com.ecsail.Gybe.wrappers.RosterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RosterServiceImpl implements RosterService {

    private final MembershipRepository membershipRepository;

    @Autowired
    public RosterServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    public RosterResponse getDefaultRosterResponse() {
        RosterResponse rosterResponse = new RosterResponse();
        rosterResponse.setMembershipListDTOS(membershipRepository.getRoster(LocalDate.now().getYear(), true));
        rosterResponse.setRosterType("active");
        rosterResponse.setYear(LocalDate.now().getYear());
        return rosterResponse;
    }

    @Override
    public List<MembershipListDTO> getRoster(int year, String rosterType, String sort, List<String> searchParams) {
        if (!searchParams.isEmpty())
            rosterType = "search";
        List<MembershipListDTO> membershipList = getRosterType(year, rosterType, searchParams);
        return membershipList;
    }

    @Override
    public List<MembershipListDTO> getRosterType(int year, String rosterType, List<String> searchParams) {
        if (rosterType.equals("active")) {
            return membershipRepository.getRoster(year, true);
        } else if (rosterType.equals("non_renew")) {
            return membershipRepository.getRoster(year, false);
        } else if (rosterType.equals("all")) {
            return membershipRepository.getRosterOfAll(year);
        } else if (rosterType.equals("new_members")) {
            return membershipRepository.getNewMemberRoster(year);
        } else if (rosterType.equals("return_members")) {
            return membershipRepository.getReturnMembers(year);
        } else if (rosterType.equals("slip_wait_list")) {
            return membershipRepository.getSlipWaitList();
        } else if (rosterType.equals("search")) {
            return membershipRepository.getSearchRoster(searchParams);
        }
        return null;
    }

    @Override
    public List<MembershipListDTO> getSlipWait() {
        List<MembershipListDTO> waitList = membershipRepository.getSlipWaitList();
        Collections.sort(waitList, Comparator.comparing(MembershipListDTO::getMembershipId));
        return waitList;
    }

    @Override
    public RosterResponse getRosterResponse(String type, Integer year, Map<String, String> allParams) {
        RosterResponse rosterResponse = new RosterResponse();
        List<String> searchParams = allParams.entrySet().stream()
                .filter(e -> e.getKey().startsWith("param"))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        rosterResponse.setMembershipListDTOS(getRosterType(year, type, searchParams));
        rosterResponse.setRosterType(type);
        rosterResponse.setYear(year);
        return rosterResponse;
    }


}
