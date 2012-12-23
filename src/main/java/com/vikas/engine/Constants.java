package com.vikas.engine;

import java.util.ArrayList;
import java.util.Random;

public interface Constants
{
	public static final String sq[] = {
		"a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
		"a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
		"a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
		"a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
		"a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
		"a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
		"a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
		"a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"
	};

	public static final int ROOK   = 1;
	public static final int KNIGHT = 2;
	public static final int BISHOP = 3;
	public static final int QUEEN  = 4;
	public static final int KING   = 5;
	public static final int PAWN   = 6;

	public static final int EMPTY   = 0;
	public static final int BROOK   = 1;
	public static final int BKNIGHT = 2;
	public static final int BBISHOP = 3;
	public static final int BQUEEN  = 4;
	public static final int BKING   = 5;
	public static final int BPAWN   = 6;
	public static final int WROOK   = 7;
	public static final int WKNIGHT = 8;
	public static final int WBISHOP = 9;
	public static final int WQUEEN  = 10;
	public static final int WKING   = 11;
	public static final int WPAWN   = 12;

	public static final int  A8 = 0;
	public static final int  B8 = 1;
	public static final int  C8 = 2;
	public static final int  D8 = 3;
	public static final int  E8 = 4;
	public static final int  F8 = 5;
	public static final int  G8 = 6;
	public static final int  H8 = 7;

	public static final int  A7 = 8;
	public static final int  B7 = 9;
	public static final int  C7 = 10;
	public static final int  D7 = 11;
	public static final int  E7 = 12;
	public static final int  F7 = 13;
	public static final int  G7 = 14;
	public static final int  H7 = 15;

	public static final int  A6 = 16;
	public static final int  B6 = 17;
	public static final int  C6 = 18;
	public static final int  D6 = 19;
	public static final int  E6 = 20;
	public static final int  F6 = 21;
	public static final int  G6 = 22;
	public static final int  H6 = 23;

	public static final int  A5 = 24;
	public static final int  B5 = 25;
	public static final int  C5 = 26;
	public static final int  D5 = 27;
	public static final int  E5 = 28;
	public static final int  F5 = 29;
	public static final int  G5 = 30;
	public static final int  H5 = 31;

	public static final int  A4 = 32;
	public static final int  B4 = 33;
	public static final int  C4 = 34;
	public static final int  D4 = 35;
	public static final int  E4 = 36;
	public static final int  F4 = 37;
	public static final int  G4 = 38;
	public static final int  H4 = 39;

	public static final int  A3 = 40;
	public static final int  B3 = 41;
	public static final int  C3 = 42;
	public static final int  D3 = 43;
	public static final int  E3 = 44;
	public static final int  F3 = 45;
	public static final int  G3 = 46;
	public static final int  H3 = 47;

	public static final int  A2 = 48;
	public static final int  B2 = 49;
	public static final int  C2 = 50;
	public static final int  D2 = 51;
	public static final int  E2 = 52;
	public static final int  F2 = 53;
	public static final int  G2 = 54;
	public static final int  H2 = 55;

	public static final int  A1 = 56;
	public static final int  B1 = 57;
	public static final int  C1 = 58;
	public static final int  D1 = 59;
	public static final int  E1 = 60;
	public static final int  F1 = 61;
	public static final int  G1 = 62;
	public static final int  H1 = 63;

	/*
	 * 1111
	 * 0111
	 * 0011
	 * 1011
	 * 1101
	 * 1100
	 * 1110
	 */
	public static final int[] CASTLE_MASK =
		new int[]{ 7,15,15,15, 3,15,15,11,
			      15,15,15,15,15,15,15,15,
			      15,15,15,15,15,15,15,15,
			      15,15,15,15,15,15,15,15,
			      15,15,15,15,15,15,15,15,
			      15,15,15,15,15,15,15,15,
			      15,15,15,15,15,15,15,15,
			      13,15,15,15,12,15,15,14};

	public static final int rook_shift[]=
	{
		52, 53, 53, 53, 53, 53, 53, 52,
		53, 54, 54, 54, 54, 54, 54, 53,
		53, 54, 54, 54, 54, 54, 54, 53,
		53, 54, 54, 54, 54, 54, 54, 53,
		53, 54, 54, 54, 54, 54, 54, 53,
		53, 54, 54, 54, 54, 54, 54, 53,
		53, 54, 54, 54, 54, 54, 54, 53,
		52, 53, 53, 53, 53, 53, 53, 52
	};

