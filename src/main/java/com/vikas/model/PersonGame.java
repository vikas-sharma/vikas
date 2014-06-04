package com.vikas.model;

/**
 * 
 * @author Vikas Sharma
 * 
 */
public class PersonGame {

	private long gameId;
	private long personId;
	private boolean active;

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
