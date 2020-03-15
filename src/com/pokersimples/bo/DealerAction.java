package com.pokersimples.bo;

import java.util.ArrayList;
import java.util.List;

public class DealerAction extends Action {
	List<Card> boardCards = new ArrayList<Card>();
	
	public void addBoardCard(Card pCard) {
		boardCards.add(pCard);
	}
}