	public static final long rook_magics[]=
	{
		0x0080001020400080L, 0x0040001000200040L, 0x0080081000200080L, 0x0080040800100080L,
		0x0080020400080080L, 0x0080010200040080L, 0x0080008001000200L, 0x0080002040800100L,
		0x0000800020400080L, 0x0000400020005000L, 0x0000801000200080L, 0x0000800800100080L,
		0x0000800400080080L, 0x0000800200040080L, 0x0000800100020080L, 0x0000800040800100L,
		0x0000208000400080L, 0x0000404000201000L, 0x0000808010002000L, 0x0000808008001000L,
		0x0000808004000800L, 0x0000808002000400L, 0x0000010100020004L, 0x0000020000408104L,
		0x0000208080004000L, 0x0000200040005000L, 0x0000100080200080L, 0x0000080080100080L,
		0x0000040080080080L, 0x0000020080040080L, 0x0000010080800200L, 0x0000800080004100L,
		0x0000204000800080L, 0x0000200040401000L, 0x0000100080802000L, 0x0000080080801000L,
		0x0000040080800800L, 0x0000020080800400L, 0x0000020001010004L, 0x0000800040800100L,
		0x0000204000808000L, 0x0000200040008080L, 0x0000100020008080L, 0x0000080010008080L,
		0x0000040008008080L, 0x0000020004008080L, 0x0000010002008080L, 0x0000004081020004L,
		0x0000204000800080L, 0x0000200040008080L, 0x0000100020008080L, 0x0000080010008080L,
		0x0000040008008080L, 0x0000020004008080L, 0x0000800100020080L, 0x0000800041000080L,
		0x00FFFCDDFCED714AL, 0x007FFCDDFCED714AL, 0x003FFFCDFFD88096L, 0x0000040810002101L,
		0x0001000204080011L, 0x0001000204000801L, 0x0001000082000401L, 0x0001FFFAABFAD1A2L
	};

	public static final long rook_mask[]=
	{	
		0x000101010101017EL, 0x000202020202027CL, 0x000404040404047AL, 0x0008080808080876L,
		0x001010101010106EL, 0x002020202020205EL, 0x004040404040403EL, 0x008080808080807EL,
		0x0001010101017E00L, 0x0002020202027C00L, 0x0004040404047A00L, 0x0008080808087600L,
		0x0010101010106E00L, 0x0020202020205E00L, 0x0040404040403E00L, 0x0080808080807E00L,
		0x00010101017E0100L, 0x00020202027C0200L, 0x00040404047A0400L, 0x0008080808760800L,
		0x00101010106E1000L, 0x00202020205E2000L, 0x00404040403E4000L, 0x00808080807E8000L,
		0x000101017E010100L, 0x000202027C020200L, 0x000404047A040400L, 0x0008080876080800L,
		0x001010106E101000L, 0x002020205E202000L, 0x004040403E404000L, 0x008080807E808000L,
		0x0001017E01010100L, 0x0002027C02020200L, 0x0004047A04040400L, 0x0008087608080800L,
		0x0010106E10101000L, 0x0020205E20202000L, 0x0040403E40404000L, 0x0080807E80808000L,
		0x00017E0101010100L, 0x00027C0202020200L, 0x00047A0404040400L, 0x0008760808080800L,
		0x00106E1010101000L, 0x00205E2020202000L, 0x00403E4040404000L, 0x00807E8080808000L,
		0x007E010101010100L, 0x007C020202020200L, 0x007A040404040400L, 0x0076080808080800L,
		0x006E101010101000L, 0x005E202020202000L, 0x003E404040404000L, 0x007E808080808000L,
		0x7E01010101010100L, 0x7C02020202020200L, 0x7A04040404040400L, 0x7608080808080800L,
		0x6E10101010101000L, 0x5E20202020202000L, 0x3E40404040404000L, 0x7E80808080808000L
	};

