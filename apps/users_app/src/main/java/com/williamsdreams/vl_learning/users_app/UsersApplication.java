package com.williamsdreams.vl_learning.users_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.williamsdreams.vl_learning")
@EnableJpaRepositories(basePackages = "com.williamsdreams.vl_learning.auth.infrastructure.persistence.postgresql.repository")
@EntityScan(basePackages = "com.williamsdreams.vl_learning.auth.infrastructure.persistence.postgresql.model")
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

}
