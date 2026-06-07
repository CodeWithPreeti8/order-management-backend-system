package com.example.Order.Management.Backend.System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OrderManagementBackendSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderManagementBackendSystemApplication.class, args);
	}

}
