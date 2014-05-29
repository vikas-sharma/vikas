package com.vikas.domain;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Vikas Sharma
 * 
 */
public class GamePosition {

	private int gameId;
	private String gameTitle;
	private String gameStatus;

	private int personId;
	private String userType;
	private String userColor;
	private String userJoinStatus;
	private String userVoteStatus;

	private boolean turn;
	private boolean whiteOnTop;

	private String whitePlayer;
	private String blackPlayer;
	private String whiteTimeLeft;
	private String blackTimeLeft;

	private String fromSq;
	private String toSq;
	private String promotedPiece;

	private String[] boardImg;
	private List<Integer> pawnPromotionSquares;
	private Map<Integer, List<Integer>> dragDrops;

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getGameTitle() {
		return gameTitle;
	}

	public void setGameTitle(String gameTitle) {
		this.gameTitle = gameTitle;
	}

	public String getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserColor() {
		return userColor;
	}

	public void setUserColor(String userColor) {
		this.userColor = userColor;
	}

	public String getUserJoinStatus() {
		return userJoinStatus;
	}

	public void setUserJoinStatus(String userJoinStatus) {
		this.userJoinStatus = userJoinStatus;
	}

	public String getUserVoteStatus() {
		return userVoteStatus;
	}

	public void setUserVoteStatus(String userVoteStatus) {
		this.userVoteStatus = userVoteStatus;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public boolean isWhiteOnTop() {
		return whiteOnTop;
	}

	public void setWhiteOnTop(boolean whiteOnTop) {
		this.whiteOnTop = whiteOnTop;
	}

	public String getWhitePlayer() {
		return whitePlayer;
	}

	public void setWhitePlayer(String whitePlayer) {
		this.whitePlayer = whitePlayer;
	}

	public String getBlackPlayer() {
		return blackPlayer;
	}

	public void setBlackPlayer(String blackPlayer) {
		this.blackPlayer = blackPlayer;
	}

	public String getWhiteTimeLeft() {
		return whiteTimeLeft;
	}

	public void setWhiteTimeLeft(String whiteTimeLeft) {
		this.whiteTimeLeft = whiteTimeLeft;
	}

	public String getBlackTimeLeft() {
		return blackTimeLeft;
	}

	public void setBlackTimeLeft(String blackTimeLeft) {
		this.blackTimeLeft = blackTimeLeft;
	}

	public String getFromSq() {
		return fromSq;
	}

	public void setFromSq(String fromSq) {
		this.fromSq = fromSq;
	}

	public String getToSq() {
		return toSq;
	}

	public void setToSq(String toSq) {
		this.toSq = toSq;
	}

	public String getPromotedPiece() {
		return promotedPiece;
	}

	public void setPromotedPiece(String promotedPiece) {
		this.promotedPiece = promotedPiece;
	}

	public String[] getBoardImg() {
		return boardImg;
	}

	public void setBoardImg(String[] boardImg) {
		this.boardImg = boardImg;
	}

	public List<Integer> getPawnPromotionSquares() {
		return pawnPromotionSquares;
	}

	public void setPawnPromotionSquares(List<Integer> pawnPromotionSquares) {
		this.pawnPromotionSquares = pawnPromotionSquares;
	}

	public Map<Integer, List<Integer>> getDragDrops() {
		return dragDrops;
	}

	public void setDragDrops(Map<Integer, List<Integer>> dragDrops) {
		this.dragDrops = dragDrops;
	}
}
