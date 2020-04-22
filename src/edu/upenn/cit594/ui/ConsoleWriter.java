package edu.upenn.cit594.ui;

import java.util.*;

/**
 *  ConsoleWriter is responsible for all interaction to the console.
 *  Both displaying and taking user input
 *  @author Kelvin
 *
 */
public class ConsoleWriter {
	
	private static Scanner scanner;

	private ConsoleWriter() {
		scanner = new Scanner(System.in);
	}
	
	private static ConsoleWriter cw = new ConsoleWriter();
	
	
	public synchronized static ConsoleWriter getConsoleWriter() {
		return cw;
	}
	
	public void intro() {
		System.out.println("Welcome to OpenDataPhilly!\n\nPlease type in the number of your selection:");
	}
	
	public void stop() {
		scanner.close();
		System.out.println("Goodbye!");
	}
	
	public void displayPrompt() {
		System.out.println(
				"0: Exit" + "\n" +
				"1: Show total population for all zip-codes" + "\n" +
				"2: Show total parking fines per capita per zip-code" + "\n" +
				"3: Show average property market value for residences in a specific zip-code" + "\n" +
				"4: Show average total livable area for residences in a specific zip-code" + "\n" +
				"5: Show total residential market value per capita in a specific zip-code" + "\n" +
				"6: Show average number of parking tickets for all zip-codes by market value" + "\n");
	}
	
//	public void resolveBadInput() {
//		scanner.nextLine();
//		return;
//	}

	/**
	 * Decided to implement it as a .nextLine() instead of .nextInt()
	 * because we want to be able to catch inputs with white spaces inside.
	 * The method will simply return a String value. Main will try to parseInt
	 * this way if a badly formatted input was given (non numeral, with whitespaces... etc)
	 * it will throw a NumberFormatException, which Main will catch.
	 * 
	 * Comes with the added benefit that if a bad input was caught, we do not have to further call
	 * a .nextLine() from Scanner to consume the bad input.
	 */
	public String getUserChoice() {
		System.out.println("Select a task to perform.");
		String choice = scanner.nextLine();
		return choice;
	}
	
	public String getUserZipCode() {
		System.out.println("Please enter a zip-code.");
		String zipCode = scanner.nextLine();
		displayZipChosen(zipCode);
		return zipCode;
	}
	
	public void displayAns(Map<Integer, String> map) {
		for(int key : map.keySet()) {
			System.out.println(key + " " + map.get(key));
		}
		System.out.println("");
	}
	
	public void displayAns(String ans) {
		System.out.println(ans);
	}

	public void displayAns(int ans) {
		System.out.println(ans);
	}
		
	public void displayZipChosen(String zip) {
		System.out.println("Zip-Code chosen: " + zip);
	}
		
}
