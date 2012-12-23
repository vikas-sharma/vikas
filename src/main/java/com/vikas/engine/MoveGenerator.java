package com.vikas.engine;

public class MoveGenerator implements Constants
{
	private static int index;
	
	public static final int generateMoveList(final BitBoard board, int []moves)	{

		index = 0;
		if (board.color) {

			/* First Priority Moves */
			genWhitePawnPromotionCaps(board, moves, false);
			genWhitePawnPromotionNonCaps(board, moves, false);

			/* Second Priority Moves */
			genWhitePawnCaps(board, moves);
			genWhiteKnightCaps(board, moves);
			genWhiteBishopCaps(board, moves);
			genWhiteRookCaps(board, moves);
			genWhiteQueenCaps(board, moves);
			genWhiteKingCaps(board, moves);

			/* Third Priority Moves */
			genWhiteCastle(board, moves);

			/* Fourth Priority Moves */
			genWhitePawnNonCaps(board, moves);
			genWhiteKnightNonCaps(board, moves);
			genWhiteBishopNonCaps(board, moves);
			genWhiteRookNonCaps(board, moves);
			genWhiteQueenNonCaps(board, moves);
			genWhiteKingNonCaps(board, moves);

		} else {

			/* First Priority Moves */
			genBlackPawnPromotionCaps(board, moves, false);
			genBlackPawnPromotionNonCaps(board, moves, false);

			/* Second Priority Moves */
			genBlackPawnCaps(board, moves);
			genBlackKnightCaps(board, moves);
			genBlackBishopCaps(board, moves);
			genBlackRookCaps(board, moves);
			genBlackQueenCaps(board, moves);
			genBlackKingCaps(board, moves);

			/* Third Priority Moves */
			genBlackCastle(board, moves);

			/* Fourth Priority Moves */
			genBlackPawnNonCaps(board, moves);
			genBlackKnightNonCaps(board, moves);
			genBlackBishopNonCaps(board, moves);
			genBlackRookNonCaps(board, moves);
			genBlackQueenNonCaps(board, moves);
			genBlackKingNonCaps(board, moves);
		}

		return index;
	}

	static final int generateCaptureMoveListForQuiesce(final BitBoard board, int []moves) {

		index = 0;

		if (board.color)
		{
			/* First Priority Moves */
			genWhitePawnPromotionCaps(board, moves, true);
			genWhitePawnPromotionNonCaps(board, moves, true);

			/* Second Priority Moves */
			genWhitePawnCaps(board, moves);
			genWhiteKnightCaps(board, moves);
			genWhiteBishopCaps(board, moves);
			genWhiteRookCaps(board, moves);
			genWhiteQueenCaps(board, moves);
			genWhiteKingCaps(board, moves);
		}
		else
		{
			/* First Priority Moves */
			genBlackPawnPromotionCaps(board, moves, true);
			genBlackPawnPromotionNonCaps(board, moves, true);

			/* Second Priority Moves */
			genBlackPawnCaps(board, moves);
			genBlackKnightCaps(board, moves);
			genBlackBishopCaps(board, moves);
			genBlackRookCaps(board, moves);
			genBlackQueenCaps(board, moves);
			genBlackKingCaps(board, moves);
		}

		return index;
	}
	
