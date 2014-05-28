package com.vikas.domain;

/**
 * 
 * @author Vikas Sharma
 * 
 */
public class Move {

	private long moveId;
	private long personId;
	private long gameId;
	private int moveNo;
	private String move;

	public long getMoveId() {
		return moveId;
	}

	public void setMoveId(long moveId) {
		this.moveId = moveId;
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public int getMoveNo() {
		return moveNo;
	}

	public void setMoveNo(int moveNo) {
		this.moveNo = moveNo;
	}

	public String getMove() {
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}
}
