package com.ecsail.Gybe.service.interfaces;

//import jakarta.mail.MessagingException;


import com.ecsail.Gybe.dto.MailDTO;
import jakarta.mail.MessagingException;

public interface SendMailService {
    void sendMail(MailDTO mailDTO);

    void sendHTMLMail(MailDTO mailDTO, String from) throws MessagingException;

    void sendMailWithAttachments(MailDTO mailDTO) throws MessagingException;
}