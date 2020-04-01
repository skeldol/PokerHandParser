package com.pokersimples.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DealerAction extends Action {
	List<Card> boardCards = new ArrayList<Card>();
	
	public void addBoardCard(Card pCard) {
		boardCards.add(pCard);
	}
	
	public Card getBoardCard(int pSeq) {
		return boardCards.get(pSeq);
	}

	
	public BigDecimal getPot() {
		// Dealer action shouldn't change the pot size so just return the last player pot size
		return getPreviousAction().getPot();
		
	}
	
}
