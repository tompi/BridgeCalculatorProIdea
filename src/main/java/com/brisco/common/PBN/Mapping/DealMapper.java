package com.brisco.common.PBN.Mapping;

import com.brisco.common.Game.Deal;
import com.brisco.common.Game.Hand;
import com.brisco.common.Game.HandMissing;

public class DealMapper {

	public static Deal GetDealFromString(String deal) {
		String[] firstAndHands = deal.split(":");
		char first = firstAndHands[0].charAt(0);
		String[] hands = firstAndHands[1].split(" ");
		Hand hand1 = null, hand2 = null, hand3 = null, hand4 = null;
		if (hands.length > 0) {
			hand1 = HandMapper.GetHandFromString(hands[0]);
			if (hands.length > 1) {
				hand2 = HandMapper.GetHandFromString(hands[1]);
				if (hands.length > 2) {
					hand3 = HandMapper.GetHandFromString(hands[2]);

					hand4 = null;
					if (hands.length > 3 && hands[3] != null
							&& hands[3].length() > 0) {
						hand4 = HandMapper.GetHandFromString(hands[3]);
					} else {
						hand4 = HandMissing.GetHandMissing(hand1, hand2, hand3);
					}
				}
			}
		}

		Deal ret = new Deal();

		if (first == 'W') {
			ret.West = hand1;
			ret.North = hand2;
			ret.East = hand3;
			ret.South = hand4;
		} else if (first == 'N') {
			ret.West = hand4;
			ret.North = hand1;
			ret.East = hand2;
			ret.South = hand3;
		} else if (first == 'E') {
			ret.West = hand3;
			ret.North = hand4;
			ret.East = hand1;
			ret.South = hand2;
		} else {
			ret.West = hand2;
			ret.North = hand3;
			ret.East = hand4;
			ret.South = hand1;
		}

		return ret;
	}

	public static String GetStringFromDeal(Deal deal) {
		StringBuffer ret = new StringBuffer("W:");
		ret.append(HandMapper.GetStringFromHand(deal.West));
		ret.append(" ");
		ret.append(HandMapper.GetStringFromHand(deal.North));
		ret.append(" ");
		ret.append(HandMapper.GetStringFromHand(deal.East));
		ret.append(" ");
		ret.append(HandMapper.GetStringFromHand(deal.South));
		return ret.toString();
	}
}
