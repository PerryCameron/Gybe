package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.AgesDTO;
import com.ecsail.Gybe.dto.StatsDTO;

import java.util.List;

public interface GeneralService {
    List<StatsDTO> getStats();

    AgesDTO getAges();
}
