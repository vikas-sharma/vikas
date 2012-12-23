package com.vikas.engine;

public final class Utility implements Constants
{
	static final long DEBRUIJN64 = 0x3f79d71b4cb0a89L;

	static final int []bitmap = {
				0, 1,48, 2,57,49,28, 3,
				61,58,50,42,38,29,17, 4,
				62,55,59,36,53,51,43,22,
				45,39,33,30,24,18,12, 5,
				63,47,56,27,60,41,37,16,
				54,35,52,21,44,32,23,11,
				46,26,40,15,34,20,31,10,
				25,14,19, 9,13, 8, 7, 6};

	static int getIndex(final long square)
	{
		return bitmap[(int) (((square & -square) * DEBRUIJN64) >>> 58)];
	}

	static long getSquare(final long bb, final int squareNum)
	{
		return (bb & (mask[A8] << squareNum));
	}
	
	static int bitCount(long i)
	{
         // HD, Figure 5-14
		 i = i - ((i >>> 1) & 0x5555555555555555L);
		 i = (i & 0x3333333333333333L) + ((i >>> 2) & 0x3333333333333333L);
		 i = (i + (i >>> 4)) & 0x0f0f0f0f0f0f0f0fL;
		 i = i + (i >>> 8);
		 i = i + (i >>> 16);
		 i = i + (i >>> 32);

		 return (int) i & 0x7f;
	}

}