package com.brisco.common.Tournament;

import java.io.Serializable;
import java.util.ArrayList;

import com.brisco.common.Game.Board;
import com.brisco.common.Game.Contract;
import com.brisco.common.Players.Table;
import com.brisco.common.Score.IScoringEngine;
import com.brisco.common.Score.Result;

public final class Round implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3432442264623321983L;

	public int Number;

	public ArrayList<BoardResult> BoardResults;

	public Round(int number) {
		Number = number;
		BoardResults = new ArrayList<BoardResult>();
	}

	public void AddBoardResult(BoardResult result) {
		BoardResults.add(result);
	}

	public void AddContract(Contract contract, Table table, int boardNumber) {
		BoardResult boardResult = GetBoardResult(boardNumber);
		boardResult.AddContract(contract, table);
	}

	private BoardResult GetBoardResult(int boardNumber) {
		for (BoardResult b : BoardResults) {
			if (b.Board.Number == boardNumber) {
				return b;
			}
		}
		// Does not exist yet, create board:
		Board board = new Board(boardNumber);
		BoardResult boardResult = new BoardResult(board);
		AddBoardResult(boardResult);
		return boardResult;
	}

	public ArrayList<BoardResult> Score(IScoringEngine engine) {
		for (BoardResult res : BoardResults) {
			res.Score(engine);
		}
		return BoardResults;
	}

	public int GetMaxTableNr() {
		int maxTableNr = 0;
		for (BoardResult br : BoardResults) {
			for (Result r : br.Results) {
				if (r.Table.Number > maxTableNr) {
					maxTableNr = r.Table.Number;
				}
			}
		}
		return maxTableNr;
	}

	public Result GetResult(int boardNumber, int tableNumber) {
		BoardResult br = GetBoardResult(boardNumber);
		for (Result result : br.Results) {
			if (result.Table != null && result.Table.Number == tableNumber) {
				return result;
			}
		}
		return null;
	}
}
