package com.vikas.service;

import java.util.List;

import com.vikas.domain.Game;
import com.vikas.domain.GamePosition;
import com.vikas.domain.Move;

/**
 * 
 * @author Vikas Sharma
 * 
 */
public interface GameService {

	List<Game> getAllGames();

	List<Game> getGamesInProgress();

	List<Game> getPersonGames(int personId);

	GamePosition getPosition(int gameId);

	GamePosition addPersonToGame(int gameId, int personId);

	GamePosition removePersonFromGame(int gameId, int personId);

	GamePosition addMove(Move move);

	void createGame(Game game);

	void updateGame(Game game);

	String getMaxVotedMove(int gameId, int moveNo);
}
