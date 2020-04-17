package edu.upenn.cit594.datamanagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

import edu.upenn.cit594.data.ParkingFine;

public class CSVParkingReader implements Reader<ParkingFine> {

	@Override
	public List<ParkingFine> reader(String filename) {
		
		try {
			FileReader file = new FileReader(filename);
			Scanner in = new Scanner(file);
			
			while(in.hasNext()) {
				String currentRow = in.next();
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
					int violationIdentifier = Integer.parseInt(parkingInfo[4]);
					String plate = parkingInfo[5];
					int zipcode = Integer.parseInt(parkingInfo[6]);
					
					
					
				} catch (NumberFormatException e) {
					//if a not able to convert, move on to next iteration
					continue;
				}
				
				
				
			}
			
			
			
			
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
