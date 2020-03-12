package com.pokersimples.bo;

import java.util.ArrayList;
import java.util.List;

public class Round {
	List<Action> actions = new ArrayList<Action>();
	
	List<Card> boardCards = new ArrayList<Card>();
	
	public Round() {}
	
	public Round(Card pCard1) {
		addBoardCard(pCard1);
	}
	
	public void addAction(Action pAction) {
		actions.add(pAction);
	}
	
	
	public void addBoardCard(Card pCard) {
		boardCards.add(pCard);
	}
}
