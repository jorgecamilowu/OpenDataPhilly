package edu.upenn.cit594.data;

/**
 * Represents a single parking fine.
 * @author jcwuz
 *
 */
public class ParkingFine {
	private String timestamp;
	private double fine;
	private String description;
	private int vehicleIdentifier;
	private int violationIdentifier;
	private String stateLicensePlate;
	private int zipcode;
	
	public ParkingFine(String timestamp, double fine, String description, int vehicle, int violation, String state, int zipcode) {
		this.timestamp = timestamp;
		this.fine = fine;
		this.description = description;
		this.vehicleIdentifier = vehicle;
		this.violationIdentifier = violation;
		this.stateLicensePlate = state;
		this.zipcode = zipcode;
	}

	//////////GETTERS//////////
	public String getTimestamp() {
		return timestamp;
	}

	public double getFine() {
		return fine;
	}

	public String getDescription() {
		return description;
	}

	public int getVehicleIdentifier() {
		return vehicleIdentifier;
	}

	public int getViolationIdentifier() {
		return violationIdentifier;
	}

	public String getStateLicensePlate() {
		return stateLicensePlate;
	}

	public int getZipcode() {
		return zipcode;
	}
	
	@Override
	public String toString() {
		return( 
				"TimeStamp: " + this.timestamp + "\n" +
					"Fine Amount: " + this.fine + "\n" +
					"Description: " + this.description + "\n" + 
					"Vechicle Identifier: " + this.vehicleIdentifier + "\n" +
					"Violation Identifier: " + this.violationIdentifier + "\n" +
					"State Plate: " + this.stateLicensePlate + "\n" +
					"ZipCode: " + this.zipcode + "\n\n"
				);
	}
}
