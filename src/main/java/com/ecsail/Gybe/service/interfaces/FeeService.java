package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.FeeDTO;

import java.util.List;

public interface FeeService {
    List<FeeDTO> getFees();
    List<FeeDTO> getFeesByType(String type);
}
