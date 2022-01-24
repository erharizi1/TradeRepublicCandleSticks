package com.trbackendchallenge.candlesticks;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.trbackendchallenge.candlesticks.controller.CandleStickChartController;
import com.trbackendchallenge.candlesticks.models.Quote;
import com.trbackendchallenge.candlesticks.models.QuoteEvent;

import reactor.core.publisher.Flux;

@SpringBootTest
class CandlesticksApplicationTests {
	
	@Autowired
	CandleStickChartController con;

	@Test
	void contextLoads() {
		//testQuoteStream();
		
		//testInstrumentStream();
	}

	private void testQuoteStream(){
         Flux.just(
                new QuoteEvent(new Quote("AP5265427051" , 1456.2)),
                new QuoteEvent(new Quote("AP5265427051" , 123.1)),
                new QuoteEvent(new Quote("AP5265427051" , 231.1)),
                new QuoteEvent(new Quote("AP5265427051" , 1255.1)),
                new QuoteEvent(new Quote("AP5265427051" , 1345.6)),
                new QuoteEvent(new Quote("AP5265427051" , 588)),
                new QuoteEvent(new Quote("AP5265427051" , 782.2)),
                new QuoteEvent(new Quote("AP5265427051" , 232)),
                new QuoteEvent(new Quote("AP5265427051" , 885.2)),
                new QuoteEvent(new Quote("AP5265427051" , 1511.1)),
                new QuoteEvent(new Quote("AP5265427051" , 1235)),
                new QuoteEvent(new Quote("AP5265427051" , 1672)),
                new QuoteEvent(new Quote("AP5265427051" , 921)),
                new QuoteEvent(new Quote("AP5265427051" , 352)),
                new QuoteEvent(new Quote("AP5265427051" , 125)),
                new QuoteEvent(new Quote("AP5265427051" , 222)),
                new QuoteEvent(new Quote("AP5265427051" , 588)),
                new QuoteEvent(new Quote("AP5265427051" , 782.2)),
                new QuoteEvent(new Quote("AP5265427051" , 232)),
                new QuoteEvent(new Quote("AP5265427051" , 885.2)),
                new QuoteEvent(new Quote("AP5265427051" , 1511.1)),
                new QuoteEvent(new Quote("AP5265427051" , 1235)))
					.subscribe(c -> {
						/* con.handleStreamOfDataStocks(c) */;});
    }
	
	

	
			private void testInstrumentStream(){
		         Flux.just()
		             
							.subscribe(c -> {
								/* con.handleStreamOfDataStocks(c) */;});
		    }
			
	 
}
