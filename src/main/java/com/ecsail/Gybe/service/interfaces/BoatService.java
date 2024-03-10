package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.BoatListDTO;

import java.util.List;

public interface BoatService {


    List<BoatListDTO> getBoatListByType(String boatListType);
}
