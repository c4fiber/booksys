package com.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MainController {

	public static void main(String[] args) {
		SpringApplication.run(MainController.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}
	
	@GetMapping("/test")
	public String test1(@RequestParam(value = "test", defaultValue = "TESTING!") String name) {
		return String.format("TESTING NOW!!!! =  %s", name);
	}
	
	@GetMapping("/error")
	public String error() {
		return String.format("ERROR OCCURED!!!!");
	}
	
}
