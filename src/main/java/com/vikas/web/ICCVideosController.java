package com.vikas.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author Vikas Sharma
 */
@Controller
@RequestMapping("chess")
public class ICCVideosController {

	@RequestMapping(value = "/iccVideos.htm", method = RequestMethod.GET)
	public String viewGames(Model model,
			@RequestParam(required = false) Integer gameId) {

		if (gameId != null) {
			model.addAttribute("gameId", gameId.intValue());
		}

		return "iccVideos";
	}
}