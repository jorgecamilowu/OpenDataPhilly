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
 *  Main and runner class for OpenDataPhilly program. 
 *  Main instantiates ConsoleWriter, Processor and also handles the Singleton Logger. 
 *  Main passes return values from Processor to ConsoleWriter and Logger
 *  Main also handles user input errors and file IO errors
 */

public class Main {
	
	private final static Set<String> FILE_FORMATS = new HashSet<>(
            Arrays.asList("csv", "json"));

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
		if(!filename.endsWith(".txt") || !filename.endsWith(".TXT")) filename += ".txt";
		return filename;
	}
		
	/**
	 * Primary method of main. Runs after all IO error checking and objects are instantiated.
	 * The program continuously takes user input from console and returns the chosen calculation until the user exits
	 * 
	 * @param cw: The instantiated ConsoleWriter. Performs all logging to log file
	 * @param logger: Singleton logger. Not instanted, just uses single instance
	 * @param p: The instanted Processor. Performs all calculations
	 */
	private static void runCore(ConsoleWriter cw, Logger logger, Processor p) {
		cw.intro();		
		int userChoice = 0;
		while(true) {
			try {
				cw.displayPrompt();
				/* .getUserChoice will return a String value. The reason for this is to be able to catch
				 * white spaces inside of inputs. Main will attempt to parse the choice into integer, and
				 * if this triggers a NumberFormatException, it will catch it and move on to the next iteration.*/
				userChoice = Integer.parseInt(cw.getUserChoice());
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
					String zip = cw.getUserZipCode();
					int zipInt = Integer.parseInt(zip);
					logger.log(zip);
					if(!p.validZip(zipInt)) {
						cw.displayAns(0);
					}
					else{
						Strategy strategy = userChoice == 3 ? new ValueStrategy() : new AreaStrategy();
						cw.displayAns(p.calculateRatio(strategy, zipInt));
					}
				}
				else if(userChoice == 5) {
					String zip = cw.getUserZipCode();
					int zipInt = Integer.parseInt(zip);
					logger.log(zip);
					if(!p.validZip(zipInt)) {
						cw.displayAns(0);
					}
					else {
						cw.displayAns(p.calculateTotalResidentialMarketValuePerCapita(zipInt));
					}				
				}
				else if(userChoice == 6) {
					Map<String, String[]> result = p.calculateFeatureSix(); 
					for(String key : result.keySet()) {
						String category = "Low";
						if(key.equals("hi")) {
							category = "High";
						}
						else if(key.equals("mid")) {
							category = "Medium";
						}
						cw.displayAns("Property Value: " + category + "\t Average fine tickets per person: " + result.get(key)[0] + "\t Fine % of total: " + result.get(key)[1]);
					}
					System.out.println("");
				}
				
			} catch (NumberFormatException | InputMismatchException e) { 
				if(userChoice == 3 || userChoice == 4 || userChoice == 5) {
					cw.displayAns("0");
					userChoice = 0;//clear input
				}
				System.out.println("Wrong format input. Menu selections are numbers from 0-6 and ZipCodes are of length 5.\n");
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
		if(!checkFileformat(parkingFormat)) {
			System.out.println("Invalid fileformat. Terminated"); 
			return;
		}
		if(!checkValidFile(parkingFilename) && !checkValidFile(propertyFilename) && !checkValidFile(populationFilename)) {
			System.out.println("Files do not exist. Terminated"); 
			return;
		}
		// CHECK CONSISTENCY OF FORMAT AND FILE SPECIFIED
		if(parkingFormat.equals("csv") && !parkingFilename.matches(".*.csv")) {
			System.out.println("Passed csv format but provided non .csv file"); 
			return;
		}
		
		if(parkingFormat.equals("json") && !parkingFilename.matches(".*.json")) {
			System.out.println("Passed json format but provided non .json file");
			return;
		}
		
		// GET PROCESSOR
		Processor p = null;
		if(parkingFormat.equals("csv")) {
			p = new CSVProcessor();
			parkingFormat = addCSV(parkingFormat);
		}
		else if(parkingFormat.equals("json")) {
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

