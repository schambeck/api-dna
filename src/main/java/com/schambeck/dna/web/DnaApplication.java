package com.schambeck.dna.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = {"com.schambeck.dna.web"})
@EnableEurekaClient
public class DnaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DnaApplication.class, args);
    }

}
