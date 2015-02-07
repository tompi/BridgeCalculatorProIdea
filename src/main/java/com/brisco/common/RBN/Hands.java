package com.brisco.common.RBN;

import com.brisco.common.Game.Direction;
import com.brisco.common.PBN.Mapping.DealMapper;

public class Hands extends RBNLine {
	public com.brisco.common.Game.Deal Deal;
	public com.brisco.common.Game.Vulnerability Vulnerability;
	public Direction Dealer;

	@Override
	public String GetRBNString() {
		return DealMapper.GetStringFromDeal(Deal).replace(' ', ':');
	}

	@Override
	public void ParseRBNString(String rbn) {
		if (rbn != null && rbn.length() > 0) {
			String pbn = rbn.replace(':', ' ');
			pbn = pbn.substring(0, 1) + ":" + pbn.substring(2);
			Deal = DealMapper.GetDealFromString(pbn);
		}
	}

	@Override
	public RBNLineType GetRBNLineType() {
		return RBNLineType.Hands;
	}

}
