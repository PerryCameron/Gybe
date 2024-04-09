package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.FormHashRequestDTO;
import com.ecsail.Gybe.dto.MailDTO;
import com.ecsail.Gybe.wrappers.MailWrapper;

import java.util.List;

public interface AdminService {
    List<FormHashRequestDTO> getFormRequests(int year);

    MailWrapper generateCredentialsEmail(String email);
}
