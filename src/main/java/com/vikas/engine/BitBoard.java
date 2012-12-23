package com.vikas.engine;


public class BitBoard implements Constants {

	long whitepieces;
	long blackpieces;
	long whitepawns;
	long blackpawns;
	long whiteknights;
	long blackknights;
	long whitebishops;
	long blackbishops;
	long whiterooks;
	long blackrooks;
	long whitequeens;
	long blackqueens;
	long whiteking;
	long blackking;

	long allpieces;

	int enpassantSquare;

	int castle;

	boolean color;
	
	boolean stalemate;

	int bestMove;
	int bestScore;

	int score;

	long zobristKey;

	int []piece = new int[64];
	
	public BitBoard() {
		this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
	}
	
	public BitBoard(String fen) {

		if (fen == null) {
			fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
		}

		String []array = fen.trim().split("\\s+");

		String board = array[0];
		String side = array[1];
		String castling = array[2];
		String ep = array[3];
//		String half = array[4];
//		String full = array[5];
		
		String []boardArr = board.split("/");
		
		whitepawns = 0;
		blackpawns = 0;
		whiteknights = 0;
		blackknights = 0;
		whitebishops = 0;
		blackbishops = 0;
		whiterooks = 0;
		blackrooks = 0;
		whitequeens = 0;
		blackqueens = 0;
		whiteking = 0;
		blackking = 0;
		
		try {

			for (int row = 0; row < 8; row++) {
				int index = 0;
				for (int i = 0; i < boardArr[row].length(); i++) {
	
					int sq = row * 8 + index;
					char c = boardArr[row].charAt(i);
	
					if (c < 48 || c > 57) {
						index++;
					}
	
					switch(c) {
					case 'p':
						blackpawns |= mask[sq];
						piece[sq] = PAWN;
						break;
					case 'n':
						blackknights |= mask[sq];
						piece[sq] = KNIGHT;
						break;
					case 'b':
						blackbishops |= mask[sq];
						piece[sq] = BISHOP;
						break;
					case 'r':
						blackrooks |= mask[sq];
						piece[sq] = ROOK;
						break;
					case 'q':
						blackqueens |= mask[sq];
						piece[sq] = QUEEN;
						break;
					case 'k':
						blackking |= mask[sq];
						piece[sq] = KING;
						break;
					case 'P':
						whitepawns |= mask[sq];
						piece[sq] = PAWN;
						break;
					case 'N':
						whiteknights |= mask[sq];
						piece[sq] = KNIGHT;
						break;
					case 'B':
						whitebishops |= mask[sq];
						piece[sq] = BISHOP;
						break;
					case 'R':
						whiterooks |= mask[sq];
						piece[sq] = ROOK;
						break;
					case 'Q':
						whitequeens |= mask[sq];
						piece[sq] = QUEEN;
						break;
					case 'K':
						whiteking |= mask[sq];
						piece[sq] = KING;
						break;
					default:
						index += Character.getNumericValue(c);
					}
				}
			}
			
			if ("w".equals(side)) {
				color = true;
			} else {
				color = false;
			}
	
			castle = 0;
			for (int i = 0; i < castling.length(); i++) {
	
				switch(castling.charAt(i)) {
	
				case 'K': castle |= 1; break;
				case 'Q': castle |= 2; break;
				case 'k': castle |= 4; break;
				case 'q': castle |= 8; break;
				}
			}
			
			if ("-".equals(ep)) {
				enpassantSquare = -1;
			} else {
	
				int x = ep.charAt(0) - 97;     // 0-7
				int y = 8 - (ep.charAt(1) - 48); // 0-7
				enpassantSquare = 8 * y + x;
			}
		} catch (Exception e) {
//			System.out.println("invalid fen string");
		}

		whitepieces = whitebishops | whiteking |
		  whiteknights | whitepawns |
		  whitequeens | whiterooks;

		blackpieces = blackbishops | blackking |
				  blackknights | blackpawns |
				  blackqueens | blackrooks;
		
		allpieces = whitepieces | blackpieces;
		
		score = Evaluator.eval(this);
		
		stalemate = false;

		initZobristKey();
	}

