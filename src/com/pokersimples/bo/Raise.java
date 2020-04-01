package com.pokersimples.bo;

import java.math.BigDecimal;

public class Raise extends PlayerAction {

	private BigDecimal raisesTo;
	
	public Raise(Player pPlayer, BigDecimal pAmount, BigDecimal pRaisesTo) {
		super(pPlayer, pAmount);
		raisesTo = pRaisesTo;
	}
	
	@Override
	public String getActionName() {
		return "Raise " + getRaiesTo();
	}
	
	public BigDecimal getRaiesTo() {
		return raisesTo;
	}
	
	/*
	 * The bet size of a raise is relative
	 * to the last bet, raise or big blind.
	 */
	public BigDecimal getBetSize() {
		BigDecimal lastBet = playersLastBet();
		if(lastBet == null) {
			lastBet = new BigDecimal(0);
		}
		return getRaiesTo().subtract(lastBet);
	}
}
