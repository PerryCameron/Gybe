package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.MembershipListDTO;
import com.ecsail.Gybe.repository.interfaces.MembershipRepository;
import com.ecsail.Gybe.service.interfaces.RosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class RosterServiceImpl implements RosterService {

    private final MembershipRepository membershipRepository;

    @Autowired
    public RosterServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    public List<MembershipListDTO> getRoster(int year, String rosterType, String sort, List<String> searchParams) {
        if (!searchParams.isEmpty())
            rosterType = "search";
        List<MembershipListDTO> membershipList = getRosterType(year, rosterType, searchParams);
        sortList(sort, membershipList);
        return membershipList;
    }

    @Override
    public List<MembershipListDTO> getRoster() {
        List<MembershipListDTO> membershipList = membershipRepository.getRoster(LocalDate.now().getYear(), true);
        return membershipList;
    }

    @Override
    public List<MembershipListDTO> getRosterType(int year, String rosterType, List<String> searchParams) {
        if (rosterType.equals("active")) {
            return membershipRepository.getRoster(year, true);
        } else if (rosterType.equals("non-renew")) {
            return membershipRepository.getRoster(year, false);
        } else if (rosterType.equals("all")) {
            return membershipRepository.getRosterOfAll(year);
        } else if (rosterType.equals("new")) {
            return membershipRepository.getNewMemberRoster(year);
        } else if (rosterType.equals("return")) {
            return membershipRepository.getReturnMembers(year);
        } else if (rosterType.equals("slip")) {
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

    private static void sortList(String sort, List<MembershipListDTO> membershipList) {
        if (sort.equals("fname")) {
            Collections.sort(membershipList, Comparator.comparing(MembershipListDTO::getFirstName));
        } else if (sort.equals("byId")) {
            Collections.sort(membershipList, Comparator.comparing(MembershipListDTO::getMembershipId));
        } else if (sort.equals("lname")) {
            Collections.sort(membershipList, Comparator.comparing(MembershipListDTO::getLastName));
        } else if (sort.equals("date")) {
            Collections.sort(membershipList, Comparator.comparing(MembershipListDTO::getJoinDate));
        } else if (sort.equals("Type")) {
            Collections.sort(membershipList, Comparator.comparing(MembershipListDTO::getMemType));
        } else if (sort.equals("adrs")) {
            Collections.sort(membershipList, Comparator.comparing(MembershipListDTO::getAddress));
        } else if (sort.equals("city")) {
            Collections.sort(membershipList, Comparator.comparing(MembershipListDTO::getCity));
        } else if (sort.equals("state")) {
            Collections.sort(membershipList, Comparator.comparing(MembershipListDTO::getState));
        } else if (sort.equals("zip")) {
            Collections.sort(membershipList, Comparator.comparing(MembershipListDTO::getZip));
        }
    }
}
