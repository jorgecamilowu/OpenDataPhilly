package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.ZipCode;

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
