package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.FormHashRequestDTO;
import com.ecsail.Gybe.dto.FormSettingsDTO;

import java.util.List;

public interface AdminService {
    List<FormHashRequestDTO> getFormRequests(int year);
    FormSettingsDTO getFormSettings();
}
