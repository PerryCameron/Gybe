package com.ecsail.Gybe.dto;

public class MailDTO {
    private String recipient;
    private String subject;
    private String message;
    private AuthDTO authDTO;

    public MailDTO() {
    }

    public MailDTO(String recipient, String subject, String message) {
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
    }

    public AuthDTO getAuthDTO() {
        return authDTO;
    }

    public void setAuthDTO(AuthDTO authDTO) {
        this.authDTO = authDTO;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
