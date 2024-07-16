package com.ecsail.Gybe.pdf.tools;

import com.ecsail.Gybe.dto.MembershipInfoDTO;
import com.ecsail.Gybe.dto.PersonDTO;
import com.ecsail.Gybe.dto.SlipPlacementDTO;

import java.util.ArrayList;
import java.util.Comparator;

public class PdfSort {
    public static void sortMembershipsByLastName(ArrayList<MembershipInfoDTO> memberships) {
        Comparator<MembershipInfoDTO> comparator = (m1, m2) -> {
            String lastName1 = m1.getPeople().stream()
                    .filter(person -> person.getMemberType() == 1)
                    .map(PersonDTO::getLastName)
                    .findFirst()
                    .orElse("");
            String lastName2 = m2.getPeople().stream()
                    .filter(person -> person.getMemberType() == 1)
                    .map(PersonDTO::getLastName)
                    .findFirst()
                    .orElse("");
            return lastName1.compareTo(lastName2);
        };
        memberships.sort(comparator);
    }

    public static void sortMembershipsByMembershipId(ArrayList<MembershipInfoDTO> memberships) {
        memberships.sort(Comparator.comparing(MembershipInfoDTO::getMembershipId));
    }

}
