package com.vikas.domain;

import java.util.List;

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
	private boolean userColor;
	private String userJoinStatus;
	private String userVoteStatus;

	private String positionStatus;

	private boolean turn;
	private boolean whiteOnTop;

	private boolean gmPlayer;

	private String whitePlayer;
	private String blackPlayer;
	private String whiteTimeLeft;
	private String blackTimeLeft;

	private String fromSq;
	private String toSq;
	private String promotedPiece;

	private String fen;
	private List<Integer> pawnPromotionSquares;
	private String dragDrop;

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

	public boolean isUserColor() {
		return userColor;
	}

	public void setUserColor(boolean userColor) {
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

	public String getPositionStatus() {
		return positionStatus;
	}

	public void setPositionStatus(String positionStatus) {
		this.positionStatus = positionStatus;
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

	public boolean isGmPlayer() {
		return gmPlayer;
	}

	public void setGmPlayer(boolean gmPlayer) {
		this.gmPlayer = gmPlayer;
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

	public String getFen() {
		return fen;
	}

	public void setFen(String fen) {
		this.fen = fen;
	}

	public List<Integer> getPawnPromotionSquares() {
		return pawnPromotionSquares;
	}

	public void setPawnPromotionSquares(List<Integer> pawnPromotionSquares) {
		this.pawnPromotionSquares = pawnPromotionSquares;
	}

	public String getDragDrop() {
		return dragDrop;
	}

	public void setDragDrop(String dragDrop) {
		this.dragDrop = dragDrop;
	}

	@Override
	public String toString() {
		return "GamePosition [gameId=" + gameId + ", gameTitle=" + gameTitle
				+ ", gameStatus=" + gameStatus + ", personId=" + personId
				+ ", userType=" + userType + ", userColor=" + userColor
				+ ", userJoinStatus=" + userJoinStatus + ", userVoteStatus="
				+ userVoteStatus + ", positionStatus=" + positionStatus
				+ ", turn=" + turn + ", whiteOnTop=" + whiteOnTop
				+ ", gmPlayer=" + gmPlayer + ", whitePlayer=" + whitePlayer
				+ ", blackPlayer=" + blackPlayer + ", whiteTimeLeft="
				+ whiteTimeLeft + ", blackTimeLeft=" + blackTimeLeft
				+ ", fromSq=" + fromSq + ", toSq=" + toSq + ", promotedPiece="
				+ promotedPiece + ", fen=" + fen + ", pawnPromotionSquares="
				+ pawnPromotionSquares + ", dragDrop=" + dragDrop + "]";
	}
}
