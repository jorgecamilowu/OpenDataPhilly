package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.Reader;

public abstract class Processor {
	protected Reader reader;
	
	public Processor() {
		reader = createReader();
	}
	
	protected abstract Reader createReader();
	
}
