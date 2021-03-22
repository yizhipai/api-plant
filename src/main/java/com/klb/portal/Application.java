package com.klb.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.klb.portal")
@EnableFeignClients
public class Application {
 

	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);
	}

}
