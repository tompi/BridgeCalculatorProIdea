package com.brisco.common.Score;

import com.brisco.common.Game.Board;
import com.brisco.common.Game.Contract;

public final class IMPCalculator {
	public static int GetNorthSouthIMP(int northPointsTableA,
			int northPointsTableB) {
		int diff = northPointsTableA - northPointsTableB;
		if (diff == 0) {
			return 0;
		}
		int absDiff = Math.abs(diff);
		int ix = 0;
		// Find first entry in table that is smaller or equal to diff
		while (ix < IMP.length) {
			if (absDiff <= IMP[ix]) {
				break;
			}
			ix++;
		}
		// Correct sign:
		if (diff < 0) {
			ix = ix * -1;
		}
		return ix;
	}

	public static int GetNorthSouthIMP(Board board, Contract contractTableA,
			Contract contractTableB) {
		return GetNorthSouthIMP(
				Calculator.GetNorthSouthPoints(contractTableA, board),
				Calculator.GetNorthSouthPoints(contractTableB, board));
	}

	public static int GetNorthSouthIMP(int boardNumber,
			Contract contractTableA, Contract contractTableB) {
		return GetNorthSouthIMP(
				Calculator.GetNorthSouthPoints(contractTableA, boardNumber),
				Calculator.GetNorthSouthPoints(contractTableB, boardNumber));
	}

	private static final short[] IMP = { 10, 40, 80, 120, 160, 210, 260, 310,
			360, 420, 490, 590, 740, 890, 1090, 1290, 1490, 1740, 1990, 2040,
			2490, 2990, 3490, 3990 };

}
