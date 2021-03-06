package com.vikas.dao;

import java.util.List;

import com.vikas.domain.Game;
import com.vikas.domain.Move;
import com.vikas.model.PersonGame;

/**
 * 
 * @author Vikas Sharma
 */
public interface GameDAO {

	void createGame(Game game);

	void addPersonToGame(int gameId, int personId);

	void removePersonFromGame(int gameId, int personId);

	void updateGame(Game game);

	void addMove(Move move);

	List<Game> getAllGames();

	List<Game> getGamesInProgress();

	List<Game> getPersonGames(int personId);

	String getMaxVotedMove(int gameId, int moveNo);

	int getPersonId(String name);

	Game getGame(int gameId);

	String getGmName(int gmId);

	PersonGame getPersonGameStatus(int gameId, int personId);

	Move getMove(int gameId, int personId);

}
