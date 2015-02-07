package com.brisco.common.PBN.Mapping;

import com.brisco.common.Game.Suit;

public class SuitMapper {

	public static Suit GetSuitFromString(String suit) {
		if ("N".equals(suit) || "NT".equals(suit)) {
			return Suit.Notrump;
		} else if ("S".equals(suit)) {
			return Suit.Spades;
		} else if ("H".equals(suit)) {
			return Suit.Hearts;
		} else if ("D".equals(suit)) {
			return Suit.Diamonds;
		} else {
			return Suit.Clubs;
		}
	}

	public static String GetStringFromSuit(Suit suit) {
		switch (suit) {
		case Notrump:
			return "NT";
		case Spades:
			return "S";
		case Hearts:
			return "H";
		case Diamonds:
			return "D";
		default:
			return "C";
		}
	}
}
