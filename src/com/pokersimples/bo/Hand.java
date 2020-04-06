package com.pokersimples.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Hand implements Serializable {
	private String mTableName;
	
	private String handId;
	
	private Map<Integer, Player> players = new Hashtable<Integer, Player>();
	private List<Action> actions =  new ArrayList<Action>();

	public Action getAction(int pSeq) {
		return actions.get(pSeq);
	}
	
	// Returns the final action in the hand
	public Action getLastAction() {
		return actions.get(actions.size() - 1 );

	}
	
	public void addAction(Action pAction) {
		pAction.setSeq(actions.size());
		Action previousAction = null;
		if(actions != null && actions.size() != 0) {
			previousAction = actions.get(actions.size() - 1);
		} else {
			previousAction = null;
		}
		pAction.setPreviousAction(previousAction);
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
	public String getHandId() {
		return handId;
	}
	public void setHandId(String pHandId) {
		this.handId = pHandId;
	}
	
	public Player getHero() {
		Iterator<Player> iter = players.values().iterator();
		while(iter.hasNext()) {
			Player player = iter.next();
			if(player.isHero()) {
				return player;
			}
		}
		
		return null;
	}

}
