package com.pokersimples.bo;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Hand {
	private String mTableName;
	
	private long handId;
	
	private Map<Integer, Player> players = new Hashtable<Integer, Player>();
	private List<Action> actions =  new ArrayList<Action>();

	public Action getAction(int pSeq) {
		return actions.get(pSeq);
	}
	
	public void addAction(Action pAction) {
		actions.add(pAction);
	}
	
	public Player getPlayer(int pSeatNumber) {
		return players.get(pSeatNumber);
	}
	
	public void addPlayer(Player pPlayer, int pSeatNumber) {
		pPlayer.setHand(this);
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



	
}
