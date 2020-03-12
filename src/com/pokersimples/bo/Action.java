package com.pokersimples.bo;

import java.math.BigDecimal;

public class Action {
	private Player player;
	BigDecimal amount;
	
	public Action(Player pPlayer) {
		player = pPlayer;
	}
	
	public Action(Player pPlayer, BigDecimal pAmount) {
		this(pPlayer);
		amount = pAmount;

	}
}