	public static final int bishop_shift[] =
	{
		58, 59, 59, 59, 59, 59, 59, 58,
		59, 59, 59, 59, 59, 59, 59, 59,
		59, 59, 57, 57, 57, 57, 59, 59,
		59, 59, 57, 55, 55, 57, 59, 59,
		59, 59, 57, 55, 55, 57, 59, 59,
		59, 59, 57, 57, 57, 57, 59, 59,
		59, 59, 59, 59, 59, 59, 59, 59,
		58, 59, 59, 59, 59, 59, 59, 58
	};

	public static final long bishop_magics[]=
	{
		0x0002020202020200L, 0x0002020202020000L, 0x0004010202000000L, 0x0004040080000000L,
		0x0001104000000000L, 0x0000821040000000L, 0x0000410410400000L, 0x0000104104104000L,
		0x0000040404040400L, 0x0000020202020200L, 0x0000040102020000L, 0x0000040400800000L,
		0x0000011040000000L, 0x0000008210400000L, 0x0000004104104000L, 0x0000002082082000L,
		0x0004000808080800L, 0x0002000404040400L, 0x0001000202020200L, 0x0000800802004000L,
		0x0000800400A00000L, 0x0000200100884000L, 0x0000400082082000L, 0x0000200041041000L,
		0x0002080010101000L, 0x0001040008080800L, 0x0000208004010400L, 0x0000404004010200L,
		0x0000840000802000L, 0x0000404002011000L, 0x0000808001041000L, 0x0000404000820800L,
		0x0001041000202000L, 0x0000820800101000L, 0x0000104400080800L, 0x0000020080080080L,
		0x0000404040040100L, 0x0000808100020100L, 0x0001010100020800L, 0x0000808080010400L,
		0x0000820820004000L, 0x0000410410002000L, 0x0000082088001000L, 0x0000002011000800L,
		0x0000080100400400L, 0x0001010101000200L, 0x0002020202000400L, 0x0001010101000200L,
		0x0000410410400000L, 0x0000208208200000L, 0x0000002084100000L, 0x0000000020880000L,
		0x0000001002020000L, 0x0000040408020000L, 0x0004040404040000L, 0x0002020202020000L,
		0x0000104104104000L, 0x0000002082082000L, 0x0000000020841000L, 0x0000000000208800L,
		0x0000000010020200L, 0x0000000404080200L, 0x0000040404040400L, 0x0002020202020200L
	};


	public static final long bishop_mask[] =
	{
		0x0040201008040200L, 0x0000402010080400L, 0x0000004020100A00L, 0x0000000040221400L,
		0x0000000002442800L, 0x0000000204085000L, 0x0000020408102000L, 0x0002040810204000L,
		0x0020100804020000L, 0x0040201008040000L, 0x00004020100A0000L, 0x0000004022140000L,
		0x0000000244280000L, 0x0000020408500000L, 0x0002040810200000L, 0x0004081020400000L,
		0x0010080402000200L, 0x0020100804000400L, 0x004020100A000A00L, 0x0000402214001400L,
		0x0000024428002800L, 0x0002040850005000L, 0x0004081020002000L, 0x0008102040004000L,
		0x0008040200020400L, 0x0010080400040800L, 0x0020100A000A1000L, 0x0040221400142200L,
		0x0002442800284400L, 0x0004085000500800L, 0x0008102000201000L, 0x0010204000402000L,
		0x0004020002040800L, 0x0008040004081000L, 0x00100A000A102000L, 0x0022140014224000L,
		0x0044280028440200L, 0x0008500050080400L, 0x0010200020100800L, 0x0020400040201000L,
		0x0002000204081000L, 0x0004000408102000L, 0x000A000A10204000L, 0x0014001422400000L,
		0x0028002844020000L, 0x0050005008040200L, 0x0020002010080400L, 0x0040004020100800L,
		0x0000020408102000L, 0x0000040810204000L, 0x00000A1020400000L, 0x0000142240000000L,
		0x0000284402000000L, 0x0000500804020000L, 0x0000201008040200L, 0x0000402010080400L,
		0x0002040810204000L, 0x0004081020400000L, 0x000A102040000000L, 0x0014224000000000L,
		0x0028440200000000L, 0x0050080402000000L, 0x0020100804020000L, 0x0040201008040200L
	};
	
	public static final long OPENING_DEPTH = 24;

