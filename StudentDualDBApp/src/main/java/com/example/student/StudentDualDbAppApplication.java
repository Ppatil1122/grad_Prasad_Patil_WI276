package com.example.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class StudentDualDbAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentDualDbAppApplication.class, args);
	}

}
