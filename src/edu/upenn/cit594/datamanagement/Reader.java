package edu.upenn.cit594.datamanagement;
import java.util.List;


public interface Reader<E> {
	
	//return value not defined yet.
	public List<E> read(String filename);
}
