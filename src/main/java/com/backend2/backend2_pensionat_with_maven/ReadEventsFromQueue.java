package com.backend2.backend2_pensionat_with_maven;

import com.backend2.backend2_pensionat_with_maven.dtos.RumEventDto;
import com.backend2.backend2_pensionat_with_maven.models.RumEvent;
import com.backend2.backend2_pensionat_with_maven.services.impl.RumEventServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;

import java.nio.charset.StandardCharsets;


@ComponentScan
public class ReadEventsFromQueue implements CommandLineRunner {

    private final String queueName = "b772d127-9330-44ca-8449-4230fec752a2";

    @Autowired
    RumEventServiceImpl rumEventService;

    @Override
    public void run(String... args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("128.140.81.47");
        factory.setUsername("djk47589hjkew789489hjf894");
        factory.setPassword("sfdjkl54278frhj7");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

            System.out.println(" [x] Received '" + message + "'");
            //Json to Pojo
       /*     RumEventDto eventDto = new ObjectMapper()
                    .readerFor(RumEventDto.class)
                    .readValue(message); */

            //Pojo to DB
         //   String messageFromService = rumEventService.sparaRumEvent(eventDto);

          //  System.out.println(" [*] Message from Service: '" + messageFromService + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
