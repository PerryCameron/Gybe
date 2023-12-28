package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.AppSettingDTO;
import com.ecsail.Gybe.repository.interfaces.SettingsRepository;
import com.ecsail.Gybe.service.interfaces.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SettingsServiceImpl implements SettingsService {

    private ArrayList<AppSettingDTO> appSettingDTOS;
    private SettingsRepository settingsRepository;

    @Autowired
    public SettingsServiceImpl(ArrayList<AppSettingDTO> appSettingDTOS, SettingsRepository settingsRepository) {
        this.appSettingDTOS = appSettingDTOS;
        this.settingsRepository = settingsRepository;
        this.appSettingDTOS = (ArrayList<AppSettingDTO>) settingsRepository.getAppSettingsByGroupName("gybe");
    }
    @Override
    public void refreshSettings() {
        appSettingDTOS.clear();
        this.appSettingDTOS = (ArrayList<AppSettingDTO>) settingsRepository.getAppSettingsByGroupName("gybe");
    }
    @Override
    public AppSettingDTO getScheme() {
        return appSettingDTOS.stream().filter(appSettingDTO -> appSettingDTO.getKey().equals("scheme"))
                .findFirst().orElse(null);
    }
    @Override
    public AppSettingDTO getHostName() {
        return appSettingDTOS.stream().filter(appSettingDTO -> appSettingDTO.getKey().equals("host_name"))
                .findFirst().orElse(null);
    }

    @Override
    public AppSettingDTO getAppPort() {
        return appSettingDTOS.stream().filter(appSettingDTO -> appSettingDTO.getKey().equals("app_port"))
                .findFirst().orElse(null);
    }

    @Override
    public AppSettingDTO getFormURL() {
        return appSettingDTOS.stream().filter(appSettingDTO -> appSettingDTO.getKey().equals("form_url"))
                .findFirst().orElse(null);
    }

    @Override
    public AppSettingDTO getFormId() {
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

}
