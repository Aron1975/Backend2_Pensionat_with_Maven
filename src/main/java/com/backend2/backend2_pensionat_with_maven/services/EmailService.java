package com.backend2.backend2_pensionat_with_maven.services;

import jakarta.mail.MessagingException;

public interface EmailService {

    public void sendSimpleMessage(String to, String subject, String text);
    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException;
}
