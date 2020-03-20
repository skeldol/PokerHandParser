package com.pokersimples.bo;

import java.math.BigDecimal;

public class Fold extends PlayerAction {
	
	public Fold(Player pPlayer) {
		super(pPlayer);
		
	}
	@Override
	public String getActionName() {
		return "Fold";
	}
	
	public BigDecimal getBetSize() {
		return getAmount();
	}
}
