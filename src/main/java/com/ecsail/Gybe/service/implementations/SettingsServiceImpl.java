package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.AppSettingsDTO;
import com.ecsail.Gybe.repository.interfaces.SettingsRepository;
import com.ecsail.Gybe.service.interfaces.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SettingsServiceImpl implements SettingsService {

    private ArrayList<AppSettingsDTO> appSettingDTOS;
    private SettingsRepository settingsRepository;

    @Autowired
    public SettingsServiceImpl(ArrayList<AppSettingsDTO> appSettingDTOS, SettingsRepository settingsRepository) {
        this.appSettingDTOS = appSettingDTOS;
        this.settingsRepository = settingsRepository;
        this.appSettingDTOS = (ArrayList<AppSettingsDTO>) settingsRepository.getAppSettingsByGroupName("gybe");
    }
    @Override
    public void refreshSettings() {
        appSettingDTOS.clear();
        this.appSettingDTOS = (ArrayList<AppSettingsDTO>) settingsRepository.getAppSettingsByGroupName("gybe");
    }
    @Override
    public AppSettingsDTO getScheme() {
        return appSettingDTOS.stream().filter(appSettingDTO -> appSettingDTO.getKey().equals("scheme"))
                .findFirst().orElse(null);
    }
    @Override
    public AppSettingsDTO getHostName() {
        return appSettingDTOS.stream().filter(appSettingDTO -> appSettingDTO.getKey().equals("host_name"))
                .findFirst().orElse(null);
    }

    @Override
    public AppSettingsDTO getAppPort() {
        return appSettingDTOS.stream().filter(appSettingDTO -> appSettingDTO.getKey().equals("app_port"))
                .findFirst().orElse(null);
    }

    @Override
    public AppSettingsDTO getFormURL() {
        return appSettingDTOS.stream().filter(appSettingDTO -> appSettingDTO.getKey().equals("form_url"))
                .findFirst().orElse(null);
    }

    @Override
    public AppSettingsDTO getFormId() {
        return appSettingDTOS.stream().filter(appSettingDTO -> appSettingDTO.getKey().equals("form_id"))
                .findFirst().orElse(null);
    }

    @Override
    public int getSelectedYear() {
        return appSettingDTOS.stream()
                .filter(appSettingDTO -> appSettingDTO.getKey().equals("selected_year"))
                .map(appSettingDTO -> {
                    try {
                        // Attempt to parse the string value to an int
                        return Integer.parseInt(appSettingDTO.getValue());
                    } catch (NumberFormatException e) {
                        // Handle the case where the value is not a valid integer
                        // You can log this error or return a default value
                        return null; // Or use a default value like 0
                    }
                })
                .findFirst()
                .orElse(null); // Or a default value if preferred
    }

    @Override
    public AppSettingsDTO getFormButtonColor() {
        return appSettingDTOS.stream().filter(appSettingDTO -> appSettingDTO.getKey().equals("button_color"))
                .findFirst().orElse(null);
    }

    @Override
    public AppSettingsDTO getFormButtonBorderColor() {
        return appSettingDTOS.stream().filter(appSettingDTO -> appSettingDTO.getKey().equals("button_border_color"))
                .findFirst().orElse(null);
    }

    @Override
    public AppSettingsDTO getFormButtonTextColor() {
        return appSettingDTOS.stream().filter(appSettingDTO -> appSettingDTO.getKey().equals("button_text_color"))
                .findFirst().orElse(null);
    }

    @Override
    public AppSettingsDTO getFormBackgroundColor() {
        return appSettingDTOS.stream().filter(appSettingDTO -> appSettingDTO.getKey().equals("form_background_color"))
                .findFirst().orElse(null);
    }

    @Override
    public AppSettingsDTO getFormImage() {
        return appSettingDTOS.stream().filter(appSettingDTO -> appSettingDTO.getKey().equals("form_image"))
                .findFirst().orElse(null);
    }
    @Override
    public boolean isTestMode() {
        AppSettingsDTO appSettingDTO = appSettingDTOS.stream().filter(settingDTO -> settingDTO.getKey().equals("test_mode"))
                .findFirst().orElse(null);
        if(appSettingDTO != null) {
         if(appSettingDTO.getValue().equals("true")) return true;
        }
        return false;
    }



}
