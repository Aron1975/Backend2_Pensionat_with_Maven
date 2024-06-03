package com.backend2.backend2_pensionat_with_maven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

@SpringBootApplication
public class Backend2PensionatWithMavenApplication {

    @Autowired
    private UserDataSeeder userDataSeeder;

    public static void main(String[] args) {

        if(args.length == 0) {
            SpringApplication.run(Backend2PensionatWithMavenApplication.class, args);
        }
        else if(Objects.equals(args[0], "fetchShippers")){
            System.out.println("Updating Shippers...");
            SpringApplication application = new SpringApplication(FetchShippers.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        }
        else if(Objects.equals(args[0], "fetchContractCustomers")){
            System.out.println("Updating Contract Customers");
            SpringApplication application = new SpringApplication(FetchContractCustomers.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        }
        else if(Objects.equals(args[0], "loadRumEvents")){
            System.out.println("Loading Rum Events...");
            SpringApplication application = new SpringApplication(ReadEventsFromQueue.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);
        }
    }

 @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            userDataSeeder.Seed();
        };
    }


}
