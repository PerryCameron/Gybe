package com.ecsail.Gybe.repository.interfaces;

import com.ecsail.Gybe.dto.AwardDTO;
import com.ecsail.Gybe.dto.BoardPositionDTO;

import java.util.List;

public interface BoardPositionsRepository {
    List<BoardPositionDTO> getPositions();
    String getByIdentifier(String code);
    String getByName(String name);
}
