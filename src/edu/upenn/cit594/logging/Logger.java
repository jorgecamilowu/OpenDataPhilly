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

	private static Logger logger;
	private static FileWriter fw;
	private static String loggerFilename = "";
	
	// if the logger filename exists, it should append new data to it
	// if it doesn't exist, it should create the new file
	
	
	/*
	 *  When the program starts, log the current time using System.currentTimeMillis() 
	 */
	public void logProgramStart(String[] runtimeArgs) {
		
	}
	
	/*
	 *  When an input file is opened, write current time and name of file
	 */
	public void logFileOpen(String filename) {
		
	}
	
	/*
	 *  When a user makes a choice in Step#0, write current time and user's selection
	 */
	public void logChoice(String choice) {
		
	}
	
	/*
	 *  When a user enters a zipCode in Step#3, 4, 5 write current time and specificed zip code
	 */
	public void logZip(int zipCode) {
		
	}
	
	
	public static void main(String[] args) {
		
	}
	
	
}
