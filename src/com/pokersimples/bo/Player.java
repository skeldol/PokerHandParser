package com.pokersimples.bo;

import java.math.BigDecimal;

public class Player {
	private String playerName;
	private int seatNumber;
	private boolean isDealer;
	private BigDecimal chips;
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
	public BigDecimal getChips() {
		return chips;
	}
	public void setChips(BigDecimal chips) {
		this.chips = chips;
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
	
}
