package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.ZipCode;

/**
 * Specifies method calculateRatio() to produce a ratio of total residential 
 * livable area over total number of properties.
 */
public class AreaStrategy implements Strategy {

	@Override
	public double getNumerator(ZipCode target) {
		return target.getTotalPropertyLivableArea();
	}

	@Override
	public double getDenominator(ZipCode target) {
		return target.getTotalNumberProperties();
	}

	@Override
	public String getStrategyType() {
		return "area";
	}

}
