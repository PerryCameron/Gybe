package com.ecsail.Gybe.service.interfaces;

import com.ecsail.Gybe.dto.AuthDTO;
import com.ecsail.Gybe.dto.HashDTO;
import com.ecsail.Gybe.dto.MailDTO;

public interface EmailService {
    MailDTO sendPasswordReset(String email);

    MailDTO processEmailSubmission(String email);

    HashDTO createHash(AuthDTO authDTO);
    Boolean verifyEmail(String apiKey, String email);
}
