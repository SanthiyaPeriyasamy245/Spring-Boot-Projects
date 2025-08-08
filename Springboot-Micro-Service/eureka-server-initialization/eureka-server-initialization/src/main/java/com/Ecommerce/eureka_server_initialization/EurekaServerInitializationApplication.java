package com.Ecommerce.eureka_server_initialization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerInitializationApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerInitializationApplication.class, args);
	}

}
