package com.brisco.BridgeCalculatorPro.Rubber;

import java.util.ArrayList;

public class RubberDirectionResult {
	public ArrayList<RubberScore> AboveLine;
	public ArrayList<RubberScore> BelowLine1;
	public ArrayList<RubberScore> BelowLine2;
	public ArrayList<RubberScore> BelowLine3;
	public int Score;

	public RubberDirectionResult() {
		AboveLine = new ArrayList<RubberScore>();
		BelowLine1 = new ArrayList<RubberScore>();
		BelowLine2 = new ArrayList<RubberScore>();
		BelowLine3 = new ArrayList<RubberScore>();
	}

	public boolean IsVulnerable() {
		return GetSum(BelowLine1) >= 100 || GetSum(BelowLine2) >= 100;
	}

	public boolean AddScore(int aboveLine, int belowLine, int currentGame,
			int contractNumber) {
		boolean gameWon = false;
		if (aboveLine > 0) {
			AboveLine.add(new RubberScore(aboveLine, contractNumber));
		}

		if (belowLine > 0) {
			if (currentGame == 1) {
				BelowLine1.add(new RubberScore(belowLine, contractNumber));
				if (GetSum(BelowLine1) >= 100)
					gameWon = true;
			} else if (currentGame == 2) {
				BelowLine2.add(new RubberScore(belowLine, contractNumber));
				if (GetSum(BelowLine2) >= 100) {
					gameWon = true;
					if (GetGamesWon() > 1) {
						// Two consecutive games makes for a 700 bonus
						AboveLine.add(new RubberScore(700, -1));
						Score += 700;
					}
				}
			} else {
				BelowLine3.add(new RubberScore(belowLine, contractNumber));
				if (GetSum(BelowLine3) >= 100) {
					gameWon = true;
					// Two games to one makes for a 500 bonus
					AboveLine.add(new RubberScore(500, -1));
					Score += 500;
				}
			}
		}

		Score += aboveLine + belowLine;
		return gameWon;
	}

	public boolean WinnerOfRubber() {
		return GetGamesWon() > 1;
	}

	public int GetGamesWon() {
		int gamesWon = 0;
		if (GetSum(BelowLine1) >= 100)
			gamesWon++;
		if (GetSum(BelowLine2) >= 100)
			gamesWon++;
		if (GetSum(BelowLine3) >= 100)
			gamesWon++;
		return gamesWon;
	}

	private int GetSum(ArrayList<RubberScore> scores) {
		int ret = 0;
		for (RubberScore score : scores) {
			ret += score.Score;
		}
		return ret;
	}

	public int GetLeg(int currentRubber) {
		switch (currentRubber) {
		case 1:
			return GetSum(BelowLine1);
		case 2:
			return GetSum(BelowLine2);
		case 3:
			return GetSum(BelowLine3);
		}
		return 0;
	}
}
