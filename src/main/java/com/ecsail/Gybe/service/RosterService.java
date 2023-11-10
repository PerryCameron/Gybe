package com.ecsail.Gybe.service;

import com.ecsail.Gybe.dto.MembershipListDTO;
import com.ecsail.Gybe.repository.interfaces.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class RosterService {

    private final MembershipRepository membershipRepository;

    @Autowired
    public RosterService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    public List<MembershipListDTO> getRoster(int year, String rosterType, String sort, List<String> searchParams) {
        List<MembershipListDTO> membershipList = getRosterType(year, rosterType);
        sortList(sort, membershipList);
        if(searchParams.isEmpty())
            System.out.println("There are no search params");
        else
            searchParams.forEach(System.out::println);
        return membershipList;
    }

    private List<MembershipListDTO> getRosterType(int year, String rosterType) {
        List<MembershipListDTO> membershipList = null;
        if(rosterType.equals("active")) {
            membershipList = membershipRepository.getRoster(year, true);
        } else if (rosterType.equals("non-renew")) {
            membershipList = membershipRepository.getRoster(year, false);
        } else if (rosterType.equals("all")) {
            membershipList = membershipRepository.getRosterOfAll(year);
        } else if (rosterType.equals("new")) {
            membershipList = membershipRepository.getNewMemberRoster(year);
        } else if (rosterType.equals("return")) {
            membershipList = membershipRepository.getReturnMembers(year);
        } else if (rosterType.equals("slip")) {
            membershipList = membershipRepository.getSlipWaitList(year);
        }
        return membershipList;
    }

    private static void sortList(String sort, List<MembershipListDTO> membershipList) {
        if(sort.equals("fname")) {
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
