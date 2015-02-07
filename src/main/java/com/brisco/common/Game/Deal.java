package com.brisco.common.Game;

import java.io.Serializable;

public class Deal implements Serializable {
	private static final long serialVersionUID = -6879168346758781461L;
	public Hand West;
	public Hand North;
	public Hand East;
	public Hand South;

	public Hand getHand(Direction direction) {
		switch (direction) {
		case West:
			return West;
		case North:
			return North;
		case East:
			return East;
		case South:
			return South;
		default:
			return null;
		}
	}

	public void setHand(Direction direction, Hand hand) {
		switch (direction) {
		case West:
			West = hand;
			break;
		case North:
			North = hand;
			break;
		case East:
			East = hand;
			break;
		case South:
			South = hand;
			break;
		default:
			return;
		}
	}

	public boolean contains(Card card) {
		if (West != null && West.contains(card))
			return true;
		if (North != null && North.contains(card))
			return true;
		if (East != null && East.contains(card))
			return true;
		if (South != null && South.contains(card))
			return true;
		return false;
	}
}
