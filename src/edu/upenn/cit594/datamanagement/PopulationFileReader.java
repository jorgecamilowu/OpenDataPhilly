package edu.upenn.cit594.datamanagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.upenn.cit594.data.ZipCode;

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
	public static void main(String[] args) {
		Reader test = new PopulationFileReader();
		long start = System.currentTimeMillis();
		List<ZipCode> result = test.read("population.txt");
		long end = System.currentTimeMillis();
		long time = end-start;
		System.out.println("Run Time: " + time);
//		for(ZipCode ele : result) {
//			System.out.println(ele);
//		}
//		System.out.println(result.size());
	}

}
