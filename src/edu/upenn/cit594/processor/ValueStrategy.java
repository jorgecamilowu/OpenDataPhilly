package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.ZipCode;

/**
 * Specifies method calculateRatio() to produce a ratio of total residential 
 * market value over total number of properties.
 * @author jcwuz
 *
 */
public class ValueStrategy implements Strategy {

	@Override
	public double getNumerator(ZipCode target) {
		return target.getTotalPropertyValue();
	}

	@Override
	public double getDenominator(ZipCode target) {
		return target.getTotalNumberProperties();
	}

	@Override
	public String getStrategyType() {
		return "value";
	}

}
