package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.AgesDTO;
import com.ecsail.Gybe.dto.StatsDTO;
import com.ecsail.Gybe.repository.interfaces.GeneralRepository;
import com.ecsail.Gybe.service.interfaces.GeneralService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GeneralServiceImpl implements GeneralService {

    private final GeneralRepository generalRepository;

    public GeneralServiceImpl(GeneralRepository generalRepository) {
        this.generalRepository = generalRepository;
    }
    @Override
    public List<StatsDTO> getStats() {
        return generalRepository.getAllStats();
    }
    @Override
    public AgesDTO getAges() { return generalRepository.getAges(); }


}
