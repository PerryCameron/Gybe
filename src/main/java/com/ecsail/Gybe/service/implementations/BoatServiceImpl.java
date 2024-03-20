package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.repository.interfaces.BoatRepository;
import com.ecsail.Gybe.service.interfaces.BoatService;
import com.ecsail.Gybe.wrappers.BoatListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BoatServiceImpl implements BoatService {

    private final BoatRepository boatRepository;

    @Autowired
    public BoatServiceImpl(BoatRepository boatRepository) {
        this.boatRepository = boatRepository;
        
    }

    @Override
    public BoatListResponse getBoatListResponse(String boatListType, Map<String, String> allParams) {
        BoatListResponse boatListResponse = new BoatListResponse();
        List<String> searchParams = allParams.entrySet().stream()
                .filter(e -> e.getKey().startsWith("param"))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        switch (boatListType) {
            case "active_sailboats" -> boatListResponse.setBoatListDTOS(boatRepository.getActiveSailBoats());
            case "active_paddlecraft" -> boatListResponse.setBoatListDTOS(boatRepository.getActiveAuxBoats());
            case "all_sailboats" -> boatListResponse.setBoatListDTOS(boatRepository.getAllSailBoats());
            case "all_paddlecraft" -> boatListResponse.setBoatListDTOS(boatRepository.getAllAuxBoats());
            case "all_watercraft" -> boatListResponse.setBoatListDTOS(boatRepository.getAllBoats());
            case "search" -> boatListResponse.setBoatListDTOS(boatRepository.findBoatsByWords(searchParams));
        }
        System.out.println(boatListType);
        boatListResponse.setBoatListType(boatListType);
        return boatListResponse;
    }
}
