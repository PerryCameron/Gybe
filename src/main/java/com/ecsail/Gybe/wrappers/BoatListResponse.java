package com.ecsail.Gybe.wrappers;

import com.ecsail.Gybe.dto.BoatListDTO;

import java.util.List;

public class BoatListResponse {
    List<BoatListDTO> boatListDTOS;
    String BoatListType;

    public BoatListResponse() {
    }

    public List<BoatListDTO> getBoatListDTOS() {
        return boatListDTOS;
    }

    public void setBoatListDTOS(List<BoatListDTO> boatListDTOS) {
        this.boatListDTOS = boatListDTOS;
    }

    public String getBoatListType() {
        return BoatListType;
    }

    public void setBoatListType(String boatListType) {
        BoatListType = boatListType;
    }
}
