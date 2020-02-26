package com.parserdigital.test.util;

import java.time.LocalDate;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Alex
 * 
 *         {"rates":{"EUR":1.1974613819},"base":"GBP","date":"2020-02-21"}
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversionResponse {

	private Map<String, Double> rates;
	private String base;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	public ConversionResponse() {
	}

	/**
	 * @return the base
	 */
	public String getBase() {
		return base;
	}

	/**
	 * @param base the base to set
	 */
	public void setBase(String base) {
		this.base = base;
	}

	/**
	 * @return the rates
	 */
	public Map<String, Double> getRates() {
		return rates;
	}

	/**
	 * @param rates the rates to set
	 */
	public void setRates(Map<String, Double> rates) {
		this.rates = rates;
	}

	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * @return String representation of ConversionResponse
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Response [{\"rates\":{\"");
		rates.forEach((k, v) -> {
			sb.append(k);
			sb.append("\":");
			sb.append(v);
			sb.append(",");
		});
		sb.append("},\"base\":\"");
		sb.append(base);
		sb.append("\",\"date\":\"");
		sb.append(date);
		sb.append("\"}]");

		return sb.toString();
	}

	/**
	 * @param currency
	 * @return the rate corrresponding to the currency
	 */
	public double getRate(String currency) {
		return rates.get(currency).doubleValue();
	}
}
