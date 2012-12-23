package com.vikas.service;

import java.util.List;
import java.util.Map;

import com.vikas.engine.BitBoard;


/**
 * 
 * @author Vikas Sharma
 */
public interface ChessEngineService {

	Map<Integer, List<Integer>> getDragDrop(BitBoard board);

	List<String> getPGNMoves(BitBoard board);

	int getMove(BitBoard board, int fromSq, int toSq, int promotedPiece);
}