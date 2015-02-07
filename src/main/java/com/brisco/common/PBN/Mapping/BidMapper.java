package com.brisco.common.PBN.Mapping;

import com.brisco.common.Game.Bid;
import com.brisco.common.Game.BidQuality;

public class BidMapper {

	public static Bid GetBidFromString(String bid) {
		// TODO
		return null;
	}

	public static String GetStringFromBid(Bid bid) {
		if (bid == null) {
			return "-";
		}
		return GetCall(bid) + GetNote(bid.Explanation) + GetNAG(bid.Quality);
	}

	private static String GetNAG(BidQuality quality) {
		if (quality == null) {
			return "";
		}
		switch (quality) {
		case Good:
			return " $1";
		case Poor:
			return " $2";
		case VeryGood:
			return " $3";
		case VeryPoor:
			return " $4";
		case Speculative:
			return " $5";
		case Questionable:
			return " $6";
		default:
			return "";
		}
	}

	private static String GetCall(Bid bid) {
		if (bid.Pass) {
			return "Pass";
		}
		if (bid.ReDouble) {
			return "XX";
		}
		if (bid.Double) {
			return "X";
		}
		return Integer.toString(bid.Level)
				+ SuitMapper.GetStringFromSuit(bid.Suit);
	}

	private static String GetNote(int note) {
		if (note < 1) {
			return "";
		}
		return " =" + Integer.toString(note) + "=";
	}
}