	public BitBoard(BitBoard board) {

		whitepieces = board.whitepieces;
		blackpieces = board.blackpieces;
		whitepawns = board.whitepawns;
		blackpawns = board.blackpawns;
		whiteknights = board.whiteknights;
		blackknights = board.blackknights;
		whitebishops = board.whitebishops;
		blackbishops = board.blackbishops;
		whiterooks = board.whiterooks;
		blackrooks = board.blackrooks;
		whitequeens = board.whitequeens;
		blackqueens = board.blackqueens;
		whiteking = board.whiteking;
		blackking = board.blackking;

		allpieces = board.allpieces;

		enpassantSquare = board.enpassantSquare;

		castle = board.castle;

		color = board.color;
		
		stalemate = board.stalemate;

		bestMove = board.bestMove;
		bestScore = board.bestScore;

		score = board.score;

		zobristKey = board.zobristKey;

//		for (int sq = A8; sq <= H1; sq++) {
//			piece[sq] = board.piece[sq];
//		}
        // faster than the loop commented above
		System.arraycopy(board.piece, 0, piece, 0, piece.length);
	}
	
	private void initZobristKey() {

		zobristKey = 0;
		for (int sq = A8; sq <= H1; sq++) {

			switch (piece[sq]) {
			case PAWN:
				if ((whitepawns & mask[sq]) != 0) {
					zobristKey ^= zobrist_hash_pieces[WPAWN][sq];
				} else {
					zobristKey ^= zobrist_hash_pieces[BPAWN][sq];
				}
				break;
			case KNIGHT:
				if ((whiteknights & mask[sq]) != 0) {
					zobristKey ^= zobrist_hash_pieces[WKNIGHT][sq];
				} else {
					zobristKey ^= zobrist_hash_pieces[BKNIGHT][sq];
				}
				break;
			case BISHOP:
				if ((whitebishops & mask[sq]) != 0) {
					zobristKey ^= zobrist_hash_pieces[WBISHOP][sq];
				} else {
					zobristKey ^= zobrist_hash_pieces[BBISHOP][sq];
				}
				break;
			case ROOK:
				if ((whiterooks & mask[sq]) != 0) {
					zobristKey ^= zobrist_hash_pieces[WROOK][sq];
				} else {
					zobristKey ^= zobrist_hash_pieces[BROOK][sq];
				}
				break;
			case QUEEN:
				if ((whitequeens & mask[sq]) != 0) {
					zobristKey ^= zobrist_hash_pieces[WQUEEN][sq];
				} else {
					zobristKey ^= zobrist_hash_pieces[BQUEEN][sq];
				}
				break;
			case KING:
				if ((whiteking & mask[sq]) != 0) {
					zobristKey ^= zobrist_hash_pieces[WKING][sq];
				} else {
					zobristKey ^= zobrist_hash_pieces[BKING][sq];
				}
				break;
			}
		}
		
		zobristKey ^= zobrist_hash_castling_rights[castle];
		if (enpassantSquare != -1) {
			zobristKey ^= zobrist_hash_en_passant[enpassantSquare % 8];
		}
		if (!color) {
			zobristKey ^= zobrist_hash_side;
		}
	}

	public boolean isWhiteInCheck() {
		return isWhiteInCheck(Utility.getIndex(whiteking));
	}

	public boolean isBlackInCheck() {
		return isBlackInCheck(Utility.getIndex(blackking));
	}

