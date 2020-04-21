package edu.upenn.cit594.datamanagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.upenn.cit594.data.ParkingFine;

public class JSONParkingReader implements Reader<ParkingFine> {

	@Override
	public List<ParkingFine> read(String filename) {
		
		JSONParser parser = new JSONParser();
		List<ParkingFine> output = new ArrayList<>();
		try {
			JSONArray rows = (JSONArray)parser.parse(new FileReader(filename));
			Iterator iter = rows.iterator();
			while(iter.hasNext()) {
				JSONObject rowContent = (JSONObject)iter.next();
				//ignore empty lines or potentially faulty input
				//expecting json objects to contain 7 fields.
				if(rowContent == null || rowContent.isEmpty() || rowContent.size() != 7) continue;
				//make sure none of the fields are empty.
				if(rowContent.get("zip_code").equals("") || rowContent.get("ticket_number").equals("") ||
						rowContent.get("plate_id").equals("") || rowContent.get("date").equals("") ||
						rowContent.get("violation").equals("") || rowContent.get("fine").equals("") ||
						rowContent.get("state").equals("")) {
					continue;
				}
				
				//parse information
				String timestamp = rowContent.get("date").toString();
				double fine = Double.parseDouble(rowContent.get("fine").toString());
				String description = rowContent.get("date").toString();
				int vehicleIdentifier = Integer.parseInt(rowContent.get("plate_id").toString());
				String state = rowContent.get("state").toString();
				int violationIdentifier = Integer.parseInt(rowContent.get("ticket_number").toString());
				int zipcode = Integer.parseInt(rowContent.get("zip_code").toString());
			
				//Create parking fine object and add to output list.
				ParkingFine parkingFineObject = new ParkingFine(timestamp, fine, description, vehicleIdentifier, violationIdentifier, state, zipcode);
				output.add(parkingFineObject);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return output;
	}
}
