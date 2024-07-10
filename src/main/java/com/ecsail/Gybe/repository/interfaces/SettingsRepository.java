package com.ecsail.Gybe.repository.interfaces;



import com.ecsail.Gybe.dto.*;

import java.util.List;

public interface SettingsRepository {

    List<AppSettingsDTO> getAppSettingsByGroupName(String groupName);

    AppSettingsDTO getSettingFromKey(String key);

    AppSettingsDTO getSettingByGroup(String group);

    List<DbRosterSettingsDTO> getSearchableListItems();
    List<MembershipListRadioDTO> getRadioChoices();
    List<DbBoatSettingsDTO> getBoatSettings();
    List<BoatListRadioDTO> getBoatRadioChoices();
    AppSettingsDTO getSelectedYear();

    ThemeDTO findThemeByYear(int year);
}
