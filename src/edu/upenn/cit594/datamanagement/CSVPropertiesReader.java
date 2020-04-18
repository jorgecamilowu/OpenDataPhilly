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
				List<String> propertyInfo = parser(currentRow);

				//if any of the fields of interest are missing, move on to next property.
				if(propertyInfo.get(valuePos).equals("") || propertyInfo.get(areaPos).equals("") || propertyInfo.get(zipPos).equals("")) {
					continue;
				}

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
			// TODO Auto-generated catch block
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

	public static void main(String[] args) {
		Reader test = new CSVPropertiesReader();
		long start = System.currentTimeMillis();
		List<Properties> result = test.read("properties.csv");
		long end = System.currentTimeMillis();
		System.out.println(end-start);
		System.out.println(result.size());

		//				String sentence = "1,,\",2\",\"hello,five\",,\"six,\"";
		//				String second = "0,,97' W OF 9TH ST          ,3517081,O50  ,ROW 3 STY MASONRY,1,Single Family,870,N,,,47.21,0.0,0.0,4,0,14.120000000000001,,0,0,A,01,,00,00912,4,912 REED ST,,,,NEW YORK NY,\"245 PARK AVENUE, 26TH FL \",10167,283600.0,,1,3,D,3,0,,SFR SA I LLC             ,,012211700,E,,2019-05-24,010S150105     ,2019-05-17,1283700.0,,,A,1001,67780,ST ,,REED,,231133.0,52467.0,F,666.6,1478.0,H,,,,I,1900,Y,191475617,RSA5 ,529789026,39.9317516718013,-75.1599051435482";
		//				String third = "0,,\"32'6\"\" S TASKER ST        \",0602048,U50  ,ROW CONV/APT 3 STY MASON,2,Multi Family,750,N,,,65.17,0.0,0.0,4,0,16.0,,0,0,A,01,0,00,01604,4,1604 S 4TH ST,C/O CHRISTOPHER CATALAW,,KAREN  CATALANO,SEWELL NJ,100 GOLFVIEW DR,08080-1836,264800.0,,2,4,D,3,0,,CATALANO KAREN           ,,011429900,E,,1986-10-15,012S090302     ,1986-01-08,22500.0,,,A,1002,87880,ST ,S,04TH,,205750.0,59050.0,F,1042.72,1800.0,H,,,,I,1900,Y,191481303,RM1  ,529788186,39.9281937976753,-75.1523680250966\r\n";
		//				String four = "0,,\"431'5\"\",315' 3/8\"\" W BUSTLE\",0612214,MD0  ,SHOP CENT STRIP MASONRY,4,Commercial,530,,581009900,,898.0,0.0,0.0,4,0,205.73000000000002,,0,,A,58,,00,01651,4,1651 GRANT AVE,2ND FLOOR,,,EATONTOWN NJ,8 INDUSTRIAL WAY EAST,07724,8608800.0,,0,0,,1,0,,GRANT PLAZA ACQUISITION  ,LP                       ,882069415,A,,1986-10-28,151N14 248     ,1986-10-26,5075000.0,,,A,3005,38120,AVE,,GRANT,,6887040.0,1721760.0,F,251631.0,57895.0,,,,,I,1979,Y,191153149,CA1  ,530297709,40.0879190943691,-75.0390597250514";
		//				
		//				List<String> result = parser(four);
		//				String[] regex = four.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
		//						for(String ele : result) {
		//							System.out.println(ele);
		//						}
		//		//		System.out.println(result[72]);
		//				System.out.println(result.size() + "///////////////////////////////////////////////////////////////////////////");
		//						for(String ele : regex) {
		//							System.out.println(ele);
		//						}
		//		//		System.out.println(regex[72]);
		//				System.out.println(regex.length + "///////////////////////////////////////////////////////////////////////////");
	}
}
