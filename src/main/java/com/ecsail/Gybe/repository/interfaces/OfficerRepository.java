package com.ecsail.Gybe.repository.interfaces;

import com.ecsail.Gybe.dto.LeadershipDTO;
import com.ecsail.Gybe.dto.OfficerDTO;
import com.ecsail.Gybe.dto.OfficerWithNameDTO;
import com.ecsail.Gybe.dto.PersonDTO;

import java.util.List;

public interface OfficerRepository {
    List<OfficerDTO> getOfficers();
    List<OfficerDTO> getOfficer(String field, int attribute);
    List<OfficerDTO> getOfficer(PersonDTO person);
    List<OfficerWithNameDTO> getOfficersWithNames(String type);
    List<LeadershipDTO> getLeadershipByYear(int year);
    int update(OfficerDTO o);
    int insert(OfficerDTO o);
    int delete(OfficerDTO o);
    int batchUpdate(List<OfficerDTO> officerDTOList);
}
