package com.pokersimples.bo;

import java.math.BigDecimal;

public class Ante extends PlayerAction {

	public Ante(Player pPlayer, BigDecimal pAmount) {
		super(pPlayer, pAmount);
	}

	@Override
	public String getActionName() {
		return "Ante " + getAmount();
	}

	@Override
	public BigDecimal getBetSize() {
		return getAmount();
	}

}
