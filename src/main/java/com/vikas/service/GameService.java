package com.vikas.service;

import java.util.List;

import com.vikas.domain.Game;
import com.vikas.domain.GamePosition;
import com.vikas.domain.Move;

/**
 * 
 * @author Vikas Sharma
 */
public interface GameService {

	List<Game> getAllGames();

	List<Game> getGamesInProgress();

	List<Game> getPersonGames(int personId);

	GamePosition getPosition(int gameId);

	void createGame(Game game);

	void addPersonToGame(int gameId, int personId);

	void removePersonFromGame(int gameId, int personId);

	void updateGame(Game game);

	void addMove(Move move);

	String getMaxVotedMove(int gameId, int moveNo);
}
