package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.AppSettingsDTO;

public interface SettingsService {
    void refreshSettings();

    AppSettingsDTO getScheme();

    AppSettingsDTO getHostName();

    AppSettingsDTO getAppPort();

    AppSettingsDTO getFormURL();

    AppSettingsDTO getFormId();

    int getSelectedYear();

    AppSettingsDTO getFormButtonColor();

    AppSettingsDTO getFormButtonBorderColor();

    AppSettingsDTO getFormButtonTextColor();

    AppSettingsDTO getFormBackgroundColor();

    AppSettingsDTO getFormImage();

    boolean isTestMode();
}
