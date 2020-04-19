package edu.upenn.cit594.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.upenn.cit594.data.ParkingFine;
import edu.upenn.cit594.data.ZipCode;
import edu.upenn.cit594.datamanagement.PopulationFileReader;
import edu.upenn.cit594.datamanagement.Reader;

public abstract class Processor {
	protected Reader finesReader;
	protected Reader populationReader = new PopulationFileReader();

	
	//Memoization cache of computed data. For now defining as individual key value pairs. 
	protected int totalPopulation = 0;
	protected Map<Integer, Integer> populationByZipcode;
	protected List<ParkingFine> parkingFines;
	protected Map<Integer, Double> finesPerCapita;
	
	public Processor() {
		finesReader = createReader();
	}
	
	
	protected abstract Reader createReader();
	
	////////METHODS///////////
	
	/**
	 * maybe arg filanme should not be passed directly to method. 
	 * Potentially have another method to handle requests.
	 * 
	 * @param populationFilename
	 */
	public int calculateTotalPopulation(String populationFilename) {
		if(this.totalPopulation != 0) {
			return this.totalPopulation;
		}
		List<ZipCode> populations = populationReader.read(populationFilename);
		for(ZipCode element : populations) {
			this.totalPopulation += element.getTotalPopulation();
		}
		return this.totalPopulation;
	}
	
	private Map<Integer, Integer> getPopulationsByZip(String populationFilename) {
		if(this.populationByZipcode != null) {
			return this.populationByZipcode;
		}
		
		this.populationByZipcode = new HashMap<>();
		List<ZipCode> zipcodes = populationReader.read(populationFilename);
		for(ZipCode element : zipcodes) {
			this.populationByZipcode.put(element.getCode(), element.getTotalPopulation());
		}
//		System.out.println(populationByZipcode);
		return this.populationByZipcode;
	}
	
	public Map<Integer, Double> calculateTotalFinesCapita(String finesFilename) {
		if(this.finesPerCapita != null) {
			return this.finesPerCapita;
		}
		
		//Retrieve list of parking fines
		//Only read from data set if it wasn't pre-computed.
		if(parkingFines == null) {
			parkingFines = finesReader.read(finesFilename);
		}
		
		//Calculate running total fines per zipcode area
		Map<Integer, Double> finesPerZip = new HashMap<>();
		for(ParkingFine element : parkingFines) {
			int currentZip = element.getZipcode();
			double currentFineAmount = element.getFine();
			
			//if the zipCode was already in the map, update its running total fine amount.
			if(finesPerZip.containsKey(currentZip)) {
				double newFineAmount = currentFineAmount;
				newFineAmount += finesPerZip.get(currentZip);
				finesPerZip.put(currentZip, newFineAmount);
			}
			//otherwise, record it.
			else {
				finesPerZip.put(currentZip, currentFineAmount);
			}
		}
		
		//Divide by population
		
		//Retrieve population by zipcode. 
		//Need to modify this so that if it wasn't initialized, 
		//it will call helper method to retrieve it.
		if(populationByZipcode == null) {
			throw new IllegalStateException("population by zipcode information missing.");
		}
		
		//Initialize finesPerCapita
		finesPerCapita = new HashMap<>();
		
		for(Integer element : finesPerZip.keySet()) {
			
			//if the required zipcode population info is not present
			//in populationByZipCode, 
			if(populationByZipcode.get(element) == null) {
				continue;
			}
			int population = populationByZipcode.get(element);
			
			
			double currentFinePerCapita = finesPerZip.get(element)/population;
			//update value
			finesPerCapita.put(element, currentFinePerCapita);
		}
		
		return finesPerCapita;
	}
	
	public static void main(String[] args) {
		Processor test = new CSVProcessor();
		test.calculateTotalPopulation("population.txt");
		test.getPopulationsByZip("population.txt");
		Map<Integer, Double> result = test.calculateTotalFinesCapita("parking.csv");
		for(Integer key : result.keySet()) {
			System.out.println(key + "\t" + result.get(key));
		}
	}
	
}
