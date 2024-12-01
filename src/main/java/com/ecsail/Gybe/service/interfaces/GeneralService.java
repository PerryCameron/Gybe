package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.*;

import java.util.List;

public interface GeneralService {
    List<StatsDTO> getStats();
    AgesDTO getAges();
    List<SlipInfoDTO> getSlipInfo();
    List<SlipStructureDTO> getSlipStructure();
}
