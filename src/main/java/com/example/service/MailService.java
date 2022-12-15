package com.example.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    void sendEmail(String toAccount, String subject, String text) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toAccount);

        mailMessage.setSubject(subject);

        mailMessage.setText(text);

        mailMessage.setFrom(fromEmail);

        mailSender.send(mailMessage);
    }


    void sendEmailMine(String toAccount, String subject, String text) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            msg.setFrom(fromEmail);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(msg);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }

}
