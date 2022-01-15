package com.schambeck.dna.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.schambeck.dna.web"})
public class DnaWebMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(DnaWebMvcApplication.class, args);
	}

}
