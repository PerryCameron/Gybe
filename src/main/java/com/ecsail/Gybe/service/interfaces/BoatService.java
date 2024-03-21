package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.BoatDTO;
import com.ecsail.Gybe.dto.BoatListDTO;
import com.ecsail.Gybe.wrappers.BoatListResponse;

import java.util.List;
import java.util.Map;

public interface BoatService {

    BoatListResponse getBoatListResponse(String boatListType, Map<String, String> allParams);


    BoatDTO getBoatById(String boatId);
}
