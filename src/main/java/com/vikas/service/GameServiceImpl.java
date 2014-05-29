package com.vikas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vikas.dao.GameDAO;
import com.vikas.domain.Game;
import com.vikas.domain.GamePosition;
import com.vikas.domain.Move;

/**
 * 
 * @author Vikas Sharma
 */
@Service
@Transactional
public class GameServiceImpl implements GameService {

	@Autowired
	private GameDAO gameDAO;

	@Override
	public List<Game> getAllGames() {

		return gameDAO.getAllGames();
	}

	@Override
	public List<Game> getGamesInProgress() {

		return gameDAO.getGamesInProgress();
	}

	@Override
	public List<Game> getPersonGames(int personId) {

		return gameDAO.getPersonGames(personId);
	}

	@Override
	public GamePosition getPosition(int gameId) {

		GamePosition position = new GamePosition();

		position.setGameId(gameId);

		String currentUser = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		int personId = gameDAO.getPersonId(currentUser);

		position.setPersonId(personId);

		return position;
	}

	@Override
	public void createGame(Game game) {

	}

	@Override
	public void addPersonToGame(int gameId, int personId) {

	}

	@Override
	public void removePersonFromGame(int gameId, int personId) {

	}

	@Override
	public void updateGame(Game game) {

	}

	@Override
	public void addMove(Move move) {

	}

	@Override
	public String getMaxVotedMove(int gameId, int moveNo) {

		return null;
	}

}
