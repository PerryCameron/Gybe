package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.BoatListDTO;
import com.ecsail.Gybe.repository.interfaces.BoatRepository;
import com.ecsail.Gybe.service.interfaces.BoatService;
import com.ecsail.Gybe.wrappers.BoatListResponse;
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
    public BoatListResponse getBoatListResponse(String boatListType) {
        BoatListResponse boatListResponse = new BoatListResponse();
        System.out.println(boatListType);
        switch (boatListType) {
            case "active_sailboats" -> boatListResponse.setBoatListDTOS(boatRepository.getActiveSailBoats());
            case "active_paddlecraft" -> boatListResponse.setBoatListDTOS(boatRepository.getActiveAuxBoats());
            case "all_sailboats" -> boatListResponse.setBoatListDTOS(boatRepository.getAllSailBoats());
            case "all_paddlecraft" -> boatListResponse.setBoatListDTOS(boatRepository.getAllAuxBoats());
            case "all_watercraft" -> boatListResponse.setBoatListDTOS(boatRepository.getAllBoats());
        }
        boatListResponse.setBoatListType(boatListType);
        return boatListResponse;
    }
}
