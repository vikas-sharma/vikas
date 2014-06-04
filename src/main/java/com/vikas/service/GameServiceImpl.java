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
import com.vikas.model.PersonGame;

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

		Game game = gameDAO.getGame(gameId);
		String gmName = gameDAO.getGmName(game.getGmId());

		if (personId == game.getGmId()) { // if person is title player

			position.setGmPlayer(true);

			position.setTurn(game.isGmTurn());
			position.setUserColor(game.isGmColorWhite());

			if (position.isUserColor()) {
				position.setWhitePlayer(gmName);
				position.setBlackPlayer(currentUser);
				position.setWhiteTimeLeft(String.valueOf(game.getTimeLeft()));
			} else {
				position.setWhitePlayer(currentUser);
				position.setBlackPlayer(gmName);
				position.setBlackTimeLeft(String.valueOf(game.getTimeLeft()));
			}

		} else {

			position.setGmPlayer(false);

			position.setTurn(!game.isGmTurn());
			position.setUserColor(!game.isGmColorWhite());

			if (position.isUserColor()) {
				position.setWhitePlayer(currentUser);
				position.setBlackPlayer(gmName);
				position.setWhiteTimeLeft(String.valueOf(game.getTimeLeft()));
			} else {
				position.setWhitePlayer(gmName);
				position.setBlackPlayer(currentUser);
				position.setBlackTimeLeft(String.valueOf(game.getTimeLeft()));
			}
		}

		position.setGameStatus(game.getStatus());
		position.setGameTitle(game.getTitle());

		PersonGame pg = gameDAO.getPersonGameStatus(gameId, personId);
		if (pg != null && pg.isActive()) {
			position.setUserJoinStatus("JOINED");
		} else {
			position.setUserJoinStatus("NOT_JOINED");
		}
		
		Move mv = gameDAO.getMove(gameId, personId);
		if (mv != null) {
			position.setUserVoteStatus("VOTED");
		} else {
			position.setUserVoteStatus("NOT_VOTED");
		}

		return position;
	}

	@Override
	public void createGame(Game game) {

		gameDAO.createGame(game);
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
