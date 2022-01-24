package com.trbackendchallenge.candlesticks.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.trbackendchallenge.candlesticks.models.CandleStick;
import com.trbackendchallenge.candlesticks.repository.CandleStickRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ChartService {
	
	@Autowired
	private  ReactiveMongoTemplate reactiveDb;
	
	@Autowired
    private MongoTemplate mongodbrepo;
	
	@Autowired
	public CandleStickRepository candlerepo;
	
	
	
	 public List<CandleStick> findByIsin(String isin){
	        Query query = new Query();
	        query.addCriteria(Criteria.where("isin").is(isin));
	        return mongodbrepo.find(query, CandleStick.class);
	  }
	 
	 
		public Mono<Double> getHighPriceForInstrument(String isin) {
			
			return candlerepo.findFirstByIsinOrderByHighPriceDesc(isin).map(p -> {
				return p.getHighPrice();
	   		});
		
		}
		
		public Mono<Double> getLowPriceForInstrument(String isin) {
			
			return candlerepo.findFirstByIsinOrderByLowPriceAsc(isin).map(p -> {
				return p.getLowPrice();
	   		});
		
		}
		
		public Mono<Double> getOpenPriceForInstrument(String isin) {
			
			return candlerepo.findFirstByIsinOrderByOpendateDesc(isin).map(p -> {
				return p.getOpenPrice();
	   		});
		
		}
		
		
		 public Mono<CandleStick> update(CandleStick candle){
		        Query query = new Query();
		        query.addCriteria(Criteria.where("isin").is(candle.getIsin()).andOperator(Criteria.where("opendate").is(candle.getOpendate())));
		        Update update = new Update();
		        update.set("highPrice", candle.getHighPrice());
		        update.set("lowPrice", candle.getLowPrice());
		        update.set("closingPrice", candle.getClosingPrice());
		        return reactiveDb.findAndModify(query, update, CandleStick.class);
		    }
		 
		 
		 public Flux<CandleStick> deleteAll(String isin){
		        Query query = new Query();
		        query.addCriteria(Criteria.where("isin").is(isin));
		       
		        return reactiveDb.findAllAndRemove(query, CandleStick.class , "isin" );
		    }
		 
			public String calculateClosedDate(String date) {
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				 String closeDate = null;
				try {
					 Date d = df.parse(date); 
					 Calendar cal = Calendar.getInstance();
					 cal.setTime(d);
					 cal.add(Calendar.MINUTE, 1);
					 closeDate = df.format(cal.getTime());
					 
					 
					 System.out.println(closeDate);
					 
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				return closeDate;
				
			}
	 

}
