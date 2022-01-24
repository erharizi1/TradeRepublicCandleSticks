package com.trbackendchallenge.candlesticks.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.trbackendchallenge.candlesticks.config.InstrumentStream;
import com.trbackendchallenge.candlesticks.config.QuoteStream;
import com.trbackendchallenge.candlesticks.models.CandleStick;
import com.trbackendchallenge.candlesticks.models.DataPointChart;
import com.trbackendchallenge.candlesticks.models.InstrumentEvent;
import com.trbackendchallenge.candlesticks.models.QuoteEvent;
import com.trbackendchallenge.candlesticks.models.Type;
import com.trbackendchallenge.candlesticks.repository.CandleStickRepository;
import com.trbackendchallenge.candlesticks.service.ChartService;

import reactor.core.publisher.Mono;

@Controller
public class CandleStickChartController {

	Logger logger = LoggerFactory.getLogger(CandleStickChartController.class);

	@Autowired
	public CandleStickRepository candlerepo;

	@Autowired
	public ChartService service;

	@Autowired
	public QuoteStream streamOfQuotes;

	@Autowired
	public InstrumentStream streamOfInstruments;
	
	
	
	/*
	 * The way that i have handled the stream of data and convert them in minute
	 * candleSticks chart goes like this. For each quote stream i have recieved i
	 * have attach them timestamp(string) then first i check if there is some
	 * candleStick existing already for the quote that correspond of some instrument
	 * , still check if there exist candlestick on the minute(date) that i get the
	 * quote if candlestick exisit for that minute i update the price otherwise i
	 * create a new candlestick for that minute . If instrument dont exist in db , i
	 * just create totally new candlestick and save them in reactive way.
	 * 
	 * (Assumption is that for open price i have kept the openprice of the opening candlestick)
	 * 
	 * (i have handle two stream of socket with "newvisionaries" socket client one for quotes and one for instrument)
	 */

	@RequestMapping("")
	public void handleStreamOfDataStocks(QuoteEvent quote) throws IOException, WebSocketException {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		new WebSocketFactory().createSocket("ws://localhost:8032/quotes").addListener(new WebSocketAdapter() {
			ArrayList<QuoteEvent> list = new ArrayList<>();

			// A text message arrived from the server.
			public void onTextMessage(WebSocket websocket, String message) {

				try {

					System.out.println(message);

					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					String str = timestamp.toString().substring(0, 16);
					QuoteEvent quote = objectMapper.readValue(message, QuoteEvent.class);
					quote.setDate(str);

					candlerepo.existsByIsin(quote.getData().getIsin()).subscribe(instrumentExist -> {
						if (instrumentExist) {
							System.out.println("exist ");
							System.out.println(quote.getData().getIsin());

							candlerepo.findByIsinAndOpenDate(quote.getData().getIsin(), quote.getDate()).hasElement()
									.subscribe(ca -> {

										if (ca) {
											candlerepo.findByIsinAndOpenDate(quote.getData().getIsin(), quote.getDate())
													.flatMap(candle -> {

														logger.info(
																"Candlestick for this Quote exist in this minute so update the price ---"
																		+ quote.getData().getIsin());

														service.getHighPriceForInstrument(quote.getData().getIsin())
																.subscribe(highprice -> {
																	service.getLowPriceForInstrument(
																			quote.getData().getIsin())
																			.subscribe(lowprice -> {

																				Double max = Double.max(highprice,
																						candle.getHighPrice());

																				Double low = Double.min(lowprice,
																						candle.getLowPrice());

																				candle.setHighPrice(highprice);
																				candle.setLowPrice(lowprice);
																				candle.setClosingPrice(
																						quote.getData().getPrice());

																				service.update(candle).subscribe();

																			});

																});
														return Mono.just(candle);
													}).subscribe(candle -> {
														System.out.print(candle.getIsin());
													});

										} else {

											logger.info("Quote dont exist in this minute so create new candlestick ---"
													+ quote.getData().getIsin());

											CandleStick newcandle = new CandleStick();

											service.getHighPriceForInstrument(quote.getData().getIsin())
													.subscribe(highprice -> {
														service.getLowPriceForInstrument(quote.getData().getIsin())
																.subscribe(lowprice -> {
																	service.getLowPriceForInstrument(
																			quote.getData().getIsin())
																			.subscribe(openprice -> {

																				Double max = Double.max(highprice,quote.getData().getPrice());

																				Double low = Double.min(lowprice,quote.getData().getPrice());

																				newcandle.setIsin(
																						quote.getData().getIsin());
																				newcandle.setOpendate(quote.getDate());
																				newcandle.setClosedate(
																						service.calculateClosedDate(
																								quote.getDate()));

																				newcandle.setHighPrice(max);
																				newcandle.setLowPrice(low);
																				newcandle.setOpenPrice(openprice);

																				newcandle.setClosingPrice(
																						quote.getData().getPrice());

																				candlerepo.save(newcandle).subscribe();

																			});

																});
													});

										}

									});
						} else {
							logger.info("Quote dont exist so create new candlestick ---" + quote.getData().getIsin());
							CandleStick newcandle = new CandleStick();
							newcandle.setIsin(quote.getData().getIsin());
							newcandle.setOpendate(quote.getDate());
							newcandle.setClosedate(service.calculateClosedDate(quote.getDate()));
							newcandle.setHighPrice(quote.getData().getPrice());
							newcandle.setLowPrice(quote.getData().getPrice());
							newcandle.setOpenPrice(quote.getData().getPrice());
							newcandle.setClosingPrice(quote.getData().getPrice());

							candlerepo.save(newcandle).subscribe();

						}
					});

				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).connect();

		new WebSocketFactory().createSocket("ws://localhost:8032/instruments").addListener(new WebSocketAdapter() { 
			public void onTextMessage(WebSocket websocket, String message)
					throws JsonMappingException, JsonProcessingException {

				InstrumentEvent instruments = objectMapper.readValue(message, InstrumentEvent.class);

				System.out.println(instruments);

				if (instruments.getType().equals(Type.DELETE)) {
					logger.info("Delete  al the instrument from db ---" + instruments.getData().getIsin());
					candlerepo.findAllByIsin(instruments.getData().getIsin()).flatMap(c -> {
						candlerepo.delete(c).subscribe();

						return Mono.just(c);
					}).subscribe(candle -> {
						System.out.print(candle.getIsin());
					});

				}

			}
		}).connect();

	}
	
	/*
	 * I have used google charts to display charts for the instrument
	 */
	@GetMapping("/candlesticks")
	public String index(ModelMap modelMap, @RequestParam("isin") String isin) {

		System.out.println(isin);

		Map<String, double[]> mapDE = new HashMap<String, double[]>();
		List<CandleStick> list = service.findByIsin(isin);

		for (CandleStick c : list) {

			mapDE.put(c.getOpendate(),
					new double[] { c.getLowPrice(), c.getOpenPrice(), c.getClosingPrice(), c.getHighPrice() });
		}
		modelMap.put("data", mapDE);

		return "index";
	}

}
