package com.vikas.service;

import static com.vikas.engine.Constants.A2;
import static com.vikas.engine.Constants.BISHOP;
import static com.vikas.engine.Constants.C1;
import static com.vikas.engine.Constants.C8;
import static com.vikas.engine.Constants.E1;
import static com.vikas.engine.Constants.E8;
import static com.vikas.engine.Constants.EMPTY;
import static com.vikas.engine.Constants.G1;
import static com.vikas.engine.Constants.G8;
import static com.vikas.engine.Constants.H7;
import static com.vikas.engine.Constants.KING;
import static com.vikas.engine.Constants.KNIGHT;
import static com.vikas.engine.Constants.PAWN;
import static com.vikas.engine.Constants.QUEEN;
import static com.vikas.engine.Constants.ROOK;
import static com.vikas.engine.Constants.mask;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.vikas.engine.BitBoard;
import com.vikas.engine.Engine;
import com.vikas.engine.MoveGenerator;
import com.vikas.engine.PGNParser;

/**
 * 
 * @author Vikas Sharma
 */
@Service
public class ChessEngineServiceImpl implements ChessEngineService {

	@Override
	public int getMove(BitBoard board, int fromSq, int toSq, int promotedPiece) {

		int p = getPiece(board, fromSq);
		int c = getPiece(board, toSq);

		int mv;
		if (p == KING && fromSq == E1 && toSq == G1) {
			mv = (KING << 12) | (G1 << 6) | E1;
		} else if (p == KING && fromSq == E1 && toSq == C1) {
			mv = (KING << 12) | (C1 << 6) | E1;
		} else if (p == KING && fromSq == E8 && toSq == G8) {
			mv = (KING << 12) | (G8 << 6) | E8;
		} else if (p == KING && fromSq == E8 && toSq == C8) {
			mv = (KING << 12) | (C8 << 6) | E8;
		} else {
			if ((board.isColor() && p == PAWN && fromSq <= H7)
					|| (!board.isColor() && p == PAWN && fromSq >= A2)) {
				mv = (promotedPiece << 18) | (c << 15) | (p << 12)
						| (toSq << 6) | fromSq;
			} else {
				mv = (c << 15) | (p << 12) | (toSq << 6) | fromSq;
			}
		}
		return mv;
	}

	private int getPiece(BitBoard board, int fromSq) {

		if ((board.getWhitebishops() & mask[fromSq]) != 0
				|| (board.getBlackbishops() & mask[fromSq]) != 0) {
			return BISHOP;
		}
		if ((board.getWhiteking() & mask[fromSq]) != 0
				|| (board.getBlackking() & mask[fromSq]) != 0) {
			return KING;
		}
		if ((board.getWhiteknights() & mask[fromSq]) != 0
				|| (board.getBlackknights() & mask[fromSq]) != 0) {
			return KNIGHT;
		}
		if ((board.getWhitepawns() & mask[fromSq]) != 0
				|| (board.getBlackpawns() & mask[fromSq]) != 0) {
			return PAWN;
		}
		if ((board.getWhitequeens() & mask[fromSq]) != 0
				|| (board.getBlackqueens() & mask[fromSq]) != 0) {
			return QUEEN;
		}
		if ((board.getWhiterooks() & mask[fromSq]) != 0
				|| (board.getBlackrooks() & mask[fromSq]) != 0) {
			return ROOK;
		}

		return EMPTY;
	}

	/**
	 * get Drag Drop mapping to implement drag drop functionality on the view
	 * page.
	 * 
	 * @param board
	 * @return Map<Integer, List<Integer>>
	 */
	@Override
	public Map<Integer, List<Integer>> getDragDrop(BitBoard board) {

		int[] moves = new int[256];
		int noMoves = MoveGenerator.generateMoveList(board, moves);

		Map<Integer, List<Integer>> dragDrop = new LinkedHashMap<Integer, List<Integer>>();

		for (int i = 0; i < noMoves; i++) {

			int castle = board.getCastle();
			int ep = board.getEnpassantSquare();
			int score = board.getScore();
			long zobristKey = board.getZobristKey();
			boolean legal = Engine.makeMove(board, moves[i]);
			if (legal) {
				int fromSq = moves[i] & 63;
				int toSq = (moves[i] >>> 6) & 63;

				List<Integer> fromSqList = dragDrop.get(toSq);
				if (fromSqList == null) {
					fromSqList = new ArrayList<Integer>();
				}
				fromSqList.add(fromSq);
				dragDrop.put(toSq, fromSqList);
			}
			board.setCastle(castle);
			board.setEnpassantSquare(ep);
			board.setScore(score);
			board.setZobristKey(zobristKey);
			Engine.undoMove(board, moves[i]);
		}
		return dragDrop;
	}

	@Override
	public List<String> getPGNMoves(BitBoard board) {

		int[] moves = new int[256];
		int noMoves = MoveGenerator.generateMoveList(board, moves);

		return PGNParser.parseMoveList(board, moves, noMoves);
	}
}