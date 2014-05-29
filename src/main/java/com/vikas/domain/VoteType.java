package com.vikas.domain;

/**
 * 
 * @author Vikas Sharma
 *
 */
public enum VoteType {

	NORMAL(1), DRAW_OFFER(2), DRAW_ACCEPT(3), RESIGN(4);

	private int type;

	private VoteType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
