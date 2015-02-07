package com.brisco.common.PBN.Mapping;

import java.io.InputStream;

import com.brisco.common.Game.Bid;
import com.brisco.common.PBN.Game.Auction;

public class AuctionMapper {

	public static Auction GetAuctionFromStream(InputStream identification) {
		// TODO
		return null;
	}

	public static void AppendAuction(StringBuilder pbn, Auction pbnAuction) {
		if (pbnAuction == null || pbnAuction.Auction == null
				|| pbnAuction.Auction.Bids == null
				|| pbnAuction.Auction.Bids.size() < 1) {
			return;
		}
		com.brisco.common.Game.Auction auction = pbnAuction.Auction;
		TagMapper.AppendTag(pbn, Auction.Tags.Auction, auction.Dealer);

		int ix = 1;
		for (Bid b : auction.Bids) {
			pbn.append(BidMapper.GetStringFromBid(b));
			if (ix == auction.Bids.size()) {
				pbn.append('\n');
			} else {
				if (ix % 4 == 0) {
					pbn.append('\n');
				} else {
					pbn.append(' ');
				}
			}
			ix++;
		}
		ix = 1;
		for (String explanation : auction.Explanations) {
			TagMapper.AppendTag(pbn, Auction.Tags.Note, Integer.toString(ix)
                    + ":" + explanation);
			ix++;
		}
	}
}
