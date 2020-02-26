/**
 * 
 */
package com.parserdigital.test.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Alex
 *
 */
class ConversionUtilTest {

	@Test
	void test() {
		try {
			ConversionUtil conversionUtil = new ConversionUtil("EUR", "GDB");
			double rate = conversionUtil.getConversionRate();
			assertTrue(rate >= 0);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception in ConversionUtil");
		}
		
	}

}
