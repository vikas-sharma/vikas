package com.vikas.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * 
 */
@Service
@Transactional
public class GameServiceImpl implements GameService {

	private static Logger LOGGER = LoggerFactory
			.getLogger(GameServiceImpl.class);

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

		String currentUser = SecurityContextHolder.getContext()
				.getAuthentication().getName();

		int personId = gameDAO.getPersonId(currentUser);

		return getPosition(gameId, personId);

	}

	@Override
	public GamePosition addPersonToGame(int gameId, int personId) {

		gameDAO.addPersonToGame(gameId, personId);

		return getPosition(gameId, personId);
	}

	@Override
	public GamePosition removePersonFromGame(int gameId, int personId) {

		gameDAO.removePersonFromGame(gameId, personId);

		return getPosition(gameId, personId);
	}

	@Override
	public GamePosition addMove(Move move) {

		return null;
	}

	@Override
	public void createGame(Game game) {

		gameDAO.createGame(game);
	}

	@Override
	public void updateGame(Game game) {

	}

	@Override
	public String getMaxVotedMove(int gameId, int moveNo) {

		return gameDAO.getMaxVotedMove(gameId, moveNo);
	}

	private GamePosition getPosition(int gameId, int personId) {

		GamePosition position = new GamePosition();

		position.setGameId(gameId);
		position.setPersonId(personId);

		String currentUser = SecurityContextHolder.getContext()
				.getAuthentication().getName();

		Game game = gameDAO.getGame(gameId);

		position.setFen(game.getFen());

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

		if ("WIN".equals(game.getStatus()) || "LOSE".equals(game.getStatus())
				|| "DRAW".equals(game.getStatus())) {

			position.setPositionStatus("GAME_OVER");

		} else {

			PersonGame pg = gameDAO.getPersonGameStatus(gameId, personId);

			if (pg != null && pg.isActive()) {

				if (position.isTurn()) {
					Move mv = gameDAO.getMove(gameId, personId);
					if (mv != null) {
						position.setPositionStatus("AFTER_VOTE");
					} else {
						position.setPositionStatus("BEFORE_VOTE");
					}
				} else {
					position.setPositionStatus("OPPONENT_TURN");
				}
			} else {
				position.setPositionStatus("NOT_JOINED");
			}
		}
		return position;
	}
}