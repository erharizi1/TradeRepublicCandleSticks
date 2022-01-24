package com.trbackendchallenge.candlesticks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class CandlesticksApplication {

	public static void main(String[] args) {
		SpringApplication.run(CandlesticksApplication.class, args);
	}

}
