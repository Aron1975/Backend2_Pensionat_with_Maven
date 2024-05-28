package com.backend2.backend2_pensionat_with_maven.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventProperties {
    private String queueName;
    private String queueHost;
    private String queueUsername;
    private String queuePassword;
}
