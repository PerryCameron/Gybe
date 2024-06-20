package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.FormHashRequestDTO;
import com.ecsail.Gybe.dto.FormRequestSummaryDTO;
import com.ecsail.Gybe.wrappers.MailWrapper;
import com.ecsail.Gybe.wrappers.MessageResponse;

import java.util.List;

public interface AdminService {
    List<FormHashRequestDTO> getFormRequests(int year);

    List<FormRequestSummaryDTO> getFormSummaries(Integer year);

    boolean isValidKey(String passKey);

    MailWrapper generateCredentialsEmail(String email);

    MessageResponse createOrUpdateUser(String key, String status, String email, String password1, String password2);
}
