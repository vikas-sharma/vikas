package com.vikas.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vikas.service.PGNParserService;

/**
 * 
 * @author Vikas Sharma
 */
@Controller
@RequestMapping("chess")
public class MyGamesController {

	@Autowired
	private PGNParserService pgnParserService;

	@RequestMapping(value = "/myGames.htm", method = RequestMethod.GET)
	public String viewGames(ModelMap model) {

		Map<Integer, String> games = pgnParserService.listGames();

		model.addAttribute("games", games);
		return "myGames";
	}
}