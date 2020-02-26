/**
 * 
 */
package com.parserdigital.test.util;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alex
 *
 */
@Component
public class ConversionUtil {

	private static final Logger log = LoggerFactory.getLogger(ConversionUtil.class);

	private String currencyOrigin;
	private String currencyDestination;

	private final String apiURL = "https://api.exchangeratesapi.io/latest?base=CU1&symbols=CU2";

	/**
	 * Default constructor
	 */
	public ConversionUtil() {

	}

	/**
	 * @param currencyOrigin
	 * @param currencyDestination
	 */
	public ConversionUtil(String currencyOrigin, String currencyDestination) {
		this.currencyOrigin = currencyOrigin;
		this.currencyDestination = currencyDestination;
	}

	/**
	 * @return conversion rate
	 */
	public double getConversionRate() {
		double result = 1.0;

		RestTemplate restTemplate = new RestTemplate();
		ConversionResponse response;

		try {
			String url = apiURL.replace("CU1", currencyOrigin).replace("CU2", currencyDestination);
			response = restTemplate.getForObject(url, ConversionResponse.class);
			log.info("==== RESTful API Response using Spring RESTTemplate START =======");
			log.info(response.toString());
			log.info("==== RESTful API Response using Spring RESTTemplate END =======");
			result = response.getRate(currencyDestination);
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

}
