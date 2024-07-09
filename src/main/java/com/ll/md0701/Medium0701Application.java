package com.ll.md0701;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Medium0701Application {

	public static void main(String[] args) {
		SpringApplication.run(Medium0701Application.class, args);
	}

}
