package com.brisco.BridgeCalculatorPro.Rubber;

import com.brisco.common.Game.Vulnerability;

public class RubberResult {
	public RubberDirectionResult NorthSouth;
	public RubberDirectionResult EastWest;
	private int _currentGame;

	public RubberResult() {
		NorthSouth = new RubberDirectionResult();
		EastWest = new RubberDirectionResult();
		_currentGame = 1;
	}

	public void AddContractResult(boolean pointsForNorthSouth, int aboveLine,
			int belowLine, int contractNumber) {
		RubberDirectionResult results = EastWest;
		if (pointsForNorthSouth) {
			results = NorthSouth;
		}
		if (results
				.AddScore(aboveLine, belowLine, _currentGame, contractNumber))
			_currentGame++;
	}

	public Vulnerability GetVulnerability() {
		boolean ns = NorthSouth.IsVulnerable();
		boolean ew = EastWest.IsVulnerable();

		if (ns) {
			return ew ? Vulnerability.Both : Vulnerability.NorthSouth;
		}
		return ew ? Vulnerability.EastWest : Vulnerability.None;
	}

	public boolean HasRubberEnded() {
		return _currentGame > 3 || (NorthWon() || EastWest.WinnerOfRubber());
	}

	public boolean NorthWon() {
		return NorthSouth.WinnerOfRubber();
	}

	public int GetCurrentGame() {
		return _currentGame;
	}
}
