package com.pokersimples.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.pokersimples.bo.Hand;
import com.pokersimples.parser.pokerstars.PokerStarsParser;

public class TestParser {
	
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		File file = new File(".\\src\\SinglePokerHand.txt"); 
	  
		List<Hand> games = new ArrayList<Hand>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			
			PokerStarsParser parser = new PokerStarsParser();
			String st; 
			
			Hand game = null;
			
			while ((st = br.readLine()) != null) 
				game = parser.parse(st); 
				
				if(game != null) {
					games.add(game);
				}
			
		
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}
}
