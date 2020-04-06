package com.pokersimples.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

public class Player implements Serializable {

	private Hand hand;
	private String playerName;
	private int seatNumber;
	private boolean isDealer;
	private BigDecimal startingChips;
	private Card holeCard1;
	private Card holeCard2;
	private BigDecimal winnings = new BigDecimal(0);   // Default didn't win anything

	public BigDecimal getWinnings() {
		return winnings;
	}

	public void setWinnings(BigDecimal winnings) {
		this.winnings = winnings;
	}

	public boolean isHero() {
		return playerName.equals("skeldol") ? true : false;
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
	
	
	public void setStartingChips(BigDecimal chips) {
		this.startingChips = chips;
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
	
	/*
	 * Winnings - chips put in pot
	 */
	public BigDecimal netWinnings() {
		BigDecimal finalChipCount = playersLastAction().getChipCount();
		BigDecimal chipsPutInPot = startingChips.subtract(finalChipCount);
		return winnings.subtract(chipsPutInPot);
	}
	
	// Return the last action in the hand by this player (i.e. a Fold or a showdown)
	private PlayerAction playersLastAction() {
		Action action = hand.getLastAction();
		while(action.getPreviousAction() != null) {
			action = action.getPreviousAction();
			if(action instanceof PlayerAction && ((PlayerAction)action).getPlayer() == this) {
				return (PlayerAction) action;
			}
	
		}
		
		return null;
		
	}
	
}
