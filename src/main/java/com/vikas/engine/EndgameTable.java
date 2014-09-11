package com.vikas.engine;

import org.lokasoft.wsdl.Tbapi;
import org.lokasoft.wsdl.TbapiLocator;

/**
 * 
 * @author Vikas Sharma
 *
 */
public class EndgameTable implements Constants {

	private static Tbapi service = new TbapiLocator();

	private static int getPiece(BitBoard board, int sq) {

		if ((board.whitebishops & mask[sq]) != 0
				|| (board.blackbishops & mask[sq]) != 0) {
			return BISHOP;
		}
		if ((board.whiteking & mask[sq]) != 0
				|| (board.blackking & mask[sq]) != 0) {
			return KING;
		}
		if ((board.whiteknights & mask[sq]) != 0
				|| (board.blackknights & mask[sq]) != 0) {
			return KNIGHT;
		}
		if ((board.whitepawns & mask[sq]) != 0
				|| (board.blackpawns & mask[sq]) != 0) {
			return PAWN;
		}
		if ((board.whitequeens & mask[sq]) != 0
				|| (board.blackqueens & mask[sq]) != 0) {
			return QUEEN;
		}
		if ((board.whiterooks & mask[sq]) != 0
				|| (board.blackrooks & mask[sq]) != 0) {
			return ROOK;
		}

		return EMPTY;
	}

	private static int getMove(BitBoard board, String move) {

		// move: Kh8-g7 or c6-c7 or d7-d8(Q)

		String[] moveArr = move.split("-");

		if (moveArr[0].length() == 3) {
			char p = moveArr[0].charAt(0);
			moveArr[0] = moveArr[0].substring(1);
		}

		int x = moveArr[0].charAt(0) - 97; // 0-7
		int y = 8 - (moveArr[0].charAt(1) - 48); // 0-7
		int fromSq = 8 * y + x;

		x = moveArr[1].charAt(0) - 97; // 0-7
		y = 8 - (moveArr[1].charAt(1) - 48); // 0-7
		int toSq = 8 * y + x;

		int p = getPiece(board, fromSq);

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
			int c = getPiece(board, toSq);
			if ((board.color && p == PAWN && fromSq <= H7)
					|| (!board.color && p == PAWN && fromSq >= A2)) {
				mv = (QUEEN << 18) | (c << 15) | (p << 12) | (toSq << 6)
						| fromSq;
			} else {
				mv = (c << 15) | (p << 12) | (toSq << 6) | fromSq;
			}
		}
		return mv;
	}

	static String playMove(BitBoard board) {

		try {
			String result = service.getTB2ComObjSoapPort().getBestMoves(
					board.getFenString());

			String[] mv = result.split(" ");

			int move = getMove(board, mv[0]);

			Engine.makeMove(board, move);

			result = move + "XXX ";

			if ("M1".equals(mv[1])) {
				if (board.isColor()) {
					result += "1-0 {White wins}";
				} else {
					result += "0-1 {Black wins}";
				}

			}
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}