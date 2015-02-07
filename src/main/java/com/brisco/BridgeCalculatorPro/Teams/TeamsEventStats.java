package com.brisco.BridgeCalculatorPro.Teams;

import com.brisco.common.Game.Contract;
import com.brisco.common.Score.IMPCalculator;
import com.brisco.common.Score.VPCalculator;
import com.brisco.common.Score.VPPoints;

public class TeamsEventStats {
	public int IMP;
	public VPPoints VP;
	public int ScoredBoards;
	public int TotalBoards;
	public int WonBoards;
	public int TiedBoards;
	public int LostBoards;
	public int PlayedPercentage;

	public TeamsEventStats(TeamsEvent event) {
		int playedByUs = 0;
		int playedByThem = 0;
		for (TeamsResult result : event.GetResults(0)) {
			TotalBoards++;
			if (result.ClosedContract != null && result.OpenContract != null) {
				ScoredBoards++;
				int imp = IMPCalculator.GetNorthSouthIMP(result.BoardNumber,
						result.OpenContract, result.ClosedContract);
				if (!event.OurTeamNSinOpenRoom) {
					imp = imp * -1;
				}
				IMP += imp;
				if (imp > 0) {
					WonBoards++;
				} else if (imp < 0) {
					LostBoards++;
				} else {
					TiedBoards++;
				}
			}
			if (wasPlayed(result.OpenContract)) {
				if (playedByUs(result.OpenContract, event.OurTeamNSinOpenRoom)) {
					playedByUs++;
				} else {
					playedByThem++;
				}
			}
			if (wasPlayed(result.ClosedContract)) {
				if (playedByUs(result.ClosedContract,
						!event.OurTeamNSinOpenRoom)) {
					playedByUs++;
				} else {
					playedByThem++;
				}
			}
		}

		if (playedByUs + playedByThem > 0) {
			PlayedPercentage = (playedByUs * 100) / (playedByUs + playedByThem);
		}
		VP = VPCalculator.GetWBFVP(IMP, TotalBoards);
	}

	private boolean playedByUs(Contract contract, boolean usIsNorthSouth) {
		if (contract.Player.IsNorthSouth()) {
			return usIsNorthSouth;
		} else {
			return !usIsNorthSouth;
		}
	}

	private boolean wasPlayed(Contract contract) {
		return contract != null && contract.Level > 0;
	}

}
