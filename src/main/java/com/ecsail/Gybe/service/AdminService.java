package com.ecsail.Gybe.service;

import com.ecsail.Gybe.dto.FormHashRequestDTO;
import com.ecsail.Gybe.dto.FormSettingsDTO;
import com.ecsail.Gybe.repository.interfaces.HashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private HashRepository hashRepository;
    @Autowired
    public AdminService(HashRepository hashRepository) {
        this.hashRepository = hashRepository;
    }

    public List<FormHashRequestDTO> getFormRequests(int year) {
        return hashRepository.getFormHashRequests(year);
    }

    public FormSettingsDTO getFormSettings() { return hashRepository.getFormSettings(); }
}
