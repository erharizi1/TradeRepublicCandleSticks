package com.trbackendchallenge.candlesticks.config;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

@Component
public class InstrumentStream {
	
	private String url = "ws://localhost:8032/instruments";
	private WebSocket ws ;
	
	

	  /**
     * Connect to the server.
     */
    public  WebSocket connect() throws IOException, WebSocketException
    {
        return new WebSocketFactory()
            .createSocket(url)
            .connect();
    }
    

}
