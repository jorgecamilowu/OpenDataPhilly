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
					String plate = parkingInfo[4];
					int violationIdentifier = Integer.parseInt(parkingInfo[5]);
					int zipcode = Integer.parseInt(parkingInfo[6]);
					
					ParkingFine parkingFineObject = new ParkingFine(timestamp, fine, description, vehicleIdentifier, violationIdentifier, plate, zipcode);
					output.add(parkingFineObject);
					
				} catch (NumberFormatException e) {
					//if a not able to convert, move on to next iteration
					e.printStackTrace();
//					System.out.println("couldn't convert");
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
//	public static void main(String[] args) {
//		Reader test = new CSVParkingReader();
//		List<ParkingFine> result = test.read("parking.csv");
//		System.out.println(result);
//	}

}
