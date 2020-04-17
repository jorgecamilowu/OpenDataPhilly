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
				
				
				
			}
			
			
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	
	public static void main(String[] args) {
//		Reader test = new CSVPropertiesReader();
//		test.read("properties.csv");
		
		String row = "0,,91'S OF MONTROSE ST      ,3272501,O40  ,ROW 2.5 STY MASONRY,1,Single Family,540,N,,,43.85,0.0,0.0,4,0,15.0,,0,0,A,02,0,00,00910,4,910 S FAIRHILL ST,,,,PHILADELPHIA PA,\"1100 VINE ST, P315       \",19107,300800.0,,1,4,D,2,0,,MARK PETER               ,,021439700,E,,2017-10-02,006S160265     ,2017-09-29,259999.0,,,A,1001,33260,ST ,S,FAIRHILL,,218380.0,82420.0,F,657.75,1335.0,B,,,,I,1920,,191474016,RSA5 ,529795176,39.9366676137638,-75.1531737879182";
		String[] result = row.split(",|\",|,\"");
		for(String ele : result) {
			System.out.println(ele);
		}
	}
}
