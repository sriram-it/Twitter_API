package com.assessment.speer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
public class TwitterApiAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(TwitterApiAssessmentApplication.class, args);
	}

}
