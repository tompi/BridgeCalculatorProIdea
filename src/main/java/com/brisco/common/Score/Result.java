package com.brisco.common.Score;

import java.io.Serializable;

public class Result implements Comparable<Result>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2650538391909511350L;
	public com.brisco.common.Game.Contract Contract;
	public com.brisco.common.Game.Board Board;
	public com.brisco.common.Players.Table Table;
	public int NorthSouthPoints;
	public double NorthSouthScore;

	public Result(com.brisco.common.Game.Contract contract, com.brisco.common.Game.Board board, com.brisco.common.Players.Table table) {
		Contract = contract;
		Board = board;
		Table = table;
		NorthSouthPoints = Calculator.GetNorthSouthPoints(Contract, Board);
	}

	public int compareTo(Result result) {
		if (result == null) {
			return 1;
		}
		return Double.compare(NorthSouthScore, result.NorthSouthScore);
	}
}
