package com.pokersimples.bo;

public class Flop extends Round {
	public Flop(Card pCard1, Card pCard2, Card pCard3) {
		addBoardCard(pCard1);
		addBoardCard(pCard2);	
		addBoardCard(pCard3);
	}
}
