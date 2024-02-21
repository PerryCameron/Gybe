package com.ecsail.Gybe.repository.interfaces;



import com.ecsail.Gybe.dto.*;

import java.util.List;

public interface SettingsRepository {

    List<AppSettingDTO> getAppSettingsByGroupName(String groupName);
    List<DbRosterSettingsDTO> getSearchableListItems();
    List<MembershipListRadioDTO> getRadioChoices();
    List<DbBoatSettingsDTO> getBoatSettings();
    List<BoatListRadioDTO> getBoatRadioChoices();
    AppSettingDTO getSelectedYear();

    ThemeDTO findThemeByYear(int year);
}
