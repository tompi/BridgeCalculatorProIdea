package com.brisco.BridgeCalculatorPro.ScoreSheet;

import java.util.List;

import com.brisco.BridgeCalculatorPro.Settings;
import com.brisco.common.Game.Bid;
import com.brisco.common.Game.Card;
import com.brisco.common.Game.Direction;
import com.brisco.common.Game.Hand;
import com.brisco.common.Game.Suit;

public class Mapping {
	public static String GetCardsString(Hand hand, Suit suit, Settings settings) {
		String ret = GetStringFromSuit(suit, settings) + " ";

		if (hand != null) {
			List<Card> cards = hand.GetCardsWithinSuit(suit);
			if (cards == null) {
				ret += "-";
			} else {
				for (Card c : cards) {
					ret += DenominationMapper.GetCharFromDenomination(
							c.Denomination, settings);
					if (settings.SpaceBetweenCardSymbols) {
						ret += " ";
					}
				}
			}
		} else {
			ret += "-";
		}
		return ret;
	}

	public static String GetStringFromCard(Card card, Settings settings) {
		if (card == null)
			return null;

		return GetStringFromSuit(card.Suit, settings)
				+ DenominationMapper.GetCharFromDenomination(card.Denomination,
						settings);
	}

	private static String GetStringFromSuit(Suit suit, Settings settings) {
		switch (suit) {
		case Notrump:
			return settings.SymbolNotrump;
		case Spades:
			return settings.SymbolSpades;
		case Hearts:
			return settings.SymbolHearts;
		case Diamonds:
			return settings.SymbolDiamonds;
		default:
			return settings.SymbolClubs;
		}
	}

	public static String GetStringFromDirection(Direction direction,
			Settings settings) {
		switch (direction) {
		case North:
			return settings.StringNorth;
		case South:
			return settings.StringSouth;
		case East:
			return settings.StringEast;
		default:
			return settings.StringWest;
		}
	}

	public static String GetBidString(Bid b, Settings settings) {
		String ret = new String();
		if (b.YourTurn) {
			return "?";
		} else if (b.ReDouble) {
			ret += settings.symbolRedouble;
		} else if (b.Double) {
			ret += settings.SymbolDouble;
		} else if (b.Pass) {
			ret += settings.SymbolPass;
		} else {
			ret += Integer.toString(b.Level);
			ret += GetStringFromSuit(b.Suit, settings);
		}
		if (b.Conventional) {
			ret += "<sup><small>*</small></sup>";
		}
		if (b.Explanation > 0) {
			ret += "<sup><small>" + Integer.toString(b.Explanation)
					+ "</small></sup>";
		}
		if (b.Quality != null) {
			switch (b.Quality) {
			case Poor:
				ret += "?";
				break;
			case VeryPoor:
				ret += "??";
				break;
			case Good:
				ret += "!";
				break;
			case VeryGood:
				ret += "!!";
				break;
			case Speculative:
				ret += "!?";
				break;
			case Questionable:
				ret += "?!";
				break;
			}
		}
		return ret;
	}

}
