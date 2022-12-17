package com.example.exampleservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ExampleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleServiceApplication.class, args);
	}

}

@RestController
class ExampleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExampleController.class);

	@GetMapping("/hello-world")
	String hello() {
		return "Hello, World!";
	}

}
