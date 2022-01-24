package com.trbackendchallenge.candlesticks.models;

import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CandleStick {
	
	
	
	public CandleStick(String isin, String opendate, String closedate, double openPrice, double highPrice,
			double lowPrice, double closingPrice) {
		super();
		this.isin = isin;
		this.opendate = opendate;
		this.closedate = closedate;
		this.openPrice = openPrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.closingPrice = closingPrice;
	}
	public CandleStick() {
		// TODO Auto-generated constructor stub
	}
	@Id
	private String _id;
	private String isin;
	private String opendate;
	private String closedate;
	private double openPrice;
	private double highPrice;
	private double lowPrice;
	private double closingPrice;

	public double getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(double openPrice) {
		this.openPrice = openPrice;
	}
	public double getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(double highPrice) {
		this.highPrice = highPrice;
	}
	public double getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(double lowPrice) {
		this.lowPrice = lowPrice;
	}
	public double getClosingPrice() {
		return closingPrice;
	}
	public void setClosingPrice(double closingPrice) {
		this.closingPrice = closingPrice;
	}
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public String getOpendate() {
		return opendate;
	}
	public void setOpendate(String opendate) {
		this.opendate = opendate;
	}
	public String getClosedate() {
		return closedate;
	}
	public void setClosedate(String closedate) {
		this.closedate = closedate;
	}



}
