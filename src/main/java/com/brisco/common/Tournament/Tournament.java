package com.brisco.common.Tournament;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;

import com.brisco.common.Game.Contract;
import com.brisco.common.Players.Pair;
import com.brisco.common.Players.Table;
import com.brisco.common.Score.IMPsAcrossScoringEngine;
import com.brisco.common.Score.IScoringEngine;
import com.brisco.common.Score.MatchPointsScoringEngine;
import com.brisco.common.Score.Result;
import com.brisco.common.Score.ScoringType;

public final class Tournament implements Serializable {
	private static final long serialVersionUID = -295143540934046089L;
	public Date Started;
	public String Name;
	public ArrayList<Pair> Pairs;
	public ArrayList<Session> Sessions;

	private IScoringEngine _scoringEngine;

	public Tournament() {
		Pairs = new ArrayList<Pair>();
		Sessions = new ArrayList<Session>();
	}

	public Pair GetPair(int pairNumber) {
		for (Pair p : Pairs) {
			if (p.Number == pairNumber) {
				return p;
			}
		}
		return null;
	}

	public void AddScore(int sessionNumber, int roundNumber, Table table,
			Contract contract, int boardNumber) {
		Session session = GetSession(sessionNumber);
		session.AddContract(roundNumber, table, contract, boardNumber);
	}

	public Result GetResult(int sessionNumber, int roundNumber,
			int boardNumber, int tableNumber) {
		Session session = GetSession(sessionNumber);
		return session.GetResult(roundNumber, boardNumber, tableNumber);
	}

	public Session GetSession(int sessionNumber) {
		for (Session s : Sessions) {
			if (s.Number == sessionNumber) {
				return s;
			}
		}
		// Does not exist, create
		Session s = new Session();
		s.Number = sessionNumber;
		return s;
	}

	public void Score(ScoringType scoringType) {
		// Clear old scores:
		for (Pair pair : Pairs) {
			pair.Score = 0;
		}
		// Score all sessions
		if (scoringType == ScoringType.MatchPoints) {
			_scoringEngine = new MatchPointsScoringEngine();
		} else if (scoringType == ScoringType.IMPsAcross) {
			_scoringEngine = new IMPsAcrossScoringEngine();
		}
		ArrayList<BoardResult> results = new ArrayList<BoardResult>();
		for (Session session : Sessions) {
			results.addAll(session.Score(_scoringEngine));
		}
		// Aggregate pair scores
		for (BoardResult br : results) {
			for (Result result : br.Results) {
				result.Table.NorthSouthPair.Score += result.NorthSouthScore;
				result.Table.EastWestPair.Score -= result.NorthSouthScore;
			}
		}
		// Sort Pair list:
		Collections.sort(Pairs);
	}

	public int GetMaxSessionNr() {
		int maxSessionNr = 1;
		for (Session s : Sessions) {
			if (s.Number > maxSessionNr) {
				maxSessionNr = s.Number;
			}
		}
		return maxSessionNr;
	}
}
