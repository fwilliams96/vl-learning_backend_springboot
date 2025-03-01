package com.williamsdreams.vl_learning.gateway_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"com.williamsdreams.vl_learning.gateway_app",
		"com.williamsdreams.vl_learning.auth"
})
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
