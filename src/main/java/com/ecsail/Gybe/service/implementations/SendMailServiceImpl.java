package com.ecsail.Gybe.service.implementations;

import com.ecsail.Gybe.dto.MailDTO;
import com.ecsail.Gybe.service.interfaces.SendMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SendMailServiceImpl implements SendMailService {
    public static Logger logger = LoggerFactory.getLogger(SendMailServiceImpl.class);
    private final JavaMailSender javaMailSender;

    @Autowired
    public SendMailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(MailDTO mailDTO) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mailDTO.getRecipient(), mailDTO.getRecipient());
        msg.setFrom("no-reply@ecsail.org");
        msg.setSubject(mailDTO.getSubject());
        msg.setText(mailDTO.getMessage());
        logger.info("Sending email to " + mailDTO.getRecipient());
        javaMailSender.send(msg);
    }

    @Override
    public void sendHTMLMail(MailDTO mailDTO, String from) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject(mailDTO.getSubject());
        helper.setFrom(from);
        helper.setTo(mailDTO.getRecipient());
        boolean html = true;
        helper.setText(mailDTO.getMessage(), html);
        javaMailSender.send(message);
    }

    @Override
    public void sendMailWithAttachments(MailDTO mailDTO) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo("to_@email");

        helper.setSubject("Testing from Spring Boot");

        helper.setText("Find the attached image", true);

        helper.addAttachment("hero.jpg", new ClassPathResource("hero.jpg"));

        javaMailSender.send(msg);
    }
}
