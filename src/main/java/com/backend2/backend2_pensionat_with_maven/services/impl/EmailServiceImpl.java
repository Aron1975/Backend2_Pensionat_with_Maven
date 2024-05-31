package com.backend2.backend2_pensionat_with_maven.services.impl;

import com.backend2.backend2_pensionat_with_maven.models.Bokning;
import com.backend2.backend2_pensionat_with_maven.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

   // @Autowired
    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    private HttpClient httpClient;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("admin@pensionat.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

    }

    @Override
    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("reception@pensionat.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("Invoice", file);

        emailSender.send(message);

    }

    public void sendMessageWithTemplate(String to, String subject, String template, Context context) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        String htmlContent = templateEngine.process(template, context);

        helper.setFrom("reception@pensionat.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);


        emailSender.send(message);

    }

    @Override
    public void sendConfirmationMail(Bokning bokning) throws MessagingException {
        Context context = new Context();
        // Set variables for the template from the POST request data
        context.setVariable("namn", bokning.getKund().getFörnamn());
        context.setVariable("antal", bokning.getAntalGäster());
        context.setVariable("startdate", bokning.getStartDatum());
        context.setVariable("slutdate", bokning.getSlutDatum());
        sendMessageWithTemplate("kristopher70@ethereal.email", "Bokningsbekräftelse", "emailTemplate", context);

    }

 /*   public void createMailPostRequest(String to, String subject, String text) throws IOException, InterruptedException {

        HttpClient client = httpClient;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/bokning/sendEmail"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers()).
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
    }*/

}
