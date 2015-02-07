package com.brisco.BridgeCalculatorPro.ScoreSheet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.brisco.BridgeCalculatorPro.Competition;
import com.brisco.common.Game.Board;
import com.brisco.common.PBN.Game.Auction;
import com.brisco.common.PBN.Game.Game;
import com.brisco.common.PBN.Game.Identification;
import com.brisco.common.PBN.Game.Supplemental;
import com.brisco.common.Score.Calculator;

public class Event extends Competition {
	private static final long serialVersionUID = -2817907846869462930L;
	private ArrayList<Result> _results;

	public Event() {
		super();
		_results = new ArrayList<Result>();
	}

	public ArrayList<Result> GetResults() {
		return _results;
	}

	public Result GetResult(int boardNr) {
		for (Result result : _results) {
			if (result.BoardNumber == boardNr) {
				return result;
			}
		}
		return null;
	}

	public void addResult(Result result) {
		Result priorBoardResult = GetResult(result.BoardNumber);
		if (priorBoardResult != null) {
			if (result == priorBoardResult)
				return;
			_results.remove(priorBoardResult);
		}
		_results.add(result);
	}

	public void deleteBoard(int boardNumber) {
		for (Result result : _results) {
			if (result != null && result.BoardNumber == boardNumber) {
				_results.remove(result);
				return;
			}
		}
	}

	public int GetFirstUnregisteredBoardnr() {
		int ret = 1;
		if (_results.size() > 0) {
			// Assume result-list is ordered by boardnr
			for (Result result : _results) {
				if (result.BoardNumber != ret) {
					return ret;
				} else {
					ret++;
				}
			}
		}
		return ret;
	}

	public int GetPreviousUnregisteredBoardNumber(int boardNumber) {
		int prev = boardNumber - 1;
		while (contains(prev)) {
			prev--;
		}
		return prev;
	}

	public int GetNextUnregisteredBoardNumber(int boardNumber) {
		int next = boardNumber + 1;
		while (contains(next)) {
			next++;
		}
		return next;
	}

	private boolean contains(int boardNumber) {
		for (Result r : _results) {
			if (r.BoardNumber == boardNumber) {
				return true;
			}
		}
		return false;
	}

	public void SortResults() {
		Collections.sort(_results);
	}

	@Override
	public List<Game> GetPBNGames() {
		ArrayList<Game> ret = new ArrayList<Game>();

		for (Result result : _results) {
			if (result != null) {
				Game game = new Game();
				game.Identification = GetPBNIdentification(result);
				game.Auction = GetPBNAuction(result);
				game.Supplemental = GetPBNSupplemental(result);
				ret.add(game);
			}
		}
		return ret;
	}

	private Auction GetPBNAuction(Result result) {
		if (result.Auction == null) {
			return null;
		}
		Auction auction = new Auction();
		auction.Auction = result.Auction;
		return auction;
	}

	private Supplemental GetPBNSupplemental(Result result) {
		Supplemental supp = new Supplemental();
		supp.Application = "Bridge Calculator Pro";
		supp.DealId = Integer.toString(result.BoardNumber);
		supp.Mode = "TABLE";
		supp.Score = Integer.toString(Calculator.GetPoints(
				result.Contract,
				Board.GetVulnerability(result.BoardNumber).IsVulnerable(
						result.Contract.Player)));
		return supp;
	}

	private Identification GetPBNIdentification(Result result) {
		Identification id = new Identification();
		id.Board = result.BoardNumber;
		id.Contract = result.Contract;
		id.Date = Started;
		id.Declarer = result.Contract.Player;
		id.Dealer = Board.GetDealer(result.BoardNumber);
		id.West = null;
		id.North = null;
		id.East = null;
		id.South = null;
		id.Event = Description;
		id.Deal = result.Deal;
		id.Scoring = null;
		id.Result = result.Contract.Tricks;
		id.Site = null;
		id.Vulnerable = Board.GetVulnerability(id.Board);
		return id;
	}

	@Override
	public void AppendHTML(StringBuilder html) {
		ScoreSheetHTMLRenderer renderer = new ScoreSheetHTMLRenderer(this);
		renderer.Render(html);
	}

}
