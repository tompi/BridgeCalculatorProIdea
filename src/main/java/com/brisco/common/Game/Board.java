package com.brisco.common.Game;

import java.io.Serializable;

public class Board implements Serializable {
	private static final long serialVersionUID = -6457132433164523746L;
	public int Number;
	public com.brisco.common.Game.Vulnerability Vulnerability;
	public Direction Dealer;
	public Deal Deal;

	public static com.brisco.common.Game.Vulnerability GetVulnerability(int boardNumber) {
		int vulnMod = boardNumber % 16;
		switch (vulnMod) {
		case 1:
		case 8:
		case 11:
		case 14:
			return com.brisco.common.Game.Vulnerability.None;
		case 4:
		case 7:
		case 10:
		case 13:
			return com.brisco.common.Game.Vulnerability.Both;
		case 3:
		case 6:
		case 9:
		case 0:
			return com.brisco.common.Game.Vulnerability.EastWest;
		default:
			return com.brisco.common.Game.Vulnerability.NorthSouth;
		}
	}

	public static Direction GetDealer(int boardNumber) {
		int dealerMod = boardNumber % 4;
		switch (dealerMod) {
		case 1:
			return Direction.North;
		case 2:
			return Direction.East;
		case 3:
			return Direction.South;
		default:
			return Direction.West;
		}
	}

	public Board(int boardNumber) {
		Number = boardNumber;
		Dealer = GetDealer(boardNumber);
		Vulnerability = GetVulnerability(boardNumber);
	}
}
