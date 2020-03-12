package com.pokersimples.parser.pokerstars;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.learn.PokerSimulator.utils.Logger;
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
import com.pokersimples.parser.ParserState;
import com.pokersimples.parser.PokerHandHistoryParser;

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
				hand.setPreFlop(new PreFlop());
				
				// Get the hand number
				hand.setmHandId(extractLongData("Hand #", ":"));
				
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
			player.setChips(new BigDecimal(chips));
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
			String playerName = extractStringData(0,":");
			Player player = getPlayerByName(playerName);
			int ante = extractIntData("posts the ante ");
			player.setAnte(new BigDecimal(ante));
		}
		if(currentLine.contains("posts small blind")) {
			String playerName = extractStringData(0,":");
			Player player = getPlayerByName(playerName);
			int smallBlind = extractIntData("posts small blind ");
			hand.addAction(new SmallBlind(player, new BigDecimal(smallBlind)));
		}		
		if(currentLine.contains("posts big blind")) {
			String playerName = extractStringData(0,":");
			Player player = getPlayerByName(playerName);
			int bigBlind = extractIntData("posts big blind ");
			hand.addAction(new BigBlind(player, new BigDecimal(bigBlind)));
		}			
	}


	
	protected void parsePlay() {
		if(currentLine.contains("raises")) {
			String playerName = extractStringData(0,":");
			Player player = getPlayerByName(playerName);
			String raises = extractStringData(" to ");
			raises = removeStringData(raises, " and is all-in");
			int value = Integer.valueOf(raises);
			hand.addAction(new Raise(player, new BigDecimal(value)));
		}		
		
		if(currentLine.contains("folds")) {
			String playerName = extractStringData(0,":");
			Player player = getPlayerByName(playerName);
			hand.addAction(new Fold(player));
		}	
		
		if(currentLine.contains("checks")) {
			String playerName = extractStringData(0,":");
			Player player = getPlayerByName(playerName);
			hand.addAction(new Check(player));
		}	
		
		if(currentLine.contains("calls")) {
			String playerName = extractStringData(0,":");
			Player player = getPlayerByName(playerName);
			hand.addAction(new Call(player));
		}	
		
		if(currentLine.contains("bets ")) {
			String playerName = extractStringData(0,":");
			Player player = getPlayerByName(playerName);
			int value = extractIntData("bets ");
			hand.addAction(new Bet(player, new BigDecimal(value)));
		}	
		
		if(currentLine.contains("Dealt to")) {
			String playerName = extractStringData("Dealt to ",  " [");
			String cards = extractStringData("[","]");
			String[] tokens = cards.split(" ");
			
			Player player = getPlayerByName(playerName);
			player.setHoleCard1(Card.parse(tokens[0]));
			player.setHoleCard2(Card.parse(tokens[1]));

		}
		if(currentLine.contains("shows")) {
			String playerName = extractStringData(0,":");
			String cards = extractStringData("[","]");
			String[] tokens = cards.split(" ");
			
			Player player = getPlayerByName(playerName);
			player.setHoleCard1(Card.parse(tokens[0]));
			player.setHoleCard2(Card.parse(tokens[1]));

		}		

		if(currentLine.contains("collected")) {
			String playerName = extractStringData(0," ");
			Player player = getPlayerByName(playerName);
			player.setWinner(true);
		}	
		
		if(currentLine.contains("*** FLOP ***")) {
			String cards = extractStringData("[","]");
			String[] tokens = cards.split(" ");
			Card card1 =Card.parse(tokens[0]);
			Card card2 =Card.parse(tokens[1]);
			Card card3 =Card.parse(tokens[2]);			
			hand.setFlop(new Flop(card1, card2, card3));
			return;
		}	

		if(currentLine.contains("*** TURN ***")) {
			String card = extractStringData("] [","]");
			hand.setTurn(new Turn(Card.parse(card)));
			return;
		}
		
		if(currentLine.contains("*** RIVER ***")) {
			String card = extractStringData("] [","]");
			hand.setRiver(new River(Card.parse(card)));
			return;
		}	
		
		if(currentLine.contains("*** SUMMARY ***")) {
			state = ParserState.COMPLETE;
			return;
		}		
	}
	
	

	


}
