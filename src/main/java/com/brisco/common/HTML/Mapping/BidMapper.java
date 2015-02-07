package com.brisco.common.HTML.Mapping;

import com.brisco.common.Game.Bid;
import com.brisco.common.Game.BidQuality;

public class BidMapper {

	public static void AppendBid(StringBuilder html, Bid bid) {
		if (bid == null) {
			return;
		}
		AppendCall(html, bid);
		AppendQuality(html, bid.Quality);
		AppendNote(html, bid.Explanation);
	}

	private static void AppendCall(StringBuilder html, Bid bid) {
		if (bid.Pass) {
			html.append("Pass");
		} else if (bid.ReDouble) {
			html.append("XX");
		} else if (bid.Double) {
			html.append("X");
		} else {
			html.append(Integer.toString(bid.Level)
					+ SuitMapper.GetStringFromSuit(bid.Suit));
		}
	}

	private static void AppendQuality(StringBuilder html, BidQuality quality) {
		if (quality == null) {
			return;
		}
		switch (quality) {
		case Good:
			html.append("!");
		case Poor:
			html.append("?");
		case VeryGood:
			html.append("!!");
		case VeryPoor:
			html.append("??");
		case Speculative:
			html.append("?!");
		case Questionable:
			html.append("!?");
		default:
			return;
		}
	}

	private static void AppendNote(StringBuilder html, int explanation) {
		if (explanation > 0) {
			html.append("<super>" + Integer.toString(explanation) + "</super>");
		}
	}

}
