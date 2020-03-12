package com.pokersimples.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Hand {
	private String mTableName;
	
	private long handId;
	
	private Map<Integer, Player> players = new Hashtable<Integer, Player>();
	
	private PreFlop preFlop;
	private Flop flop;
	private Turn turn;
	private River river;
	
	public void addAction(Action pAction) {
		if(river != null) {
			river.addAction(pAction);
		} else if(turn != null) {
			turn.addAction(pAction);	
		} else if(flop != null) {
			flop.addAction(pAction);
		} else if(preFlop != null) {
			preFlop.addAction(pAction);
		} else {
			throw new RuntimeException("No Round available for action." + pAction);
		}
	}
	
	public void addPlayer(Player pPlayer, int pSeatNumber) {

		players.put(pSeatNumber , pPlayer);
	}
	
	public String getmTableName() {
		return mTableName;
	}
	public void setmTableName(String mTableName) {
		this.mTableName = mTableName;
	}
	public long getmHandId() {
		return handId;
	}
	public void setmHandId(long mHandId) {
		this.handId = mHandId;
	}

	public PreFlop getPreFlop() {
		return preFlop;
	}
	public void setPreFlop(PreFlop preFlop) {
		this.preFlop = preFlop;
	}
	public Flop getFlop() {
		return flop;
	}
	public void setFlop(Flop flop) {
		this.flop = flop;
	}
	public Turn getTurn() {
		return turn;
	}
	public void setTurn(Turn turn) {
		this.turn = turn;
	}
	public River getRiver() {
		return river;
	}
	public void setRiver(River river) {
		this.river = river;
	}

	
}
