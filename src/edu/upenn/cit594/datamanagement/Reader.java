package edu.upenn.cit594.datamanagement;
import java.util.Set;

import edu.upenn.cit594.data.ZipCode;

public interface Reader<E> {
	
	//return value not defined yet.
	public Set<E> reader();
}
