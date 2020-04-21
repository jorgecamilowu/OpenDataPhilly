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
	
	public void resolveBadInput() {
		scanner.next();
		return;
	}
	
	public int getUserChoice() {
		System.out.println("Select a task to perform.");
		int choice = scanner.nextInt();
		return choice;
	}
	
	public int getUserZipCode() {
		System.out.println("Please enter a zip-code.");
		int zipCode = scanner.nextInt();
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
	
	public void displayAns(double ans) {
		System.out.println(ans);
	}
		
	public void displayZipChosen(int zip) {
		System.out.println("Zip-Code chosen: " + zip);
	}
		
}
