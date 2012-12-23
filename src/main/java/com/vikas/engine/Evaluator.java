package com.vikas.engine;

public final class Evaluator implements Constants
{

	static final int eval(final BitBoard board)
	{
		int score = 0;

		long piece_map;
		int sq;

		piece_map = board.whitepawns;
		while (piece_map != 0) {
			sq = Utility.getIndex(piece_map);
			piece_map ^= mask[sq];
			score += 100 + WPAWN_SQ_VAL[sq];
		}

		piece_map = board.whiteknights;
		while (piece_map != 0) {
			sq = Utility.getIndex(piece_map);
			piece_map ^= mask[sq];
			score += 330 + WKNIGHT_SQ_VAL[sq];
		}

		piece_map = board.whitebishops;
		while (piece_map != 0) {
			sq = Utility.getIndex(piece_map);
			piece_map ^= mask[sq];
			score += 350 + WBISHOP_SQ_VAL[sq];
		}

		piece_map = board.whiterooks;
		while (piece_map != 0) {
			sq = Utility.getIndex(piece_map);
			piece_map ^= mask[sq];
			score += 500;
		}

		piece_map = board.whitequeens;
		while (piece_map != 0) {
			sq = Utility.getIndex(piece_map);
			piece_map ^= mask[sq];
			score += 900;
		}

		if (Utility.bitCount(board.allpieces) > 19) {
			sq = Utility.getIndex(board.whiteking);
			score += WKING_SQ_VAL[sq];
		}

		piece_map = board.blackpawns;
		while (piece_map != 0) {
			sq = Utility.getIndex(piece_map);
			piece_map ^= mask[sq];
			score -= 100 + BPAWN_SQ_VAL[sq];
		}

		piece_map = board.blackknights;
		while (piece_map != 0) {
			sq = Utility.getIndex(piece_map);
			piece_map ^= mask[sq];
			score -= 330 + BKNIGHT_SQ_VAL[sq];
		}

		piece_map = board.blackbishops;
		while (piece_map != 0) {
			sq = Utility.getIndex(piece_map);
			piece_map ^= mask[sq];
			score -= 350 + BBISHOP_SQ_VAL[sq];
		}

		piece_map = board.blackrooks;
		while (piece_map != 0) {
			sq = Utility.getIndex(piece_map);
			piece_map ^= mask[sq];
			score -= 500;
		}

		piece_map = board.blackqueens;
		while (piece_map != 0) {
			sq = Utility.getIndex(piece_map);
			piece_map ^= mask[sq];
			score -= 900;
		}

		if (Utility.bitCount(board.allpieces) > 19) {
			sq = Utility.getIndex(board.blackking);
			score -= BKING_SQ_VAL[sq];
		}
		
		if (board.color) {
			return score;
		}
		return -score;
	}
}