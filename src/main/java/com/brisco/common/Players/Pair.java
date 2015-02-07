package com.brisco.common.Players;

public final class Pair implements Comparable<Pair> {
	public Person NorthEast;
	public Person SouthWest;
	public double Score;
	public int Number;

	public int compareTo(Pair other) {
		if (other == null) {
			return 1;
		}
		return Double.compare(Score, other.Score);
	}

	public String getDisplayName() {
		return NorthEast.DisplayName + "/" + SouthWest.DisplayName;
	}
}
