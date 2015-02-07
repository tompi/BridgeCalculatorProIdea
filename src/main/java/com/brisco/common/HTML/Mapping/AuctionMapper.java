package com.brisco.common.HTML.Mapping;

import java.util.ArrayList;

import com.brisco.common.Game.Auction;
import com.brisco.common.Game.Bid;
import com.brisco.common.Game.Direction;

public class AuctionMapper {

	public static void AppendAuction(StringBuilder html, Auction auction) {
		if (auction == null) {
			return;
		}
		html.append("<div>");
		html.append("<table cellspacing=\"0\" cellpadding=\"4\" border=\"1px\">");
		html.append("<colgroup><col span=\"4\" align=\"center\"/></colgroup>");
		html.append("<tr>");
		html.append("<th><b>"
				+ DirectionMapper.GetStringFromDirection(Direction.West)
				+ "</b></th>");
		html.append("<th><b>"
				+ DirectionMapper.GetStringFromDirection(Direction.North)
				+ "</b></th>");
		html.append("<th><b>"
				+ DirectionMapper.GetStringFromDirection(Direction.East)
				+ "</b></th>");
		html.append("<th><b>"
				+ DirectionMapper.GetStringFromDirection(Direction.South)
				+ "</b></th></tr>");

		// Make "balanced" array of bids:
		ArrayList<Bid> bids = new ArrayList<Bid>();
		switch (auction.Dealer) {
		case South:
			bids.add(null);
		case East:
			bids.add(null);
		case North:
			bids.add(null);
		case West:
		}
		bids.addAll(auction.Bids);

		for (int i = 0; i < (bids.size() / 4) + 1; i++) {
			int ix = i * 4;
			html.append("<tr>");
			AddBid(html, bids, ix);
			AddBid(html, bids, ix + 1);
			AddBid(html, bids, ix + 2);
			AddBid(html, bids, ix + 3);
			html.append("</tr>");
		}

		html.append("</table></div>");

	}

	private static void AddBid(StringBuilder html, ArrayList<Bid> bids, int i) {
		html.append("<td>");
		Bid b = null;
		if (i < bids.size()) {
			b = bids.get(i);
		}

		if (b != null) {
			BidMapper.AppendBid(html, b);
		} else {
			html.append("&nbsp;");
		}
		html.append("</td>");
	}
}
