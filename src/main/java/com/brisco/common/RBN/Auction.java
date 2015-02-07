package com.brisco.common.RBN;

import com.brisco.common.RBN.Mapping.AuctionMapper;

public class Auction extends RBNLine {
	public com.brisco.common.Game.Auction Auction;
	public com.brisco.common.Game.Vulnerability Vulnerability;

	@Override
	public String GetRBNString() {
		return null;
	}

	@Override
	public void ParseRBNString(String rbn) {
		if (rbn != null && rbn.length() > 0) {
			Auction = AuctionMapper.GetAuctionFromString(rbn);
			Vulnerability = AuctionMapper.GetVulnerabilityFromString(rbn);
		}
	}

	@Override
	public RBNLineType GetRBNLineType() {
		return RBNLineType.Auction;
	}

}
