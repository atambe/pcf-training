package com.spring.boot.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class WelcomeController {

	@Value("${application.message:Hello World}")
	private String message = "Hello World";

	@Autowired
	protected RestTemplate restTemplate;

	public static final String BOOT_SERVICE_URL = "http://boot-service2";

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("time", new Date());

		this.message = restTemplate.getForObject(BOOT_SERVICE_URL + "/welcome",
				String.class);
		System.out.println("Test");
		model.put("message", this.message);
		return "welcome";
	}

}