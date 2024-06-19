package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.BoardPositionDTO;
import com.ecsail.Gybe.dto.LeadershipDTO;
import com.ecsail.Gybe.dto.MembershipListDTO;
import com.ecsail.Gybe.dto.ThemeDTO;
import com.ecsail.Gybe.wrappers.BoardOfDirectorsResponse;

import java.util.List;

public interface MembershipService {
    MembershipListDTO getMembership(int msId, int selectedYear);
    List<BoardPositionDTO> getBoardPositions();
    List<LeadershipDTO> getLeaderShip(int number);

    ThemeDTO getThemeByYear(Integer year);

    BoardOfDirectorsResponse getBodResponse(int year);

    List<LeadershipDTO> getLeadershipByYear(int year);

}