	public static final long mask[] = Initializer.initMask();
	public static final long king[] = Initializer.initKing();
	public static final long knight[] = Initializer.initKnight();
	public static final long wpawnCaps[] = Initializer.initWPawnCaps();
	public static final long wpawnNonCaps[] = Initializer.initWPawnNonCaps();
	public static final long bpawnCaps[] = Initializer.initBPawnCaps();
	public static final long bpawnNonCaps[] = Initializer.initBPawnNonCaps();
	public static final long rankA2H2 = Initializer.initRankA2H2();
	public static final long rankA7H7 = Initializer.initRankA7H7();

	public static final long rook_indices[][] = Initializer.initRookIndices();
	public static final long bishop_indices[][] = Initializer.initBishopIndices();

	public static final long files[] = Initializer.initFiles();
	
	public static final long zobrist_hash_pieces[][] = Initializer.initZobristForPieces();

	public static final long zobrist_hash_side = Initializer.initZobristForSide();
	public static final long zobrist_hash_castling_rights[] = Initializer.initZobristForCastlingRights();
	public static final long zobrist_hash_en_passant[] = Initializer.initZobristForEnPassant();

	class Initializer {

		static long[][] initRookIndices() {
			
			long ROOK[][] = new long[64][];
			int []squares;
			int size;
			long occ;

			for (int sq = A8; sq <= H1; sq++) {

				size = (int)(1L << (64 - rook_shift[sq]));
				ROOK[sq] = new long[size];

				squares = getSquaresForRook(sq);

				for (int occNum = 0; occNum < size; occNum++) {

					occ = getNextOccupancy(occNum, squares);

					int index = (int)((occ * rook_magics[sq]) >>> rook_shift[sq]);

					if (ROOK[sq][index] == 0) {
						ROOK[sq][index] = generateMovesForRook(sq, occ);
					}
				}
			}

			return ROOK;
		}

		static long[][] initBishopIndices() {
			
			long BISHOP[][] = new long[64][];
			int []squares;
			int size;
			long occ;

			for (int sq = A8; sq <= H1; sq++) {

				size = (int)(1L << (64 - bishop_shift[sq]));
				BISHOP[sq] = new long[size];

				squares = getSquaresForBishop(sq);

				for (int occNum = 0; occNum < size; occNum++) {

					occ = getNextOccupancy(occNum, squares);

					int index = (int)((occ * bishop_magics[sq]) >>> bishop_shift[sq]);

					if (BISHOP[sq][index] == 0) {
						BISHOP[sq][index] = generateMovesForBishop(sq, occ);
					}
				}
			}

			return BISHOP;
		}
		
		static long getNextOccupancy(int occNum, int []squares) {

			long occ = 0;
			for(int counter = 0; counter < squares.length; counter++) {
				if ((occNum & (1 << counter)) != 0) {
					occ |= (1L << squares[counter]);
				}
			}
			return occ;
		}

		static int[] getSquaresForRook(int sq) {

			ArrayList<Integer> squares = new ArrayList<Integer>();

			// north
			for (int toSq = sq - 8; toSq >= 8; toSq -= 8) {
				squares.add(toSq);
			}
			// east
			for (int toSq = sq + 1; (sq % 8) != 7 && (toSq % 8) != 7; toSq++) {
				squares.add(toSq);
			}
			// south
			for (int toSq = sq + 8; toSq <= 55; toSq += 8) {
				squares.add(toSq);
			}
			// west
			for (int toSq = sq - 1; (sq % 8) != 0 && (toSq % 8) != 0; toSq--) {
				squares.add(toSq);
			}

			Object[] temp = squares.toArray();
			int []sqs = new int[temp.length];
			for(int i = 0; i < temp.length; i++) {
				 sqs[i] = ((Integer)temp[i]).intValue();
			}

			return sqs;
		}

		static long generateMovesForRook(int square, long occ) {

			long ret=0;
			long bit;
			long rowbits = ((0xFFL) << (8 * (square / 8)));

			bit = (1L << square);
			do {
				bit <<= 8;
				ret |= bit;
			} while(bit != 0 && (bit & occ) == 0);

			bit = (1L << square);
			do {
				bit >>>= 8;
				ret |= bit;
			} while(bit != 0 && (bit & occ) == 0);

			bit=(1L << square);
			do {
				bit <<= 1;
				if((bit & rowbits) != 0) {
					ret|=bit;
				} else {
					break;
				}
			} while((bit & occ) == 0);

			bit=(1L << square);
			do {
				bit >>>= 1;
				if ((bit & rowbits) != 0) {
					ret|=bit;
				} else {
					break;
				}
			} while((bit & occ) == 0);
			return ret;
		}

