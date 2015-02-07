package com.brisco.common.Game;

public class HandMissing {
	public static Hand GetHandMissing(Hand hand1, Hand hand2, Hand hand3) {
		if (hand1 == null || hand2 == null || hand3 == null) {
			return null;
		}
		if (!hand1.isComplete() || !hand2.isComplete() || !hand3.isComplete()) {
			return null;
		}
		Hand missingHand = new Hand();
		for (Suit s : Suit.values()) {
			for (Denomination d : Denomination.values()) {
				if (d != Denomination.Small && d != Denomination.Unknown) {
					Card c = new Card(s, d);
					if (!hand1.contains(c) && !hand2.contains(c)
							&& !hand3.contains(c)) {
						missingHand.Add(c);
					}
				}
			}
		}
		return missingHand;
	}
}
