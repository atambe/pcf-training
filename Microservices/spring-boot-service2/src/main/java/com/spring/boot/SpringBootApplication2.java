package com.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class SpringBootApplication2 {

	public static void main(String[] args) {
		 System.setProperty("spring.config.name", "registration-server");
		SpringApplication.run(SpringBootApplication2.class, args);
		System.out.println("Spring Boot service2:");

	}

}
