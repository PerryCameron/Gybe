package com.ecsail.Gybe.wrappers;

import com.ecsail.Gybe.dto.MailDTO;

public class MailWrapper {
    MailDTO mailDTO;
    String message;
    boolean sendEmail = true;

    public MailDTO getMailDTO() {
        return mailDTO;
    }

    public void setMailDTO(MailDTO mailDTO) {
        this.mailDTO = mailDTO;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean sendEmail() {
        return sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }
}
