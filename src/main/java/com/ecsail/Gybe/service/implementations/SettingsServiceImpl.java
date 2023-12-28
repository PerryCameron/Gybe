package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.AppSettingDTO;
import com.ecsail.Gybe.repository.interfaces.SettingsRepository;
import com.ecsail.Gybe.service.interfaces.SettingsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SettingsServiceImpl implements SettingsService {

    private ArrayList<AppSettingDTO> appSettingDTOS;
    private SettingsRepository settingsRepository;


    public SettingsServiceImpl(ArrayList<AppSettingDTO> appSettingDTOS, SettingsRepository settingsRepository) {
        this.appSettingDTOS = appSettingDTOS;
        this.settingsRepository = settingsRepository;
        this.appSettingDTOS = (ArrayList<AppSettingDTO>) settingsRepository.getAppSettingsByGroupName("2023_Gybe");
    }





}
