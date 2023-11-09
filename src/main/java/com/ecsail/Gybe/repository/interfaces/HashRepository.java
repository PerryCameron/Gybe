package com.ecsail.Gybe.repository.interfaces;

import com.ecsail.Gybe.dto.HashDTO;

public interface HashRepository {

    HashDTO getHashDTOFromHash(long hash);
}
