package edu.upenn.cit594.datamanagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.upenn.cit594.data.ParkingFine;
public class CSVParkingReader implements Reader<ParkingFine> {

	@Override
	public List<ParkingFine> read(String filename) {
		List<ParkingFine> output = new ArrayList<>();
		try {
			FileReader file = new FileReader(filename);
			Scanner in = new Scanner(file);
			
			while(in.hasNext()) {
				String currentRow = in.nextLine();
				String[] parkingInfo = currentRow.split(",");
				//make sure the row data has all information
				if(parkingInfo.length != 7) {
					continue;
				}
				try {
					//parse information
					String timestamp = parkingInfo[0];
					double fine = Double.parseDouble(parkingInfo[1]);
					String description = parkingInfo[2];
					int vehicleIdentifier = Integer.parseInt(parkingInfo[3]);
					String state = parkingInfo[4];
					int violationIdentifier = Integer.parseInt(parkingInfo[5]);
					int zipcode = Integer.parseInt(parkingInfo[6]);
					
					ParkingFine parkingFineObject = new ParkingFine(timestamp, fine, description, vehicleIdentifier, violationIdentifier, state, zipcode);
					output.add(parkingFineObject);
					
				} catch (NumberFormatException e) {
					//skip badly formatted data rows 
					continue;
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
	public static void main(String[] args) {
		Reader test = new CSVParkingReader();
		long start = System.currentTimeMillis();
		List<ParkingFine> result = test.read("parking.csv");
		long end = System.currentTimeMillis();
		long time = end-start;
		System.out.println("Run Time: " + time);
//		System.out.println("in csv: " + result.size());
	}

}
