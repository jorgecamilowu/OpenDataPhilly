package edu.upenn.cit594.logging;

import java.io.*;

/**
 * Singleton instance of Logger (following singleton design pattern)
 * Logger is responsible for creating, writing, and appending to the log file (filename set in Main)
 * @author Kelvin
 *
 */

public class Logger {

	private static Logger logger = null;
	private static Writer w = null;
	private static String loggerFilename = null;

	private Logger() {
		if(loggerFilename.equals("")) {
			System.out.println("Input filename for logger");
			return;
		}
		try {
			w = new BufferedWriter(new FileWriter(new File(loggerFilename), true));
		} catch (IOException e) { 
			System.err.println("ERROR: Logger cannot write"); 
			e.printStackTrace(); 
		}
	}

	public static Logger getLoggerInstance() {
		if(logger == null) {
			logger = new Logger();
		}
		return logger;
	}

	/*
	 * Set logger filename only once. Subsequent attempts will be ignored.
	 */
	public static void setLogFile(String filename) {
		if(loggerFilename != null) {
			return;
		}
		loggerFilename = filename;
	}

	/*
	 *  When the program starts, log the current time using System.currentTimeMillis()
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
	 *  When an input file is opened, write current time and name of file. We cannot reuse the overload log(String string) as
	 *  to preserve the accurate time of file reading.
	 */

	public void log(long time, String string) {
		try {
			w.append(time + " " + string + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void log(String string) {
		try {
			w.append(Long.toString(System.currentTimeMillis()) + " " + string + "\n");
		} catch (IOException e) {
			System.err.println("ERROR: Logger cannot write");
			e.printStackTrace(); 
		}
	}

	public void close() {
		try {
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}