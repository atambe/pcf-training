package com.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class SpringBootApplication1 {

	public static void main(String[] args) {
		 System.setProperty("spring.config.name", "registration-server");
		SpringApplication.run(SpringBootApplication1.class, args);
		System.out.println("Spring Boot service1:");

	}

}
