package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.Reader;
import edu.upenn.cit594.datamanagement.JSONParkingReader;;

public class JSONProcessor extends Processor {

	@Override
	protected Reader createReader() {
		return new JSONParkingReader();
	}

}
