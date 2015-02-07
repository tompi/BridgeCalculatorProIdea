package com.brisco.common.Tournament;

import java.io.Serializable;
import java.util.ArrayList;

import com.brisco.common.Game.Contract;
import com.brisco.common.Players.Table;
import com.brisco.common.Score.IScoringEngine;
import com.brisco.common.Score.Result;

public final class Session implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 709697146047136010L;
	public int Number;
	public ArrayList<Round> Rounds;

	public Session() {
		Rounds = new ArrayList<Round>();
	}

	public void AddContract(int roundNumber, Table table, Contract contract,
			int boardNumber) {
		Round round = GetRound(roundNumber);
		round.AddContract(contract, table, boardNumber);
	}

	public Round GetRound(int roundNumber) {
		for (Round r : Rounds) {
			if (r.Number == roundNumber) {
				return r;
			}
		}
		// Does not exist, create
		Round round = new Round(roundNumber);
		Rounds.add(round);
		return round;
	}

	public ArrayList<BoardResult> Score(IScoringEngine engine) {
		ArrayList<BoardResult> ret = new ArrayList<BoardResult>();
		for (Round round : Rounds) {
			ret.addAll(round.Score(engine));
		}
		return ret;
	}

	public int GetMaxRoundNr() {
		int maxRoundNr = 1;
		for (Round r : Rounds) {
			if (r.Number > maxRoundNr) {
				maxRoundNr = r.Number;
			}
		}
		return maxRoundNr;
	}

	public Result GetResult(int roundNumber, int boardNumber, int tableNumber) {
		Round round = GetRound(roundNumber);
		return round.GetResult(boardNumber, tableNumber);
	}
}
