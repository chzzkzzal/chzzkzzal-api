package com.chzzkzzal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ChzzkzzalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChzzkzzalApplication.class, args);
	}

}
