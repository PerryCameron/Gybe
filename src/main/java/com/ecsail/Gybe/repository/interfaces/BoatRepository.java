package com.ecsail.Gybe.repository.interfaces;


import com.ecsail.Gybe.dto.*;

import java.util.List;

public interface BoatRepository {

    List<BoatListDTO> getActiveSailBoats();
    List<BoatListDTO> getActiveAuxBoats();
    List<BoatListDTO> getAllSailBoats();
    List<BoatListDTO> getAllAuxBoats();
    List<BoatListDTO> getAllBoats();
    List<BoatDTO> getBoatsByMsId(int msId);
    List<BoatDTO> getOnlySailboatsByMsId(int msId);
    List<BoatOwnerDTO> getBoatOwnersByBoatId(int boatId);
    List<BoatPhotosDTO> getImagesByBoatId(String boat_id);
    int update(BoatDTO o);
    int update(BoatListDTO boatListDTO);
    int update(BoatPhotosDTO boatPhotosDTO);
    int updateAux(boolean aux, int boatId);
    int delete(BoatDTO o);
    int insert(BoatDTO o);
    int insert(BoatPhotosDTO boatPhotosDTO);
    int delete(BoatPhotosDTO boatPhotosDTO);
    int insertOwner(BoatOwnerDTO boatOwnerDTO);
    int deleteBoatOwner(MembershipListDTO membershipListDTO, BoatListDTO boatListDTO);

    int deleteBoatOwner(MembershipListDTO membershipListDTO, BoatDTO boatDTO);

    int setAllDefaultImagesToFalse(int boatId);

    int setDefaultImageTrue(int id);

    List<BoatListDTO> getSearchResult(List<String> searchParams);

    List<BoatListDTO> findBoatsByWords(List<String> words);

    BoatDTO findBoatById(String boatId);

    List<DbBoatSettingsDTO> getBoatSettings();
}
