package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.BoardPositionDTO;
import com.ecsail.Gybe.dto.MembershipListDTO;

import java.util.List;

public interface MembershipService {
    MembershipListDTO getMembership(int msId, int selectedYear);
    List<BoardPositionDTO> getBoardPositions();
}