		static int[] getSquaresForBishop(int sq) {

			ArrayList<Integer> squares = new ArrayList<Integer>();

			// upper-left
			for (int toSq = sq - 9; toSq >= 9 && sq % 8 != 0 && toSq % 8 != 0; toSq -= 9) {
				squares.add(toSq);
			}
			// upper-right
			for (int toSq = sq - 7; toSq >= 9 && sq % 8 != 7 && toSq % 8 != 7; toSq -= 7) {
				squares.add(toSq);
			}
			// lower-left
			for (int toSq = sq + 7; toSq <= 54 && sq % 8 != 0 && toSq % 8 != 0; toSq += 7) {
				squares.add(toSq);
			}
			// lower-right
			for (int toSq = sq + 9; toSq <= 54 && sq % 8 != 7 && toSq % 8 != 7; toSq += 9) {
				squares.add(toSq);
			}

			Object[] temp = squares.toArray();
			int []sqs = new int[temp.length];
			for(int i = 0; i < temp.length; i++) {
				 sqs[i] = ((Integer)temp[i]).intValue();
			}

			return sqs;
		}

		static long generateMovesForBishop(int square, long occ) {

			long ret = 0;
			long bit;
			long bit2;
			long rowbits = ((0xFFL) << (8 * (square / 8)));

			bit = (1L << square);
			bit2 = bit;
			do
			{
				bit <<= 7;
				bit2 >>>= 1;
				if((bit2 & rowbits) != 0) {
					ret |= bit;
				} else {
					break;
				}
			} while(bit != 0 && (bit & occ) == 0);

			bit = 1L << square;
			bit2 = bit;
			do
			{
				bit <<= 9;
				bit2 <<= 1;
				if((bit2 & rowbits) != 0) {
					ret |= bit;
				} else {
					break;
				}
			} while(bit != 0 && (bit & occ) == 0);

			bit = 1L << square;
			bit2 = bit;
			do
			{
				bit >>>= 7;
				bit2 <<= 1;
				if((bit2 & rowbits) != 0) {
					ret |= bit;
				} else {
					break;
				}
			} while(bit != 0 && (bit & occ) == 0);

			bit = 1L << square;
			bit2 = bit;
			do
			{
				bit >>>= 9;
				bit2 >>>= 1;
				if((bit2 & rowbits) != 0) {
					ret |= bit;
				} else {
					break;
				}
			} while(bit != 0 && (bit & occ) == 0);

			return ret;
		}

		static long[] initFiles() {
			long files[] = new long[64];
			for(int sq = A7; sq <= H7; sq++) {
				for(int sq1 = sq; sq1 <= sq + 40; sq1 += 8) {
					for (int sq2 = sq; sq2 <= sq + 40; sq2 += 8) {
						files[sq1] |= mask[sq2];
					}
				}
			}
			return files;
		}

		static long initRankA2H2() {
			long rankA2H2 = 0;
			for(int sq = A2; sq <= H2; sq++)
				rankA2H2 |= mask[sq];
			return rankA2H2;
		}

		static long initRankA7H7()
		{
			long rankA7H7 = 0;
			for(int sq = A7; sq <= H7; sq++)
				rankA7H7 |= mask[sq];
			return rankA7H7;
		}

		static long[] initMask()
		{
			long []mask = new long[64];
			for(int sq = A8; sq <= H1; sq++)
				mask[sq] = 1L << sq;
			return mask;
		}

		static long[] initKing()
		{
			long []king = new long[64];
			int kingsq[] = {-1, -9, -8, -7, 1, 9, 8, 7};
			for(int sq = A8; sq <= H1; sq++)
			{
				king[sq] = 0;
				for(int i = 0; i < 8; i++)
				{
					if (sq + kingsq[i] >= A8 && sq + kingsq[i] <= H1 &&
						distance(sq, sq + kingsq[i]) <= 1)
						king[sq] |= mask[sq + kingsq[i]];
				}
			}
			return king;
		}

