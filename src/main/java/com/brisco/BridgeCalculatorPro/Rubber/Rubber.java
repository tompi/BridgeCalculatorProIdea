package com.brisco.BridgeCalculatorPro.Rubber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.brisco.BridgeCalculatorPro.Competition;
import com.brisco.BridgeCalculatorPro.IAddContractHandler;
import com.brisco.common.Game.Contract;
import com.brisco.common.Game.Direction;
import com.brisco.common.Game.Vulnerability;
import com.brisco.common.PBN.Game.Game;
import com.brisco.common.PBN.Game.Identification;
import com.brisco.common.PBN.Game.Supplemental;
import com.brisco.common.Score.Calculator;

public class Rubber extends Competition implements IAddContractHandler,
		Serializable {
	private static final long serialVersionUID = 6476938995031611566L;
	private ArrayList<Contract> _contracts;

	public Rubber() {
		super();
		_contracts = new ArrayList<Contract>();
	}

	public Contract GetContract(int index) {
		return _contracts.get(index);
	}

	public RubberResult GetRubberResult() {
		RubberResult result = new RubberResult();

		int contractNumber = 0;
		for (Contract contract : _contracts) {

			Vulnerability vulnerability = result.GetVulnerability();
			boolean vulnerable = vulnerability.IsVulnerable(contract.Player);
			boolean pointsForNorthSouth = false;
			boolean contractMade = contract.ContractMade();
			if (contract.Player == Direction.North
					|| contract.Player == Direction.South) {
				pointsForNorthSouth = contractMade;
			} else {
				pointsForNorthSouth = !contractMade;
			}

			int belowLine = 0;
			int aboveLine = 0;

			if (contract.Level > 0) {
				if (!contractMade) {
					aboveLine = -1
							* Calculator.GetPenalty(contract, vulnerable);
				} else {
					belowLine = Calculator.GetContractPoints(contract);
					aboveLine = Calculator.GetLevelBonus(contract, vulnerable,
							belowLine, true);
					aboveLine += Calculator.GetInsultBonus(contract);
					aboveLine += Calculator.GetOverTrickPoints(contract,
							vulnerable);
				}
			}

			result.AddContractResult(pointsForNorthSouth, aboveLine, belowLine,
					contractNumber);
			contractNumber++;
		}

		return result;
	}

	@Override
	public void AddContract(Contract contract) {
		_contracts.add(contract);
	}

	public int GetContractsSize() {
		return _contracts.size();
	}

	public void DeleteLastGame() {
		if (_contracts.size() > 0) {
			_contracts.remove(_contracts.size() - 1);
		}
	}

	@Override
	public List<Game> GetPBNGames() {
		if (_contracts.size() < 1) {
			return null;
		}
		ArrayList<Game> ret = new ArrayList<Game>();
		RubberResult result = new RubberResult();

		int contractNumber = 1;
		for (Contract contract : _contracts) {
			Vulnerability vulnerability = result.GetVulnerability();
			Game game = new Game();
			game.Identification = GetPBNIdentification(contractNumber,
					contract, vulnerability);
			boolean vulnerable = vulnerability.IsVulnerable(contract.Player);
			boolean pointsForNorthSouth = false;
			boolean contractMade = contract.ContractMade();
			if (contract.Player == Direction.North
					|| contract.Player == Direction.South) {
				pointsForNorthSouth = contractMade;
			} else {
				pointsForNorthSouth = !contractMade;
			}

			int belowLine = 0;
			int aboveLine = 0;

			if (contract.Level > 0) {
				if (!contractMade) {
					aboveLine = -1
							* Calculator.GetPenalty(contract, vulnerable);
				} else {
					belowLine = Calculator.GetContractPoints(contract);
					aboveLine = Calculator.GetLevelBonus(contract, vulnerable,
							belowLine, true);
					aboveLine += Calculator.GetInsultBonus(contract);
					aboveLine += Calculator.GetOverTrickPoints(contract,
							vulnerable);
				}
			}
			game.Supplemental = new Supplemental();
			game.Supplemental.Application = "Bridge Calculator Pro";
			game.Supplemental.Competition = "Rubber";
			game.Supplemental.DealId = Integer.toString(contractNumber);
			game.Supplemental.Mode = "TABLE";
			game.Supplemental.Round = game.Supplemental.DealId;
			game.Supplemental.ScoreRubber = GetPBNScore(aboveLine, belowLine);
			game.Supplemental.ScoreRubberHistory = GetPBNScoreHistory(result);

			result.AddContractResult(pointsForNorthSouth, aboveLine, belowLine,
					contractNumber);
			contractNumber++;
			ret.add(game);
		}
		return ret;
	}

	private String GetPBNScoreHistory(RubberResult result) {
		int currentGame = result.GetCurrentGame();
		String NSHistory = GetPBNScoreHistory(currentGame, result.NorthSouth);
		String EWHistory = GetPBNScoreHistory(currentGame, result.EastWest);
		return "NS " + NSHistory + " EW " + EWHistory;
	}

	private String GetPBNScoreHistory(int currentGame,
			RubberDirectionResult results) {
		String ret = "0 " + GetSum(results.AboveLine) + "/"
				+ GetSum(results.BelowLine1);
		if (currentGame > 1) {
			ret = ret + " " + GetSum(results.BelowLine2);
			if (currentGame > 2) {
				ret = ret + " " + GetSum(results.BelowLine3);
			}
		}
		return ret;
	}

	private String GetSum(ArrayList<RubberScore> scores) {
		int i = 0;
		for (RubberScore score : scores) {
			i += score.Score;
		}
		return Integer.toString(i);
	}

	private String GetPBNScore(int aboveLine, int belowLine) {
		return Integer.toString(aboveLine) + "/" + Integer.toString(belowLine);
	}

	private Identification GetPBNIdentification(int contractNumber,
			Contract contract, Vulnerability vulnerability) {
		Identification id = new Identification();
		id.Board = contractNumber;
		id.Contract = contract;
		id.Date = Started;
		id.Declarer = contract.Player;
		id.West = null;
		id.North = null;
		id.East = null;
		id.South = null;
		id.Event = Description;
		id.Deal = null;
		id.Scoring = "Rubber";
		id.Result = contract.Tricks;
		id.Site = null;
		id.Vulnerable = vulnerability;
		return id;
	}

	@Override
	public void AppendHTML(StringBuilder html) {
		RubberHTMLRenderer renderer = new RubberHTMLRenderer(_contracts,
				GetRubberResult());
		renderer.Render(html);
	}
}
