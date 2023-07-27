package com.atorres.nttdata.clientmsf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ClientMsfApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientMsfApplication.class, args);
	}

}
