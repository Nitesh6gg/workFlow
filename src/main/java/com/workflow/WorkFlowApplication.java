package com.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.workflow.config")
public class WorkFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkFlowApplication.class, args);
	}

}
