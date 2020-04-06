package com.pokersimples.bo;

import java.io.Serializable;
import java.math.BigDecimal;

public abstract class Action implements Serializable {
	private int seq;
	private Action previousAction;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	public Action getPreviousAction() { return previousAction; }
	
	void setPreviousAction(Action pPreviousAction) {
		previousAction = pPreviousAction;
	}
	
	
	public abstract BigDecimal getPot();
	
}