	static final void genWhitePawnPromotionCaps(final BitBoard board, int []moves, boolean quiesce)
	{
		long piece_map, to_map;
		int from_sq, to_sq;
		int move;

		piece_map = board.whitepawns & rankA7H7;

		while(piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];
			to_map = wpawnCaps[from_sq] & board.blackpieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				move = (board.piece[to_sq] << 15) | (PAWN << 12) | (to_sq << 6) | from_sq;
				moves[index++] = (QUEEN << 18) | move;
				if (!quiesce) {
					moves[index++] = (KNIGHT << 18) | move;
					moves[index++] = (ROOK << 18) | move;
					moves[index++] = (BISHOP << 18) | move;
				}
			}
		}
	}

	static final void genWhitePawnPromotionNonCaps(final BitBoard board, int []moves, boolean quiesce)
	{
		long piece_map, to_map;
		int from_sq, to_sq;
		int move;

		piece_map = board.whitepawns & rankA7H7;

		while(piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];
			to_map = wpawnNonCaps[from_sq] & (~(board.allpieces));
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				move = (PAWN << 12) | (to_sq << 6) | from_sq;
				moves[index++] = (QUEEN << 18) | move;
				if (!quiesce) {
					moves[index++] = (KNIGHT << 18) | move;
					moves[index++] = (ROOK << 18) | move;
					moves[index++] = (BISHOP << 18) | move;
				}
			}
		}
	}

	static final void genWhitePawnCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.whitepawns & (~rankA7H7);

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			if (board.enpassantSquare != -1) {
				to_map = wpawnCaps[from_sq] & (board.blackpieces | mask[board.enpassantSquare]);
			} else {
				to_map = wpawnCaps[from_sq] & board.blackpieces;
			}

			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (board.piece[to_sq] << 15) | (PAWN << 12) | (to_sq << 6) | from_sq;
			}
		}
	}

	static final void genWhiteKnightCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.whiteknights;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = knight[from_sq] & board.blackpieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (board.piece[to_sq] << 15) | (KNIGHT << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genWhiteBishopCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.whitebishops;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = bishopMoves(board, from_sq) & board.blackpieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (board.piece[to_sq] << 15) | (BISHOP << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genWhiteRookCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.whiterooks;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = rookMoves(board, from_sq) & board.blackpieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (board.piece[to_sq] << 15) | (ROOK << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genWhiteQueenCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.whitequeens;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = (rookMoves(board, from_sq) | bishopMoves(board, from_sq)) &
				board.blackpieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (board.piece[to_sq] << 15) | (QUEEN << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genWhiteKingCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.whiteking;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = king[from_sq] & board.blackpieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (board.piece[to_sq] << 15) | (KING << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genWhiteCastle(final BitBoard board, int []moves)
	{
		if ((board.castle & 1) != 0 &&
			(board.allpieces & mask[F1]) == 0 &&
			(board.allpieces & mask[G1]) == 0)
		{
			moves[index++] = (KING << 12) | (G1 << 6) | E1;
		}

		if ((board.castle & 2) != 0 &&
			(board.allpieces & mask[D1]) == 0 &&
			(board.allpieces & mask[C1]) == 0 &&
			(board.allpieces & mask[B1]) == 0)
		{
			moves[index++] = (KING << 12) | (C1 << 6) | E1;
		}

	}

	static final void genWhitePawnNonCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.whitepawns & (~rankA7H7);

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = wpawnNonCaps[from_sq]  & ~board.allpieces;
			if(from_sq >= A2 && from_sq <= H2 && to_map != 0 &&
				(board.allpieces & mask[from_sq - 8]) != 0) {
				to_map ^= mask[from_sq - 16];
			}
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (PAWN << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genWhiteKnightNonCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.whiteknights;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = knight[from_sq] & ~board.allpieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (KNIGHT << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genWhiteBishopNonCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.whitebishops;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = bishopMoves(board, from_sq) & ~board.allpieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (BISHOP << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genWhiteRookNonCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.whiterooks;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = rookMoves(board, from_sq) & ~board.allpieces;

			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (ROOK << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genWhiteQueenNonCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.whitequeens;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = (rookMoves(board, from_sq) | bishopMoves(board, from_sq)) & ~board.allpieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (QUEEN << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genWhiteKingNonCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.whiteking;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = king[from_sq] & ~board.allpieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (KING << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genBlackPawnPromotionCaps(final BitBoard board, int []moves, boolean quiesce)
	{
		long piece_map, to_map;
		int from_sq, to_sq;
		int move;

		piece_map = board.blackpawns & rankA2H2;

		while(piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];
			to_map = bpawnCaps[from_sq] & board.whitepieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				move = (board.piece[to_sq] << 15) | (PAWN << 12) | (to_sq << 6) | from_sq;
				moves[index++] = (QUEEN << 18) | move;
				if (!quiesce) {
					moves[index++] = (KNIGHT << 18) | move;
					moves[index++] = (ROOK << 18) | move;
					moves[index++] = (BISHOP << 18) | move;
				}
			}
		}

	}

	static final void genBlackPawnPromotionNonCaps(final BitBoard board, int []moves, boolean quiesce)
	{
		long piece_map, to_map;
		int from_sq, to_sq;
		int move;

		piece_map = board.blackpawns & rankA2H2;

		while(piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];
			to_map = bpawnNonCaps[from_sq] & ~board.allpieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				move = (PAWN << 12) | (to_sq << 6) | from_sq;
				moves[index++] = (QUEEN << 18) | move;
				if (!quiesce) {
					moves[index++] = (KNIGHT << 18) | move;
					moves[index++] = (ROOK << 18) | move;
					moves[index++] = (BISHOP << 18) | move;
				}
			}
		}

	}

	static final void genBlackPawnCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.blackpawns & ~rankA2H2;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			if (board.enpassantSquare != -1) {
				to_map = bpawnCaps[from_sq] & (board.whitepieces | mask[board.enpassantSquare]);
			} else {
				to_map = bpawnCaps[from_sq] & board.whitepieces;
			}
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (board.piece[to_sq] << 15) | (PAWN << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genBlackKnightCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.blackknights;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = knight[from_sq] & board.whitepieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (board.piece[to_sq] << 15) | (KNIGHT << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genBlackBishopCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.blackbishops;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = bishopMoves(board, from_sq) & board.whitepieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (board.piece[to_sq] << 15) | (BISHOP << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genBlackRookCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.blackrooks;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = rookMoves(board, from_sq) & board.whitepieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (board.piece[to_sq] << 15) | (ROOK << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genBlackQueenCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.blackqueens;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = (rookMoves(board, from_sq) | bishopMoves(board, from_sq)) &
				board.whitepieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (board.piece[to_sq] << 15) | (QUEEN << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genBlackKingCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.blackking;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = king[from_sq] & board.whitepieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (board.piece[to_sq] << 15) | (KING << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genBlackCastle(final BitBoard board, int []moves)
	{
		if ((board.castle & 4) != 0 &&
			(board.allpieces & mask[F8]) == 0 &&
			(board.allpieces & mask[G8]) == 0)
		{
			moves[index++] = (KING << 12) | (G8 << 6) | E8;
		}

		if ((board.castle & 8) != 0 &&
			(board.allpieces & mask[D8]) == 0 &&
			(board.allpieces & mask[C8]) == 0 &&
			(board.allpieces & mask[B8]) == 0)
		{
			moves[index++] = (KING << 12) | (C8 << 6) | E8;
		}

	}

	static final void genBlackPawnNonCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.blackpawns & (~rankA2H2);

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = bpawnNonCaps[from_sq]  & ~board.allpieces;
			if(from_sq >= A7 && from_sq <= H7 && to_map != 0 &&
				(board.allpieces & mask[from_sq + 8]) != 0) {
				to_map ^= mask[from_sq + 16];
			}
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (PAWN << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genBlackKnightNonCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.blackknights;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = knight[from_sq] & ~board.allpieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (KNIGHT << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genBlackBishopNonCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.blackbishops;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = bishopMoves(board, from_sq) & ~board.allpieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (BISHOP << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genBlackRookNonCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.blackrooks;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = rookMoves(board, from_sq) & ~board.allpieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (ROOK << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genBlackQueenNonCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.blackqueens;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = (rookMoves(board, from_sq) | bishopMoves(board, from_sq)) & ~board.allpieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (QUEEN << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static final void genBlackKingNonCaps(final BitBoard board, int []moves)
	{
		long piece_map, to_map;
		int from_sq, to_sq;

		piece_map = board.blackking;

		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];

			to_map = king[from_sq] & ~board.allpieces;
			while (to_map != 0)
			{
				to_sq = Utility.getIndex(to_map);
				to_map ^= mask[to_sq];

				moves[index++] = (KING << 12) | (to_sq << 6) | from_sq;
			}
		}

	}

	static long rookMoves(final BitBoard board, int sq)
	{
		long index = (rook_magics[sq] * (rook_mask[sq] & board.allpieces)) >>> rook_shift[sq];
		return rook_indices[sq][(int)index];
	}
 
	static long bishopMoves(final BitBoard board, int sq)
	{
		long index = (bishop_magics[sq] * (bishop_mask[sq] & board.allpieces)) >>> bishop_shift[sq];
		return bishop_indices[sq][(int)index];
	}
}