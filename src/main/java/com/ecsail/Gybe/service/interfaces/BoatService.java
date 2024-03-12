package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.BoatListDTO;
import com.ecsail.Gybe.wrappers.BoatListResponse;

import java.util.List;

public interface BoatService {
    BoatListResponse getBoatListResponse(String boatListType);
}
