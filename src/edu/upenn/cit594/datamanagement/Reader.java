package edu.upenn.cit594.datamanagement;
import java.util.List;


public interface Reader<E> {
	public List<E> read(String filename);
}
