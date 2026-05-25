package com.example.blogapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailSender;

    public void sendCommentNotification(String toEmail, String postTitle, String commenterName){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailSender);
        message.setTo(toEmail);
        message.setSubject("New Comment on your post!");
        message.setText(
                "Hello,\n\n" +
                        commenterName + " commented on your post: \"" + postTitle + "\"\n\n" +
                        "Check it out!"
        );
        javaMailSender.send(message);
    }

    public void sendWelcomeEmail(String toEmail, String username){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailSender);
        message.setTo(toEmail);
        message.setSubject("Welcome to Blog API  \uD83C\uDF89" + "🎉");

        message.setText(
                "Hello " + username + ",\n\n" +
                        "Welcome to our platform! We're glad to have you.\n\n" +
                        "Start creating posts and engaging with the community!"
        );
        javaMailSender.send(message);
    }

}
