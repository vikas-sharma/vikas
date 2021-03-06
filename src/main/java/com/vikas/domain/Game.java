package com.vikas.domain;

/**
 * 
 * @author Vikas Sharma
 * 
 */
public class Game {

	private long gameId;
	private String title;
	private String status;
	private int gmId;
	private boolean gmTurn;
	private boolean gmColorWhite;
	private int timeLeft;
	private String fen;

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getGmId() {
		return gmId;
	}

	public void setGmId(int gmId) {
		this.gmId = gmId;
	}

	public boolean isGmTurn() {
		return gmTurn;
	}

	public void setGmTurn(boolean gmTurn) {
		this.gmTurn = gmTurn;
	}

	public boolean isGmColorWhite() {
		return gmColorWhite;
	}

	public void setGmColorWhite(boolean gmColorWhite) {
		this.gmColorWhite = gmColorWhite;
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	public String getFen() {
		return fen;
	}

	public void setFen(String fen) {
		this.fen = fen;
	}
}
