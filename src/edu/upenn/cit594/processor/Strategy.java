package edu.upenn.cit594.processor;

import edu.upenn.cit594.data.ZipCode;

/**
 * Strategy design interface. Modifies in runtime the calculateRatio() method in Processor abstract class
 * @author jcwuz
 *
 */
public interface Strategy {
	public double getNumerator(ZipCode target);
	public double getDenominator(ZipCode target);
	public String getStrategyType();
}