	boolean isWhiteInCheck(int kingSq)
	{
		int from_sq;

		long piece_map, to_map;

		piece_map = blackking;
		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];
			to_map = king[from_sq] & whitepieces;
			if ((to_map & mask[kingSq]) != 0) {
				return true;
			}
		}

		piece_map = blackqueens;
		while (piece_map != 0)
		{
			from_sq = Utility.getIndex(piece_map);
			
			piece_map ^= mask[from_sq];
			to_map = (MoveGenerator.rookMoves(this, from_sq) |
				MoveGenerator.bishopMoves(this, from_sq)) & whitepieces;
			if ((to_map & mask[kingSq]) != 0) {
				return true;
			}
		}

		piece_map = blackrooks;
		while (piece_map != 0) {
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];
			to_map = MoveGenerator.rookMoves(this, from_sq) & whitepieces;
			if ((to_map & mask[kingSq]) != 0) {
				return true;
			}
		}

		piece_map = blackbishops;
		while (piece_map != 0) {
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];
			to_map = MoveGenerator.bishopMoves(this, from_sq) & whitepieces;
			if ((to_map & mask[kingSq]) != 0) {
				return true;
			}
		}

		piece_map = blackknights;
		while (piece_map != 0) {
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];
			to_map = knight[from_sq] & whitepieces;
			if ((to_map & mask[kingSq]) != 0) {
				return true;
			}
		}

		piece_map = blackpawns;
		while (piece_map != 0) {
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];
			to_map = bpawnCaps[from_sq] & whitepieces;
			if ((to_map & mask[kingSq]) != 0) {
				return true;
			}
		}

		return false;
	}

	boolean isBlackInCheck(int kingSq)
	{
		int from_sq;

		long piece_map, to_map;

		piece_map = whiteking;
		while (piece_map != 0) {
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];
			to_map = king[from_sq] & blackpieces;
			if ((to_map & mask[kingSq]) != 0) {
				return true;
			}
		}

		piece_map = whitequeens;
		while (piece_map != 0) {
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];
			to_map = (MoveGenerator.rookMoves(this, from_sq) |
				MoveGenerator.bishopMoves(this, from_sq)) & blackpieces;
			if ((to_map & mask[kingSq]) != 0) {
				return true;
			}
		}

		piece_map = whiterooks;
		while (piece_map != 0) {
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];
			to_map = MoveGenerator.rookMoves(this, from_sq) & blackpieces;
			if ((to_map & mask[kingSq]) != 0) {
				return true;
			}
		}

		piece_map = whitebishops;
		while (piece_map != 0) {
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];
			to_map = MoveGenerator.bishopMoves(this, from_sq) & blackpieces;
			if ((to_map & mask[kingSq]) != 0) {
				return true;
			}
		}

		piece_map = whiteknights;
		while (piece_map != 0) {
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];
			to_map = knight[from_sq] & blackpieces;
			if ((to_map & mask[kingSq]) != 0) {
				return true;
			}
		}

		piece_map = whitepawns;
		while (piece_map != 0) {
			from_sq = Utility.getIndex(piece_map);
			piece_map ^= mask[from_sq];
			to_map = wpawnCaps[from_sq] & blackpieces;
			if ((to_map & mask[kingSq]) != 0) {
				return true;
			}
		}

		return false;
	}

	public boolean insufficientMaterial() {

		if ((whitequeens | blackqueens | whiterooks | blackrooks | whitepawns | blackpawns) != 0) {
			return false;
		}

		int noPieces = Utility.bitCount(allpieces);

		if (noPieces >= 5) {
			return false;
		}
		if (noPieces <= 3) {
			return true;
		}

		// TODO: knight vs knight is not insufficient material
		// bishop vs bishop is draw only if they are of same color
		// and 2 bishops of the same color is insufficient material
		if ((whitebishops | blackbishops) == 0) {
			return true;
		}

		return false;
	}

	public String getFenString() {

		String fen = "";

		for (int row = 0; row < 8; row++) {
			
			int ctr = 0;
			
			for (int col = 0; col < 8; col++) {

				int sq = row * 8 + col;
				if ((allpieces & mask[sq]) == 0) {
					ctr++;
				} else {
					if (ctr != 0) {
						fen += ctr;
						ctr = 0;
					}
					if ((whitepieces & mask[sq]) != 0) {
						
						switch (piece[sq]) {
						
						case PAWN:
							fen += "P";
							break;
						case KNIGHT:
							fen += "N";
							break;
						case BISHOP:
							fen += "B";
							break;
						case ROOK:
							fen += "R";
							break;
						case QUEEN:
							fen += "Q";
							break;
						case KING:
							fen += "K";
						}
					} else {
					
						switch (piece[sq]) {
						
						case PAWN:
							fen += "p";
							break;
						case KNIGHT:
							fen += "n";
							break;
						case BISHOP:
							fen += "b";
							break;
						case ROOK:
							fen += "r";
							break;
						case QUEEN:
							fen += "q";
							break;
						case KING:
							fen += "k";
						}
					}
				}
			}
			if (ctr != 0) {
				fen += ctr;
				ctr = 0;
			}
			fen += "/";
		}
		
		if (color) {
			fen += " w";
		} else {
			fen += " b";
		}
		
		if ((castle & 15) == 0) {
			fen += " -";
		} else {
			fen += " ";
			if ((castle & 1) != 0) {
				fen += "K";
			}
			if ((castle & 2) != 0) {
				fen += "Q";
			}
			if ((castle & 4) != 0) {
				fen += "k";
			}
			if ((castle & 8) != 0) {
				fen += "q";
			}
		}

		if (enpassantSquare == -1) {
			fen += " -";
		} else {
			char [] files = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
			int file = enpassantSquare % 8;
			int rank = 8 - (enpassantSquare / 8);
			
			fen += " " + files[file] + rank;
		}
		
		fen += " 0 1";

		return fen;
	}

	public long getWhitepieces() {
		return whitepieces;
	}

	public void setWhitepieces(long whitepieces) {
		this.whitepieces = whitepieces;
	}

	public long getBlackpieces() {
		return blackpieces;
	}

	public void setBlackpieces(long blackpieces) {
		this.blackpieces = blackpieces;
	}

	public long getWhitepawns() {
		return whitepawns;
	}

	public void setWhitepawns(long whitepawns) {
		this.whitepawns = whitepawns;
	}

	public long getBlackpawns() {
		return blackpawns;
	}

	public void setBlackpawns(long blackpawns) {
		this.blackpawns = blackpawns;
	}

	public long getWhiteknights() {
		return whiteknights;
	}

	public void setWhiteknights(long whiteknights) {
		this.whiteknights = whiteknights;
	}

	public long getBlackknights() {
		return blackknights;
	}

	public void setBlackknights(long blackknights) {
		this.blackknights = blackknights;
	}

	public long getWhitebishops() {
		return whitebishops;
	}

	public void setWhitebishops(long whitebishops) {
		this.whitebishops = whitebishops;
	}

	public long getBlackbishops() {
		return blackbishops;
	}

	public void setBlackbishops(long blackbishops) {
		this.blackbishops = blackbishops;
	}

	public long getWhiterooks() {
		return whiterooks;
	}

	public void setWhiterooks(long whiterooks) {
		this.whiterooks = whiterooks;
	}

	public long getBlackrooks() {
		return blackrooks;
	}

	public void setBlackrooks(long blackrooks) {
		this.blackrooks = blackrooks;
	}

	public long getWhitequeens() {
		return whitequeens;
	}

	public void setWhitequeens(long whitequeens) {
		this.whitequeens = whitequeens;
	}

	public long getBlackqueens() {
		return blackqueens;
	}

	public void setBlackqueens(long blackqueens) {
		this.blackqueens = blackqueens;
	}

	public long getWhiteking() {
		return whiteking;
	}

	public void setWhiteking(long whiteking) {
		this.whiteking = whiteking;
	}

	public long getBlackking() {
		return blackking;
	}

	public void setBlackking(long blackking) {
		this.blackking = blackking;
	}

	public long getAllpieces() {
		return allpieces;
	}

	public void setAllpieces(long allpieces) {
		this.allpieces = allpieces;
	}

	public long getZobristKey() {
		return zobristKey;
	}

	public void setZobristKey(long zobristKey) {
		this.zobristKey = zobristKey;
	}

	public int[] getPiece() {
		return piece;
	}

	public void setPiece(int [] piece) {
		this.piece = piece;
	}

	public int getEnpassantSquare() {
		return enpassantSquare;
	}

	public void setEnpassantSquare(int enpassantSquare) {
		this.enpassantSquare = enpassantSquare;
	}

	public int getBestMove() {
		return bestMove;
	}

	public void setBestMove(int bestMove) {
		this.bestMove = bestMove;
	}

	public boolean isColor() {
		return color;
	}

	public void setColor(boolean color) {
		this.color = color;
	}

	public boolean isStalemate() {
		return stalemate;
	}

	public void setStalemate(boolean stalemate) {
		this.stalemate = stalemate;
	}

	public int getCastle() {
		return castle;
	}

	public void setCastle(int castle) {
		this.castle = castle;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getBestScore() {
		return bestScore;
	}

	public void setBestScore(int bestScore) {
		this.bestScore = bestScore;
	}
}