package com.ecsail.Gybe.repository.interfaces;



import com.ecsail.Gybe.dto.BoatListRadioDTO;
import com.ecsail.Gybe.dto.DbBoatSettingsDTO;
import com.ecsail.Gybe.dto.DbRosterSettingsDTO;
import com.ecsail.Gybe.dto.MembershipListRadioDTO;

import java.util.List;

public interface SettingsRepository {

    List<DbRosterSettingsDTO> getSearchableListItems();
    List<MembershipListRadioDTO> getRadioChoices();
    List<DbBoatSettingsDTO> getBoatSettings();
    List<BoatListRadioDTO> getBoatRadioChoices();
}
