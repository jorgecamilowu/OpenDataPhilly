package edu.upenn.cit594.ui;

import java.util.*;

/**
 *  Interaction class between console and user
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
	
	public void run() {
		System.out.println("Welcome to OpenDataPhilly!\nPlease type in the number of your selection:");
//		displayPrompt();		
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
				"6: Show custom feature \n");
	}
	
	public void resolveBadInput() {
		scanner.next();
		return;
	}
	
	public int getUserChoice() {
		System.out.println("Select a task to perform.");
//		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();
//		scanner.close();
		return choice;
	}
	
	public int getUserZipCode() {
		System.out.println("Please enter a zip-code.");
//		Scanner scanner = new Scanner(System.in);
		int zipCode = scanner.nextInt();
//		scanner.close();
		return zipCode;
	}
	
	public void displayAns(Map<Integer, String> map) {
		for(int key : map.keySet()) {
			System.out.println(key + " " + map.get(key));
		}
	}
	
	public void displayAns(double ans) {
		System.out.println(ans);
	}
	
	public void displayAns(int ans) {
		System.out.println(ans);
	}
		
	// PRINT OUT VALUE FROM PROCESSOR
	
}
