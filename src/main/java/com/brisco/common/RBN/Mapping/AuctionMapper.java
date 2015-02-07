package com.brisco.common.RBN.Mapping;

import com.brisco.common.Game.Auction;
import com.brisco.common.Game.Bid;
import com.brisco.common.Game.BidQuality;
import com.brisco.common.Game.Direction;
import com.brisco.common.Game.Suit;
import com.brisco.common.Game.Vulnerability;
import com.brisco.common.PBN.Mapping.DirectionMapper;
import com.brisco.common.PBN.Mapping.SuitMapper;

public class AuctionMapper {

	public static Vulnerability GetVulnerabilityFromString(String auction) {
		return VulnerabilityMapper.GetVulnerabilityFromChar(auction.charAt(1));
	}

	public static Auction GetAuctionFromString(String auction) {
		if (auction == null || auction.length() < 1) {
			return null;
		}
		Direction dealer = DirectionMapper.GetDirectionFromString(auction
                .charAt(0));
		Auction ret = new Auction(dealer);
		int i = 3;
		while (i < auction.length()) {
			Bid b = null;
			char c = auction.charAt(i);
			switch (c) {
			case 'P':
				b = new Bid();
				b.Pass = true;
				break;
			case 'X':
				b = new Bid();
				b.Double = true;
				break;
			case 'R':
				b = new Bid();
				b.ReDouble = true;
				break;
			case 'Y':
				b = new Bid();
				b.YourTurn = true;
				break;
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
				b = new Bid();
				b.Level = c - 48;
				i++;
				if (auction.charAt(i) == 'N') {
					b.Suit = Suit.Notrump;
					i++;
				} else {
					b.Suit = SuitMapper.GetSuitFromString(auction.substring(i,
                            i + 1));
				}

				break;
			}

			// Check if there is a preceding "comment" of the bid
			if (b != null) {
				ret.Bids.add(b);
				if (i + 1 < auction.length()) {
					switch (auction.charAt(i + 1)) {
					case '?':
						i++;
						if ((i + 1 < auction.length())
								&& auction.charAt(i + 1) == '!') {
							i++;
							b.Quality = BidQuality.Questionable;
						} else if ((i + 1 < auction.length())
								&& auction.charAt(i + 1) == '?') {
							i++;
							b.Quality = BidQuality.VeryPoor;
						} else {
							b.Quality = BidQuality.Poor;
						}
						break;
					case '!':
						i++;
						if ((i + 1 < auction.length())
								&& auction.charAt(i + 1) == '!') {
							i++;
							b.Quality = BidQuality.VeryGood;
						} else if ((i + 1 < auction.length())
								&& auction.charAt(i + 1) == '?') {
							i++;
							b.Quality = BidQuality.Speculative;
						} else { // Check if there is a preceding "comment" of
									// the bid
							if (i + 1 < auction.length()) {
								switch (auction.charAt(i + 1)) {
								case '?':
									i++;
									if ((i + 1 < auction.length())
											&& auction.charAt(i + 1) == '!') {
										i++;
										b.Quality = BidQuality.Questionable;
									} else if ((i + 1 < auction.length())
											&& auction.charAt(i + 1) == '?') {
										i++;
										b.Quality = BidQuality.VeryPoor;
									} else {
										b.Quality = BidQuality.Poor;
									}
									break;
								case '!':
									i++;
									if ((i + 1 < auction.length())
											&& auction.charAt(i + 1) == '!') {
										i++;
										b.Quality = BidQuality.VeryGood;
									} else if ((i + 1 < auction.length())
											&& auction.charAt(i + 1) == '?') {
										i++;
										b.Quality = BidQuality.Speculative;
									} else {
										b.Quality = BidQuality.Good;
									}
									break;
								case '*':
									i++;
									b.Conventional = true;
									break;
								case '^':
									i += 2;
									b.Explanation = Integer.parseInt(auction
											.substring(i, i + 1));
									break;
								}
							}

							b.Quality = BidQuality.Good;
						}
						break;
					case '*':
						i++;
						b.Conventional = true;
						break;
					case '^':
						i += 2;
						b.Explanation = Integer.parseInt(auction.substring(i,
								i + 1));
						break;
					}
				}
			}

			i++;
		}

		return ret;
	}
}
