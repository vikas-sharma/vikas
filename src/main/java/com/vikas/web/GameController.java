package com.vikas.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vikas.domain.Game;
import com.vikas.domain.GamePosition;
import com.vikas.domain.VoteType;
import com.vikas.service.GameService;

/**
 * 
 * @author Vikas Sharma
 */
@Controller
@RequestMapping("chess")
public class GameController {

	@Autowired
	GameService gameService;

	@RequestMapping(value = "/listgames.htm", method = RequestMethod.GET)
	public String listGames(Model model) {

		List<Game> games = gameService.getAllGames();

		model.addAttribute("games", games);
		return "listgames";
	}

	@RequestMapping(value = "/game.htm", method = RequestMethod.GET)
	public String showGame(Model model, @RequestParam int gameId) {

		GamePosition currentPosition = gameService.getPosition(gameId);

		model.addAttribute("position", currentPosition);

		return "game";
	}

	@RequestMapping(value = "/game.htm", params = "Join Game", method = RequestMethod.POST)
	public String joinGame(Model model) {
		return "game";
	}

	@RequestMapping(value = "/game.htm", params = "Vote Move", method = RequestMethod.POST)
	public String voteMove(Model model, @RequestParam VoteType voteType) {
		return "game";
	}
}
