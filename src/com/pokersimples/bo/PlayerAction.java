package com.pokersimples.bo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.learn.PokerSimulator.utils.Logger;

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
	 * Returns the amount put into the pot by the previous player
	 * Returns 0 if previous action was a dealer action
	 */
	public BigDecimal previousBet() {
		Action action = this;
		BigDecimal lastBet = null;
		while(action.getPreviousAction() != null) {
			
		}
		if(getPreviousAction() == null  || getPreviousAction() instanceof DealerAction) {
			return new BigDecimal(0);
		} else 
			return ((PlayerAction)getPreviousAction()).getAmount();
	}
	
	
	// Returns the last action played by this player
	public PlayerAction previousPlayerAction() {
		Action action = this;
		while(action.getPreviousAction() != null) {
			action = action.getPreviousAction();
			if(action instanceof PlayerAction && ((PlayerAction)action).getPlayer() == getPlayer()) {
				return (PlayerAction) action;
			}
	
		}
		
		return null;
	}
	
	/*
	* Returns the size of the last bet in this betting round for the current player
	*/
	public BigDecimal playersLastBet() {
		Action action = this;
		BigDecimal lastBet = null;
		// Keep going back until we find the last bet made by the current player
		while(action.getPreviousAction() != null) {
			action = action.getPreviousAction();
			// First check if we found a betting action
			if(action instanceof SmallBlind || action instanceof BigBlind || action instanceof Bet || action instanceof Raise || 
					action instanceof Call) {
				PlayerAction lastAction = (PlayerAction)action;
				// If we found a betting action and it was for the current player return it.
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
		
		Logger.debug("playersLastBet  size is " + lastBet);
		return lastBet;
	}  
	
	/*
	 * Returns the pot size after this action is played
	 */
	public BigDecimal getPot() {
		 //The size of the pot is equal to the previous pot size plus this persons bet
		 //If the person has raised then need to understand what their raise increment is from their
		 //last bet.
		BigDecimal lastPot = getPotBeforeThisAction();
		
		return lastPot.add(getBetSize());
	}
	
	
	/*
	 * Returns the last active bet, basically ignores any Fold
	 * If a dealer action is reached before an active bet returns 0
	 */
	public BigDecimal activeBet() {
		Action action = this;
		BigDecimal activeBet = null;
	
		while(action.getPreviousAction() != null) {
			action = action.getPreviousAction();
			// First check if we found a betting action
			if(action instanceof SmallBlind || action instanceof BigBlind || action instanceof Bet || action instanceof Raise || 
						action instanceof Call) {
				if(action instanceof Raise) {
					activeBet = (BigDecimal)((Raise)action).getRaiesTo();
				} else {
					activeBet = (BigDecimal)((PlayerAction)action).getBetSize();
				}

				break;

			// If  found the start of the round first so return null
			} else if(action instanceof Flop || action instanceof Turn || action instanceof River) {
				activeBet = null;
				break;
			}
			
		}
		
		Logger.debug("activeBet  is " + activeBet);
		return activeBet;
	}  
	
	
	// returns the pot size at the end of last action
	private BigDecimal getPotBeforeThisAction() {
		BigDecimal lastPot = new BigDecimal(0);
		if(getPreviousAction() != null) {
			lastPot = getPreviousAction().getPot();
		}
		return lastPot;
	}
	
	
	/*
	 * The pot odds being offered when this action is happening
	 */
	public BigDecimal getPotOdds() {
		BigDecimal betSize = activeBet();
		BigDecimal playersLastBet = playersLastBet();
		
		if(betSize == null) {
			betSize = BigDecimal.ZERO;
		}
		
		if(playersLastBet == null) {
			playersLastBet = BigDecimal.ZERO;
		}
		
		BigDecimal callSize = betSize.subtract(playersLastBet);
		
		// https://stackoverflow.com/questions/10950914/how-to-check-if-bigdecimal-variable-0-in-java
		if(betSize == null || betSize.compareTo(BigDecimal.ZERO) == 0) {
			return new BigDecimal(0);
		} else {
			//https://stackoverflow.com/questions/4591206/arithmeticexception-non-terminating-decimal-expansion-no-exact-representable
			return getPotBeforeThisAction().divide(callSize, 1, RoundingMode.HALF_UP);	
		}

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
