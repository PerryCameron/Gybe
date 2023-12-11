package com.ecsail.Gybe.repository.interfaces;

import com.ecsail.Gybe.dto.FormHashRequestDTO;
import com.ecsail.Gybe.dto.FormSettingsDTO;
import com.ecsail.Gybe.dto.HashDTO;

import java.util.List;

public interface HashRepository {
    List<FormHashRequestDTO> getFormHashRequests();
    HashDTO getHashDTOFromMsid(int msid);
    HashDTO getHashDTOFromHash(long hash);
    FormSettingsDTO getFormSettings();
}
