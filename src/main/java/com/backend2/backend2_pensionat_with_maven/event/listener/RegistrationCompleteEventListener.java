package com.backend2.backend2_pensionat_with_maven.event.listener;



import com.backend2.backend2_pensionat_with_maven.models.User;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener {

    private final JavaMailSender mailSender;
    private String theUser;

    public void sendPasswordResetVerificationEmail(String url, User user) throws MessagingException, UnsupportedEncodingException, jakarta.mail.MessagingException {
        theUser = user.getUsername();
        String subject = "Återställning av lösenord";
        String senderName = "reception@pensionatet.se";
        String mailContent = "<p> Hej!</p>"+
                "<p>Du har begärt återställning av ditt lösenord," +
                "Har du inte begärt detta så kan du ignorera detta mail.</p>"+
                "<a href=\"" +url+ "\">Följ länken för att återställa ditt lösenord.</a>"+
                "<p> MVH <br> Pensionatet";

        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(senderName);
        messageHelper.setTo(theUser);
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

}