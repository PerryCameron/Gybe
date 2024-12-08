package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.*;
import com.ecsail.Gybe.wrappers.BoardOfDirectorsResponse;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface MembershipService {
    MembershipListDTO getMembership(int msId, int selectedYear);
    List<BoardPositionDTO> getBoardPositions();
    List<LeadershipDTO> getLeaderShip(int number);

    ThemeDTO getThemeByYear(Integer year);

    BoardOfDirectorsResponse getBodResponse(int year);

    List<LeadershipDTO> getLeadershipByYear(int year);

    List<JsonNode> getMembershipListAsJson();

    JsonNode getMembershipAsJson(int msId, int year);

    SlipDTO getSubleaseInfo(int msId);

    MembershipIdDTO getMembershipId(int msId);

    SlipDTO changeSlip(int membershipId, String changeType, int ownerMsId);

    Integer releaseSublease(int ownerMsId);
}
