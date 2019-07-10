package com.sunian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"com.sunian"})
public class CloudUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudUserApplication.class, args);
	}

}