		static long[] initKnight()
		{
			long []knight = new long[64];
			int knightsq[] = {-10, -17, -15, -6, 10, 17, 15, 6};
			for(int sq = A8; sq <= H1; sq++)
			{
				knight[sq] = 0;
				for(int i = 0; i < 8; i++)
				{
					if (sq + knightsq[i] >= A8 && sq + knightsq[i] <= H1
						&& distance(sq, sq + knightsq[i]) <= 2)
						knight[sq] |= mask[sq + knightsq[i]];
				}
			}
			return knight;
		}

		static long[] initWPawnCaps()
		{
			long []wpawnCaps = new long[64];
			int pawnsq[] = {-9, -7};
			for (int sq = A7; sq <= H2; sq++)
			{
				wpawnCaps[sq] = 0;
				for(int i = 0; i < 2; i++)
				{
					if (sq + pawnsq[i] >= A8 && sq + pawnsq[i] <= H3
						&& distance(sq, sq + pawnsq[i]) == 1)
						wpawnCaps[sq] |= mask[sq + pawnsq[i]];
				}
			}
			return wpawnCaps;
		}

		static long[] initWPawnNonCaps()
		{
			long []wpawnNonCaps = new long[64];
			for(int sq = A7; sq <= H2; sq++)
			{
				wpawnNonCaps[sq] = 0;
				wpawnNonCaps[sq] |= mask[sq - 8];
				if (sq >= A2)
					wpawnNonCaps[sq] |= mask[sq - 16];
			}
			return wpawnNonCaps;
		}

		static long[] initBPawnCaps()
		{
			long []bpawnCaps = new long[64];
			int pawnsq[] = {9, 7};
			for (int sq = A7; sq <= H2; sq++)
			{
				bpawnCaps[sq] = 0;
				for(int i = 0; i < 2; i++)
				{
					if (sq + pawnsq[i] >= A6 && sq + pawnsq[i] <= H1
						&& distance(sq, sq + pawnsq[i]) == 1)
						bpawnCaps[sq] |= mask[sq + pawnsq[i]];
				}
			}
			return bpawnCaps;
		}

		static long[] initBPawnNonCaps()
		{
			long []bpawnNonCaps = new long[64];
			for(int sq = A7; sq <= H2; sq++)
			{
				bpawnNonCaps[sq] = 0;
				bpawnNonCaps[sq] |= mask[sq + 8];
				if (sq <= H7)
					bpawnNonCaps[sq] |= mask[sq + 16];
			}
			return bpawnNonCaps;
		}

		static long[][] initZobristForPieces() {

			long pieces [][] = new long[13][64]; // 12 pieces (and 1 empty square) on 64 squares
			
			Random rand = new Random(0x9C81BC1ABD606281L);
			for (int piece = BROOK; piece <= WPAWN; piece++) {
				for (int sq = A8; sq <= H1; sq++) {
					pieces[piece][sq] = rand.nextLong();
				}
			}
			return pieces;
		}

		static long initZobristForSide() {

			long side = 0; // required only for black side

			Random rand = new Random(0x9C81BC1ABD606281L);
			side = rand.nextLong();

			return side;
		}

		static long[] initZobristForCastlingRights() {
			
			long castlingRights [] = new long[16];
			
			Random rand = new Random(0x9C81BC1ABD606281L);
			for (int i = 0; i < castlingRights.length; i++) {
				castlingRights[i] = rand.nextLong();
			}

			return castlingRights;
		}

		static long[] initZobristForEnPassant() {

			long enPassant[] = new long[8]; // 8 files

			Random rand = new Random(0x9C81BC1ABD606281L);
			for (int i = 0; i < enPassant.length; i++) {
				enPassant[i] = rand.nextLong();
			}

			return enPassant;
		}

		static int distance(int x, int y)
		{
			return (Math.abs(x % 8 - y % 8));
		}
	} // end of class initializer

	public static final int WPAWN_SQ_VAL[] = {
					0,  0,  0,  0,  0,  0,  0, 0,
					5, 10, 15, 20, 20, 15, 10, 5,
					3,  6, 12, 12, 12, 12,  6, 3,
					1,  3,  6, 12, 12,  6,  3, 1,
					1,  3,  6, 12, 12,  6,  3, 1,
					1,  3,  5, 10, 10,  5,  3, 1,
					1,  3,  5,  6,  6,  5,  3, 1,
					0,  0,  0,  0,  0,  0,  0, 0 };

