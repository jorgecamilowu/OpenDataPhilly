package edu.upenn.cit594.datamanagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.upenn.cit594.data.Properties;

public class CSVPropertiesReader implements Reader<Properties> {

	@Override
	public List<Properties> read(String filename) {
		List<Properties> output = new ArrayList<>();
		try {
			FileReader file = new FileReader(filename);
			Scanner in = new Scanner(file);
			
			//findout index positions of categories of interest. 
			String[] positions = in.nextLine().split(",");
			int valuePos = -1, areaPos = -1, zipPos = -1; 
			for(int i = 0; i < positions.length; i++) {
				if(positions[i].equals("market_value" )) {
					valuePos = i;
				}
				if(positions[i].equals("total_livable_area" )) {
					areaPos = i;
				}
				if(positions[i].equals("zip_code" )) {
					zipPos = i;
				}
			}
			//if any of the positions remain unchanged, we could not find the categories
			if(valuePos == -1 || areaPos == -1 || zipPos == -1) {
				in.close();
				throw new IllegalStateException("Could not find categories of interest");
			}
			//Proceed to read in data and create Properties Objects to populate our output list
			while(in.hasNextLine()) {
				String currentRow = in.nextLine();
				String[] propertyInfo = currentRow.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
				
				//if any of the fields of interest are missing, move on to next property.
				if(propertyInfo[valuePos].equals("") || propertyInfo[areaPos].equals("") || propertyInfo[zipPos].equals("")) {
					continue;
				}
				
				try {
					double marketValue = Double.parseDouble(propertyInfo[valuePos]);
					double totalArea = Double.parseDouble(propertyInfo[areaPos]);
					//only want first 5 digits of zipcode
					String temp = propertyInfo[zipPos].substring(0, 5);
					int zipcode = Integer.parseInt(temp);
					
					Properties prop = new Properties(marketValue, totalArea, zipcode);
					output.add(prop);
				} catch (NumberFormatException e) {
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
		Reader test = new CSVPropertiesReader();
		long start = System.currentTimeMillis();
		List<Properties> result = test.read("properties.csv");
		long end = System.currentTimeMillis();
		System.out.println(end-start);

	}
}
