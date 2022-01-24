package com.trbackendchallenge.candlesticks.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Instrument {
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("isin")
	private String isin;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}
	


}
