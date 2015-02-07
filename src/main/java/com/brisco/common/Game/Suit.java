package com.brisco.common.Game;

public enum Suit {
	Clubs, Diamonds, Hearts, Spades, Notrump;

	public boolean IsMajor() {
		return (this == Spades || this == Hearts);
	}

	public boolean IsMinor() {
		return (this == Clubs || this == Diamonds);
	}

	public int PointsPerTrick() {
		return IsMinor() ? 20 : 30;
	}

	public int ExtraPointsForFirstTrick() {
		return this == Notrump ? 10 : 0;
	}

}
