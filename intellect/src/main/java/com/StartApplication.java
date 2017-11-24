package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import utilities.UserUtility;

@ComponentScan
@EnableJpaRepositories
@SpringBootApplication
public class StartApplication {

	private final static Logger Log = LoggerFactory.getLogger(UserUtility.class);

	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
		Log.info("Application Started Successfully!!");
	}
}

