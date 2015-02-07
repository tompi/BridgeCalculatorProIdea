package com.brisco.BridgeCalculatorPro.Rubber;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.brisco.BridgeCalculatorPro.BridgeCalculatorProApplication;
import com.brisco.BridgeCalculatorPro.R;
import com.brisco.BridgeCalculatorPro.Contract.ContractActivityBase;
import com.brisco.BridgeCalculatorPro.Contract.Mapping;
import com.brisco.common.Game.Board;
import com.brisco.common.Game.Contract;
import com.brisco.common.Game.Suit;
import com.brisco.common.Score.Calculator;
import com.brisco.common.Score.IMPCalculator;
import com.brisco.common.Score.RussianIMPCalculator;

public class RussianBoardActivity extends ContractActivityBase {
	public static final int GET_CONTRACT = 123;
	public static final int DIALOG_CHANGE_BOARDNUMBER = 0;
	public static final String BOARDNUMBER = "BOARD_NUMBER";
	private int _boardNr;
	private RussianChicago _event;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_boardNr = getIntent().getExtras().getInt(BOARDNUMBER);
		BridgeCalculatorProApplication application = ((BridgeCalculatorProApplication) getApplication());
		_event = (RussianChicago) application.GetCompetition();
		_views.CustomButtonsLayout.setVisibility(View.GONE);
		_views.DeclarerLayout.setVisibility(View.GONE);
		_views.HCP.setVisibility(View.VISIBLE);
		_views.HCPLayout.setVisibility(View.VISIBLE);
		UpdateLabels();
		FillBoardInfo();
		_views.HCPPlus.setOnClickListener(this);
		_views.HCPMinus.setOnClickListener(this);
		_views.HCP.setOnSeekBarChangeListener(this);
		for (Button b : _views.Declarer) {
			b.setOnClickListener(this);
		}
	}

	public void SetBoardNumber(int boardNumber) {
		_boardNr = boardNumber;
		FillBoardInfo();
	}

	private void FillBoardInfo() {
		_views.Vulnerability.setBoardNumber(_boardNr);
		_views.BoardNumber.setVisibility(View.VISIBLE);
		int vulnDrawable = getVulnerabilityDrawable(_boardNr);
		_views.BoardNumber.setBackgroundDrawable(getResources().getDrawable(
				vulnDrawable));
		_views.BoardNumber.setText(Integer.toString(_boardNr));
		UpdateLabels();
	}

	public static int getVulnerabilityDrawable(int boardNumber) {
		switch (Board.GetVulnerability(boardNumber)) {
		case Both:
			return R.drawable.vuln_all;
		case None:
			return R.drawable.vuln_none;
		case NorthSouth:
			return R.drawable.vuln_ns;
		case EastWest:
			return R.drawable.vuln_ew;
		default:
			return R.drawable.vuln_none;
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		super.onProgressChanged(seekBar, progress, fromUser);
		UpdateLabels();
	}

	@Override
	public void onClick(View v) {
		if (v == _views.OKButton) {
			String errorMessage = IsContractFilled();
			if (errorMessage != null) {
				new AlertDialog.Builder(this).setMessage(errorMessage)
						.setPositiveButton("OK", null).show();
			} else {
				Contract c = _views.GetContract();
				int hcp = _views.HCP.getProgress();
				_event.AddContract(c, hcp);
				setResult(RESULT_OK);
				finish();
			}
		} else if (v == _views.HCPPlus) {
			_views.HCP.incrementProgressBy(1);
			UpdateLabels();
		} else if (v == _views.HCPMinus) {
			_views.HCP.incrementProgressBy(-1);
			UpdateLabels();
		} else {
			for (Button b : _views.Declarer) {
				if (v == b) {
					_views.ToggleToggleButtons(_views.Declarer, v.getId());
				}
			}
		}
		super.onClick(v);
		UpdateLabels();
	}

	private void UpdateLabels() {
		String hcpString = null;
		String contractString = null;
		int hcp = _views.HCP.getProgress();
		_views.HCPLabel.setText("HCP: " + Integer.toString(hcp));
		Contract contract = _views.GetContract();
		if (contract.Level >= 0 && contract.Suit != null) {
			boolean vulnerable = _event.GetVulnerability(_boardNr - 1)
					.IsVulnerable(contract.Player);
			int expectedResult = RussianIMPCalculator.GetExpectedResult(hcp,
					vulnerable);
			hcpString = Integer.toString(hcp) + " hcp ";
			hcpString += vulnerable ? "vuln" : "not vuln";
			hcpString += " should be " + Integer.toString(expectedResult);
			int actualResult = Calculator.GetNorthSouthPoints(contract,
					_boardNr);
			int IMPs = IMPCalculator.GetNorthSouthIMP(actualResult,
					expectedResult);
			contractString = Mapping.GetContractString(contract, this) + " ";

			if (contract.Player != null) {
				contractString += "(" + Integer.toString(actualResult) + "): ";
				contractString += Integer.toString(IMPs) + " IMP";
			}
		}
		_views.RussianLabel.setText(hcpString);
		_views.ContractLabel.setText(contractString);
	}

	private String IsContractFilled() {
		int level = _views.GetLevel();
		if (level < 0) {
			return "You must mark the contract level, 1-7 or pass.";
		}
		if (level > 0) {
			Suit suit = _views.GetSuit();
			if (suit == null) {
				return "You must choose a suit.";
			}
		}
		return null;
	}
}