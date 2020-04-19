package edu.upenn.cit594.ui;

import java.util.*;

/**
 *  Interaction class between console and user
 *  @author Kelvin
 *
 */
public class ConsoleWriter {
	
	static ConsoleWriter consoleWriter = null;
	
	public static void run() {
		consoleWriter = new ConsoleWriter();
		System.out.println("Welcome to OpenDataPhilly!\nPlease type your selection:");
		displayPrompt();		
	}
	
	public static void displayPrompt() {
		System.out.println("0: Exit");
		System.out.println("1: Show total population for all zip-codes");
		System.out.println("2: Show total parking fines per capita per zip-code");
		System.out.println("3: Show average property market value for residences in a specific zip-code");
		System.out.println("4: Show average total livable area for residences in a specific zip-code");
		System.out.println("5: Show total residential market value per capita in a specific zip-code");
		System.out.println("6: Show custom feature");
	}
	
	public static int getUserChoice() {
		System.out.println("Please select a choice.");
		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();
		scanner.close();
		return choice;
	}
	
	public static void displayAns(Map<Integer, String> map) {
		for(int key : map.keySet()) {
			System.out.println(key + " " + map.get(key));
		}
	}
	
	public static void displayAns(double ans) {
		System.out.println(ans);
	}
	
	public static void displayAns(int ans) {
		System.out.println(ans);
	}
		
	// PRINT OUT VALUE FROM PROCESSOR
	
}
