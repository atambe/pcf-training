package com.pcf.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PcfDemoApplication {

	
	@RequestMapping("/hello")
	public String method()
	{
		
		return "HELOO WORLD";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(PcfDemoApplication.class, args);
		System.out.println("this is from argument" + args[0].toString());
	}
}
