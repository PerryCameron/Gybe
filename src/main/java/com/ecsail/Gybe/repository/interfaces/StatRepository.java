package com.ecsail.Gybe.repository.interfaces;


import com.ecsail.Gybe.dto.StatsDTO;

import java.util.List;

public interface StatRepository {

    List<StatsDTO> getStatistics(int startYear , int stopYear);
    List<StatsDTO> createStatDTO(int year, int statID);
    StatsDTO createStatDTO(int year);
    int getNumberOfStatYears();
    int deleteAllStats();
    int addStatRecord(StatsDTO s);
}
