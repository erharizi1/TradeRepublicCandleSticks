package com.trbackendchallenge.candlesticks.config;

import java.io.IOException;
import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.trbackendchallenge.candlesticks.models.QuoteEvent;

@Component
public class QuoteStream {

	private  String urlInstrumentStreams = "ws://localhost:8032/instruments";
	
	private String url = "ws://localhost:8032/quotes";
	
	  /**
     * Connect to the server.
     */
    public  WebSocket connect() throws IOException, WebSocketException
    {
        return new WebSocketFactory()
            .createSocket(url)
            .connect();
        
    }
    
    
	public WebSocket testStream() throws WebSocketException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		return new WebSocketFactory().createSocket("ws://localhost:8032/quotes").addListener(new WebSocketAdapter() {
			
			// A text message arrived from the server.
			public void onTextMessage(WebSocket websocket, String message) {
				try {
					System.out.println("events hyri");

					System.out.println(message);

					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					String str = timestamp.toString().substring(0, 16);// (yes , i know)
					QuoteEvent quote = objectMapper.readValue(message, QuoteEvent.class);
					quote.setDate(str);
				
				

				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			}
		}).connect();

	}
}
