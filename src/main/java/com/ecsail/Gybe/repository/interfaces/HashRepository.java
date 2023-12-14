package com.ecsail.Gybe.repository.interfaces;

import com.ecsail.Gybe.dto.FormHashRequestDTO;
import com.ecsail.Gybe.dto.FormSettingsDTO;
import com.ecsail.Gybe.dto.HashDTO;

import java.util.List;

public interface HashRepository {
    List<FormHashRequestDTO> getFormHashRequests(int year);
    HashDTO getHashDTOFromMsid(int msid);
    HashDTO getHashDTOFromHash(long hash);
    FormSettingsDTO getFormSettings();

    HashDTO insertHash(HashDTO h);

    FormHashRequestDTO insertHashRequestHistory(FormHashRequestDTO formHashRequestDTO);
}
