package edu.upenn.cit594;

import java.io.File;
import java.util.*;

import edu.upenn.cit594.*;
import edu.upenn.cit594.processor.JSONProcessor;
import edu.upenn.cit594.processor.Processor;
// not able to move into package edu.upenn.cit594
import edu.upenn.cit594.processor.TextProcessor;
import edu.upenn.cit594.ui.ConsoleWriter;


/**
 *  @author Kelvin
 *  Runner class for program
 *
 */

public class Main {
	
	private final static List<String> FILE_FORMATS = Collections.unmodifiableList(
            Arrays.asList("csv", ".csv", "json", ".json"));

	public static boolean checkFileformat(String fileformat) {
		return FILE_FORMATS.contains(fileformat);
	}
	
	public static boolean checkValidFile(String filename) {
		File file = new File(filename);
		return file.canRead() || file.exists();
	}

	/*
	 * Runtime args: [parkingFormat, parkingFilename, propertyFilename, populationFilename, logFilename]
	 */
	public static void main(String[] args) {
		
		if(args.length != 5) {
			System.out.println("Incorrect number of args. Terminated"); return;
		}
		
		String parkingFormat = args[0];
		String parkingFilename = args[1];
		String propertyFilename = args[2];
		String populationFilename = args[3];
		String logFilename = args[4];
		
		// CHECK VALID FILES / FORMATS
		if(!checkFileformat(parkingFormat)) {
			System.out.println("Invalid fileformat. Terminated"); return;
		}
		if(!checkValidFile(parkingFilename) || checkValidFile(propertyFilename) || !checkValidFile(populationFilename)) {
			System.out.println("Files do not exist. Terminated"); return;
		}
		
		// GET PROCESSOR
		Processor p;
		if(parkingFormat.toLowerCase().equals("csv") || parkingFormat.toLowerCase().equals(".csv")) {
			p = new TextProcessor();
		}
		else if(parkingFormat.toLowerCase().equals("json") || parkingFormat.toLowerCase().equals(".json")) {
			p = new JSONProcessor();
		}
		
		// CREATE LOGGER HERE
		
		// GET CONSOLE WRITER
		ConsoleWriter cw = new ConsoleWriter();
		cw.run();
		// create separte method in main class
		int userChoice;
		
		while(true) {
			userChoice = cw.getUserChoice();
			if(userChoice < 0 || userChoice > 6) {
				System.out.println("Invalid selection. Terminating"); 
			}
			if(userChoice == 0) {
				break;
			}
			// else pass into PROCESSOR
		}
		// CLOSE LOGGER HERE
		System.out.println("Exiting. Goodbye!"); 
	}
	
}

