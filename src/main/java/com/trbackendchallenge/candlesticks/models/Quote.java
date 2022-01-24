package com.trbackendchallenge.candlesticks.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
	

	@JsonProperty("isin")
	private String isin;
	@JsonProperty("price")
	private double price;
	
	public Quote(String isin, double d) {
		// TODO Auto-generated constructor stub
		this.isin = isin;
		this.price = d;
		
	}
	
	
	public Quote() {
		
	}
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

}
