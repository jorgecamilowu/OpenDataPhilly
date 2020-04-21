package edu.upenn.cit594.datamanagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.upenn.cit594.data.ParkingFine;

/**
 * Reads a .csv parking violations file. 
 * @author jcwuz
 *
 */
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
					
					//create object and add to output list
					ParkingFine parkingFineObject = new ParkingFine(timestamp, fine, description, vehicleIdentifier, violationIdentifier, state, zipcode);
					output.add(parkingFineObject);
					
				} catch (NumberFormatException e) {
					//skip badly formatted data rows 
					continue;
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return output;
	}
}
