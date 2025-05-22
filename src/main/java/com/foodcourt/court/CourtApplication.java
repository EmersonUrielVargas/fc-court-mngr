package com.foodcourt.court;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CourtApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourtApplication.class, args);
	}

}
