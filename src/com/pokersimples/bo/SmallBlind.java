package com.pokersimples.bo;

import java.math.BigDecimal;

public class SmallBlind extends PlayerAction {

	public SmallBlind(Player pPlayer, BigDecimal pAmount) {
		super(pPlayer, pAmount);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getActionName() {
		return "Small Blind " + getAmount();
	}
	
	public BigDecimal getBetSize() {
		return getAmount();
	}
}
