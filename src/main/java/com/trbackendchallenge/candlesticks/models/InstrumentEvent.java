package com.trbackendchallenge.candlesticks.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InstrumentEvent {
	
	@JsonProperty("data")
	private Instrument data;
	
	@JsonProperty("type")
	private Type type;

	public Instrument getData() {
		return data;
	}

	public void setData(Instrument data) {
		this.data = data;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	
}
