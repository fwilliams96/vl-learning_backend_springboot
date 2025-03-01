package com.williamsdreams.vl_learning.users_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
		"com.williamsdreams.vl_learning.users_app",
		"com.williamsdreams.vl_learning.users"
})
@EnableJpaRepositories(basePackages = {
		"com.williamsdreams.vl_learning.users.infrastructure.persistence.postgresql.repository",
		"com.williamsdreams.vl_learning.users.descriptions.infrastructure.persistence.postgresql.repository",
		"com.williamsdreams.vl_learning.users.readings.infrastructure.persistence.postgres.repository",
		"com.williamsdreams.vl_learning.users.listenings.infrastructure.persistence.postgres.repository"
})
@EntityScan(basePackages = {
		"com.williamsdreams.vl_learning.users.infrastructure.persistence.postgresql.entity",
		"com.williamsdreams.vl_learning.users.descriptions.infrastructure.persistence.postgresql.entity",
		"com.williamsdreams.vl_learning.users.readings.infrastructure.persistence.postgres.entity",
		"com.williamsdreams.vl_learning.users.listenings.infrastructure.persistence.postgres.entity"
})
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

}
