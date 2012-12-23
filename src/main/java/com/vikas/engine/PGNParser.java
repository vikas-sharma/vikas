package com.vikas.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PGNParser implements Constants {

	public static ArrayList<String> parseMoveList(BitBoard board, int[] moves, int noMoves) {

		ArrayList<String> pgnMoves = new ArrayList<String>();
		String pgnMove = "";

		for(int i = 0; i < noMoves; i++) {

			int move = moves[i];

			int board_castle = board.getCastle();
			int board_ep = board.getEnpassantSquare();
			int board_score = board.getScore();
			long zobrist_key = board.zobristKey;

			boolean legal = Engine.makeMove(board, move);

			boolean checkMove = false;
			if ((board.color && board.isWhiteInCheck(Utility.getIndex(board.whiteking))) ||
					(!board.color && board.isBlackInCheck(Utility.getIndex(board.blackking)))) {

				checkMove = true;
			}

			board.setCastle(board_castle);
			board.setEnpassantSquare(board_ep);
			board.setScore(board_score);
			board.zobristKey = zobrist_key;
			Engine.undoMove(board, move);

			if (!legal) {
				pgnMoves.add("");
				continue;
			}

			int promotedPiece = (move >>> 18) & 7;
			int piece = (move >>> 12) & 7;
			int to_sq = (move >>> 6)  & 63;
			int from_sq = move & 63;

			if (piece == KING && (to_sq - from_sq) == 2) {
				pgnMove = "O-O";
			} else if (piece == KING && (from_sq - to_sq) == 2) {
				pgnMove = "O-O-O";
			} else if (piece == PAWN) {

				if ((board.allpieces & mask[to_sq]) == 0 && to_sq != board.enpassantSquare) {
					pgnMove = sq[to_sq];
				} else {
					pgnMove = sq[from_sq].charAt(0) + "x" + sq[to_sq];
				}
				if (promotedPiece != EMPTY) {
					pgnMove += "=" + getPiece(promotedPiece);
				}
			} else if (piece == KING) {

				if ((board.allpieces & mask[to_sq]) == 0) {
					pgnMove = "K" + sq[to_sq];
				} else {
					pgnMove = "Kx" + sq[to_sq];
				}
			} else {

				pgnMove = "" + getPiece(piece);
				
				boolean fileAmbiguity = false;
				boolean rankAmbiguity = false;

				for (int j = 0; j < noMoves; j++) {
	
					int mv = moves[j];

					int p = (mv >>> 12) & 7;
					int to = (mv >>> 6)  & 63;
					int from = mv & 63;

					if (p == piece && to == to_sq) {

						if (sq[from].charAt(0) != sq[from_sq].charAt(0) && isLegal(board, mv)) {
							fileAmbiguity = true;
						} else if (sq[from].charAt(1) != sq[from_sq].charAt(1) && isLegal(board, mv)) {
							rankAmbiguity = true;
						}
					}
				}

				if (fileAmbiguity) {
					pgnMove += sq[from_sq].charAt(0);
				}
				if (rankAmbiguity) {
					pgnMove += sq[from_sq].charAt(1);
				}

				if ((board.allpieces & mask[to_sq]) != 0) {
					pgnMove += "x";
				}

				pgnMove += sq[to_sq];
			}

			if (checkMove) {
				pgnMove += "+";
			}

			pgnMoves.add(pgnMove);
		}
		return pgnMoves;
	}

	public static String getPGN(BitBoard board, int move, int[] moves, int noMoves) {

		String pgnMove = "";

		int castle = board.getCastle();
		int ep = board.getEnpassantSquare();
		int score = board.getScore();
		long zobristKey = board.zobristKey;

		boolean legal = Engine.makeMove(board, move);

		boolean checkMove = false;
		if ((board.color && board.isWhiteInCheck(Utility.getIndex(board.whiteking))) ||
				(!board.color && board.isBlackInCheck(Utility.getIndex(board.blackking)))) {

			checkMove = true;
		}

		board.setCastle(castle);
		board.setEnpassantSquare(ep);
		board.setScore(score);
		board.zobristKey = zobristKey;
		Engine.undoMove(board, move);

		if (!legal) {
			return null;
		}

		int promotedPiece = (move >>> 18) & 7;
		int piece = (move >>> 12) & 7;
		int toSq = (move >>> 6)  & 63;
		int fromSq = move & 63;
	
		if (piece == KING && (toSq - fromSq) == 2) {
			pgnMove = "O-O";
		} else if (piece == KING && (fromSq - toSq) == 2) {
			pgnMove = "O-O-O";
		} else if (piece == PAWN) {
	
			if ((board.allpieces & mask[toSq]) == 0 && toSq != board.enpassantSquare) {
				pgnMove = sq[toSq];
			} else {
				pgnMove = sq[fromSq].charAt(0) + "x" + sq[toSq];
			}
			if (promotedPiece != EMPTY) {
				pgnMove += "=" + getPiece(promotedPiece);
			}
		} else if (piece == KING) {
	
			if ((board.allpieces & mask[toSq]) == 0) {
				pgnMove = "K" + sq[toSq];
			} else {
				pgnMove = "Kx" + sq[toSq];
			}
		} else {
	
			pgnMove = "" + getPiece(piece);
			
			boolean fileAmbiguity = false;
			boolean rankAmbiguity = false;
	
			for (int j = 0; j < noMoves; j++) {
	
				int mv = moves[j];
	
				int p = (mv >>> 12) & 7;
				int to = (mv >>> 6)  & 63;
				int from = mv & 63;
	
				if (p == piece && to == toSq) {
	
					if (sq[from].charAt(0) != sq[fromSq].charAt(0) && isLegal(board, mv)) {
						fileAmbiguity = true;
					} else if (sq[from].charAt(1) != sq[fromSq].charAt(1) && isLegal(board, mv)) {
						rankAmbiguity = true;
					}
				}
			}

			if (fileAmbiguity) {
				pgnMove += sq[fromSq].charAt(0);
			}
			if (rankAmbiguity) {
				pgnMove += sq[fromSq].charAt(1);
			}

			if ((board.allpieces & mask[toSq]) != 0) {
				pgnMove += "x";
			}

			pgnMove += sq[toSq];
		}

		if (checkMove) {
			pgnMove += "+";
		}

		return pgnMove;
	}

	private static boolean isLegal(BitBoard board, int move) {

		int board_castle = board.getCastle();
		int board_ep = board.getEnpassantSquare();
		int board_score = board.getScore();
		long zobrist_key = board.zobristKey;

		boolean legal = Engine.makeMove(board, move);

		board.setCastle(board_castle);
		board.setEnpassantSquare(board_ep);
		board.setScore(board_score);
		board.zobristKey = zobrist_key;
		Engine.undoMove(board, move);
		
		return legal;
	}
	
	static void readPGN() {

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("../opening/gm-2600-m35.pgn");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		try {
			String singleGameContents = new String();
			String line = null; //not declared within while loop
			/*
			 * readLine is a bit quirky :
			 * it returns the content of a line MINUS the newline.
			 * it returns null only for the END of the stream.
			 * it returns an empty String if two newlines appear in a row.
			 */
			while (( line = br.readLine()) != null) {

				if("".equals(line)) {
					continue;
				}
				
				if (line.charAt(0)=='[') {
					if (!"".equals(singleGameContents)) {
						parsePGNList(singleGameContents);
					}
					singleGameContents = "";
					continue;
				}
				singleGameContents += line + "\n" ;
			}

			parsePGNList(singleGameContents);
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			// IF PLAYER CANCELS, BUFFEREDREADER IS NULL
			// AND WE GET A NULLPOINTER EXCEPTION
		} finally {
			try {
				br.close();
			} catch (Exception e) { }
		}
	}

	private static void parsePGNList(String pgnString) {

		// REMOVE ALL NEWLINES/CARRIAGE RETURNS/TABS
		String RE = "[\\n\\r\\t]";
		Pattern pattern = Pattern.compile(RE);
		Matcher matcher = pattern.matcher(pgnString);
        pgnString = matcher.replaceAll(" ");

		// REMOVE ALL NESTED BRACKETS
        pgnString = removeNestedBrackets(pgnString);

        // REMOVE ALL UNWANTED TEXT
        RE = "(\\d+\\.)|(\\$\\d+)|(\\.+)|(!+)|(\\?+)|(1/2-1/2)|(1/2)|(0-1)|(1-0)|(\\*)";

		pattern = Pattern.compile(RE);
		matcher = pattern.matcher(pgnString);
		pgnString = matcher.replaceAll("");

		// THESE MOVES MAY CONTAIN + OR ++ OR #
		String[] algebraicMoves = pgnString.trim().split("\\s+");

		int len = algebraicMoves.length;

		BitBoard board = new BitBoard();

		// WE REMOVE THE + OR ++ OR # BEFORE WE TRANSLATE
		String checkOrMateSymbolRemovedMove;
		for(int lp = 0; lp < len && lp < OPENING_DEPTH; lp++) {

			RE = "(\\+)|(\\#)";
			pattern = Pattern.compile(RE);
			matcher = pattern.matcher(algebraicMoves[lp]);
			checkOrMateSymbolRemovedMove = matcher.replaceAll("");

			int move = getMove(board, checkOrMateSymbolRemovedMove);
			if (move != 0) {
				int NUM_ENTRIES = 262144;
				int index = (int)(board.zobristKey & (NUM_ENTRIES - 1));
				OpeningTable.fillEntry(index, 0, board.zobristKey, move);
				Engine.makeMove(board, move);
			}
		}	
	}

	private static String removeNestedBrackets(String string) {
		int numLefts = 0;
		int numRights = 0;
		
		String result = "";
		char c;
		char bracketType = 'n';    // NONE
		char oppBracketType = 'n'; // NONE
		
		int len = string.length();
		for(int p=0;p<len;p++)
		{
			c = string.charAt(p);
			if(bracketType == 'n')
			{
				switch(c)
				{
				case '(': bracketType = '('; oppBracketType = ')'; numLefts = 1; break;
				case '[': bracketType = '['; oppBracketType = ']'; numLefts = 1; break;
				case '{': bracketType = '{'; oppBracketType = '}'; numLefts = 1; break;
				default : result += c;
				}
			}
			else
			{
				if(c == bracketType) numLefts++;
				
				else if(c == oppBracketType)
				{
					numRights++;

					if(numLefts == numRights)
					{
						numLefts = 0;
						numRights = 0;
						bracketType = 'n';
						oppBracketType = 'n';
					}
				}
			}
		}
		
		return result;
	}

	private static int getMove(BitBoard board, String pgn) {
		
		if (pgn == null) {
			return 0;
		}

		int[] moves = new int[256];
		int noMoves = MoveGenerator.generateMoveList(board, moves);
		ArrayList<String> pgnMoves = parseMoveList(board, moves, noMoves);
		int index = pgnMoves.indexOf(pgn);
		if (index == -1) {
			return 0;
		}
		return moves[index];
	}

    private static char getPiece(int piece) {
    	
		if (piece == KING) {
			return 'K';
		}
		if (piece == QUEEN) {
			return 'Q';
		}
		if (piece == ROOK) {
			return 'R';
		}
		if (piece == BISHOP) {
			return 'B';
		}
		if (piece == KNIGHT) {
			return 'N';
		}
		return ' '; // empty for pawn
    }
}