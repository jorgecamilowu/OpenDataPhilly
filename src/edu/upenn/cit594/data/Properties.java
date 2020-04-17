package edu.upenn.cit594.data;

public class Properties {
	private double marketValue;
	private double totalLivableArea;
	private int zipcode;
	
	public Properties(double marketValue, double totalLivableArea, int zipcode) {
		this.marketValue = marketValue;
		this.totalLivableArea = totalLivableArea;
		this.zipcode = zipcode;
	}
	
	
	////////////GETTERS////////////
	public double getMarketValue() {
		return marketValue;
	}

	public double getTotalLivableArea() {
		return totalLivableArea;
	}

	public int getZipcode() {
		return zipcode;
	}

	@Override
	public String toString() {
		return (
				"Market Value: " + this.marketValue + "\n" +
				"Total Livable Area: " + this.totalLivableArea + "\n" + 
				"ZipCode: " + this.zipcode + "\n\n"
				);
	}
}
