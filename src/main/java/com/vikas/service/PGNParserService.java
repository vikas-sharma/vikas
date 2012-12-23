package com.vikas.service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 
 * @author Vikas Sharma
 */
public interface PGNParserService {

	List<Integer> getMoveList(int gameNo);

	LinkedHashMap<Integer, String> listGames();

	String getPGN(int gameNo);
}