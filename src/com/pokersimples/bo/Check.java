package com.pokersimples.bo;

import java.math.BigDecimal;

public class Check extends PlayerAction {

	public Check(Player pPlayer) {
		super(pPlayer);
		
	}
	@Override
	public String getActionName() {
		return "Check";
	}
	
	public BigDecimal getBetSize() {
		return getAmount();
	}
}
