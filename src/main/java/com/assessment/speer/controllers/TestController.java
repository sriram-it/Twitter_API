package com.assessment.speer.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	 public TestController() {
		 System.out.println("TEST CONTROLLER CREATED");
	 }

	@RequestMapping("/test")
	public String test() {
		return "Hey! It's me";
	}
}
