package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.ZipCode;

public interface Strategy {
	public double getNumerator(ZipCode target);
	public double getDenominator(ZipCode target);
}
