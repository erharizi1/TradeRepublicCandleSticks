package com.trbackendchallenge.candlesticks.models;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@Document(collection = "Quotes")
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteEvent  {
	
	
	private String date;
	@JsonProperty("data")
	private Quote data;


	public QuoteEvent(Quote quote) {
		// TODO Auto-generated constructor stub
		this.data = quote;
	}

	public QuoteEvent() {
		
	}



	public Quote getData() {
		return data;
	}

	public void setData(Quote data) {
		this.data = data;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
