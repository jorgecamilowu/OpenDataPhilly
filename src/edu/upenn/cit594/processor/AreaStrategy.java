package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.ZipCode;

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
