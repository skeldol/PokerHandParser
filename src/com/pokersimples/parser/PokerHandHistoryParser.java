package com.pokersimples.parser;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.pokersimples.bo.Hand;
import com.pokersimples.bo.Player;

public abstract class PokerHandHistoryParser {
	protected Hand hand;
	protected ParserState state = null;
	protected int buttonSeat = 0;
	protected String currentLine = null;
	protected Map<String, Player> playerSeatMap = new Hashtable<String, Player>();
	protected List<Player> players =  new ArrayList<Player>();
	
	public Hand parse(String pLine) {
		currentLine = pLine;
		if(state == null || state == ParserState.HEAD) {
			parseHeader();
		}
		
		if(state == null || state == ParserState.PLAY) {
			parsePlay();
		}		
		
		if(state == ParserState.COMPLETE) {
			return hand;
		} else {
			return null;
		}
	}
	
	protected Player getPlayerByName(String pName) {
		Player player = playerSeatMap.get(pName);
		if(player == null) {
			throw new RuntimeException("Could not find a player called \"" + pName + "\" in playerSeatMap");
		}
		return player;
	}
	
	protected abstract void parseHeader();
	protected abstract void parsePlay();
	
	/*
	 * Given a line of string and two features to search for it returns
	 * the data between the two features. 

	 */
	protected String extractStringData(String pStartToken, String pEndToken) {
		try {
			int start = currentLine.indexOf(pStartToken);
			int finish = currentLine.indexOf(pEndToken, start + pStartToken.length() - 1);
			return currentLine.substring(start + pStartToken.length(), finish);
		} catch(Exception e1) {
			if(hand != null) {
				System.out.println("Parsing error for hand " + hand.getHandId());
				System.out.println("Attempting to parse from " + pStartToken + " to " + pEndToken);
				System.out.println(currentLine);

			}
			return null;
		}
	}
	
	/*
	 * Returns the String data found between the start index and token
	 */
	protected String extractStringData(int pStartIndex, String pEndToken) {
		try {
			int finish = currentLine.indexOf(pEndToken, pStartIndex);
			return currentLine.substring(pStartIndex, finish);
			
		} catch(Exception e1) {
			if(hand != null) {
				System.out.println("Parsing error for hand " + hand.getHandId());
				System.out.println("Attempting to parse from " + pStartIndex + " to " + pEndToken);
				System.out.println(currentLine);

			}
			return null;
		}
	}
	
	/*
	 * Returns all the data in the current line after pToken
	 */
	protected String extractStringData(String pFromToken) {
		try {
			int start = currentLine.indexOf(pFromToken);
			return currentLine.substring(start + pFromToken.length());
		} catch(Exception e1) {
			if(hand != null) {
				System.out.println("Parsing error for hand " + hand.getHandId());
				System.out.println("Attempting to parse from " + pFromToken + " to end");
				System.out.println(currentLine);

			}
			return null;
		}
	}	
	
	
	
	/*
	 * Returns the long value found between the two tokens
	 */
	protected long extractLongData(String pStartToken, String pEndToken) {
		return Long.parseLong(extractStringData(pStartToken, pEndToken));
	}	
	
	/*
	 * Returns the int value found between the two tokens
	 */
	protected int extractIntData(String pStartToken, String pEndToken) {
		return Integer.parseInt(extractStringData(pStartToken, pEndToken));
	}	
	
	/*
	 * Extracts the int data found post the token passed
	 * The rest of the String post the token must form the int data
	 */
	protected int extractIntData(String pToken) {
		return Integer.parseInt(extractStringData(pToken));
	}	
	
	/*
	 * Returns the integer value found between the start & end indexes passed
	 */
	protected int extractIntData(int pStart, int pEnd) {
		return Integer.parseInt(currentLine.substring(pStart, pEnd));		
	}
	
	/*
	 * Removes the contents of one String from another
	 * @param pRemove is removed from pToken if it exists in pToken
	 * @param return the cleaned token
	 */
	protected String removeStringData(String pToken, String pRemove) {
		if(!pToken.contains(pRemove)) {
			return pToken;
		} else {
			int end = pToken.indexOf(pRemove);
			return pToken.substring(0, end);
		}
			
	}
	
}
