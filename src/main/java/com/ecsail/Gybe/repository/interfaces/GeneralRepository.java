package com.ecsail.Gybe.repository.interfaces;

import com.ecsail.Gybe.dto.StatsDTO;

import java.util.List;

public interface GeneralRepository {
    boolean recordExists(String tableName, String columnName, int value);

    List<StatsDTO> getAllStats();
}
