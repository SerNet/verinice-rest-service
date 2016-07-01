package org.verinice.service.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"org.verinice.service","org.verinice.persistence"})
@EntityScan("org.verinice.persistence.entities")
public class ServiceApplicationTest {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplicationTest.class, args);
	}
}
