package com.ecsail.Gybe.repository.interfaces;


import com.ecsail.Gybe.dto.MembershipListDTO;

import java.util.List;

public interface MembershipRepository {

    List<MembershipListDTO> getActiveRoster(Integer selectedYear);
    List<MembershipListDTO> getInActiveRoster(Integer selectedYear);
    List<MembershipListDTO> getAllRoster(Integer selectedYear);
    List<MembershipListDTO> getNewMemberRoster(Integer selectedYear);
    List<MembershipListDTO> getReturnMemberRoster(Integer selectedYear);
    List<MembershipListDTO> getSlipWaitList(Integer selectedYear);
    List<MembershipListDTO> getMembershipByBoatId(Integer boatId);
    int update(MembershipListDTO membershipListDTO);

    int updateJoinDate(MembershipListDTO membershipListDTO);

    MembershipListDTO getMembershipByMembershipId(int membershipId);

    MembershipListDTO getMembershipByMsId(int msId);

    //    @Override
    //    public MembershipListDTO getMembershipByMsId(int msId) {  // will get by newest year available
    //        String query = """
    //                Select m.MS_ID,m.P_ID,id.MEMBERSHIP_ID,id.FISCAL_YEAR,id.FISCAL_YEAR,m.JOIN_DATE,
    //                id.MEM_TYPE,s.SLIP_NUM,p.L_NAME,p.F_NAME,s.SUBLEASED_TO,m.address,m.city,m.state,m.zip
    //                from slip s right join membership m on m.MS_ID=s.MS_ID left join membership_id id on
    //                m.MS_ID=id.MS_ID left join person p on p.MS_ID=m.MS_ID where p.MEMBER_TYPE=1
    //                AND id.fiscal_year=(SELECT MAX(FISCAL_YEAR) FROM membership_id where MS_ID=76) AND m.MS_ID=?
    //                                """;
    //        return template.queryForObject(query, new MembershipListRowMapper(), msId);
    //    }
    MembershipListDTO getMembershipByMsId(int msId, int specifiedYear);

    List<MembershipListDTO> getRoster(int year, boolean isActive);

    List<MembershipListDTO> getRosterOfAll(int year);

    List<MembershipListDTO> getReturnMembers(int year);

    MembershipListDTO getMembershipListFromMsidAndYear(String year);

    List<MembershipListDTO> getSearchRoster(List<String> searchParams);
}
