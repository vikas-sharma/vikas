package com.vikas.engine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OpeningTable implements Constants {

    private static int NUM_ENTRIES = 262144;
    private static int RANGE = 131;
    private static int MAX_RANGE = 0;
    private static OpeningPosition[] positions;

	static {

		positions = new OpeningPosition[NUM_ENTRIES + RANGE];
	}

	static void generateOpeningBook() {

		BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter("opening/opening_book.dat"));

            for (int index = 0; index < NUM_ENTRIES; index++) {

	            if (positions[index] != null) {
					positions[index].sortMoveGroups();
					int move = positions[index].moveGroups.get(0).move;
					bufferedWriter.write(positions[index].zobristKey + ":" + move);
	            } else {
	            	bufferedWriter.write("0");
	            }
	            if (index != NUM_ENTRIES - 1) {
	            	bufferedWriter.newLine();
	            }
            }
        } catch (FileNotFoundException ex) {
        	ex.printStackTrace();
        } catch (IOException ex) {
        	ex.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
	}

	public static void readOpeningBook() {

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("opening/opening_book.dat");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		try {
			String line = null;
			int index = 0;
			while (( line = br.readLine()) != null) {

				if(!"0".equals(line)) {
					String[] pos = line.split(":");
					positions[index] = new OpeningPosition(Long.parseLong(pos[0]), Integer.parseInt(pos[1]));
				}
				index++;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e) { }
		}
	}

	static int getMove(long zobristKey) {
		int move = 0;

		for (int index = 0; index < NUM_ENTRIES + RANGE; index++) {

			if (positions[index] != null && positions[index].zobristKey == zobristKey) {
				positions[index].sortMoveGroups();
				MoveGroup moveGroup = positions[index].moveGroups.get(0);
				move = moveGroup.move;
				break;
			}
		}
		return move;
	}

	// Quadratic probing is used since linear requires too many
	// probes before empty space is found. The drawback is longer
	// time needed to retrieve entries as they are now spaced out
	static void fillEntry(int firstIndex, int i, long zobristKey, int move)	{

		int index = (firstIndex + (i + i * i) / 2) & (NUM_ENTRIES - 1);
		
		// POSITION 'index' IS FILLED
		if(positions[index] != null) {

			// SEE IF ZOBRIST KEY IS THE SAME
			// IF SAME, THEN JUST ADD MOVE IN
			// IF DIFFERENT, NEED TO TRY NEXT LOCATION
			if(positions[index].zobristKey == zobristKey) {

				positions[index].addMove(move);
				
				// SET MAX_RANGE
				if(i > MAX_RANGE) {
					MAX_RANGE = i;
				}
			} else if(i < RANGE) {
				// TEST NEXT POSITION
				fillEntry(firstIndex, i + 1, zobristKey, move);
			} else {
//				throw new Exception("NO EMPTY SPACES FOUND WITHIN "+RANGE+"  first index: "+first);
			}
		} else { // POSITION 'index' IS EMPTY
			positions[index] = new OpeningPosition(zobristKey, move);

            // SET MAX_RANGE
			if(i > MAX_RANGE) {
				MAX_RANGE = i;
			}
		}
	}
	
	public static int getMoveFromColinDB(BitBoard board) {

		String result = send("FEN", board.getFenString(), "http://www.colin-java.co.uk/JC4/BookServlet");
		// f3d4:15 d1d4:1
		if (result == null || "NONE".equalsIgnoreCase(result.trim())) {
			return 0;
		}
		String []moves = result.trim().split("\\s+");
		List<Integer> possibleMoves = new ArrayList<Integer>();
		
		String []mv = new String[2];
		int move, weight;
		for (int i = 0; i < moves.length; i++) {
			mv = moves[i].split(":");

			move = getMove(board, mv[0]);
			weight = Integer.parseInt(mv[1]);

			for (int j = 0; j < weight; j++) {
				possibleMoves.add(move);
			}
		}

		Random rand = new Random(System.currentTimeMillis());
		int index = rand.nextInt(possibleMoves.size());

		return possibleMoves.get(index);
	}

	public static String send(String name, String mess, String urlString) {
		try {
			String data = URLEncoder.encode(name, "UTF-8") + "=" + URLEncoder.encode(mess, "UTF-8");

			URL url = new URL(urlString);

			URLConnection conn = url.openConnection();

			conn.setDoOutput(true);
			conn.setDoInput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			BufferedReader reader = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();

			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			}
			catch (IOException e) {
			} finally {
				try {
					is.close();
				} catch (IOException e) {
				}
			}

			return sb.toString().trim();
		} catch (Exception e) {
		}
		return null;
	}

	private static int getMove(BitBoard board, String move) {

		int x = move.charAt(0) - 97;     // 0-7
		int y = 8 - (move.charAt(1) - 48); // 0-7
		int fromSq = 8 * y + x;

		x = move.charAt(2) - 97;     // 0-7
		y = 8 - (move.charAt(3) - 48); // 0-7
		int toSq = 8 * y + x;

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
			if ((board.color && p == PAWN && fromSq <= H7) ||
				(!board.color && p == PAWN && fromSq >= A2)) {
				mv = (QUEEN << 18) | (c << 15) | (p << 12) | (toSq << 6) | fromSq;
			} else {
				mv = (c << 15) | (p << 12) | (toSq << 6) | fromSq;
			}
		}
		return mv;
	}

	private static int getPiece(BitBoard board, int fromSq) {

		if ((board.whitebishops & mask[fromSq]) != 0 || (board.blackbishops & mask[fromSq]) != 0) {
			return BISHOP;
		}
		if ((board.whiteking & mask[fromSq]) != 0 || (board.blackking & mask[fromSq]) != 0) {
			return KING;
		}
		if ((board.whiteknights & mask[fromSq]) != 0 || (board.blackknights & mask[fromSq]) != 0) {
			return KNIGHT;
		}
		if ((board.whitepawns & mask[fromSq]) != 0 || (board.blackpawns & mask[fromSq]) != 0) {
			return PAWN;
		}
		if ((board.whitequeens & mask[fromSq]) != 0 || (board.blackqueens & mask[fromSq]) != 0) {
			return QUEEN;
		}
		if ((board.whiterooks & mask[fromSq]) != 0 || (board.blackrooks & mask[fromSq]) != 0) {
			return ROOK;
		}

		return EMPTY;
	}

}