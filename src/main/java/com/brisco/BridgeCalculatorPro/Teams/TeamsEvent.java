package com.brisco.BridgeCalculatorPro.Teams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.brisco.BridgeCalculatorPro.Competition;
import com.brisco.common.Game.Board;
import com.brisco.common.Game.Contract;
import com.brisco.common.PBN.Game.Auction;
import com.brisco.common.PBN.Game.Game;
import com.brisco.common.PBN.Game.Identification;
import com.brisco.common.PBN.Game.Supplemental;
import com.brisco.common.Score.Calculator;

public class TeamsEvent extends Competition {
	private static final long serialVersionUID = -5485293293508707536L;
	private List<ArrayList<TeamsResult>> _sessions;
	public boolean OurTeamNSinOpenRoom;

	public TeamsEvent() {
		super();
		_sessions = new ArrayList<ArrayList<TeamsResult>>();
		_sessions.add(new ArrayList<TeamsResult>());
		_sessions.add(new ArrayList<TeamsResult>());
	}

	public ArrayList<TeamsResult> GetResults(int session) {
		return _sessions.get(session);
	}

	public TeamsResult GetResult(int boardNr, int session) {
		for (TeamsResult result : _sessions.get(session)) {
			if (result.BoardNumber == boardNr) {
				return result;
			}
		}
		return null;
	}

	public void addResult(TeamsResult result, int session) {
		TeamsResult priorBoardResult = GetResult(result.BoardNumber, session);
		if (priorBoardResult != null) {
			if (result == priorBoardResult)
				return;
			_sessions.get(session).remove(priorBoardResult);
		}
		_sessions.get(session).add(result);
	}

	public void deleteBoard(int boardNumber, int session) {
		for (TeamsResult result : _sessions.get(session)) {
			if (result.BoardNumber == boardNumber) {
				_sessions.get(session).remove(result);
				return;
			}
		}
	}

	public int GetFirstUnregisteredBoardnr(int session, boolean lookDownWards,
			int startBoardNumber) {
		if (_sessions.get(session).size() > 0) {
			ArrayList<Integer> playedBoards = new ArrayList<Integer>();
			// Assume result-list is ordered by boardnr
			for (TeamsResult result : _sessions.get(session)) {
				playedBoards.add(result.BoardNumber);
			}
			if (lookDownWards) {
				for (int i = startBoardNumber; i > 0; i--) {
					if (!playedBoards.contains(i))
						return i;
				}
			}
			// If none found, we look upwards anyways...
			int i = startBoardNumber;
			if (i == 0)
				i = 1;
			while (playedBoards.contains(i))
				i++;
			return i;
		} else {
			return 1;
		}
	}

	public int GetPreviousUnregisteredBoardNumber(int boardNumber, int session) {
		int prev = boardNumber - 1;
		while (contains(prev, session)) {
			prev--;
		}
		return prev;
	}

	public int GetNextUnregisteredBoardNumber(int boardNumber, int session) {
		int next = boardNumber + 1;
		while (contains(next, session)) {
			next++;
		}
		return next;
	}

	private boolean contains(int boardNumber, int session) {
		for (TeamsResult r : _sessions.get(session)) {
			if (r.BoardNumber == boardNumber) {
				return true;
			}
		}
		return false;
	}

	public void SortResults(int session) {
		Collections.sort(_sessions.get(session));
	}

	@Override
	public List<Game> GetPBNGames() {
		ArrayList<Game> ret = new ArrayList<Game>();

		for (TeamsResult result : _sessions.get(0)) {
			if (result != null) {
				ret.add(GetPBNGame(result, true));
				ret.add(GetPBNGame(result, false));
			}
		}
		return ret;
	}

	private Game GetPBNGame(TeamsResult result, boolean openRoom) {
		Contract contract = openRoom ? result.OpenContract
				: result.ClosedContract;
		if (contract == null)
			return null;

		Game game = new Game();
		game.Identification = GetPBNIdentification(result, openRoom);
		game.Auction = GetPBNAuction(result, openRoom);
		game.Supplemental = GetPBNSupplemental(result, openRoom);
		return game;
	}

	private Auction GetPBNAuction(TeamsResult result, boolean openRoom) {
		com.brisco.common.Game.Auction auction = openRoom ? result.OpenAuction
				: result.ClosedAuction;
		if (auction == null) {
			return null;
		}
		Auction ret = new Auction();
		ret.Auction = auction;
		return ret;
	}

	private Supplemental GetPBNSupplemental(TeamsResult result, boolean openRoom) {
		Supplemental supp = new Supplemental();
		Contract contract = openRoom ? result.OpenContract
				: result.ClosedContract;
		supp.Application = "Bridge Calculator Pro";
		supp.DealId = Integer.toString(result.BoardNumber);
		supp.Mode = "TABLE";
		supp.Score = Integer.toString(Calculator.GetPoints(
				contract,
				Board.GetVulnerability(result.BoardNumber).IsVulnerable(
						contract.Player)));
		supp.Room = openRoom ? "Open" : "Closed";
		return supp;
	}

	private Identification GetPBNIdentification(TeamsResult result,
			boolean openRoom) {
		Contract contract = openRoom ? result.OpenContract
				: result.ClosedContract;
		Identification id = new Identification();
		id.Board = result.BoardNumber;
		id.Contract = contract;
		id.Date = Started;
		id.Declarer = contract.Player;
		id.Dealer = Board.GetDealer(result.BoardNumber);
		id.West = null;
		id.North = null;
		id.East = null;
		id.South = null;
		id.Event = Description;
		id.Deal = result.Deal;
		id.Scoring = "IMP";
		id.Result = contract.Tricks;
		id.Site = null;

		id.Vulnerable = Board.GetVulnerability(id.Board);
		return id;
	}

	@Override
	public void AppendHTML(StringBuilder html) {
		TeamsEventHTMLRenderer renderer = new TeamsEventHTMLRenderer(this);
		renderer.Render(html);
	}

}
