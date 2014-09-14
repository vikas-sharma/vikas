package com.vikas.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vikas.domain.Game;
import com.vikas.domain.GamePosition;
import com.vikas.service.GameService;

/**
 * 
 * @author Vikas Sharma
 */
@Controller
@RequestMapping("chess")
public class GameController {

	private static Logger LOGGER = LoggerFactory
			.getLogger(GameController.class);

	@Autowired
	private GameService gameService;

	@RequestMapping(value = "/listgames.htm", method = RequestMethod.GET)
	public String listGames(Model model) {

		List<Game> games = gameService.getAllGames();

		model.addAttribute("games", games);
		return "listgames";
	}

	@RequestMapping(value = "/game.htm", method = RequestMethod.GET)
	public String showGame(Model model, @RequestParam int gameId) {

		GamePosition position = gameService.getPosition(gameId);

		model.addAttribute("position", position);

		return getViewName(position.getPositionStatus());
	}

	@RequestMapping(value = "/game.htm", params = "Join Game", method = RequestMethod.POST)
	public String joinGame(Model model,
			@ModelAttribute("position") GamePosition position) {

		GamePosition newPosition = gameService.addPersonToGame(
				position.getGameId(), position.getPersonId());

		model.addAttribute("position", newPosition);

		return getViewName(newPosition.getPositionStatus());
	}

	@RequestMapping(value = "/game.htm", params = "UnJoin Game", method = RequestMethod.POST)
	public String abort(Model model,
			@ModelAttribute("position") GamePosition position) {

		GamePosition newPosition = gameService.removePersonFromGame(
				position.getGameId(), position.getPersonId());

		model.addAttribute("position", newPosition);

		return getViewName(newPosition.getPositionStatus());
	}

	@RequestMapping(value = "/game.htm", params = "Vote Move", method = RequestMethod.POST)
	public String voteMove(Model model,
			@ModelAttribute("position") GamePosition position) {

		// GamePosition newPosition = gameService.addMove(move);
		return "readOnlyGame";
	}

	private String getViewName(String status) {

		if ("BEFORE_VOTE".equals(status)) {
			return "game";
		} else {
			return "readOnlyGame";
		}
	}
}
