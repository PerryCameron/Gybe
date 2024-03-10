package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.repository.interfaces.BoatRepository;
import com.ecsail.Gybe.service.interfaces.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoatServiceImpl implements BoatService {

    private final BoatRepository boatRepository;

    @Autowired
    public BoatServiceImpl(BoatRepository boatRepository) {
        this.boatRepository = boatRepository;
        
    }
}
