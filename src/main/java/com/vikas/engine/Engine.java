package com.vikas.engine;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Engine implements Constants {

	private static Logger LOGGER = LoggerFactory.getLogger(Engine.class);

	private int[][] POOL = new int[64][256];

	public final String search(BitBoard board) {

//		int move = OpeningTable.getMove(board.zobristKey);
		int move = OpeningTable.getMoveFromColinDB(board);
		if (move != 0) {
			Engine.makeMove(board, move);
			return move + "XXX ";
		}

		if (Utility.bitCount(board.allpieces) <= 5) {

			String result = EndgameTable.playMove(board);
			
			if (result != null) {
				return result;
			}
		}

		BitBoard startBoard = new BitBoard(board);
		int depth = 1;
		int score = 0;
		boolean computerSide = board.color;
		long startTime = System.currentTimeMillis();
		for (depth = 1; depth <= 64; depth++) {
			score = alphaBeta(depth, 0, -20000, 20000, board);
			long timeTaken = System.currentTimeMillis() - startTime;
			if (timeTaken >= 400) {
				break;
			}
			if (score == -16000 || score == 15999) {
				break;
			}
		}

		long entry = TranspositionTable.getEntry(startBoard.zobristKey);
		if (entry != 0) {
			int bestMove = (int)((entry >>> 23) & 2097151L);
			int value = (int)((entry >>> 8) & 32767L) - 16000;
			LOGGER.debug("move = {}, depth = {}, score = {}", bestMove, depth, value);
		} else {
			LOGGER.debug("depth = {}", depth);
		}

		LOGGER.debug("PV:");
		int bestMove = 0;
		for (int d = 1; d <= depth; d++) {

			entry = TranspositionTable.getEntry(startBoard.zobristKey);
			if (entry == 0) {
				break;
			}

			bestMove = (int)((entry >>> 23) & 2097151L);

			String pgn = getPGN(startBoard, bestMove);
			if (pgn == null) {
				break;
			}
			LOGGER.debug(" {}", pgn);

			Engine.makeMove(startBoard, bestMove);
		}

		String result = board.bestMove + "XXX ";
		if (score == -16000) { // computer lose
			if (computerSide) {
				result += "0-1 {Black wins}";
			} else {
				result += "1-0 {White wins}";
			}
		} else if (score == 15999) { // player lose

			int mv = board.bestMove;
			Engine.makeMove(board, mv);
			if (computerSide) {
				result += "1-0 {White wins}";
			} else {
				result += "0-1 {Black wins}";
			}
		} else if (board.stalemate) {
			result += "1/2-1/2 {Stalemate}";
		} else if (board.insufficientMaterial()) {
			result += "Game Drawn. Insufficient Material";
		} else {
			int mv = board.bestMove;
			Engine.makeMove(board, mv);
		}

		return result;
	}

	private String getPGN(BitBoard startBoard, int bestMove) {

		int []moves = new int[256];
		int noMoves = MoveGenerator.generateMoveList(startBoard, moves);

		ArrayList<String> pgnMoves = PGNParser.parseMoveList(startBoard, moves, noMoves);

		for (int i = 0; i < noMoves; i++) {

			int mv = moves[i];
			if (mv == bestMove) {
				return pgnMoves.get(i);
			}
		}
		return null;
	}

	private final int alphaBeta(int depth, int ply, int alpha, int beta, final BitBoard board) {

		long entry = TranspositionTable.getEntry(board.zobristKey);
		if (entry != 0) {

			if (((entry >>> 2) & 63L) >= depth) {

				board.bestMove = (int)((entry >>> 23) & 2097151L);
				int type = (int)(entry & 3L);
				int value = (int)((entry >>> 8) & 32767L) - 16000;

				if (type == TTEntry.EXACT_VALUE) {
					return value;
				}
				if (type == TTEntry.ALPHA_VALUE && value <= alpha) {
					return alpha;
				}
				if(type == TTEntry.BETA_VALUE && value >= beta) {
					return beta;
				}
			}
		}

		if (depth == 0) {
			return quiesce(alpha, beta, ply, board);
		}

		int hashCode = TTEntry.ALPHA_VALUE;
		int bestMove = 0;

		boolean moveFound = false;

		boolean searchPV = true;

		int noMoves = MoveGenerator.generateMoveList(board, POOL[ply]);
		
		int hashMove = 0;
		if (entry != 0) {
			hashMove = (int)((entry >>> 23) & 2097151L);
		}

		for (int i = -1; i < noMoves; i++) {

			int mv = 0;

			if (i == -1) {
				if (hashMove == 0) {
					continue;
				}
				mv = hashMove;
			} else {
				mv = POOL[ply][i];
				if (mv == hashMove) {
					continue;
				}
			}

			int board_castle = board.castle;
			int board_ep = board.enpassantSquare;
			int board_score = board.score;
			long zobristKey = board.zobristKey;

			if (!makeMove(board, mv)) {
				board.castle = board_castle;
				board.enpassantSquare = board_ep;
				board.score = board_score;
				board.zobristKey = zobristKey;

				undoMove(board, mv);
				continue;
			}

			moveFound = true;

			int score = 0;
			if (board.insufficientMaterial()) {
				score = 0;
			} else if (searchPV) {
				score = -alphaBeta(depth - 1, ply + 1, -beta, -alpha, board);
			} else {
				score = -alphaBeta(depth - 1, ply + 1, -alpha-1, -alpha, board);
				if (score > alpha) {
					score = -alphaBeta(depth - 1, ply + 1, -beta, -alpha, board);
				}
			}
			board.castle = board_castle;
			board.enpassantSquare = board_ep;
			board.score = board_score;
			board.zobristKey = zobristKey;

			undoMove(board, mv);

			// If a move results in a score that was less than or equal to alpha, it was just a bad move and
			// it can be forgotten about, since, as I stated a few paragraphs ago, there is known to be a strategy
			// that gets the moving side a position valued at alpha.
			
			// do nothing

			// If a move results in a score that is greater than or equal to beta, this whole node is trash,
			// since the opponent is not going to let the side to move achieve this position, because there is
			// some choice the opponent can make that will avoid it.  So if we find something with a score of
			// beta or better, it has been proven that this whole node is not going to happen, so the rest of
			// the legal moves do not have to be searched.
			if (score >= beta) {

				TranspositionTable.storeEntry(board.zobristKey, beta, TTEntry.BETA_VALUE, depth, bestMove);
				return beta;
			}

			// If a move results in a score that is greater than alpha, but less than beta,
			// this is the move that the side to move is going to plan to play, unless something changes later on.
			// So alpha is increased to reflect this new value.
			if (score > alpha) {
				hashCode = TTEntry.EXACT_VALUE;

				bestMove = mv;
				alpha = score;
				searchPV = false;
			}
		}

		if (!moveFound) { // no legal moves

			int score = 0;

			if ((board.color && board.isWhiteInCheck(Utility.getIndex(board.whiteking))) ||
                (!board.color && board.isBlackInCheck(Utility.getIndex(board.blackking)))) {

            	score = -16000 + ply; // checkmate
            } else {
            	board.stalemate = true;
            	score = 0; // stalemate
            }

            if(score <= alpha) {
        		TranspositionTable.storeEntry(board.zobristKey, score, TTEntry.ALPHA_VALUE, depth, 0);
        	} else if(score >= beta) {
        		TranspositionTable.storeEntry(board.zobristKey, score, TTEntry.BETA_VALUE, depth, 0);
        	} else {
        		TranspositionTable.storeEntry(board.zobristKey, score, TTEntry.EXACT_VALUE, depth, 0);
        	}

            return score;
		}
		
		// It's possible, and in fact quite common, that none of the legal moves result in a score
		// that exceeds alpha, in which case this position was junk from our point of view, and we
		// will avoid it by making a different choice somewhere above here in the game tree.

		if (bestMove != 0) {
			TranspositionTable.storeEntry(board.zobristKey, alpha, hashCode, depth, bestMove);
		}

		board.bestMove = bestMove;
		board.bestScore = alpha;

		return alpha;
	}

	final int quiesce(int alpha, int beta, int ply, BitBoard board) {

		int stand_pat = board.score;
		if (!board.color) {
			stand_pat = - stand_pat;
		}

		if(stand_pat >= beta) {
			return beta;
		}
	    if(alpha < stand_pat) {
	    	alpha = stand_pat;
	    }
	    
	    int noMoves = MoveGenerator.generateCaptureMoveListForQuiesce(board, POOL[ply]);

		for (int i = 0; i < noMoves; i++) {

			int board_castle = board.castle;
			int board_ep = board.enpassantSquare;
			int board_score = board.score;
			long zobristKey = board.zobristKey;
			if (!makeMove(board, POOL[ply][i])) {
				board.castle = board_castle;
				board.enpassantSquare = board_ep;
				board.score = board_score;
				board.zobristKey = zobristKey;
				undoMove(board, POOL[ply][i]);
				continue;
			}
	        int score = -quiesce(-beta, -alpha, ply + 1, board);

			board.castle = board_castle;
			board.enpassantSquare = board_ep;
			board.score = board_score;
			board.zobristKey = zobristKey;
			undoMove(board, POOL[ply][i]);

			if(score >= beta) {
				return beta;
			}
			if( score > alpha ) {
				alpha = score;
			}
	    }
	    return alpha;
	}

	public static final boolean makeMove(BitBoard board, int move) {

		int promotedPiece = (move >>> 18) & 7;
		int capturedPiece = (move >>> 15) & 7;
		int piece = (move >>> 12) & 7;
		int to_sq = (move >>> 6)  & 63;
		int from_sq = move & 63;

		boolean legal = true;
		
		if (board.enpassantSquare != -1) {
			board.zobristKey ^= zobrist_hash_en_passant[board.enpassantSquare % 8];
		}
		if (to_sq != board.enpassantSquare || piece != PAWN) {
			board.enpassantSquare = -1;
		}

		if (board.color && piece == KING && from_sq == E1 && to_sq == G1) {

			if (board.isWhiteInCheck(E1)) {
				legal = false;
			}

			board.whitepieces ^= mask[E1] | mask[G1];
			board.whitepieces ^= mask[H1] | mask[F1];

			board.whiteking ^= mask[E1] | mask[G1];

			board.whiterooks ^= mask[H1] | mask[F1];

			board.allpieces = board.whitepieces | board.blackpieces;

			if (board.isWhiteInCheck(F1) || board.isWhiteInCheck(G1)) {
				legal = false;
			}

			board.score += WKING_SQ_VAL[G1] - WKING_SQ_VAL[E1];

			board.zobristKey ^= zobrist_hash_castling_rights[board.castle]; // erase old one
			board.castle &= CASTLE_MASK[E1];
			board.zobristKey ^= zobrist_hash_castling_rights[board.castle]; // set new one

			board.piece[E1] = EMPTY;
			board.piece[F1] = ROOK;
			board.piece[G1] = KING;
			board.piece[H1] = EMPTY;

			board.color = !board.color; // toggle colors.
			if (!legal) {
				return false;
			}
			
			board.zobristKey ^= zobrist_hash_pieces[WKING][E1];
			board.zobristKey ^= zobrist_hash_pieces[WKING][G1];
			board.zobristKey ^= zobrist_hash_pieces[WROOK][H1];
			board.zobristKey ^= zobrist_hash_pieces[WROOK][F1];

			board.zobristKey ^= zobrist_hash_side;

			return true;
		}

		if (board.color && piece == KING && from_sq == E1 && to_sq == C1) {

			if (board.isWhiteInCheck(E1)) {
				legal = false;
			}

			board.whitepieces ^= mask[E1] | mask[C1];
			board.whitepieces ^= mask[A1] | mask[D1];

			board.whiteking ^= mask[E1] | mask[C1];

			board.whiterooks ^= mask[A1] | mask[D1];
			
			board.allpieces = board.whitepieces | board.blackpieces;

			if (board.isWhiteInCheck(D1) || board.isWhiteInCheck(C1)) {
				legal = false;
			}

			board.score += WKING_SQ_VAL[C1] - WKING_SQ_VAL[E1];

			board.zobristKey ^= zobrist_hash_castling_rights[board.castle]; // erase old one
			board.castle &= CASTLE_MASK[E1];
			board.zobristKey ^= zobrist_hash_castling_rights[board.castle]; // set new one

			board.piece[E1] = EMPTY;
			board.piece[D1] = ROOK;
			board.piece[C1] = KING;
			board.piece[A1] = EMPTY;

			board.color = !board.color; // toggle colors.
			if (!legal) {
				return false;
			}

			board.zobristKey ^= zobrist_hash_pieces[WKING][E1];
			board.zobristKey ^= zobrist_hash_pieces[WKING][C1];
			board.zobristKey ^= zobrist_hash_pieces[WROOK][A1];
			board.zobristKey ^= zobrist_hash_pieces[WROOK][D1];

			board.zobristKey ^= zobrist_hash_side;

			return true;
		}

		if (!board.color && piece == KING && from_sq == E8 && to_sq == G8) {

			if (board.isBlackInCheck(E8)) {
				legal = false;
			}

			board.blackpieces ^= mask[E8] | mask[G8];
			board.blackpieces ^= mask[H8] | mask[F8];

			board.blackking ^= mask[E8] | mask[G8];

			board.blackrooks ^= mask[H8] | mask[F8];
			
			board.allpieces = board.whitepieces | board.blackpieces;

			if (board.isBlackInCheck(F8) || board.isBlackInCheck(G8)) {
				legal = false;
			}

			board.score -= BKING_SQ_VAL[G8] - BKING_SQ_VAL[E8];

			board.zobristKey ^= zobrist_hash_castling_rights[board.castle]; // erase old one
			board.castle &= CASTLE_MASK[E8];
			board.zobristKey ^= zobrist_hash_castling_rights[board.castle]; // set new one

			board.piece[E8] = EMPTY;
			board.piece[F8] = ROOK;
			board.piece[G8] = KING;
			board.piece[H8] = EMPTY;

			board.color = !board.color; // toggle colors.
			if (!legal) {
				return false;
			}

			board.zobristKey ^= zobrist_hash_pieces[BKING][E8];
			board.zobristKey ^= zobrist_hash_pieces[BKING][G8];
			board.zobristKey ^= zobrist_hash_pieces[BROOK][H8];
			board.zobristKey ^= zobrist_hash_pieces[BROOK][F8];

			board.zobristKey ^= zobrist_hash_side;

			return true;
		}

		if (!board.color && piece == KING && from_sq == E8 && to_sq == C8) {
			
			if (board.isBlackInCheck(E8)) {
				legal = false;
			}

			board.blackpieces ^= mask[E8] | mask[C8];
			board.blackpieces ^= mask[A8] | mask[D8];

			board.blackking ^= mask[E8] | mask[C8];

			board.blackrooks ^= mask[A8] | mask[D8];

			board.allpieces = board.whitepieces | board.blackpieces;

			if (board.isBlackInCheck(D8) || board.isBlackInCheck(C8)) {
				legal = false;
			}

			board.score -= BKING_SQ_VAL[C8] - BKING_SQ_VAL[E8];

			board.zobristKey ^= zobrist_hash_castling_rights[board.castle]; // erase old one
			board.castle &= CASTLE_MASK[E8];
			board.zobristKey ^= zobrist_hash_castling_rights[board.castle]; // set new one

			board.piece[E8] = EMPTY;
			board.piece[D8] = ROOK;
			board.piece[C8] = KING;
			board.piece[A8] = EMPTY;

			board.color = !board.color; // toggle colors.
			if (!legal) {
				return false;
			}

			board.zobristKey ^= zobrist_hash_pieces[BKING][E8];
			board.zobristKey ^= zobrist_hash_pieces[BKING][C8];
			board.zobristKey ^= zobrist_hash_pieces[BROOK][A8];
			board.zobristKey ^= zobrist_hash_pieces[BROOK][D8];

			board.zobristKey ^= zobrist_hash_side;

			return true;
		}

		board.piece[from_sq] = EMPTY;
		board.piece[to_sq] = piece;

		board.zobristKey ^= zobrist_hash_castling_rights[board.castle]; // erase old one
		board.castle &= CASTLE_MASK[from_sq] & CASTLE_MASK[to_sq];
		board.zobristKey ^= zobrist_hash_castling_rights[board.castle]; // set new one

		if (board.color) { // white

			board.whitepieces ^= mask[from_sq] | mask[to_sq];

			if (capturedPiece != EMPTY) {

				board.blackpieces ^= mask[to_sq];
				if (capturedPiece == BISHOP) {
					board.blackbishops ^= mask[to_sq];
					board.score += 350 + BBISHOP_SQ_VAL[to_sq];
					board.zobristKey ^= zobrist_hash_pieces[BBISHOP][to_sq];
				} else if (capturedPiece == KING) {
					board.blackking ^= mask[to_sq];
					board.zobristKey ^= zobrist_hash_pieces[BKING][to_sq];
				} else if (capturedPiece == KNIGHT) {
					board.blackknights ^= mask[to_sq];
					board.score += 330 + BKNIGHT_SQ_VAL[to_sq];
					board.zobristKey ^= zobrist_hash_pieces[BKNIGHT][to_sq];
				} else if (capturedPiece == PAWN) {

					if (Utility.bitCount(board.blackpawns & files[to_sq]) > 1) { // double pawns
						board.score -= 30;
					}

					board.blackpawns ^= mask[to_sq];
					board.score += 100 + BPAWN_SQ_VAL[to_sq];
					
					if (to_sq != board.enpassantSquare) {
						board.zobristKey ^= zobrist_hash_pieces[BPAWN][to_sq];
					}
					
				} else if (capturedPiece == QUEEN) {
					board.blackqueens ^= mask[to_sq];
					board.score += 900;
					board.zobristKey ^= zobrist_hash_pieces[BQUEEN][to_sq];
				} else { // rook
					board.blackrooks ^= mask[to_sq];
					board.score += 500;
					board.zobristKey ^= zobrist_hash_pieces[BROOK][to_sq];
				}
			}

			switch(piece) {

			case PAWN:

				board.zobristKey ^= zobrist_hash_pieces[WPAWN][from_sq];

				switch(promotedPiece) {

				case EMPTY:

					// score setting for doubled pawns
					if (capturedPiece != EMPTY || to_sq == board.enpassantSquare) {

						if (Utility.bitCount(board.whitepawns & files[from_sq]) > 1) {
							board.score += 30; // reward
						}
						if (Utility.bitCount(board.whitepawns & files[to_sq]) != 0) {
							board.score -= 30; // penalty
						}
					}
					board.whitepawns ^= mask[from_sq] | mask[to_sq];
					board.zobristKey ^= zobrist_hash_pieces[WPAWN][to_sq];

					if (to_sq == board.enpassantSquare) {
						board.blackpieces ^= mask[to_sq + 8];
						board.blackpawns ^= mask[to_sq + 8];
						
						board.enpassantSquare = -1;
						board.score += 100 + BPAWN_SQ_VAL[to_sq + 8];
						board.piece[to_sq + 8] = EMPTY;

						board.zobristKey ^= zobrist_hash_pieces[BPAWN][to_sq + 8];
					}

					if (from_sq - to_sq == 16) {
						board.enpassantSquare = from_sq - 8;
						board.zobristKey ^= zobrist_hash_en_passant[board.enpassantSquare % 8];
					}
					board.score += WPAWN_SQ_VAL[to_sq] - WPAWN_SQ_VAL[from_sq];
					break;
				case QUEEN:
					board.whitepawns ^= mask[from_sq];
					board.whitequeens ^= mask[to_sq];
					board.score += 800 - WPAWN_SQ_VAL[from_sq];
					board.piece[to_sq] = QUEEN;
					board.zobristKey ^= zobrist_hash_pieces[WQUEEN][to_sq];
					break;
				case ROOK:
					board.whitepawns ^= mask[from_sq];
					board.whiterooks ^= mask[to_sq];
					board.score += 400 - WPAWN_SQ_VAL[from_sq];
					board.piece[to_sq] = ROOK;
					board.zobristKey ^= zobrist_hash_pieces[WROOK][to_sq];
					break;
				case BISHOP:
					board.whitepawns ^= mask[from_sq];
					board.whitebishops ^= mask[to_sq];
					board.score += 250 + WBISHOP_SQ_VAL[to_sq] - WPAWN_SQ_VAL[from_sq];
					board.piece[to_sq] = BISHOP;
					board.zobristKey ^= zobrist_hash_pieces[WBISHOP][to_sq];
					break;
				case KNIGHT:
					board.whitepawns ^= mask[from_sq];
					board.whiteknights ^= mask[to_sq];
					board.score += 230 + WKNIGHT_SQ_VAL[to_sq] - WPAWN_SQ_VAL[from_sq];
					board.piece[to_sq] = KNIGHT;
					board.zobristKey ^= zobrist_hash_pieces[WKNIGHT][to_sq];
				}

				break;
			case KNIGHT:
				board.whiteknights ^= mask[from_sq] | mask[to_sq];
				board.score += WKNIGHT_SQ_VAL[to_sq] - WKNIGHT_SQ_VAL[from_sq];
				board.zobristKey ^= zobrist_hash_pieces[WKNIGHT][from_sq];
				board.zobristKey ^= zobrist_hash_pieces[WKNIGHT][to_sq];
				break;
			case BISHOP:
				board.whitebishops ^= mask[from_sq] | mask[to_sq];
				board.score += WBISHOP_SQ_VAL[to_sq] - WBISHOP_SQ_VAL[from_sq];
				board.zobristKey ^= zobrist_hash_pieces[WBISHOP][from_sq];
				board.zobristKey ^= zobrist_hash_pieces[WBISHOP][to_sq];
				break;
			case ROOK:
				board.whiterooks ^= mask[from_sq] | mask[to_sq];
				board.zobristKey ^= zobrist_hash_pieces[WROOK][from_sq];
				board.zobristKey ^= zobrist_hash_pieces[WROOK][to_sq];
				break;
			case QUEEN:
				board.whitequeens ^= mask[from_sq] | mask[to_sq];
				board.zobristKey ^= zobrist_hash_pieces[WQUEEN][from_sq];
				board.zobristKey ^= zobrist_hash_pieces[WQUEEN][to_sq];
				break;
			case KING:
				board.whiteking ^= mask[from_sq] | mask[to_sq];
				board.zobristKey ^= zobrist_hash_pieces[WKING][from_sq];
				board.zobristKey ^= zobrist_hash_pieces[WKING][to_sq];

				if (Utility.bitCount(board.allpieces) > 19) {
					board.score += WKING_SQ_VAL[to_sq] - WKING_SQ_VAL[from_sq];
				}
			}

			board.allpieces = board.whitepieces | board.blackpieces;

			if (board.isWhiteInCheck(Utility.getIndex(board.whiteking))) {
				legal = false;
			}
		} else { // black

			board.blackpieces ^= mask[from_sq] | mask[to_sq];

			if (capturedPiece != EMPTY) {

				board.whitepieces ^= mask[to_sq];
				if (capturedPiece == BISHOP) {
					board.whitebishops ^= mask[to_sq];
					board.score -= 350 + WBISHOP_SQ_VAL[to_sq];
					board.zobristKey ^= zobrist_hash_pieces[WBISHOP][to_sq];
				} else if (capturedPiece == KING) {
					board.whiteking ^= mask[to_sq];
					board.zobristKey ^= zobrist_hash_pieces[WKING][to_sq];
				} else if (capturedPiece == KNIGHT) {
					board.whiteknights ^= mask[to_sq];
					board.score -= 330 + WKNIGHT_SQ_VAL[to_sq];
					board.zobristKey ^= zobrist_hash_pieces[WKNIGHT][to_sq];
				} else if (capturedPiece == PAWN) {

					if (Utility.bitCount(board.whitepawns & files[to_sq]) > 1) {
						board.score += 30;
					}
					board.whitepawns ^= mask[to_sq];
					board.score -= 100 + WPAWN_SQ_VAL[to_sq];
					if (to_sq != board.enpassantSquare) {
						board.zobristKey ^= zobrist_hash_pieces[WPAWN][to_sq];
					}
				} else if (capturedPiece == QUEEN) {
					board.whitequeens ^= mask[to_sq];
					board.score -= 900;
					board.zobristKey ^= zobrist_hash_pieces[WQUEEN][to_sq];
				} else { // rook
					board.whiterooks ^= mask[to_sq];
					board.score -= 500;
					board.zobristKey ^= zobrist_hash_pieces[WROOK][to_sq];
				}
			}

			switch(piece) {

			case PAWN:

				board.zobristKey ^= zobrist_hash_pieces[BPAWN][from_sq];

				switch(promotedPiece)
				{
				case EMPTY:
					// score setting for doubled pawns
					if (capturedPiece != EMPTY || to_sq == board.enpassantSquare) {

						if (Utility.bitCount(board.blackpawns & files[from_sq]) > 1) {
							board.score -= 30; // reward
						}
						if (Utility.bitCount(board.blackpawns & files[to_sq]) != 0) {
							board.score += 30; // penalty
						}
					}
					board.blackpawns ^= mask[from_sq] | mask[to_sq];
					board.zobristKey ^= zobrist_hash_pieces[BPAWN][to_sq];
					if (to_sq == board.enpassantSquare) {
						board.whitepieces ^= mask[to_sq - 8];
						board.whitepawns ^= mask[to_sq - 8];

						board.enpassantSquare = -1;
						board.score -= 100 + WPAWN_SQ_VAL[to_sq - 8];
						board.piece[to_sq - 8] = EMPTY;
						board.zobristKey ^= zobrist_hash_pieces[WPAWN][to_sq - 8];
					}
					if (to_sq - from_sq == 16) {
						board.enpassantSquare = to_sq - 8;
						board.zobristKey ^= zobrist_hash_en_passant[board.enpassantSquare % 8];
					}
					board.score -= BPAWN_SQ_VAL[to_sq] - BPAWN_SQ_VAL[from_sq];
					break;
				case QUEEN:
					board.blackpawns ^= mask[from_sq];
					board.blackqueens ^= mask[to_sq];
					board.score -= 800 - BPAWN_SQ_VAL[from_sq];
					board.piece[to_sq] = QUEEN;
					board.zobristKey ^= zobrist_hash_pieces[BQUEEN][to_sq];
					break;
				case ROOK:
					board.blackpawns ^= mask[from_sq];
					board.blackrooks ^= mask[to_sq];
					board.score -= 400 - BPAWN_SQ_VAL[from_sq];
					board.piece[to_sq] = ROOK;
					board.zobristKey ^= zobrist_hash_pieces[BROOK][to_sq];
					break;
				case BISHOP:
					board.blackpawns ^= mask[from_sq];
					board.blackbishops ^= mask[to_sq];
					board.score -= 250 + BBISHOP_SQ_VAL[to_sq] - BPAWN_SQ_VAL[from_sq];
					board.piece[to_sq] = BISHOP;
					board.zobristKey ^= zobrist_hash_pieces[BBISHOP][to_sq];
					break;
				case KNIGHT:
					board.blackpawns ^= mask[from_sq];
					board.blackknights ^= mask[to_sq];
					board.score -= 230 + BKNIGHT_SQ_VAL[to_sq] - BPAWN_SQ_VAL[from_sq];
					board.piece[to_sq] = KNIGHT;
					board.zobristKey ^= zobrist_hash_pieces[BKNIGHT][to_sq];
				}

				break;
			case KNIGHT:
				board.blackknights ^= mask[from_sq] | mask[to_sq];
				board.score -= BKNIGHT_SQ_VAL[to_sq] - BKNIGHT_SQ_VAL[from_sq];
				board.zobristKey ^= zobrist_hash_pieces[BKNIGHT][from_sq];
				board.zobristKey ^= zobrist_hash_pieces[BKNIGHT][to_sq];
				break;
			case BISHOP:
				board.blackbishops ^= mask[from_sq] | mask[to_sq];
				board.score -= BBISHOP_SQ_VAL[to_sq] - BBISHOP_SQ_VAL[from_sq];
				board.zobristKey ^= zobrist_hash_pieces[BBISHOP][from_sq];
				board.zobristKey ^= zobrist_hash_pieces[BBISHOP][to_sq];
				break;
			case ROOK:
				board.blackrooks ^= mask[from_sq] | mask[to_sq];
				board.zobristKey ^= zobrist_hash_pieces[BROOK][from_sq];
				board.zobristKey ^= zobrist_hash_pieces[BROOK][to_sq];
				break;
			case QUEEN:
				board.blackqueens ^= mask[from_sq] | mask[to_sq];
				board.zobristKey ^= zobrist_hash_pieces[BQUEEN][from_sq];
				board.zobristKey ^= zobrist_hash_pieces[BQUEEN][to_sq];
				break;
			case KING:
				board.blackking ^= mask[from_sq] | mask[to_sq];
				board.zobristKey ^= zobrist_hash_pieces[BKING][from_sq];
				board.zobristKey ^= zobrist_hash_pieces[BKING][to_sq];

				if (Utility.bitCount(board.allpieces) > 19) {
					board.score -= BKING_SQ_VAL[to_sq] - BKING_SQ_VAL[from_sq];
				}
			}

			board.allpieces = board.whitepieces | board.blackpieces;

			if (board.isBlackInCheck(Utility.getIndex(board.blackking))) {
				legal = false;
			}
		}

		board.color = !board.color; // toggle colors.

		if (!legal) {
			return false;
		}

		board.zobristKey ^= zobrist_hash_side;

		return true;
	}

	public static final void undoMove(BitBoard board, int move) {

		board.color = !board.color; // toggle colors.
		board.stalemate = false;

		int promotedPiece = (move >>> 18) & 7;
		int capturedPiece = (move >>> 15) & 7;
		int piece = (move >>> 12) & 7;
		int to_sq = (move >>> 6)  & 63;
		int from_sq = move & 63;
		
		if (piece == KING && from_sq == E1 && to_sq == G1) {

			board.whitepieces ^= mask[E1] | mask[G1];
			board.whitepieces ^= mask[H1] | mask[F1];

			board.whiteking ^= mask[E1] | mask[G1];

			board.whiterooks ^= mask[H1] | mask[F1];

			board.allpieces = board.whitepieces | board.blackpieces;

			board.piece[E1] = KING;
			board.piece[F1] = EMPTY;
			board.piece[G1] = EMPTY;
			board.piece[H1] = ROOK;

			return;
		}

		if (piece == KING && from_sq == E1 && to_sq == C1) {

			board.whitepieces ^= mask[E1] | mask[C1];
			board.whitepieces ^= mask[A1] | mask[D1];

			board.whiteking ^= mask[E1] | mask[C1];

			board.whiterooks ^= mask[A1] | mask[D1];

			board.allpieces = board.whitepieces | board.blackpieces;

			board.piece[E1] = KING;
			board.piece[D1] = EMPTY;
			board.piece[C1] = EMPTY;
			board.piece[A1] = ROOK;

			return;
		}

		if (piece == KING && from_sq == E8 && to_sq == G8) {

			board.blackpieces ^= mask[E8] | mask[G8];
			board.blackpieces ^= mask[H8] | mask[F8];

			board.blackking ^= mask[E8] | mask[G8];

			board.blackrooks ^= mask[H8] | mask[F8];

			board.allpieces = board.whitepieces | board.blackpieces;

			board.piece[E8] = KING;
			board.piece[F8] = EMPTY;
			board.piece[G8] = EMPTY;
			board.piece[H8] = ROOK;

			return;
		}

		if (piece == KING && from_sq == E8 && to_sq == C8) {
			
			board.blackpieces ^= mask[E8] | mask[C8];
			board.blackpieces ^= mask[A8] | mask[D8];

			board.blackking ^= mask[E8] | mask[C8];

			board.blackrooks ^= mask[A8] | mask[D8];

			board.allpieces = board.whitepieces | board.blackpieces;
			board.piece[E8] = KING;
			board.piece[D8] = EMPTY;
			board.piece[C8] = EMPTY;
			board.piece[A8] = ROOK;

			return;
		}

		board.piece[from_sq] = piece;
		board.piece[to_sq] = capturedPiece;

		if (board.color) { // white

			board.whitepieces ^= mask[from_sq] | mask[to_sq];

			if (capturedPiece != EMPTY) {

				board.blackpieces ^= mask[to_sq];
				if (capturedPiece == BISHOP) {
					board.blackbishops ^= mask[to_sq];
				} else if (capturedPiece == KING) {
					board.blackking ^= mask[to_sq];
				} else if (capturedPiece == KNIGHT) {
					board.blackknights ^= mask[to_sq];
				} else if (capturedPiece == PAWN) {
					board.blackpawns ^= mask[to_sq];
				} else if (capturedPiece == QUEEN) {
					board.blackqueens ^= mask[to_sq];
				} else { // rook
					board.blackrooks ^= mask[to_sq];
				}
			}

			switch(piece) {

			case PAWN:

				switch(promotedPiece) {

				case EMPTY:
					board.whitepawns ^= mask[from_sq] | mask[to_sq];
					if (to_sq == board.enpassantSquare) {
						board.blackpieces ^= mask[to_sq + 8];
						board.blackpawns ^= mask[to_sq + 8];

						board.piece[to_sq + 8] = PAWN;
					}
					break;
				case QUEEN:
					board.whitepawns ^= mask[from_sq];
					board.whitequeens ^= mask[to_sq];
					break;
				case ROOK:
					board.whitepawns ^= mask[from_sq];
					board.whiterooks ^= mask[to_sq];
					break;
				case BISHOP:
					board.whitepawns ^= mask[from_sq];
					board.whitebishops ^= mask[to_sq];
					break;
				case KNIGHT:
					board.whitepawns ^= mask[from_sq];
					board.whiteknights ^= mask[to_sq];
				}

				break;
			case KNIGHT:
				board.whiteknights ^= mask[from_sq] | mask[to_sq];
				break;
			case BISHOP:
				board.whitebishops ^= mask[from_sq] | mask[to_sq];
				break;
			case ROOK:
				board.whiterooks ^= mask[from_sq] | mask[to_sq];
				break;
			case QUEEN:
				board.whitequeens ^= mask[from_sq] | mask[to_sq];
				break;
			case KING:
				board.whiteking ^= mask[from_sq] | mask[to_sq];
			}

			board.allpieces = board.whitepieces | board.blackpieces;
		}
		else { // black

			board.blackpieces ^= mask[from_sq] | mask[to_sq];

			if (capturedPiece != EMPTY) {

				board.whitepieces ^= mask[to_sq];
				if (capturedPiece == BISHOP) {
					board.whitebishops ^= mask[to_sq];
				} else if (capturedPiece == KING) {
					board.whiteking ^= mask[to_sq];
				} else if (capturedPiece == KNIGHT) {
					board.whiteknights ^= mask[to_sq];
				} else if (capturedPiece == PAWN) {
					board.whitepawns ^= mask[to_sq];
				} else if (capturedPiece == QUEEN) {
					board.whitequeens ^= mask[to_sq];
				} else { // rook
					board.whiterooks ^= mask[to_sq];
				}
			}

			switch(piece) {

			case PAWN:
				switch(promotedPiece)
				{
				case EMPTY:
					board.blackpawns ^= mask[from_sq] | mask[to_sq];
					if (to_sq == board.enpassantSquare) {
						board.whitepieces ^= mask[to_sq - 8];
						board.whitepawns ^= mask[to_sq - 8];

						board.piece[to_sq - 8] = PAWN;
					}
					break;
				case QUEEN:
					board.blackpawns ^= mask[from_sq];
					board.blackqueens ^= mask[to_sq];
					break;
				case ROOK:
					board.blackpawns ^= mask[from_sq];
					board.blackrooks ^= mask[to_sq];
					break;
				case BISHOP:
					board.blackpawns ^= mask[from_sq];
					board.blackbishops ^= mask[to_sq];
					break;
				case KNIGHT:
					board.blackpawns ^= mask[from_sq];
					board.blackknights ^= mask[to_sq];
				}

				break;
			case KNIGHT:
				board.blackknights ^= mask[from_sq] | mask[to_sq];
				break;
			case BISHOP:
				board.blackbishops ^= mask[from_sq] | mask[to_sq];
				break;
			case ROOK:
				board.blackrooks ^= mask[from_sq] | mask[to_sq];
				break;
			case QUEEN:
				board.blackqueens ^= mask[from_sq] | mask[to_sq];
				break;
			case KING:
				board.blackking ^= mask[from_sq] | mask[to_sq];
			}

			board.allpieces = board.whitepieces | board.blackpieces;
		}
	}
}