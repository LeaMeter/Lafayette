package com.example.workflow.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HnbExchangeInfo {

	@JsonProperty("broj_tecajnice")
	public String dailyRate;
	@JsonProperty("datum_primjene")
	public String applicationDate;
	@JsonProperty("drzava")
	public String country;
	@JsonProperty("drzava_iso")
	public String countryISO;
	@JsonProperty("sifra_valute")
	public String currencyCode;
	@JsonProperty("valuta")
	public String currency;
	@JsonProperty("kupovni_tecaj")
	public String purchaseRate;
	@JsonProperty("srednji_tecaj")
	public String middleRate;
	@JsonProperty("prodajni_tecaj")
	public String sellRate;

	public HnbExchangeInfo() {
	}

	public HnbExchangeInfo(String dailyRate, String applicationDate, String country, String countryISO,
			String currencyCode, String currency, String purchaseRate, String middleRate, String sellRate) {
		this.dailyRate = dailyRate;
		this.applicationDate = applicationDate;
		this.country = country;
		this.countryISO = countryISO;
		this.currencyCode = currencyCode;
		this.currency = currency;
		this.purchaseRate = purchaseRate;
		this.middleRate = middleRate;
		this.sellRate = sellRate;
	}

	public String getMiddleRate() {
		return middleRate;
	}
}
