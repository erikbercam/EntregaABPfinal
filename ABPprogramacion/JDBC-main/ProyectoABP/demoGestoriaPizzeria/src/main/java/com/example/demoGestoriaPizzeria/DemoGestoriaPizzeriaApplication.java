package com.example.demoGestoriaPizzeria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "com.example.demoGestoriaPizzeria.Model")
@EnableJpaRepositories(basePackages = "com.example.demoGestoriaPizzeria.Repository")
@EnableTransactionManagement
public class DemoGestoriaPizzeriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoGestoriaPizzeriaApplication.class, args);
	}
}
