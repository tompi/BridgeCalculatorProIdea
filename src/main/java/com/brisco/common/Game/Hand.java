package com.brisco.common.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hand implements Serializable {
	private static final long serialVersionUID = 7954384636794999331L;
	public Card[] Cards;

	public Hand() {
		Cards = new Card[13];
	}

	public boolean isComplete() {
		for (Card c : Cards) {
			if (c == null) {
				return false;
			}
		}
		return true;
	}

	public boolean contains(Card card) {
		if (card == null) {
			return false;
		}
		for (Card c : Cards) {
			if (card.equals(c)) {
				return true;
			}
		}
		return false;
	}

	public void Add(Card c) {
		for (int i = 0; i < 13; i++) {
			if (Cards[i] == null) {
				Cards[i] = c;
				return;
			}
		}
	}

	public List<Card> GetCardsWithinSuit(Suit suit) {
		ArrayList<Card> ret = null;
		for (Card c : Cards) {
			if (c != null && c.Suit == suit) {
				if (ret == null) {
					ret = new ArrayList<Card>();
				}
				ret.add(c);
			}
		}
		return ret;
	}

	public void addCards(List<Card> cards) {
		for (Card c : cards) {
			Add(c);
		}
	}

	public void removeSuit(Suit suit) {
		for (int i = 0; i < 13; i++) {
			if (Cards[i] != null && Cards[i].Suit == suit) {
				Cards[i] = null;
			}
		}
		// Fill empty slots:
		// ("Stable" removeSuit)
		int i = 0;
		int j = 1;
		while (i < 13 && j < 13) {
			if (Cards[i] == null) {
				if (j <= i)
					j = i + 1;
				while (j < 13) {
					if (Cards[j] != null) {
						Cards[i] = Cards[j];
						Cards[j] = null;
						break;
					}
					j++;
				}
			}
			i++;
		}
	}
}
