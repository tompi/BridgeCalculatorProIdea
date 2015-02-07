package com.brisco.BridgeCalculatorPro.Teams;

import java.io.Serializable;

public class TeamsResult implements Serializable, Comparable<TeamsResult> {
	private static final long serialVersionUID = -1720287731799019650L;
	public int BoardNumber;
	public com.brisco.common.Game.Contract OpenContract;
	public com.brisco.common.Game.Contract ClosedContract;
	public com.brisco.common.Game.Auction OpenAuction;
	public com.brisco.common.Game.Auction ClosedAuction;
	public com.brisco.common.Game.Deal Deal;

	@Override
	public int compareTo(TeamsResult another) {
		if (another == null) {
			return -1;
		}
		Integer me = (Integer) BoardNumber;
		return me.compareTo((Integer) another.BoardNumber);
	}
}
