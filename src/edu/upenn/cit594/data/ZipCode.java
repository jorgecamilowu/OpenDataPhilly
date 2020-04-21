package edu.upenn.cit594.data;

/**
 * Represents a zip code with its associated values
 * @author jcwuz
 *
 */
public class ZipCode {
	private final int code;
	private int totalPopulation = 0;
	private int totalNumberProperties = 0;
	private double totalPropertyValue = 0;
	private double totalPropertyLivableArea = 0;
	private int totalParkingTickets = 0;
	private double totalParkingTicketFines = 0;
	
	public ZipCode(int code) {
		this.code = code;
	}

	
	//////////GETTERS & SETTERS//////////
	public int getTotalPopulation() {
		return totalPopulation;
	}

	public void setTotalPopulation(int totalPopulation) {
		this.totalPopulation = totalPopulation;
	}

	public int getTotalNumberProperties() {
		
		return totalNumberProperties;
	}

	public void setTotalNumberProperties(int totalNumberProperties) {
		this.totalNumberProperties = totalNumberProperties;
	}

	public double getTotalPropertyValue() {
		return totalPropertyValue;
	}

	public void setTotalPropertyValue(double totalPropertyValue) {
		this.totalPropertyValue = totalPropertyValue;
	}

	public double getTotalPropertyLivableArea() {
		return totalPropertyLivableArea;
	}

	public void setTotalPropertyLivableArea(double totalPropertyLivableArea) {
		this.totalPropertyLivableArea = totalPropertyLivableArea;
	}

	public int getTotalParkingTickets() {
		return totalParkingTickets;
	}

	public void setTotalParkingTickets(int totalParkingTickets) {
		this.totalParkingTickets = totalParkingTickets;
	}

	public double getTotalParkingTicketFines() {
		return totalParkingTicketFines;
	}

	public void setTotalParkingTicketFines(double totalParkingTicketFines) {
		this.totalParkingTicketFines = totalParkingTicketFines;
	}
	
	public int getCode() {
		return code;
	}


	@Override
	public String toString() {
		return "ZipCode: " + this.code + "\t" + "Population: " + this.totalPopulation;
	}
	
}
