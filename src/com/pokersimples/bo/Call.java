package com.pokersimples.bo;

import java.math.BigDecimal;

public class Call extends PlayerAction {

	public Call(Player pPlayer, BigDecimal pAmount) {
		super(pPlayer, pAmount);
	}
		
	
	@Override
	public String getActionName() {
		return "Call " + getAmount();
	}
	
	public BigDecimal getBetSize() {
		return getAmount();
	}
}
