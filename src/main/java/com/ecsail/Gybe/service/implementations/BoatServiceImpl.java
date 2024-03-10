package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.BoatListDTO;
import com.ecsail.Gybe.repository.interfaces.BoatRepository;
import com.ecsail.Gybe.service.interfaces.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoatServiceImpl implements BoatService {

    private final BoatRepository boatRepository;

    @Autowired
    public BoatServiceImpl(BoatRepository boatRepository) {
        this.boatRepository = boatRepository;
        
    }
    @Override
    public List<BoatListDTO> getBoatListByType(String boatListType) {
        List<BoatListDTO> boats = new ArrayList<>();
        switch (boatListType) {
            case "active_sailboats" -> boats = boatRepository.getActiveSailBoats();
            case "active_Auxiliary" -> boats = boatRepository.getActiveAuxBoats();
            case "all_sailboats" -> boats = boatRepository.getAllSailBoats();
            case "all_auxiliary" -> boats = boatRepository.getAllAuxBoats();
            case "all_boats" -> boats = boatRepository.getAllBoats();
        }
        return boats;
    }
}
