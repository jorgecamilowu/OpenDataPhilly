package edu.upenn.cit594.datamanagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.upenn.cit594.data.ZipCode;

/**
 * Reads in a .txt population file.
 */
public class PopulationFileReader implements Reader {

	@Override
	public List<ZipCode> read(String filename) {
		List<ZipCode> output = new ArrayList<>();
		try {
			FileReader file = new FileReader(filename);
			Scanner in = new Scanner(file);
			while(in.hasNextLine()) {
				String currentRow = in.nextLine();
				String[] rowInfo = currentRow.split(" ");
				
				//Skip empty rows
				if(rowInfo.length < 2 || rowInfo[0].equals("") || rowInfo[1].equals("")) {
					continue;
				}
				//parse information, create zip object and add it to the output list.
				try {
					int zipcode = Integer.parseInt(rowInfo[0]);
					int population = Integer.parseInt(rowInfo[1]);
					ZipCode zipInstance = new ZipCode(zipcode);
					zipInstance.setTotalPopulation(population);
					output.add(zipInstance);
					
				} catch (NumberFormatException e) {
					//Skip badly formatted rows
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
}
