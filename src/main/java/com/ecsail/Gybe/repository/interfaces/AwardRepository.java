package com.ecsail.Gybe.repository.interfaces;
import com.ecsail.Gybe.dto.AwardDTO;
import com.ecsail.Gybe.dto.PersonDTO;

import java.util.List;

public interface AwardRepository {
    List<AwardDTO> getAwards(PersonDTO p);
    List<AwardDTO> getAwards();
    int update(AwardDTO o);
    int insert(AwardDTO o);

    int delete(AwardDTO awardDTO);
}
