package com.pokersimples.bo;

import java.math.BigDecimal;

public abstract class PlayerAction extends Action{
	private Player player;
	private BigDecimal amount = new BigDecimal(0);
	private BigDecimal sign = new BigDecimal(-1);
	
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
	
	/*
	 * Returns the size of the last bet in this betting round:
	 * Big Blind, Bet or Raise TO
	 */
	public BigDecimal lastBet() {
		Action action = this;
		BigDecimal lastBet = null;
		while(action.getPreviousAction() != null) {
			action = action.getPreviousAction();
			// We found a bet in this round so return it
			if(action instanceof BigBlind || action instanceof Bet || action instanceof Raise) {
				PlayerAction lastAction = (PlayerAction)action;
				if(lastAction.getPlayer() == getPlayer()) {
					lastBet = (BigDecimal)((PlayerAction)action).getBetSize();
					break;
				}

				
			// We found the start of the round first so return null
			} else if(action instanceof Flop || action instanceof Turn || action instanceof River) {
				lastBet = null;
				break;
			}
		}
		
		return lastBet;
	}  
	
	/*
	 * Returns the pot size after this action is played
	 */
	public BigDecimal getPot() {
		 //The size of the pot is equal to the previous pot size plus this persons bet
		 //If the person has raised then need to understand what their raise increment is from their
		 //last bet.
		BigDecimal lastPot = new BigDecimal(0);
		if(getPreviousAction() != null) {
			lastPot = getPreviousAction().getPot();
		}
		
		
		return lastPot.add(getBetSize());
	}
	  
	/*
	 * Returns the last Action played by the player
	 * assoicated with this action.
	 */
	public PlayerAction lastPlayerAction() {
		Action action = this;
		PlayerAction lastPlayerAction = null;
		while(action.getPreviousAction() != null && action.getPreviousAction() != this) {
			action = action.getPreviousAction();
			if(action instanceof PlayerAction & ((PlayerAction)action).getPlayer() == getPlayer()) {
				lastPlayerAction = (PlayerAction)action;
				break;
			}
		}
		
		return lastPlayerAction;
	}
	
	/*
	 * Returns the players chip count after this action. 
	 */
	public BigDecimal getChipCount() {
		 //The players chip count is equal to the previous chip count - their last betting action
		BigDecimal lastChipCount = null;
		if(lastPlayerAction() != null) {
			lastChipCount = lastPlayerAction().getChipCount();
		} else {
			lastChipCount = getPlayer().getStartingChips();
			
			BigDecimal ante = getPlayer().getAnte();
			if(ante != null) {
				lastChipCount = lastChipCount.subtract(ante);
			}
		}		
		
		return lastChipCount.subtract(getBetSize());
		
	}
	
	public abstract String getActionName();
	
	/*
	 * Should return the value contributed to the pot for this action
	 * For most bets this is the bet size of the current action only
	 * However for a raise it will be based on the previous bet 
	 * e.g. B bets 200, A raises to 500, B raises to 600  In A's second raise he only contributes another 400 on his second raise.
	 */
	public abstract BigDecimal getBetSize();
}
