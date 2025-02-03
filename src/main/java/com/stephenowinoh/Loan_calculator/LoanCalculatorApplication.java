package com.stephenowinoh.Loan_calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication(exclude = { SessionAutoConfiguration.class})
@EnableJpaRepositories(basePackages = "com.stephenowinoh.Loan_calculator.Repository")
@EntityScan(basePackages = "com.stephenowinoh.Loan_calculator.Entity")
public class LoanCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanCalculatorApplication.class, args);
	}

}
