package com.pokersimples.bo;

import java.math.BigDecimal;

public class PlayerAction extends Action{
	private Player player;
	private BigDecimal amount;
	
	public Player getPlayer() {
		return player;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public PlayerAction(Player pPlayer) {
		player = pPlayer;
	}
	
	public PlayerAction(Player pPlayer, BigDecimal pAmount) {
		this(pPlayer);
		amount = pAmount;

	}
}
