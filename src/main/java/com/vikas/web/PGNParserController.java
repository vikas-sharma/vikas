package com.vikas.web;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vikas.service.PGNParserService;

/**
 * 
 * @author Vikas Sharma
 */
@Controller
@RequestMapping("chess")
public class PGNParserController {

	@Autowired
	private PGNParserService pgnParserService;

	@RequestMapping(value = "/pgnparser/moves.htm", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, List<Integer>> getMovesList(@RequestParam int gameNo) {

		List<Integer> moveList = pgnParserService.getMoveList(gameNo);
		return Collections.singletonMap("moveList", moveList);
	}

	@RequestMapping(value = "/pgnparser/pgn.htm", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getPGN(@RequestParam int gameNo) {

		String pgnString = pgnParserService.getPGN(gameNo);
		return Collections.singletonMap("pgnString", pgnString);
	}
}
