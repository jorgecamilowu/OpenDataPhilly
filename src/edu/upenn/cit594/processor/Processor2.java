package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.PopulationFileReader;
import edu.upenn.cit594.datamanagement.Reader;

import java.util.*;
import edu.upenn.cit594.data.*;
import edu.upenn.cit594.data.Properties;

public abstract class Processor2 {

	protected Reader finesReader;
	protected Reader populationReader = new PopulationFileReader();
	protected Set<ZipCode> zipCodes;
	protected Map<Integer, LinkedList<Properties>> zipProperties;
	protected Map<Integer, LinkedList<ParkingFine>> zipParkingFines;
	
	
	public Processor2() {
		finesReader = createReader();
	}
	
	protected abstract Reader createReader();
	
	public void makeZipKeys(String populationFilename) {
		List<ZipCode> populations = populationReader.read(populationFilename);
		for(ZipCode zip : populations) {
			zipProperties.put(zip.getCode(), null);
			zipParkingFines.put(zip.getCode(), null);
		}
	}
	
	public void placeProperties(String finesFile) {
		List<Properties> properties = finesReader.read(finesFile);
		
	}
	
	public void placeParkingFines() {
		
	}
	
	
	public int calculateTotalPopulation() {
		int totalPopulation = 0;
		for(ZipCode z : zipProperties.keySet()) {
			totalPopulation += z.getTotalPopulation();
		}
		return totalPopulation; // NEED TO MEMO THIS
	}
	
	public boolean validZip(int zipCode) {
		return zipCodes.contains(zipCode);
	}
	
//	public Map<Integer, Double> calculateTotalFinesPerCapita(int zipCode) {
//		
//		Map<Integer, Double> finesPerCapita = new HashMap<>();
//		List<ParkingFine> parkingFines = finesReader.read(finesFilename);
//		for(ZipCode z : ans.keySet()) {
//			
//		}
//		// if(validZip(zipCode)) return 0;
//	}
	
	
	
	
}
