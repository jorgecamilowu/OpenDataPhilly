package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.datamanagement.CSVPropertiesReader;;

public class CSVProcessor extends Processor {

	@Override
	protected Reader createReader() {
		return new CSVPropertiesReader();
	}

}
