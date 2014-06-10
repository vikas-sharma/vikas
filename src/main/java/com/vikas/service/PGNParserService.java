package com.vikas.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Vikas Sharma
 */
public interface PGNParserService {

	List<Integer> getMoveList(int gameNo);

	Map<Integer, String> listGames();

	String getPGN(int gameNo);
}