package com.brisco.common.Score;

import java.util.ArrayList;

public class MatchPointsScoringEngine implements IScoringEngine
{

	public void ScoreBoard(ArrayList<Result> results) {
		for (int i=0; i<results.size(); i++) {
			int nsScore = 0;
			Result result = results.get(i);
			int nsPoints = result.NorthSouthPoints;
			for (int j=0; j<results.size(); j++) {
				if (j != i ) {
					int nsPointsTableB = results.get(j).NorthSouthPoints;
					if (nsPoints > nsPointsTableB) {
						nsScore += 2;
					} else if (nsPoints == nsPointsTableB) {
						nsScore += 1;
					}
				}
			}
			result.NorthSouthScore = (double)nsScore;
		}
		
	}
}
