package com.pokersimples.parser.handhistory.pokerstars;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.learn.PokerSimulator.utils.Logger;
import com.pokersimples.bo.Ante;
import com.pokersimples.bo.Bet;
import com.pokersimples.bo.BigBlind;
import com.pokersimples.bo.Call;
import com.pokersimples.bo.Card;
import com.pokersimples.bo.Check;
import com.pokersimples.bo.Flop;
import com.pokersimples.bo.Fold;
import com.pokersimples.bo.Hand;
import com.pokersimples.bo.Player;
import com.pokersimples.bo.PreFlop;
import com.pokersimples.bo.Raise;
import com.pokersimples.bo.River;
import com.pokersimples.bo.SmallBlind;
import com.pokersimples.bo.Turn;
import com.pokersimples.parser.handhistory.ParserState;
import com.pokersimples.parser.handhistory.PokerHandHistoryParser;

/*
 * Header
 * Holecards
 * Flop
 * Turn
 * River
 * Summary
 */
public class PokerStarsParser  extends PokerHandHistoryParser {


	
	
	/*
	 * In the header we are looking:
	 * 	Header line 
	 *  Player names & stack sizes
	 *  Who is the button?
	 *  Antes & blinds
	 *  The hole cards being dealt (move to next state)
	 */
	protected void parseHeader() {
		
		// Are we trying to find the start of the header?
		if(state == null) {
			if(currentLine.contains("PokerStars")) {
				state = ParserState.HEAD;
				hand = new Hand();
				
				// Get the hand number
				hand.setHandId(extractStringData("Hand #", ":"));
				
				return;
			} else {
				return;
			}
		}		
		// We are already in the header
		if(currentLine.contains("is the button")) {
			buttonSeat = extractIntData(" Seat #", " ");		
			return;
		}

		if(currentLine.contains("Seat")) {
			int seatNumber = extractIntData(5,6);
			String playerName = extractStringData(8," (");
			int chips = extractIntData(" (", " in chips)");
			Player player = new Player();
			player.setPlayerName(playerName);
		
			player.setStartingChips(new BigDecimal(chips));
			player.setSeatNumber(seatNumber);
			hand.addPlayer(player  , seatNumber);
			playerSeatMap.put(playerName, player);
			if(seatNumber == buttonSeat) {
				player.setDealer(true);
			}
						return;
		}
		
		if(currentLine.contains("*** HOLE CARDS ***")) {
			state = ParserState.PLAY;
			return;
		}		
		

		if(currentLine.contains("posts the ante")) {
			Player player = getPlayerByName(extractStringData(0,":"));
			BigDecimal ante = new BigDecimal(extractIntData("posts the ante "));
			
			hand.addAction(new Ante(player, ante));
		}
		if(currentLine.contains("posts small blind")) {
			Player player = getPlayerByName(extractStringData(0,":"));
			int smallBlind = extractIntData("posts small blind ");
			hand.addAction(new SmallBlind(player, new BigDecimal(smallBlind)));
		}		
		if(currentLine.contains("posts big blind")) {
			Player player = getPlayerByName(extractStringData(0,":"));
			int bigBlind = extractIntData("posts big blind ");
			hand.addAction(new BigBlind(player, new BigDecimal(bigBlind)));
		}			
	}


	
	protected void parsePlay() {
		if(currentLine.contains("raises")) {
			Player player = getPlayerByName(extractStringData(0,":"));
			String raisesFrom = extractStringData("raises ", " to");
			String raisesTo = extractStringData(" to ");
			raisesTo = removeStringData(raisesTo, " and is all-in");
			hand.addAction(new Raise(player, new BigDecimal(raisesFrom), new BigDecimal(raisesTo)));
			
		}	else if(currentLine.contains("folds")) {
			Player player = getPlayerByName(extractStringData(0,":"));
			hand.addAction(new Fold(player));
			
		}	else if(currentLine.contains("checks")) {
			Player player = getPlayerByName(extractStringData(0,":"));
			hand.addAction(new Check(player));
			
		}	else if(currentLine.contains("calls")) {
			Player player = getPlayerByName(extractStringData(0,":"));
			int value = extractIntData("calls ");
			hand.addAction(new Call(player, new BigDecimal(value)));
			
		}	else if(currentLine.contains("bets ")) {
			Player player = getPlayerByName(extractStringData(0,":"));
			int value = extractIntData("bets ");
			hand.addAction(new Bet(player, new BigDecimal(value)));
			
		} else if(currentLine.contains("Dealt to")) {
			String playerName = extractStringData("Dealt to ",  " [");
			String cards = extractStringData("[","]");
			String[] tokens = cards.split(" ");
			
			Player player = getPlayerByName(playerName);
			player.setHoleCard1(Card.parse(tokens[0]));
			player.setHoleCard2(Card.parse(tokens[1]));

		} else if(currentLine.contains("shows")) {
			String playerName = extractStringData(0,":");
			String cards = extractStringData("[","]");
			String[] tokens = cards.split(" ");
			
			Player player = getPlayerByName(playerName);
			player.setHoleCard1(Card.parse(tokens[0]));
			player.setHoleCard2(Card.parse(tokens[1]));

		} else if(currentLine.contains("collected")) {
			String playerName = extractStringData(0," ");
			Player player = getPlayerByName(playerName);
			String collected = extractStringData("collected "," from");
			player.setWinnings(new BigDecimal(collected));
			
		} else if(currentLine.contains("*** FLOP ***")) {
			String cards = extractStringData("[","]");
			String[] tokens = cards.split(" ");
			Card card1 =Card.parse(tokens[0]);
			Card card2 =Card.parse(tokens[1]);
			Card card3 =Card.parse(tokens[2]);			
			hand.addAction(new Flop(card1, card2, card3));
		
			
		} else if(currentLine.contains("*** TURN ***")) {
			String card = extractStringData("] [","]");
			hand.addAction(new Turn(Card.parse(card)));
		
		} else if(currentLine.contains("*** RIVER ***")) {
			String card = extractStringData("] [","]");
			hand.addAction(new River(Card.parse(card)));
		
		} else if(currentLine.contains("*** SUMMARY ***")) {
			state = ParserState.COMPLETE;
		}		
		
		return;
	}
	
	

	


}
