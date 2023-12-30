package com.ecsail.Gybe.repository.interfaces;

import com.ecsail.Gybe.dto.SlipDTO;

public interface SlipRepository {
    SlipDTO getSlip(int msId);

    SlipDTO getSlipFromMsid(int ms_id);

    boolean slipExists(int ms_id);
}
