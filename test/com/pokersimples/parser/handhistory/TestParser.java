package com.pokersimples.parser.handhistory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;

import com.pokersimples.bo.Card;
import com.pokersimples.bo.Hand;
import com.pokersimples.bo.Player;
import com.pokersimples.bo.PlayerAction;
import com.pokersimples.parser.handhistory.pokerstars.PokerStarsParser;

import junit.framework.TestCase;

public class TestParser extends TestCase {
	List<Hand> hands = new ArrayList<Hand>();
	Hand hand;
	
	@BeforeClass
    public void setUp() {
		System.out.println(System.getProperty("user.dir"));
		File file = new File(".\\test\\TestSinglePokerHand.txt"); 
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			
			PokerStarsParser parser = new PokerStarsParser();
			String st; 
			
			Hand hand = null;
			
			while ((st = br.readLine()) != null) 
				hand = parser.parse(st); 
				
				if(hand != null) {
					hands.add(hand);
				}
			
		} catch(Exception e1) {
			e1.printStackTrace();
		}
		
		hand = hands.get(0);
	}
	
	public void testGamesParsed() {
		assertEquals(1, hands.size());
	}
	
	public void testPlayers() {
		Player player1 = hand.getPlayer(1);
		Player player2 = hand.getPlayer(2);
		Player player3 = hand.getPlayer(3);
		Player player4 = hand.getPlayer(4);
		Player player5 = hand.getPlayer(5);
		Player player6 = hand.getPlayer(6);
		assertEquals("Creeker60", player1.getPlayerName());
		assertEquals("skeldol", player2.getPlayerName());
		assertEquals("NagsarInaste", player3.getPlayerName());
		assertEquals("Mikos108", player4.getPlayerName());
		assertEquals("nycflasher", player5.getPlayerName());
		
		assertEquals("2982", player1.getStartingChips().toString());
		assertEquals("972", player2.getStartingChips().toString());
		assertEquals("2255", player3.getStartingChips().toString());
		assertEquals("1171", player4.getStartingChips().toString());
		assertEquals("1620", player5.getStartingChips().toString());

		assertNull(player1.getHoleCard1());
		assertNull(player1.getHoleCard2());
		assertEquals(Card.parse("Ad"), player2.getHoleCard1());
		assertEquals(Card.parse("Kh"), player2.getHoleCard2());
		assertEquals(Card.parse("Th"), player3.getHoleCard1());
		assertEquals(Card.parse("Tc"), player3.getHoleCard2());
		assertNull(player4.getHoleCard1());
		assertNull(player4.getHoleCard2());
		assertNull(player5.getHoleCard1());
		assertNull(player5.getHoleCard2());
		
	}
	
	public void testPotOdds() {
		// Start at UTG
		PlayerAction playerAction = (PlayerAction)hand.getAction(7);
		assertEquals("2.1", playerAction.getPotOdds().toString());
		playerAction = (PlayerAction)hand.getAction(8);
		assertEquals("1.7", playerAction.getPotOdds().toString());
		playerAction = (PlayerAction)hand.getAction(9);
		assertEquals("2.0", playerAction.getPotOdds().toString());
		playerAction = (PlayerAction)hand.getAction(10);
		assertEquals("2.2", playerAction.getPotOdds().toString());
		playerAction = (PlayerAction)hand.getAction(11);
		assertEquals("2.5", playerAction.getPotOdds().toString());
		playerAction = (PlayerAction)hand.getAction(12);
		assertEquals("5.1", playerAction.getPotOdds().toString());
		playerAction = (PlayerAction)hand.getAction(13);
		assertEquals("1.8", playerAction.getPotOdds().toString());
	}
	
	public void testNetWinnings() {
		Player player2 = hand.getPlayer(2);
		assertEquals("-972", player2.netWinnings().toString());
	}
}
