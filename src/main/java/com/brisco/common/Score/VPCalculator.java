package com.brisco.common.Score;

public final class VPCalculator {
	public static VPPoints GetWBFVP(int imps, int boards) {
		if (imps < 0) {
			imps = imps * -1;
		}
		short imp_table_index = GetImpTableIndex(boards);
		int i = 0;
		while (i < 15) {
			if (imps <= WBF_IMP[i][imp_table_index]) {
				break;
			}
			i++;
		}
		return WBF_POINTS[i];
	}

	private static short GetImpTableIndex(int boards) {
		short i = 1;
		while (i < 12) {
			short maxBoards = WBF_BOARDS[i];
			if (boards < maxBoards) {
				break;
			}
			i++;
		}
		return (short) (i - 1);
	}

	private static final short[] WBF_BOARDS = { 8, 10, 12, 14, 16, 20, 24, 28,
			32, 36, 40, 48 };

	private static final VPPoints[] WBF_POINTS = { new VPPoints(15, 15),
			new VPPoints(16, 14), new VPPoints(17, 13), new VPPoints(18, 12),
			new VPPoints(19, 11), new VPPoints(20, 10), new VPPoints(21, 9),
			new VPPoints(22, 8), new VPPoints(23, 7), new VPPoints(24, 6),
			new VPPoints(25, 5), new VPPoints(25, 4), new VPPoints(25, 3),
			new VPPoints(25, 2), new VPPoints(25, 1), new VPPoints(25, 0) };

	// From WBF 2010
	private static final short[][] WBF_IMP = {
			{ 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 3, 4 },
			{ 5, 6, 6, 7, 7, 8, 9, 10, 10, 11, 11, 12 },
			{ 8, 9, 9, 10, 11, 12, 14, 15, 16, 17, 18, 20 },
			{ 11, 12, 12, 14, 15, 16, 19, 20, 22, 23, 25, 28 },
			{ 14, 15, 16, 18, 19, 21, 24, 25, 28, 29, 32, 36 },
			{ 17, 18, 20, 22, 23, 26, 29, 31, 34, 36, 39, 44 },
			{ 20, 21, 24, 26, 27, 31, 34, 37, 40, 43, 46, 52 },
			{ 23, 25, 28, 30, 31, 36, 39, 43, 46, 50, 53, 60 },
			{ 26, 29, 32, 34, 36, 41, 45, 49, 52, 57, 60, 68 },
			{ 29, 33, 36, 38, 41, 47, 51, 55, 58, 64, 68, 76 },
			{ 33, 37, 40, 43, 46, 53, 57, 61, 65, 71, 76, 84 },
			{ 37, 41, 45, 48, 52, 59, 64, 68, 73, 79, 84, 93 },
			{ 41, 45, 50, 54, 58, 65, 71, 76, 82, 88, 93, 102 },
			{ 45, 50, 55, 60, 64, 72, 79, 85, 91, 97, 102, 112 },
			{ 51, 55, 61, 66, 71, 79, 87, 94, 100, 106, 112, 123 } };
}
