package com.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.stereotype.Controller;


@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class ZuulBootApplication {

	public static void main(String[] args) {
		 System.setProperty("spring.config.name", "registration-server");
		SpringApplication.run(ZuulBootApplication.class, args);
		System.out.println("Spring Boot Zuul:");

	}

}
