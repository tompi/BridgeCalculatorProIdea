package com.brisco.common.PBN.Mapping;

import com.brisco.common.Game.Card;
import com.brisco.common.Game.Hand;
import com.brisco.common.Game.Suit;

public class HandMapper {

	public static Hand GetHandFromString(String hand) {
		if (hand == null || hand == "-" || hand.length() < 1) {
			return null;
		}
		Suit suit = Suit.Spades;
		int ix = 0;
		Hand ret = new Hand();

		for (char c : hand.toCharArray()) {
			if (c == '.') {
				suit = GetSuitBelow(suit);
			} else {
				Card card = new Card(suit,
						DenominationMapper.GetDenominationFromChar(c));
				ret.Cards[ix] = card;
				ix++;
			}
		}

		return ret;
	}

	private static Suit GetSuitBelow(Suit suit) {
		switch (suit) {
		case Spades:
			return Suit.Hearts;
		case Hearts:
			return Suit.Diamonds;
		default:
			return Suit.Clubs;
		}
	}

	public static String GetStringFromHand(Hand hand) {
		if (hand == null || hand.Cards == null || hand.Cards.length < 1) {
			return "-";
		}
		StringBuffer spades = new StringBuffer("");
		StringBuffer hearts = new StringBuffer(".");
		StringBuffer diamonds = new StringBuffer(".");
		StringBuffer clubs = new StringBuffer(".");

		for (Card card : hand.Cards) {
			if (card != null && card.Denomination != null) {
				char symbol = DenominationMapper
						.GetCharFromDenomination(card.Denomination);
				switch (card.Suit) {
				case Spades:
					spades.append(symbol);
					break;
				case Hearts:
					hearts.append(symbol);
					break;
				case Diamonds:
					diamonds.append(symbol);
					break;
				default:
					clubs.append(symbol);
					break;
				}
			}
		}
		return spades.append(hearts).append(diamonds).append(clubs).toString();
	}
}
