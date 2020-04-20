package edu.upenn.cit594;

import java.io.File;
import java.util.*;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.AreaStrategy;
import edu.upenn.cit594.processor.CSVProcessor;
import edu.upenn.cit594.processor.JSONProcessor;
import edu.upenn.cit594.processor.Processor;
import edu.upenn.cit594.processor.Strategy;
import edu.upenn.cit594.processor.ValueStrategy;
// not able to move into package edu.upenn.cit594
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
	
	private static Logger loggerSetUp(String logFilename) {
		if(!logFilename.endsWith(".txt")) logFilename += ".txt";
		Logger.setLogFile(logFilename);
		Logger logger = Logger.getLoggerInstance();
		return logger;
	}

	/*
	 * Runtime args: [parkingFormat, parkingFilename, propertyFilename, populationFilename, logFilename]
	 */
	public static void main(String[] args) {
		
		if(args.length < 5) {
			System.out.println("Incorrect number of args. Terminated"); return;
		}
		
		String parkingFormat = args[0];
		String parkingFilename = args[1];
		String propertyFilename = args[2];
		String populationFilename = args[3];
		String logFilename = args[4];
		
		// CREATE LOGGER HERE
		Logger logger = loggerSetUp(logFilename);
		logger.log(args);
		
		// CHECK VALID FILES / FORMATS
		if(!checkFileformat(parkingFormat)) {
			System.out.println("Invalid fileformat. Terminated"); return;
		}
		if(!checkValidFile(parkingFilename) || !checkValidFile(propertyFilename) || !checkValidFile(populationFilename)) {
			System.out.println("Files do not exist. Terminated"); return;
		}
		
		// GET PROCESSOR
		Processor p = null;
		if(parkingFormat.toLowerCase().equals("csv") || parkingFormat.toLowerCase().equals(".csv")) {
			p = new CSVProcessor();
		}
		else if(parkingFormat.toLowerCase().equals("json") || parkingFormat.toLowerCase().equals(".json")) {
			p = new JSONProcessor();
		}
		System.out.println("Reading in data sets...");
		p.run();
		long timePopulation = p.makeZipKeys(populationFilename);
		logger.log(timePopulation, populationFilename);
		long timeProperty = p.placeProperties(propertyFilename);
		logger.log(timeProperty, propertyFilename);
		long timeParking = p.placeParkingFines(parkingFilename);
		logger.log(timeParking, parkingFilename);
		
		// GET CONSOLE WRITER
		ConsoleWriter cw = ConsoleWriter.getConsoleWriter();
		cw.run();
		
		int userChoice;
		while(true) {
			try {
				cw.displayPrompt();
				userChoice = cw.getUserChoice();
				if(userChoice < 0 || userChoice > 6) {
					System.out.println("Invalid selection. Please choose between options 0-6"); 
				}
				if(userChoice == 0) {
					break;
				}
				if(userChoice == 1) {
					cw.displayAns(p.calculateTotalPopulation());
				}
				if(userChoice == 2) {
					cw.displayAns(p.calculateTotalFinesPerCapita());
				}
				if(userChoice == 3 || userChoice == 4) {
					int zip = cw.getUserZipCode();
					logger.log(zip);
					if(!p.validZip(zip)) {
						cw.displayAns(0);
					}
					//ValueStrategy if option 3, AreaStrategy for option 4
					Strategy strategy = userChoice == 3 ? new ValueStrategy() : new AreaStrategy();
					cw.displayAns(p.calculateRatio(strategy, zip)); // FILL IN STRATEGY
				}
				if(userChoice == 5) {
					int zip = cw.getUserZipCode();
					logger.log(zip);
					if(!p.validZip(zip)) {
						cw.displayAns(0);
					}
					cw.displayAns(p.calculateTotalResidentialMarketValuePerCapita(zip));
				}
			} catch (InputMismatchException e) {
				System.out.println("Wrong format input. Menu selections are numbers from 0-6 and ZipCodes are of length 5.");
				cw.resolveBadInput();
				continue;
			}
		}
		cw.stop();
		logger.close();
	}
	
}

