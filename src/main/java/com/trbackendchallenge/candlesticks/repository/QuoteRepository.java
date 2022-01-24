package com.trbackendchallenge.candlesticks.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.trbackendchallenge.candlesticks.models.CandleStick;
import com.trbackendchallenge.candlesticks.models.QuoteEvent;

import reactor.core.publisher.Mono;

@Repository
public interface QuoteRepository  extends ReactiveMongoRepository<QuoteEvent, Long>{

	
	 Mono<QuoteEvent> save( QuoteEvent quote);
}
