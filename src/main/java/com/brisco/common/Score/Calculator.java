package com.brisco.common.Score;

import com.brisco.common.Game.Board;
import com.brisco.common.Game.Contract;
import com.brisco.common.Game.Direction;

public final class Calculator {

	public static int GetNorthSouthPoints(Contract Contract, Board board) {
		boolean vulnerable = board.Vulnerability.IsVulnerable(Contract.Player);
		return GetNorthSouthPoints(Contract, vulnerable);
	}

	public static int GetNorthSouthPoints(Contract Contract, int boardNumber) {
		Direction direction = Direction.North;
		if (Contract.Player != null) {
			direction = Contract.Player;
		}
		boolean vulnerable = Board.GetVulnerability(boardNumber).IsVulnerable(
				direction);
		return GetNorthSouthPoints(Contract, vulnerable);
	}

	public static int GetNorthSouthPoints(Contract contract, boolean vulnerable) {
		int score = GetPoints(contract, vulnerable);
		// Check if this score was indeed for east-west :)
		if (contract.Player != null) {
			if (!contract.Player.IsNorthSouth()) {
				score = score * -1;
			}
		}

		return score;
	}

	public static int GetPoints(Contract contract, boolean vulnerable) {
		if (contract.Level < 1) {
			return 0;
		}

		int score = 0;

		if (contract.ContractMade()) {
			score = GetContractPoints(contract);
			score += GetLevelBonus(contract, vulnerable, score, false);
			score += GetInsultBonus(contract);
			score += GetOverTrickPoints(contract, vulnerable);
		} else {
			score = GetPenalty(contract, vulnerable);
		}
		return score;
	}

	public static int GetContractPoints(Contract contract) {
		if (!contract.ContractMade()) {
			return 0;
		}
		int extraPoints = contract.Suit.ExtraPointsForFirstTrick();
		int basePoints = contract.Suit.PointsPerTrick();

		if (contract.ReDoubled) {
			extraPoints *= 4;
			basePoints *= 4;
		} else if (contract.Doubled) {
			extraPoints *= 2;
			basePoints *= 2;
		}

		return basePoints * contract.Level + extraPoints;
	}

	public static int GetLevelBonus(Contract contract, boolean vulnerable,
			int contractPoints, boolean rubberScoring) {

		if (contractPoints < 100) {
			return rubberScoring ? 0 : 50;
		}
		// At least game:
		int score = rubberScoring ? 0 : vulnerable ? 500 : 300;

		if (contract.IsGrandSlam()) {
			score += vulnerable ? 1500 : 1000;
		} else if (contract.IsSmallSlam()) {
			score += vulnerable ? 750 : 500;
		}
		return score;
	}

	public static int GetInsultBonus(Contract contract) {
		if (contract.ContractMade()) {
			if (contract.ReDoubled) {
				return 100;
			} else if (contract.Doubled) {
				return 50;
			}
		}
		return 0;
	}

	public static int GetOverTrickPoints(Contract contract, boolean vulnerable) {
		int overTricks = contract.OverTricks();
		if (overTricks < 1) {
			return 0;
		} else {
			if (contract.ReDoubled) {
				return overTricks * (vulnerable ? 400 : 200);
			} else if (contract.Doubled) {
				return overTricks * (vulnerable ? 200 : 100);
			} else {
				return overTricks * contract.Suit.PointsPerTrick();
			}
		}
	}

	public static int GetPenalty(Contract contract, boolean vulnerable) {
		if (contract.ContractMade()) {
			return 0;
		}
		int diff = contract.OverTricks();
		int nsScore = 0;
		int ix = -1 * (diff + 1);
		if (contract.ReDoubled) {
			if (vulnerable) {
				nsScore = REDOUBLED_ZONE[ix];
			} else {
				nsScore = REDOUBLED_NOZONE[ix];
			}
		} else if (contract.Doubled) {
			if (vulnerable) {
				nsScore = DOUBLED_ZONE[ix];
			} else {
				nsScore = DOUBLED_NOZONE[ix];
			}
		} else {
			if (vulnerable) {
				nsScore = diff * 100;
			} else {
				nsScore = diff * 50;
			}
		}
		return nsScore;
	}

	private static final short[] DOUBLED_NOZONE = { -100, -300, -500, -800,
			-1100, -1400, -1700, -2000, -2300, -2600, -2900, -3200, -3500 };
	private static final short[] DOUBLED_ZONE = { -200, -500, -800, -1100,
			-1400, -1700, -2000, -2300, -2600, -2900, -3200, -3500, -3800 };
	private static final short[] REDOUBLED_NOZONE = { -200, -600, -1000, -1600,
			-2200, -2800, -3400, -4000, -4600, -5200, -5800, -6400, -7000 };
	private static final short[] REDOUBLED_ZONE = { -400, -1000, -1600, -2200,
			-2800, -3400, -4000, -4600, -5200, -5800, -6400, -7000, -7600 };

}
