package io.github.barbosa.costumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CostumerApplication {
	private static final Logger logger = LoggerFactory.getLogger(CostumerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CostumerApplication.class, args);
		logger.info("Funcionando");
	}
}

