package com.trbackendchallenge.candlesticks.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@Configuration
@EnableReactiveMongoRepositories
public class MongoDbConfig extends AbstractReactiveMongoConfiguration {

	 	@Value("${spring.data.mongodb.port}")
	    private String port;

	    @Value("${spring.data.mongodb.database}")
	    private String dbName;

	    @Override
	    public MongoClient reactiveMongoClient() {
	        return MongoClients.create();
	    }

	    @Override
	    protected String getDatabaseName() {
	        return dbName;
	    }

	    @Bean
	    public ReactiveMongoTemplate reactiveMongoTemplate() {
	        return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
	    }
	

}
