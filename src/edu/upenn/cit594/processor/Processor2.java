package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.CSVPropertiesReader;
import edu.upenn.cit594.datamanagement.PopulationFileReader;
import edu.upenn.cit594.datamanagement.Reader;

import java.text.DecimalFormat;
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
		/**
		 * createReader will return a CSV or JSON Parking Fines reader. For properties
		 * reader we'll probably need to instantiate it separately. Or we create
		 * another method that will instantiate it the same fashion as createReader()
		 */
//		propertiesReader = createReader();
		propertiesReader = new CSVPropertiesReader();
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
		for(Properties property : properties) {
			if(validZip(property.getZipcode())) {
				zipProperties.get(property.getZipcode()).add(property); // APPEND TO LINKED LIST
				ZipCode curr = zipCodes.get(property.getZipcode());
				curr.setTotalNumberProperties(curr.getTotalNumberProperties() + 1);
				curr.setTotalPropertyLivableArea(curr.getTotalPropertyLivableArea() + property.getTotalLivableArea());
				curr.setTotalPropertyValue(curr.getTotalPropertyValue() + property.getMarketValue());
			}
		}
	}
	
	// Place parkingfines into correct key. Update zip instance varibles in memo
	public void placeParkingFines(String finesFile) {
		List<ParkingFine> parkingFines = finesReader.read(finesFile);
		for(ParkingFine parkingFine : parkingFines) {
			
			int propertyCode = parkingFine.getZipcode();
			
			//Check that zip is valid and license plate is PA
			if(validZip(propertyCode) && (parkingFine.getStateLicensePlate().equals("PA"))) {
				zipParkingFines.get(propertyCode).add(parkingFine); // APPEND TO LINKED LIST
				
				
				/**
				 * there might be a problem here. We are instantiating a new object and modifying
				 * the new object. Maybe it is not updating the memoized one?
				 * 
				 * Not sure if we are handling with a pointer to the object or we are just modifying the 
				 * newly instantiated ZipCode object curr
				 */
//				ZipCode curr = zipCodes.get(property.getZipcode());
//				curr.setTotalParkingTickets(curr.getTotalParkingTickets() + 1);
//				curr.setTotalParkingTicketFines(curr.getTotalParkingTicketFines() + property.getFine());
				
				int currentTotalParkingTickets = zipCodes.get(parkingFine.getZipcode()).getTotalParkingTickets(); //memoized running total tickets
				double currentTotalFines = zipCodes.get(parkingFine.getZipcode()).getTotalParkingTicketFines(); //memoozed running total fine amount
				double currentPropertyFine = parkingFine.getFine(); //current property's fine amount
				zipCodes.get(parkingFine.getZipcode()).setTotalParkingTickets(currentTotalParkingTickets + 1); //update running total on memoized object
				zipCodes.get(parkingFine.getZipcode()).setTotalParkingTicketFines(currentTotalFines + currentPropertyFine); //update running total fine amount on memoized object
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
	
	
	// NUMBER 2
	public Map<Integer, String> calculateTotalFinesPerCapita(int zipCode) {		
		// must be written in ascending numerical order, 4 digits after decimal point
		if(!validZip(zipCode)) return null;
		TreeMap<Integer, String> ans = new TreeMap<>();
		for(int z : zipCodes.keySet()) {
			double finesPerCapita = zipCodes.get(z).getTotalParkingTicketFines() / zipCodes.get(z).getTotalPopulation();
			if(finesPerCapita > 0.00009) {
				ans.put(z, truncateToString(finesPerCapita));
			}
		}
		return ans;
	}
	
	// NUMBER 5
	public int calculateTotalResidentialMarketValuePerCapita(int zipCode) {
		if(!validZip(zipCode)) return 0;
		return (int) Math.floor(zipCodes.get(zipCode).getTotalPropertyValue() / zipCodes.get(zipCode).getTotalPopulation());
	}
	
	///////////////private helper methods///////////////
	private boolean validZip(int zipCode) {
		return zipProperties.containsKey(zipCode);
	}
	
	private static String truncateToString(double d) {
		DecimalFormat df = new DecimalFormat("#.####");
		return df.format(d);
	}
	
	
	
	
}
