package com.vikas.web;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vikas.engine.BitBoard;
import com.vikas.engine.Engine;
import com.vikas.service.ChessEngineService;

/**
 * 
 * @author Vikas Sharma
 */
@Controller
@RequestMapping("chess")
public class ChessEngineController {

	private static Logger LOGGER = LoggerFactory
			.getLogger(ChessEngineController.class);

	@Autowired
	private ChessEngineService chessEngineService;

	private static final String SEPARATOR = "XXX";

	@RequestMapping(value = "/engine/init.htm", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> initDragDrop(@RequestParam String fen) {

		BitBoard board = new BitBoard(fen);
		Map<Integer, List<Integer>> dragDrop = chessEngineService
				.getDragDrop(board);

		List<String> pgnMoves = chessEngineService.getPGNMoves(board);

		StringBuilder response = new StringBuilder(dragDrop.toString())
				.append(SEPARATOR).append(board.getFenString())
				.append(SEPARATOR).append(pgnMoves);

		return Collections.singletonMap("dragDrop", response.toString());
	}

	@RequestMapping(value = "/engine/drag_drop.htm", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getDragDrop(@RequestParam int fromSq,
			@RequestParam int toSq, @RequestParam int promotedPiece,
			@RequestParam String fen) {

		BitBoard board = new BitBoard(fen);
		int userMove = chessEngineService.getMove(board, fromSq, toSq,
				promotedPiece);
		Engine.makeMove(board, userMove);

		long t = System.currentTimeMillis();
		Engine e = new Engine();
		String result = e.search(board); // get the move
		LOGGER.info("time taken: {}", (System.currentTimeMillis() - t));

		String[] arr = result.split(SEPARATOR);
		int compMove = Integer.parseInt(arr[0]);
		String message = arr[1];

		Map<Integer, List<Integer>> dragDrop = chessEngineService
				.getDragDrop(board);

		List<String> pgnMoves = chessEngineService.getPGNMoves(board);

		StringBuilder response = new StringBuilder(userMove).append(SEPARATOR)
				.append(dragDrop.toString()).append(SEPARATOR).append(compMove)
				.append(SEPARATOR).append(board.getFenString())
				.append(SEPARATOR).append(message).append(SEPARATOR)
				.append(pgnMoves);

		return Collections.singletonMap("dragDrop", response.toString());
	}

	@RequestMapping(value = "/engine/play.htm", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> playCompMove(@RequestParam String fen) {

		BitBoard board = new BitBoard(fen);

		long t = System.currentTimeMillis();
		Engine e = new Engine();
		String result = e.search(board); // get the move
		LOGGER.info("time taken: {}", (System.currentTimeMillis() - t));

		String[] arr = result.split(SEPARATOR);
		int compMove = Integer.parseInt(arr[0]);
		String message = arr[1];

		Map<Integer, List<Integer>> dragDrop = chessEngineService
				.getDragDrop(board);

		List<String> pgnMoves = chessEngineService.getPGNMoves(board);

		StringBuilder response = new StringBuilder(dragDrop.toString())
				.append(SEPARATOR).append(compMove).append(SEPARATOR)
				.append(board.getFenString()).append(SEPARATOR).append(message)
				.append(SEPARATOR).append(pgnMoves);

		return Collections.singletonMap("dragDrop", response.toString());
	}

}
