package com.ecsail.Gybe.repository.interfaces;

import com.ecsail.Gybe.dto.SlipDTO;
import com.ecsail.Gybe.dto.SlipInfoDTO;
import com.ecsail.Gybe.dto.SlipStructureDTO;

import java.util.List;

public interface SlipRepository {
    SlipDTO getSlip(int msId);

    SlipDTO getSlipFromMsid(int ms_id);

    boolean slipExists(int ms_id);

    List<SlipInfoDTO> getSlipInfo();

    List<SlipStructureDTO> getSlipStructure();
}
