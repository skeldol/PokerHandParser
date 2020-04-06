package com.pokersimples.parser.handparser;


import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Pattern;

import com.learn.PokerSimulator.cominatorics.Combinations;
import com.pokersimples.bo.Card;
import com.pokersimples.bo.Rank;
import com.pokersimples.bo.Suit;


/*
 * Parses pocket pair ranges
 * I think we can make this parser a lot more OO.....
 * At the moment it doesn't do unique so if you pass hand ranges with duplicates duplicates will come back.  (e.g AKo, AcKd)
 */
public class HandParser {

	public static final String ALL_CARDS = "random";

	
	
	/*
	 * Used to convert String hand ranges into Lists of holecards matching the String ranges
	 * 
	 *    
	 * Pass in a range e.g.  
	 * AcAd     -exact cards (4 char match)
	 * AA,22+   -pair and pairs greater (two of the same possibly with a plus)
	 * AQo,AQo+ -offsuite & offsuit greater (contains lower o possibly with plus)
	 * AQs,AQs+ -suited and greater suited (contains lower s possibly with plsu)
	 * random will compute hole card combinations
	 * Ranges can be comma seperated.  The ranges between the commas will be calculated and then the entire lot joined with duplicates removed.
	 *
	 */
	public  List<List<Integer>> parseRange(String pRange) {
		// First turn the String into the various ranges
		// Calculate each range (seperated by commas) seperately
		// Combine and get rid of duplicates.
		// The sub class is responsible for transposing into the type of card used by the desired comparator 
		
		java.util.List<Integer> rangeCardList = new ArrayList<Integer>();
		
		List<List<Integer>> rangeCardHoleCombos = new ArrayList<List<Integer>>();
		
		String[] tokens = pRange.split(",");
		if(tokens == null) {
			throw new IllegalArgumentException("Nothing to parse");
		}
		
		// Parse each of the hand ranges given
		for(String hand: tokens) {
			
			Pattern plusAtEnd = Pattern.compile("\\+$");                             // For AA+, etc
			Pattern random = Pattern.compile("random");  
			Pattern offsuit = Pattern.compile("^[TJQKA2-9]{2}o\\+?");  
			Pattern suited = Pattern.compile("^[TJQKA2-9]{2}s\\+?");
			Pattern namedCards = Pattern.compile("^[TJQKA2-9][cdhs][TJQKA2-9][cdhs]");			
			Pattern pair = Pattern.compile("(.)\\1+\\+?");  		
			
			// Can't get the regex for this working, pattern works in https://regex101.com/
			boolean andAbove = hand.substring(hand.length() - 1).equals("+");
			
			if(random.matcher(hand).matches()) {
				for (int i = 1; i < 53; i++) {
					rangeCardList.add(i);
				}
				rangeCardHoleCombos = Combinations.combinations(rangeCardList, 2);		
				
			} else if(offsuit.matcher(hand).matches()) {
				rangeCardHoleCombos.addAll(calculateOffsuit(hand, andAbove));
				
			} else if(suited.matcher(hand).matches()) {
				rangeCardHoleCombos.addAll(calculateSuited(hand, andAbove));
				
			} else if(pair.matcher(hand).matches()) {
				rangeCardHoleCombos.addAll(calculatePairs(hand, andAbove));
				
			} else if(namedCards.matcher(hand).matches()) {
				List<Integer> holeCards = new ArrayList<Integer>();
				holeCards.add(Card.parse(hand.substring(0,1) + hand.substring(1,2)).ordinal()); 
				holeCards.add(Card.parse(hand.substring(2,3) + hand.substring(3,4)).ordinal()); 
				rangeCardHoleCombos.add(holeCards);
				
			} else {
				throw new IllegalArgumentException("Unable to parse " + hand);
			}
		}		
		return rangeCardHoleCombos;
	}
	
	
	
	/*
	 * Calculate the suited cards
	 */
	private  List<List<Integer>> calculateSuited(String hand, boolean andAbove) {
		// Get the two ranks we are interested in
		Rank rank1 = Rank.parse(hand.substring(0,1));	
		Rank rank2 = Rank.parse(hand.substring(1,2));		
		
		return calculateNonPairs(rank1, rank2, andAbove, false);
	}	
	
	/*
	 * Calculate the offsuit cards
	 */
	private  List<List<Integer>> calculateOffsuit(String hand, boolean andAbove) {
		// Get the two ranks we are interested in
		Rank rank1 = Rank.parse(hand.substring(0,1));	
		Rank rank2 = Rank.parse(hand.substring(1,2));		
		
		return calculateNonPairs(rank1, rank2, andAbove, true);
	}
	
	
	
