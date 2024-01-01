package com.ecsail.Gybe.repository.interfaces;

import com.ecsail.Gybe.dto.*;

import java.util.List;

public interface HashRepository {
    List<FormHashRequestDTO> getFormHashRequests(int year);
    HashDTO getHashDTOFromMsid(int msid);
    HashDTO getHashDTOFromHash(long hash);
    HashDTO insertHash(HashDTO h);
    FormHashRequestDTO insertHashRequestHistory(FormHashRequestDTO formHashRequestDTO);
    List<FormRequestSummaryDTO> getFormRequestSummariesForYear(int year);
    FormRequestDTO insertHashHistory(FormRequestDTO fr);
}
