package com.pokersimples.bo;

import java.math.BigDecimal;

public class BigBlind extends PlayerAction {

	public BigBlind(Player pPlayer, BigDecimal pAmount) {
		super(pPlayer, pAmount);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getActionName() {
		return "Big Blind " + getAmount();
	}
	
	public BigDecimal getBetSize() {
		return getAmount();
	}
	
}
