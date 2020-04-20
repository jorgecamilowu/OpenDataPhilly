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
		logFilename = addTXT(logFilename);
		Logger.setLogFile(logFilename);
		Logger logger = Logger.getLoggerInstance();
		return logger;
	}
	
	private static String addCSV(String filename) {
		if(!filename.endsWith(".csv") || !filename.endsWith(".CSV")) filename += ".csv";
		return filename;
	}
	
	private static String addJSON(String filename) {
		if(!filename.endsWith(".json") || !filename.endsWith(".JSON")) filename += ".json";
		return filename;
	}
	
	private static String addTXT(String filename) {
		if(!filename.endsWith(".txt") || !filename.endsWith(".TXT")) filename += ".";
		return filename;
	}

	private static void runCore(ConsoleWriter cw, Logger logger, Processor p) {
		cw.run();		
		int userChoice;
		while(true) {
			try {
				cw.displayPrompt();
				userChoice = cw.getUserChoice();
				logger.log(userChoice);
				if(userChoice < 0 || userChoice > 6) {
					System.out.println("Invalid selection. Please choose between options 0-6"); 
				}
				else if (userChoice == 0) {
					break;
				}
				else if (userChoice == 1) {
					cw.displayAns(p.calculateTotalPopulation());
				}
				else if(userChoice == 2) {
					cw.displayAns(p.calculateTotalFinesPerCapita());
				}
				else if(userChoice == 3 || userChoice == 4) {
					int zip = cw.getUserZipCode();
					logger.log(zip);
					if(!p.validZip(zip)) {
						cw.displayAns(0);
					}
					//ValueStrategy if option 3, AreaStrategy for option 4
					else{
						Strategy strategy = userChoice == 3 ? new ValueStrategy() : new AreaStrategy();
						cw.displayAns(p.calculateRatio(strategy, zip));
					}
				}
				else if(userChoice == 5) {
					int zip = cw.getUserZipCode();
					logger.log(zip);
					if(!p.validZip(zip)) {
						cw.displayAns(0);
					}
					else {
						cw.displayAns(p.calculateTotalResidentialMarketValuePerCapita(zip));
					}				
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
		
		addTXT(populationFilename);
		addCSV(propertyFilename);
		
		// CREATE LOGGER HERE
		Logger logger = loggerSetUp(logFilename);
		logger.log(args);
		
		// CHECK VALID FILES / FORMATS
		// IS THIS NECESSARY? SEE ABOVE ADDTXT AND ADDCSV
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
			parkingFormat = addCSV(parkingFormat);
		}
		else if(parkingFormat.toLowerCase().equals("json") || parkingFormat.toLowerCase().equals(".json")) {
			p = new JSONProcessor();
			parkingFormat = addJSON(parkingFormat);
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
		
		// RUN CORE
		runCore(cw, logger, p);
	}
	
}

