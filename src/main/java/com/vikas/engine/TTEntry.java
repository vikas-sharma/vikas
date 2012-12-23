package com.vikas.engine;

public class TTEntry {
	
	static int EXACT_VALUE = 1;
	static int ALPHA_VALUE = 2;
	static int BETA_VALUE = 3;
	
//	The Zobrist or BCH key of the current position, so we can make sure
//	we are comparing to the same position
	long zobristKey; 

//	The type of node as determined by the Search, either
//	an Exact Score of a PV-Node 
//	an Upper Bound of an All-Node 
//	a Lower Bound of a Cut-Node 
	int type;

//	The score, either an Exact Score or a Bound of that node,
//	depending on the Node Type 
	int value;

//	The depth (draft) to which it was previously searched.
//	(The depth after probing must be adequate, or the score is not useful.)
	int depth;

//	The best move or refutation move found by search. This is used for move ordering -
//	the hash move should be tried first later if a hit occurs.
	int bestMove;

//	The age of the entry (This is used to determine when to overwrite entries from search
//	of previous positions during the game of chess).
	long age;
	
	int move;
}