package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.AgesDTO;
import com.ecsail.Gybe.dto.SlipInfoDTO;
import com.ecsail.Gybe.dto.SlipStructureDTO;
import com.ecsail.Gybe.dto.StatsDTO;
import com.ecsail.Gybe.repository.interfaces.GeneralRepository;
import com.ecsail.Gybe.repository.interfaces.SlipRepository;
import com.ecsail.Gybe.service.interfaces.GeneralService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GeneralServiceImpl implements GeneralService {

    private final GeneralRepository generalRepository;
    private final SlipRepository slipRepository;

    public GeneralServiceImpl(GeneralRepository generalRepository, SlipRepository slipRepository) {
        this.generalRepository = generalRepository;
        this.slipRepository = slipRepository;
    }
    @Override
    public List<StatsDTO> getStats() {
        return generalRepository.getAllStats();
    }
    @Override
    public AgesDTO getAges() { return generalRepository.getAges(); }
    @Override
    public List<SlipInfoDTO> getSlipInfo() { return  slipRepository.getSlipInfo(); }
    @Override
    public List<SlipStructureDTO> getSlipStructure() { return slipRepository.getSlipStructure(); }
}
