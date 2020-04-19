package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.PopulationFileReader;
import edu.upenn.cit594.datamanagement.Reader;

import java.util.*;
import edu.upenn.cit594.data.*;
import edu.upenn.cit594.data.Properties;

public abstract class Processor2 {

	protected Reader finesReader;
	protected Reader propertiesReader;
	protected Reader populationReader = new PopulationFileReader();
	protected Map<Integer, ZipCode> zipCodes;
	protected Map<Integer, LinkedList<Properties>> zipProperties;
	protected Map<Integer, LinkedList<ParkingFine>> zipParkingFines;
	
	
	public Processor2() {
		finesReader = createReader();
		propertiesReader = createReader();
	}
	
	protected abstract Reader createReader();
	
	public void run(String populationFilename, String propertiesFilename, String finesFilename) {
		makeZipKeys(populationFilename);
		placeProperties(propertiesFilename);
		placeParkingFines(finesFilename);
		// DO WHATEVER ELSE
	}
	
	// Make the memo tables with key as valid zips
	public void makeZipKeys(String populationFilename) {
		List<ZipCode> populations = populationReader.read(populationFilename);
		for(ZipCode zip : populations) {
			zipProperties.put(zip.getCode(), null);
			zipParkingFines.put(zip.getCode(), null);
		}
	}
	
	// Place properties into correct key. Update zip instance variables in memo
	public void placeProperties(String propertiesFile) {
		List<Properties> properties = propertiesReader.read(propertiesFile);
		for(Properties p : properties) {
			if(zipProperties.containsKey(p.getZipcode())) {
				zipProperties.get(p.getZipcode()).add(p); // APPEND TO LINKED LIST
				ZipCode curr = zipCodes.get(p.getZipcode());
				curr.setTotalNumberProperties(curr.getTotalNumberProperties() + 1);
				curr.setTotalPropertyLivableArea(curr.getTotalPropertyLivableArea() + p.getTotalLivableArea());
				curr.setTotalPropertyValue(curr.getTotalPropertyValue() + p.getMarketValue());
			}
		}
	}
	
	// Place parkingfines into correct key. Update zip instance varibles in memo
	public void placeParkingFines(String finesFile) {
		List<ParkingFine> pFines = finesReader.read(finesFile);
		for(ParkingFine p : pFines) {
			if(zipParkingFines.containsKey(p.getZipcode())) {
				zipParkingFines.get(p.getZipcode()).add(p); // APPEND TO LINKED LIST
				ZipCode curr = zipCodes.get(p.getZipcode());
				curr.setTotalParkingTickets(curr.getTotalParkingTickets() + 1);
				curr.setTotalParkingTicketFines(curr.getTotalParkingTicketFines() + p.getFine());
			}
		}
	}
	
	public int calculateTotalPopulation() {
		int totalPopulation = 0;
		for(int i : zipCodes.keySet()) {
			totalPopulation += zipCodes.get(i).getTotalPopulation();
		}
		return totalPopulation; // NEED TO MEMO THIS
	}
	
	public boolean validZip(int zipCode) {
		return zipProperties.containsKey(zipCode);
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
