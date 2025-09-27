package com.banque.credits.servicecredits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.banque.credits.servicecredits.service")

@SpringBootApplication
public class ServiceCreditsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceCreditsApplication.class, args);
	}

}
