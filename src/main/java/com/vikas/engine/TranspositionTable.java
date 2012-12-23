package com.vikas.engine;

public class TranspositionTable {

	static long[] hash_table;
	
	static  {
		hash_table = new long[2 * ((int)Math.pow(2, 20) + 4)];
		for (int i = 0; i < hash_table.length; i++) {
			hash_table[i] = 0L;
		}
	}

	static long getEntry(long zobristKey) {

		int index = (int)(zobristKey & 1048575L); // 2 ^ 20 - 1 = 1048575
		for (int i = 0; i < 4; i++) {

			if (hash_table[2 * (index + i)] == zobristKey ) {

				return hash_table[2 * (index + i) + 1];
			}
		}
		return 0;
	}

/*
	move - 21 bits
	value - 15 bits
	depth - 6 bits
	type - 2 bits

	(21 bits)(15 bits)(7 bits)(2 bits)
	*/

	static void storeEntry(long zobristKey, int value, int type, int depth, int move) {

		int index = (int)(zobristKey & 1048575L); // 2 ^ 20 - 1 = 1048575
		for (int i = 0; i < 4; i++) {

			if (depth > ((hash_table[2 * (index + i) + 1] >>> 2) & 63L)) {

				hash_table[2 * (index + i)] = zobristKey;
				hash_table[2 * (index + i) + 1] = (long)move << 23 | (long)(value + 16000) << 8 | (long)depth << 2 | (long)type;

				return;
			}
		}
	}
}