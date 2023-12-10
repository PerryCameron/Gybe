package com.ecsail.Gybe.service;

import com.ecsail.Gybe.dto.FormHashRequestDTO;
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

    public List<FormHashRequestDTO> getFormRequests() {
        return hashRepository.getFormHashRequests();
    }
}
