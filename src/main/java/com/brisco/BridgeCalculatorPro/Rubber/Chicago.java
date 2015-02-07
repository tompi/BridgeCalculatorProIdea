package com.brisco.BridgeCalculatorPro.Rubber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.brisco.BridgeCalculatorPro.Competition;
import com.brisco.BridgeCalculatorPro.IAddContractHandler;
import com.brisco.common.Game.Contract;
import com.brisco.common.Game.Vulnerability;
import com.brisco.common.PBN.Game.Game;
import com.brisco.common.PBN.Game.Identification;
import com.brisco.common.PBN.Game.Supplemental;
import com.brisco.common.Score.Calculator;

public class Chicago extends Competition implements IAddContractHandler,
		Serializable {
	private static final long serialVersionUID = -5764418201004607573L;
	protected Contract[] _contracts;
	// HACK: difficult to get rid of because of serialization...
	private static final Vulnerability[] _vulnerability = { Vulnerability.None,
			Vulnerability.NorthSouth, Vulnerability.EastWest,
			Vulnerability.Both };

	public Chicago() {
		super();
		_contracts = new Contract[4];
	}

	public Contract GetContract(int index) {
		return _contracts[index];
	}

	@Override
	public void AddContract(Contract contract) {
		int ix = GetContractsSize();
		if (ix < 4) {
			_contracts[ix] = contract;
		}
	}

	public ArrayList<Integer> GetScores() {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		int i = 0;
		for (Contract c : _contracts) {
			if (c != null) {
				ret.add(Calculator.GetNorthSouthPoints(c,
						_vulnerability[i].IsVulnerable(c.Player)));
			}
			i++;
		}
		return ret;
	}

	public Vulnerability GetVulnerability() {
		int ix = GetContractsSize();
		if (ix == 4) {
			// There is no more than 4 rounds...
			ix = 3;
		}
		return _vulnerability[ix];
	}

	protected Vulnerability GetVulnerability(int ix) {
		return _vulnerability[ix];
	}

	public int GetContractsSize() {
		int size = 0;
		for (Contract c : _contracts) {
			if (c != null)
				size++;
		}
		return size;
	}

	public void DeleteLastGame() {
		int size = GetContractsSize() - 1;
		_contracts[size] = null;
	}

	@Override
	public List<Game> GetPBNGames() {
		ArrayList<Game> ret = new ArrayList<Game>();

		int contractNumber = 1;
		for (Contract contract : _contracts) {
			if (contract != null) {
				Vulnerability vulnerability = _vulnerability[contractNumber - 1];
				Game game = new Game();
				game.Identification = GetPBNIdentification(contractNumber,
						contract, vulnerability);
				boolean vulnerable = vulnerability
						.IsVulnerable(contract.Player);
				game.Supplemental = new Supplemental();
				game.Supplemental.Application = "Bridge Calculator Pro";
				game.Supplemental.Competition = "Chicago";
				game.Supplemental.DealId = Integer.toString(contractNumber);
				game.Supplemental.Mode = "TABLE";
				game.Supplemental.Round = game.Supplemental.DealId;
				game.Supplemental.Score = Integer.toString(Calculator
						.GetPoints(contract, vulnerable));
				ret.add(game);
			}
			contractNumber++;
		}
		return ret;

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
		id.Scoring = "Chicago";
		id.Result = contract.Tricks;
		id.Site = null;
		id.Vulnerable = vulnerability;
		return id;
	}

	@Override
	public void AppendHTML(StringBuilder html) {
		ChicagoHTMLRenderer renderer = new ChicagoHTMLRenderer(this);
		renderer.Render(html);
	}

}
