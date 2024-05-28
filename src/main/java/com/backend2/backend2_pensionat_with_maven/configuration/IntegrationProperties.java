package com.backend2.backend2_pensionat_with_maven.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@ConfigurationPropertiesScan
@Configuration
@ConfigurationProperties(prefix = "integrations")
@Getter
@Setter
public class IntegrationProperties {
    private BlacklistProperties blacklistProperties;
    private ContractCustomerProperties contractCustomerProperties;
    private ShipperProperties shipperProperties;
    private EventProperties eventProperties;
}
