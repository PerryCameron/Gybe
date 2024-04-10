package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.FormHashRequestDTO;
import com.ecsail.Gybe.dto.MailDTO;
import com.ecsail.Gybe.wrappers.MailWrapper;

import java.util.List;

public interface AdminService {
    List<FormHashRequestDTO> getFormRequests(int year);

    boolean isValidKey(String passKey);

    MailWrapper generateCredentialsEmail(String email);

    void setUserPass(String key, String status, String email, String password1);
}
