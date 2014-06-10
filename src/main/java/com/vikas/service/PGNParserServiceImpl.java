package com.vikas.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
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
public class PGNParserServiceImpl implements PGNParserService {

	@Value("${pgn.file}")
	private String pgnFile;

	@Override
	public List<Integer> getMoveList(int gameNo) {

		String pgn = readPGN(pgnFile, gameNo);

		if (pgn == null) {
			return null;
		}

		List<Integer> moveList = parsePGNList(pgn);
		return moveList;

	}

	private String readPGN(String pgnFile, int gameNo) {

		String gameContents = new String();

		InputStream is = null;
		BufferedReader br = null;

		try {
			UrlResource ur = new UrlResource(pgnFile);
			is = ur.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));

			String line = null; // not declared within while loop
			/*
			 * readLine is a bit quirky : it returns the content of a line MINUS
			 * the newline. it returns null only for the END of the stream. it
			 * returns an empty String if two newlines appear in a row.
			 */
			int gameCtr = 0;
			boolean incFlag = true;
			while ((line = br.readLine()) != null) {

				if ("".equals(line)) {
					continue;
				}

				if (line.charAt(0) == '[') {
					if (gameNo == gameCtr && !"".equals(gameContents)) {
						break;
					}
					if (incFlag) {
						gameCtr++;
						incFlag = false;
					}
					continue;
				} else {
					incFlag = true;
				}
				if (gameNo == gameCtr) {
					gameContents += line + "\n";
				}
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			// IF PLAYER CANCELS, BUFFEREDREADER IS NULL
			// AND WE GET A NULLPOINTER EXCEPTION
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
			}
		}
		return gameContents;
	}

	private List<Integer> parsePGNList(String pgnString) {

		List<Integer> moveList = new ArrayList<Integer>();

		// REMOVE ALL NEWLINES/CARRIAGE RETURNS/TABS
		String remove = "[\\n\\r\\t]";
		Pattern pattern = Pattern.compile(remove);
		Matcher matcher = pattern.matcher(pgnString);
		pgnString = matcher.replaceAll(" ");

		// REMOVE ALL NESTED BRACKETS
		pgnString = removeNestedBrackets(pgnString);

		// REMOVE ALL UNWANTED TEXT
		remove = "(\\d+\\.)|(\\$\\d+)|(\\.+)|(!+)|(\\?+)|(1/2-1/2)|(1/2)|(0-1)|(1-0)|(\\*)";

		pattern = Pattern.compile(remove);
		matcher = pattern.matcher(pgnString);
		pgnString = matcher.replaceAll("");

		// THESE MOVES MAY CONTAIN + OR ++ OR #
		String[] algebraicMoves = pgnString.trim().split("\\s+");

		int len = algebraicMoves.length;

		BitBoard board = new BitBoard();

		// WE REMOVE THE + OR ++ OR # BEFORE WE TRANSLATE
		String mateSymbolRemovedMove;
		for (int lp = 0; lp < len; lp++) {

			remove = "(\\#)";
			pattern = Pattern.compile(remove);
			matcher = pattern.matcher(algebraicMoves[lp]);
			mateSymbolRemovedMove = matcher.replaceAll("");

			int move = getMove(board, mateSymbolRemovedMove);
			if (move != 0) {
				moveList.add(move);
				Engine.makeMove(board, move);
			} else {
				return moveList;
			}
		}

		return moveList;
	}

	private static String removeNestedBrackets(String string) {
		int numLefts = 0;
		int numRights = 0;

		String result = "";
		char c;
		char bracketType = 'n'; // NONE
		char oppBracketType = 'n'; // NONE

		int len = string.length();
		for (int p = 0; p < len; p++) {
			c = string.charAt(p);
			if (bracketType == 'n') {
				switch (c) {
				case '(':
					bracketType = '(';
					oppBracketType = ')';
					numLefts = 1;
					break;
				case '[':
					bracketType = '[';
					oppBracketType = ']';
					numLefts = 1;
					break;
				case '{':
					bracketType = '{';
					oppBracketType = '}';
					numLefts = 1;
					break;
				default:
					result += c;
				}
			} else {
				if (c == bracketType)
					numLefts++;

				else if (c == oppBracketType) {
					numRights++;

					if (numLefts == numRights) {
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

	private int getMove(BitBoard board, String pgn) {

		if (pgn == null) {
			return 0;
		}

		int[] moves = new int[256];
		int noMoves = MoveGenerator.generateMoveList(board, moves);
		ArrayList<String> pgnMoves = PGNParser.parseMoveList(board, moves,
				noMoves);
		int index = pgnMoves.indexOf(pgn);
		if (index == -1) {
			return 0;
		}
		return moves[index];
	}

	public Map<Integer, String> listGames() {

		Map<Integer, String> gameList = new LinkedHashMap<Integer, String>();

		InputStream is = null;
		BufferedReader br = null;

		try {
			String gameDesc = new String();

			UrlResource ur = new UrlResource(pgnFile);
			is = ur.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));

			int gameNo = 1;

			String line = null; // not declared within while loop
			while ((line = br.readLine()) != null) {

				if (line.startsWith("[White ")) {

					int beginIndex = line.indexOf('"') + 1;
					int endIndex = line.lastIndexOf('"');
					gameDesc = line.substring(beginIndex, endIndex);

				} else if (line.startsWith("[Black ")) {

					int beginIndex = line.indexOf('"') + 1;
					int endIndex = line.lastIndexOf('"');
					gameDesc += " vs " + line.substring(beginIndex, endIndex);

					gameList.put(gameNo, gameDesc);
					gameNo++;
				}
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
			}
		}
		return gameList;
	}

	@Override
	public String getPGN(int gameNo) {

		String gameContents = new String();
		String event = new String();
		String whitePlayer = new String();
		String blackPlayer = new String();
		String date = new String();
		String eco = new String();
		String whiteRating = new String();
		String blackRating = new String();

		InputStream is = null;
		BufferedReader br = null;

		try {
			UrlResource ur = new UrlResource(pgnFile);
			is = ur.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));

			int beginIndex, endIndex;

			String line = null; // not declared within while loop

			int gameCtr = 0;
			boolean incFlag = true;
			while ((line = br.readLine()) != null) {

				if ("".equals(line)) {
					continue;
				}

				if (line.charAt(0) == '[') {

					if (gameNo == gameCtr && !"".equals(gameContents)) {
						break;
					}
					if (incFlag) {
						gameCtr++;
						incFlag = false;
					}

					if (gameNo == gameCtr) {

						beginIndex = line.indexOf('"') + 1;
						endIndex = line.lastIndexOf('"');
						if (line.startsWith("[Event ")) {
							event = line.substring(beginIndex, endIndex);
						} else if (line.startsWith("[Date ")) {
							date = line.substring(beginIndex, endIndex);
						} else if (line.startsWith("[White ")) {
							whitePlayer = line.substring(beginIndex, endIndex);
						} else if (line.startsWith("[Black ")) {
							blackPlayer = line.substring(beginIndex, endIndex);
						} else if (line.startsWith("[ECO ")) {
							eco = line.substring(beginIndex, endIndex);
						} else if (line.startsWith("[WhiteElo ")) {
							whiteRating = line.substring(beginIndex, endIndex);
						} else if (line.startsWith("[BlackElo ")) {
							blackRating = line.substring(beginIndex, endIndex);
						}
					}

					continue;
				} else {
					incFlag = true;
				}
				if (gameNo == gameCtr) {
					gameContents += line + " ";
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			// IF PLAYER CANCELS, BUFFEREDREADER IS NULL
			// AND WE GET A NULLPOINTER EXCEPTION
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
			}
		}

		gameContents = addLink(gameContents);

		gameContents = gameContents
				.replaceAll("\\{", "")
				.replaceAll("\\}", "")
				.replaceAll(" \\(", " \\[")
				.replaceAll("\\) ", "\\] ")
				.replaceAll("\\$142", "")
				// better is
				.replaceAll("\\$11", "=").replaceAll("\\$13", "&infin;")
				.replaceAll("\\$14", "+=").replaceAll("\\$15", "=+")
				.replaceAll("\\$16", "&plusmn;").replaceAll("\\$17", "&#8723;")
				.replaceAll("\\$18", "+ -").replaceAll("\\$19", "- +")
				.replaceAll("\\$20", "+--").replaceAll("\\$21", "--+")
				.replaceAll("\\$36", "&rarr;").replaceAll("\\$37", "&rarr;")
				.replaceAll("\\$40", "&uarr;").replaceAll("\\$41", "&uarr;")
				.replaceAll("\\$1", "!").replaceAll("\\$2", "?")
				.replaceAll("\\$3", "!!").replaceAll("\\$4", "??")
				.replaceAll("\\$5", "!?").replaceAll("\\$6", "?!");

		String pgnString = new String();
		if (whiteRating != null && !"".equals(whiteRating.trim())) {
			pgnString += whitePlayer + "(" + whiteRating + ") vs ";
		} else {
			pgnString += whitePlayer + " vs ";
		}
		if (blackRating != null && !"".equals(blackRating.trim())) {
			pgnString += blackPlayer + "(" + blackRating + ") [" + eco + "]";
		} else {
			pgnString += blackPlayer + " [" + eco + "]";
		}
		pgnString += "<BR>" + event + ", " + date;
		pgnString += "<p>" + gameContents;

		return pgnString;
	}

	private String addLink(String gameContents) {

		String result = new String();

		String pgn;
		String regex;
		Matcher matcher, bracketMatcher;

		char bracketType1 = '(';
		char oppBracketType1 = ')';
		int bracketType1Ctr = 0;

		char bracketType2 = '{';
		char oppBracketType2 = '}';
		int bracketType2Ctr = 0;

		String[] pgnArray = gameContents.split("\\s");
		int moveNo = 0;

		for (int x = 0; x < pgnArray.length; x++) {

			pgn = pgnArray[x];

			regex = "(\\d+\\.)|(\\$\\d+)|(\\.+)|(!+)|(\\?+)|(1/2-1/2)|(1/2)|(0-1)|(1-0)|(\\*)";

			matcher = Pattern.compile(regex).matcher(pgn);

			regex = "[\\(\\)\\{\\}]";
			bracketMatcher = Pattern.compile(regex).matcher(pgn);

			if (matcher.find() && !bracketMatcher.find()) {
				result += pgn + " ";
			} else {
				boolean flag = false;

				regex = "\\" + bracketType1;
				matcher = Pattern.compile(regex).matcher(pgn);
				while (matcher.find()) {
					bracketType1Ctr++;
					flag = true;
				}
				regex = "\\" + oppBracketType1;
				matcher = Pattern.compile(regex).matcher(pgn);
				while (matcher.find()) {
					bracketType1Ctr--;
					flag = true;
				}

				regex = "\\" + bracketType2;
				matcher = Pattern.compile(regex).matcher(pgn);
				while (matcher.find()) {
					bracketType2Ctr++;
					flag = true;
				}
				regex = "\\" + oppBracketType2;
				matcher = Pattern.compile(regex).matcher(pgn);
				while (matcher.find()) {
					bracketType2Ctr--;
					flag = true;
				}

				if (flag || bracketType1Ctr != 0 || bracketType2Ctr != 0) {
					result += pgn + " ";
				} else {
					result += "<B><a href='javascript:goToPosition(" + moveNo
							+ ")' id='pgn_" + moveNo + "' class='pgn-string'>"
							+ pgn + "</a></B> ";
					moveNo++;
				}
			}
		}

		return result;
	}
}