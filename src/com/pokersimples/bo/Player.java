package com.pokersimples.bo;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

public class Player {

	private Hand hand;
	private String playerName;
	private int seatNumber;
	private boolean isDealer;
	private BigDecimal startingChips;
	private BigDecimal ante;	
	private Card holeCard1;
	private Card holeCard2;
	private boolean winner;
	
	
	public boolean isWinner() {
		return winner;
	}
	public void setWinner(boolean winner) {
		this.winner = winner;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public int getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
	public boolean isDealer() {
		return isDealer;
	}
	public void setDealer(boolean isDealer) {
		this.isDealer = isDealer;
	}
	public BigDecimal getStartingChips() {
		return startingChips;
	}
	
	public BigDecimal getChipsAfterAnte() {
		if(ante != null) {
			return startingChips.subtract(ante);
		} else {
			return startingChips;
		}
	}
	
	public void setStartingChips(BigDecimal chips) {
		this.startingChips = chips;
	}

	public BigDecimal getAnte() {
		return ante;
	}
	public void setAnte(BigDecimal ante) {
		this.ante = ante;
	}
	public Card getHoleCard1() {
		return holeCard1;
	}
	public void setHoleCard1(Card holeCard1) {
		this.holeCard1 = holeCard1;
	}
	public Card getHoleCard2() {
		return holeCard2;
	}
	public void setHoleCard2(Card holeCard2) {
		this.holeCard2 = holeCard2;
	}
	
	void setHand(Hand hand) {
		this.hand = hand;
	}
	
}
