package com.ecsail.Gybe.wrappers;

import com.ecsail.Gybe.dto.MembershipListDTO;

import java.util.List;

public class RosterResponse {

    List<MembershipListDTO> membershipListDTOS;
    String rosterType;
    int year;
    public RosterResponse() {
    }

    public List<MembershipListDTO> getMembershipListDTOS() {
        return membershipListDTOS;
    }

    public void setMembershipListDTOS(List<MembershipListDTO> membershipListDTOS) {
        this.membershipListDTOS = membershipListDTOS;
    }

    public String getRosterType() {
        return rosterType;
    }

    public void setRosterType(String rosterType) {
        this.rosterType = rosterType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
