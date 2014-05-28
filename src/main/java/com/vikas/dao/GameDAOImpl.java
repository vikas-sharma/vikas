package com.vikas.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.vikas.domain.Game;
import com.vikas.domain.Move;

/**
 * 
 * @author Vikas Sharma
 */
public class GameDAOImpl implements GameDAO {

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert insertGame;
	private SimpleJdbcInsert insertMove;

	public void setDataSource(DataSource dataSource) {

		jdbcTemplate = new JdbcTemplate(dataSource);
		insertGame = new SimpleJdbcInsert(dataSource).withTableName("GAME")
				.usingGeneratedKeyColumns("GAME_ID");
		insertMove = new SimpleJdbcInsert(dataSource).withTableName("MOVE")
				.usingGeneratedKeyColumns("MOVE_ID");
	}

	@Override
	public void createGame(Game game) {

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(game);
		Number newId = insertGame.executeAndReturnKey(parameters);

		game.setGameId(newId.longValue());
	}

	@Override
	public void addPersonToGame(int gameId, int personId) {

		String sql = "SELECT COUNT(1) FROM PERSON_GAME WHERE GAME_ID = ? AND PERSON_ID = ?";

		int count = jdbcTemplate.queryForObject(sql, Integer.class, gameId,
				personId);

		if (count == 0) {
			sql = "INSERT INTO PERSON_GAME (GAME_ID, PERSON_ID, ACTIVE, CREATION_TIME) VALUES (?, ?, ?, ?)";
			jdbcTemplate.update(sql, gameId, personId, 'Y', null);
		} else {
			sql = "UPDATE PERSON_GAME SET ACTIVE='Y' WHERE GAME_ID = ? AND PERSON_ID = ?";
			jdbcTemplate.update(sql, gameId, personId);
		}
	}

	@Override
	public void removePersonFromGame(int gameId, int personId) {

		String sql = "UPDATE PERSON_GAME SET ACTIVE='N' WHERE GAME_ID = ? AND PERSON_ID = ?";

		jdbcTemplate.update(sql, gameId, personId);
	}

	@Override
	public void updateGame(Game game) {

		String sql = "UPDATE GAME SET STATUS=?, FEN=? WHERE GAME_ID = ?";

		jdbcTemplate.update(sql, game.getStatus(), game.getFen(),
				game.getGameId());
	}

	@Override
	public void addMove(Move move) {

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(move);
		Number newId = insertMove.executeAndReturnKey(parameters);

		move.setMoveId(newId.longValue());
	}

	@Override
	public List<Game> getAllGames() {

		String sql = "SELECT * FROM Game";

		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Game.class));
	}

	@Override
	public List<Game> getGamesInProgress() {

		String sql = "SELECT * FROM Game where STATUS='STARTED'";

		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Game.class));
	}

	@Override
	public List<Game> getPersonGames(int personId) {

		String sql = "select g.* from game g inner join person_game pg on g.game_id = pg.game_id where pg.active='Y' and pg.person_id=?";

		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Game.class),
				personId);
	}

	@Override
	public String getMaxVotedMove(int gameId, int moveNo) {

		String sql = "select mv from move where game_id=? and move_no=? group by mv order by count(mv) desc limit 1";

		String move = null;
		try {
			move = jdbcTemplate.queryForObject(sql, String.class, gameId,
					moveNo);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

		return move;
	}
}
