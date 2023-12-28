package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.AppSettingDTO;

public interface SettingsService {
    void refreshSettings();

    AppSettingDTO getScheme();

    AppSettingDTO getHostName();

    AppSettingDTO getAppPort();

    AppSettingDTO getFormURL();

    AppSettingDTO getFormId();

    AppSettingDTO getSelectedYear();
}