	/*
	 * Calculate all the non-paired hands. 
	 * 
	 */
	private List<List<Integer>> calculateNonPairs(Rank pRank1, Rank pRank2, boolean pAndAbove, boolean pIsOffsuit){
		Rank rankTo;
		final Rank rank1;
		final Rank rank2;
		
		// Make sure pRank1 is the higher rank.
		if(pRank1.ordinal() > pRank2.ordinal()) {
			rank1 = pRank1;
			rank2 = pRank2;
		} else {
			rank1 = pRank2;
			rank2 = pRank1;
		}
		
		
		// We can't go any higher for the second card thank the rank below the rank of the 1st card
		if(pAndAbove) {
			rankTo = rank1.getPrevious();
		} else {
			rankTo = rank2;
		}
		
		List<List<Integer>> pairHoleCards = new ArrayList<List<Integer>>();
		
		// Get all four cards for the 1st rank
		List<Card> rank1Cards = getCards(rank1);
		
		// For each rank we need to make in the second card (e.g ATo+ is ATo, AJo, AQo, AKo)
		EnumSet.range(rank2, rankTo).forEach(rank -> {
			// For each card create it's counterparts of the other rank
			for(Card card1: rank1Cards) {
				// Get all cards for the second card that aren't of the suit of the 1st card
				List<Card> rank2Cards = getCardsofRank(rank, card1.getSuit(), pIsOffsuit);	
				for(Card card2: rank2Cards) {
					pairHoleCards.add(mapToEvalCards(card1, card2));
				}
			}
		});	
		
		return pairHoleCards;
	}
	
	
	/*
	 * Calculate the cards for the passed pair or pair+
	 */
	private  List<List<Integer>> calculatePairs(String hand, boolean andAbove) {
		Rank rank1 = Rank.parse(hand.substring(0,1));
		Rank rankTo;
		
		if(andAbove) {
			rankTo =Rank.Ace;
		} else {
			rankTo = rank1;
		}		
		
		List<List<Integer>> pairHoleCards = new ArrayList<List<Integer>>();
		
		// Loop round calculating the pairs.
		EnumSet.range(rank1, rankTo).forEach(rank -> {
			List<Card> rangeCards = getCards(rank);
			List<Integer> rangeInts = new ArrayList<Integer>();
			
			for(Card card: rangeCards) {
				rangeInts.add(card.ordinal());
			}
			
			pairHoleCards.addAll(Combinations.combinations(rangeInts, 2));
		});	


		
		return pairHoleCards;
	}
	
	
	
	
	/*
	 * For the passed rank returns all cards of that rank for all 4 suits
	 * You can pass in a list of suits that should not be included in the generated cards
	 */
	private  List<Card> getCardsofRank(Rank pRank, List<Suit> pFilter, boolean pIsFilterExclusive) {
		List<Card> rangeCards = new ArrayList<Card>();
		
		// Create all cards of the passed rank
		
		EnumSet<Suit> set = EnumSet.allOf(Suit.class);
		
		for(Suit suit : set) {
			// If there is no filter just add the card
			if(pFilter == null || pFilter.isEmpty()) {
				rangeCards.add(Card.get(pRank, suit));	
			
			// If the filter is exclusive don't add anything where it matches the filter
			} else if(pIsFilterExclusive && !pFilter.contains(suit)) {
				rangeCards.add(Card.get(pRank, suit));
			
				// If the filter is inclusive add anything where it matches the filter				
			} else if(!pIsFilterExclusive && pFilter.contains(suit)) {
				rangeCards.add(Card.get(pRank, suit));
			}
		}
		
		return rangeCards;
	}
	
	private  List<Card> getCardsofRank(Rank pRank, Suit pSuit, boolean pIsOffsuit) {
		List<Suit> filter = new ArrayList<Suit>();
		filter.add(pSuit);
		return pIsOffsuit ? getCardsofRank(pRank, filter, true) : getCardsofRank(pRank, filter, false);
	}
	
	
	private  List<Card> getCards(Rank pRank) {

		return getCardsofRank(pRank, new ArrayList<Suit>(), false);
	}
	
	
	
	
	/*
	 * Pass in two Cards & get returned a List of the int values of the two cards.
	 */
	private List<Integer> mapToEvalCards(Card pCard1, Card pCard2) {
		List<Integer> holeCards = new ArrayList<Integer>();
		holeCards.add(Card.get(pCard1.getRank(), pCard1.getSuit()).ordinal());
		holeCards.add(Card.get(pCard2.getRank(), pCard2.getSuit()).ordinal());
		return holeCards;
		
	}
	
}
