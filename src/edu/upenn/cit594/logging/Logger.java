package edu.upenn.cit594.logging;

import java.io.*;

/**
 * Singleton instance of Logger.
 * 
 * Responsible for creating log file, writing current time, zip code
 * @author Kelvin
 *
 */

public class Logger {

	private static Logger logger = null;
	// private static FileWriter fw;
	private static File file = null;
	private static String loggerFilename = "";
	
	private Logger() {
		if(loggerFilename.equals("")) {
			System.out.println("Input filename for logger");
			return;
		}
		file = new File(loggerFilename);
	}
	
	public static Logger getLoggerInstance() {
		if(logger == null) {
			logger = new Logger();
		}
		return logger;
	}
	
	/*
	 * Sets logger filename
	 * if the logger filename exists, it should append new data to it
	 * if it doesn't exist, it should create the new file
	 */
	public static void setLogFile(String filename) {

		loggerFilename = filename;
		
	}
	
	/*
	 *  When the program starts, log the current time using System.currentTimeMillis()
	 *  
	 *   argsFormat: [fileformat, filename, filename, filename, logfilename]
	 */
	public void log(String[] runtimeArgs) {
		
		StringBuilder sb = new StringBuilder();
		for(String s : runtimeArgs) {
			sb.append(s + " ");
		}
		sb.deleteCharAt(sb.length()-1); // remove the last whitespace
		log(sb.toString());
	}
	
	public void log(int choice) {
		// should we do the valid choice checking here?
		
		String writeChoice = "";
		if(choice < 7 && choice >= 0) {
			writeChoice = "User Choice: " + Integer.toString(choice);
		}
		else if (choice > 1000) { // check valid zip
			writeChoice = "ZipCode: " + Integer.toString(choice);
		}
		log(writeChoice);		
	}
	
	/*
	 *  When an input file is opened, write current time and name of file
	 */
	public void log(String string) {
		try {
			Writer w = new BufferedWriter(new FileWriter(file, true));
			w.append(Long.toString(System.currentTimeMillis()) + " " + string + "\n");
			w.close();
		} catch (IOException e) {
			System.err.println("ERROR: Logger cannot write");
			e.printStackTrace();
		}
	}
		
	public static void main(String[] args) {
		
		Logger.setLogFile("log.txt");
		Logger l = Logger.getLoggerInstance();
		
		l.log("hi");
		l.log(3);
	}
	
}