package com.ecsail.Gybe.repository.interfaces;


import com.ecsail.Gybe.dto.MembershipListDTO;

import java.util.List;

public interface MembershipRepository {

    List<MembershipListDTO> getActiveRoster(Integer selectedYear);
    List<MembershipListDTO> getInActiveRoster(Integer selectedYear);
    List<MembershipListDTO> getAllRoster(Integer selectedYear);
    List<MembershipListDTO> getNewMemberRoster(Integer selectedYear);
    List<MembershipListDTO> getReturnMemberRoster(Integer selectedYear);
    List<MembershipListDTO> getSlipWaitList();
    List<MembershipListDTO> getMembershipByBoatId(Integer boatId);
    int update(MembershipListDTO membershipListDTO);

    int updateJoinDate(MembershipListDTO membershipListDTO);

    MembershipListDTO getMembershipByMembershipId(int membershipId);

    MembershipListDTO getMembershipByMsId(int msId);
    MembershipListDTO getMembershipByMsId(int msId, int specifiedYear);

    List<MembershipListDTO> getRoster(int year, boolean isActive);

    List<MembershipListDTO> getRosterOfAll(int year);

    List<MembershipListDTO> getReturnMembers(int year);

    MembershipListDTO getMembershipListFromMsidAndYear(int year, int msId);

    List<MembershipListDTO> getSearchRoster(List<String> searchParams);
}
