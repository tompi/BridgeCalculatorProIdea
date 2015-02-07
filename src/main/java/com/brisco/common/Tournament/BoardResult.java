package com.brisco.common.Tournament;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import com.brisco.common.Game.Contract;
import com.brisco.common.Players.Table;
import com.brisco.common.Score.IScoringEngine;
import com.brisco.common.Score.Result;

public final class BoardResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2223490167454432442L;
	public com.brisco.common.Game.Board Board;
	public ArrayList<Result> Results;

	public BoardResult(com.brisco.common.Game.Board board) {
		Board = board;
		Results = new ArrayList<Result>();
	}

	public void AddContract(Contract contract, Table table) {
		Results.add(new Result(contract, Board, table));
	}

	public void Score(IScoringEngine engine) {
		engine.ScoreBoard(Results);
	}

	public void Sort() {
		Collections.sort(Results);
	}
}