	public static final int BPAWN_SQ_VAL[] = {
					0,  0,  0,  0,  0,  0,  0, 0,
					1,  3,  5,  6,  6,  5,  3, 1,
					0,  3,  5, 10, 10,  5,  3, 0,
					0,  3,  6, 12, 12,  6,  3, 0,
					1,  3,  6, 12, 12,  6,  3, 1,
					3,  6, 12, 12, 12, 12,  6, 3,
					5, 10, 15, 20, 20, 15, 10, 5,
					0,  0,  0,  0,  0,  0,  0, 0 };

	public static final int WKNIGHT_SQ_VAL[] = {
					-10, -10, -10, -10, -10, -10, -10, -10,
					-10,   0,   0,   0,   0,   0,   0, -10,
					-10,   0,   5,   5,   5,   5,   0, -10,
					-10,   0,   5,   5,   5,   5,   0, -10,
					-10,   0,   5,   5,   5,   5,   0, -10,
					-10,   0,   5,   5,   5,   5,   0, -10,
					-10,   0,   0,   0,   0,   0,   0, -10,
					-10, -30, -10, -10, -10, -10, -30, -10 };

	public static final int BKNIGHT_SQ_VAL[] = {
					-10, -30, -10, -10, -10, -10, -30, -10,
					-10,   0,   0,   0,   0,   0,   0, -10,
					-10,   0,   5,   5,   5,   5,   0, -10,
					-10,   0,   5,   5,   5,   5,   0, -10,
					-10,   0,   5,   5,   5,   5,   0, -10,
					-10,   0,   5,   5,   5,   5,   0, -10,
					-10,   0,   0,   0,   0,   0,   0, -10,
					-10, -10, -10, -10, -10, -10, -10, -10 };

	public static final int WBISHOP_SQ_VAL[] = {
					-10, -10, -10, -10, -10, -10, -10, -10,
					-10,   0,   0,   0,   0,   0,   0, -10,
					-10,   0,   5,   5,   5,   5,   0, -10,
					-10,   0,   5,  10,  10,   5,   0, -10,
					-10,   0,   5,  10,  10,   5,   0, -10,
					-10,   0,   5,   5,   5,   5,   0, -10,
					-10,   0,   0,   0,   0,   0,   0, -10,
					-10, -10, -20, -10, -10, -20, -10, -10 };

	public static final int BBISHOP_SQ_VAL[] = {
					-10, -10, -20, -10, -10, -20, -10, -10,
					-10,   0,   0,   0,   0,   0,   0, -10,
					-10,   0,   5,   5,   5,   5,   0, -10,
					-10,   0,   5,  10,  10,   5,   0, -10,
					-10,   0,   5,  10,  10,   5,   0, -10,
					-10,   0,   5,   5,   5,   5,   0, -10,
					-10,   0,   0,   0,   0,   0,   0, -10,
					-10, -10, -10, -10, -10, -10, -10, -10 };

	public static final int WKING_SQ_VAL[] = {
					-40, -40, -40, -40, -40, -40, -40, -40,
					-40, -40, -40, -40, -40, -40, -40, -40,
					-40, -40, -40, -40, -40, -40, -40, -40,
					-40, -40, -40, -40, -40, -40, -40, -40,
					-40, -40, -40, -40, -40, -40, -40, -40,
					-40, -40, -40, -40, -40, -40, -40, -40,
					-20, -20, -20, -20, -20, -20, -20, -20,
					  0,  20,  40, -20,   0, -20,  40,  20 };

	public static final int BKING_SQ_VAL[] = {
					  0,  20,  40, -20,   0, -20,  40,  20,
					-20, -20, -20, -20, -20, -20, -20, -20,
					-40, -40, -40, -40, -40, -40, -40, -40,
					-40, -40, -40, -40, -40, -40, -40, -40,
					-40, -40, -40, -40, -40, -40, -40, -40,
					-40, -40, -40, -40, -40, -40, -40, -40,
					-40, -40, -40, -40, -40, -40, -40, -40,
					-40, -40, -40, -40, -40, -40, -40, -40 };
}