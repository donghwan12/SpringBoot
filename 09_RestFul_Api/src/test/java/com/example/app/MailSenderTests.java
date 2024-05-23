package com.example.app;

import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class MailSenderTests {
    @Test
    public void t1(){

        //메일 설정
        JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
        mailSender.setHost("stmp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("dlehdghks012@gmail.com");
        mailSender.setPassword("");

        Properties mailProps=new Properties();
        mailProps.put("mail.smtp.auth","ture");
        mailProps.put("mail.smtp.starttls.enable","true");
        mailSender.setJavaMailProperties(mailProps);

        //메세지 지정
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo("");
        message.setSubject("");
        message.setText("");

        //메일발송
        mailSender.send(message);
    }
}
