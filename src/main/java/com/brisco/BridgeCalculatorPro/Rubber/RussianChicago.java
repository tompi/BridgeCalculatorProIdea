package com.brisco.BridgeCalculatorPro.Rubber;

import java.util.ArrayList;

import com.brisco.common.Game.Contract;
import com.brisco.common.Game.Direction;
import com.brisco.common.Score.RussianIMPCalculator;

public class RussianChicago extends Chicago {
	private static final long serialVersionUID = -1260683145804657872L;
	private int[] _HCP;

	public RussianChicago() {
		super();
		_HCP = new int[4];
	}

	public void AddContract(Contract contract, int northSouthHCP) {
		int ix = GetContractsSize();
		AddContract(contract);
		_HCP[ix] = northSouthHCP;
	}

	public int GetHCP(int ix) {
		return _HCP[ix];
	}

	public ArrayList<RussianScore> GetRussianScores() {
		ArrayList<RussianScore> ret = new ArrayList<RussianScore>();
		ArrayList<Integer> scores = GetScores();
		for (int i = 0; i < scores.size(); i++) {
			RussianScore russian = new RussianScore();
			russian.Score = scores.get(i);
			Contract c = _contracts[i];
			Direction opponents = c.Player.IsNorthSouth() ? Direction.East
					: Direction.North;
			boolean vulnerable = GetVulnerability(i).IsVulnerable(c.Player);
			boolean opponentsVulnerable = GetVulnerability(i).IsVulnerable(
					opponents);
			russian.IMP = RussianIMPCalculator.GetIMP(russian.Score, _HCP[i],
					vulnerable, opponentsVulnerable);
			ret.add(russian);
		}
		return ret;
	}
}
