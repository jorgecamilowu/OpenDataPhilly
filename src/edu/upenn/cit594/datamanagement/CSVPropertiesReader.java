package edu.upenn.cit594.datamanagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.upenn.cit594.data.Properties;

/**
 * Reads in a .csv properties file.
 * @author jcwuz
 *
 */
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
				throw new IllegalArgumentException("Could not find categories of interest in the file.");
			}
			//Proceed to read in data and create Properties Objects to populate our output list
			while(in.hasNextLine()) {
				String currentRow = in.nextLine();
				List<String> propertyInfo = parser(currentRow);//parsing

				//if any of the fields of interest are missing, move on to next property.
				if(propertyInfo.get(valuePos).equals("") || propertyInfo.get(areaPos).equals("") || propertyInfo.get(zipPos).equals("")) {
					continue;
				}

				//parse information, create property object and add it to the output list.
				try {
					double marketValue = Double.parseDouble(propertyInfo.get(valuePos));
					double totalArea = Double.parseDouble(propertyInfo.get(areaPos));
					//only want first 5 digits of zipcode
					String temp = propertyInfo.get(zipPos).substring(0, 5);
					int zipcode = Integer.parseInt(temp);
					Properties prop = new Properties(marketValue, totalArea, zipcode);
					output.add(prop);

				} catch (NumberFormatException e) {
					//discard any badly formatted row
					continue;
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return output;
	}

	/**
	 * Private helper method to parse a String by commas. Ignores commas in between quotes.
	 * @param input
	 * @return list of comma separated values.
	 */
	private List<String> parser(String input) {
		List<String> result = new ArrayList<String>();
		int start = 0;
		boolean inQuotes = false;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '\"') {
				inQuotes = !inQuotes; // toggle state
			}
			boolean atLastChar = (i == input.length() - 1);
			if(atLastChar) result.add(input.substring(start));
			else if (input.charAt(i) == ',' && !inQuotes) {
				result.add(input.substring(start, i));
				start = i + 1;
			}
		}
		return result;
	}
}
