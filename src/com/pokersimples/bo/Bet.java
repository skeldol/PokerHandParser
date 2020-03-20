package com.pokersimples.bo;

import java.math.BigDecimal;

public class Bet extends PlayerAction {

	public Bet(Player pPlayer, BigDecimal pAmount) {
		super(pPlayer, pAmount);
	}

	@Override
	public String getActionName() {
		return "Bet " + getAmount();
	}
	
	public BigDecimal getBetSize() {
		return getAmount();
	}
}
