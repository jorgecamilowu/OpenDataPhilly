package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.datamanagement.CSVParkingReader;

public class CSVProcessor extends Processor {

	@Override
	protected Reader createReader() {
		return new CSVParkingReader();
	}

}
