package com.trbackendchallenge.candlesticks.repository;

import org.springframework.stereotype.Repository;

import com.trbackendchallenge.candlesticks.models.CandleStick;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;

@Repository
public interface CandleStickRepository extends ReactiveMongoRepository<CandleStick, Long> {

	Mono<CandleStick> findByIsin(String isin);
	
	Flux<CandleStick> findAllByIsin(String isin);
	
	Flux<CandleStick> deleteByIsin(String isin);
 	
	Mono<Boolean>  existsByIsin(String isin);
	
	@Query("{isin : ?0, opendate : ?1}")
	Mono<Boolean>  existsByIsinAndOpenDate(String isin, String opendate);

	
	Mono<CandleStick> findFirstByIsinOrderByHighPriceDesc(String isin);
	
	@Query("{isin : ?0, opendate : ?1}")
	Mono<CandleStick> findByIsinAndOpenDate(String isin , String openprice);
	
	
	Mono<CandleStick> findFirstByIsinOrderByLowPriceAsc(String isin);
	
	Mono<CandleStick> findFirstByIsinOrderByOpendateDesc(String isin);
	
	
//	@Query("SELECT c FROM candleStick c WHERE (c.isin) AND (c.opendate);")
//	Mono<CandleStick> findByIsinAndOpenDate(String isin , String openprice);

	Flux<CandleStick> findByIsinAndOpendate(String isin , Instant instant);
	
	Flux<CandleStick> findByOpendate(Instant instant);
	
	Flux<CandleStick> findByOpenPrice(double price);
	
//	Flux<CandleStick> findByIsinWhereOpenPriceEquals(String isin , double instant);
	
	
	@Aggregation(pipeline = { "{ $match : { openTimestamp : ?0 } }", "{ $count : total }" })
	void findAllByStartDateLessThanEqualAndEndDateGreaterThanEqual();
	
	@Query(value = "SELECT * FROM CANDLESTICK WHERE OPENTIMESTAMP = :opentime")
	Flux<CandleStick> findByMinute(@Param("opentime") Instant instant);


	@Query("{'timestamp' : { $gte: ?0, $lte: ?1 } }")                 
	public Flux<CandleStick> findAllByStartDateLessThanEqualAndEndDate(Instant from, Instant to); 
}
