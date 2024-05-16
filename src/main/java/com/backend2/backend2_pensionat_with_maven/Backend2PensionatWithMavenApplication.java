package com.backend2.backend2_pensionat_with_maven;

import com.backend2.backend2_pensionat_with_maven.models.Kund;
import com.backend2.backend2_pensionat_with_maven.models.Rum;
import com.backend2.backend2_pensionat_with_maven.repos.KundRepo;
import com.backend2.backend2_pensionat_with_maven.repos.RumRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

@SpringBootApplication
public class Backend2PensionatWithMavenApplication {

    public static void main(String[] args) {

        if(args.length == 0) {
            SpringApplication.run(Backend2PensionatWithMavenApplication.class, args);


        }else if(Objects.equals(args[0], "fetchShippers")){
            System.out.println("Updating Shippers...");
            SpringApplication application = new SpringApplication(FetchShippers.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);

        }else if(Objects.equals(args[0], "fetchContractCustomers")){
            System.out.println("Updating Contract Customers");
            SpringApplication application = new SpringApplication(FetchContractCustomers.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);

        }/*else if(Objects.equals(args[0], "fetchBlacklist")){
            System.out.println("Updating Blacklist");
            SpringApplication application = new SpringApplication(FetchBlacklist.class);
            application.setWebApplicationType(WebApplicationType.NONE);
            application.run(args);

        }*/
    }



}
