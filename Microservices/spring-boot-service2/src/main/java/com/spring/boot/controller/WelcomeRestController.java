package com.spring.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeRestController {

	private String message = "Hello Test";

	@RequestMapping("/welcome")
	public String welcome() {
		System.out.println("welcome2");
		return message;
	}

}