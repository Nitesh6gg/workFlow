package com.workFlow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.workFlow.config")
public class WorkFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkFlowApplication.class, args);
	}

}
