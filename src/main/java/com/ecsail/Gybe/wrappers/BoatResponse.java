package com.ecsail.Gybe.wrappers;

import com.ecsail.Gybe.dto.BoatDTO;
import com.ecsail.Gybe.dto.BoatPhotosDTO;
import com.ecsail.Gybe.dto.MembershipListDTO;

import java.util.List;

public class BoatResponse {
    private BoatDTO boatDTO;
    private List<MembershipListDTO> owners;
    private List<BoatPhotosDTO> photosDTOS;

    public BoatResponse() {
    }

    public BoatDTO getBoatDTO() {
        return boatDTO;
    }

    public void setBoatDTO(BoatDTO boatDTO) {
        this.boatDTO = boatDTO;
    }

    public List<MembershipListDTO> getOwners() {
        return owners;
    }

    public void setOwners(List<MembershipListDTO> owners) {
        this.owners = owners;
    }

    public List<BoatPhotosDTO> getPhotosDTOS() {
        return photosDTOS;
    }

    public void setPhotosDTOS(List<BoatPhotosDTO> photosDTOS) {
        this.photosDTOS = photosDTOS;
    }
}
