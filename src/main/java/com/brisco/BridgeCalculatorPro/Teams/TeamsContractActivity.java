package com.brisco.BridgeCalculatorPro.Teams;

import java.io.IOException;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.brisco.BridgeCalculatorPro.BridgeCalculatorProApplication;
import com.brisco.BridgeCalculatorPro.R;
import com.brisco.BridgeCalculatorPro.Contract.ContractActivity;
import com.brisco.BridgeCalculatorPro.Contract.ContractActivityBase;
import com.brisco.BridgeCalculatorPro.Contract.Mapping;
import com.brisco.BridgeCalculatorPro.persistence.DB;
import com.brisco.common.Game.Board;
import com.brisco.common.Game.Contract;
import com.brisco.common.Game.Suit;
import com.brisco.common.Score.Calculator;

public class TeamsContractActivity extends ContractActivityBase {
	public static final int GET_CONTRACT = 123;
	public static final int DIALOG_CHANGE_BOARDNUMBER = 0;
	public static final String BOARDNUMBER = "BOARD_NUMBER";
	private int _boardNr;
	private int _session = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Find existing contract-info
		BridgeCalculatorProApplication app = (BridgeCalculatorProApplication) getApplication();
		TeamsEvent _event = (TeamsEvent) app.GetCompetition();
		Bundle extras = getIntent().getExtras();
		_boardNr = extras.getInt(BOARDNUMBER);
		TeamsResult _teamsResult = _event.GetResult(_boardNr, _session);
		if (_teamsResult != null) {
			int room = extras.getInt(BoardActivity.REQUEST_CODE);
			if (room == BoardActivity.GET_CONTRACT_OPEN) {
				_views.SetContract(_teamsResult.OpenContract);
			} else if (room == BoardActivity.GET_CONTRACT_CLOSED) {
				_views.SetContract(_teamsResult.ClosedContract);
			}
		}
		FillBoardInfo();

		for (Button b : _views.Declarer) {
			b.setOnClickListener(this);
		}
		_views.PlayedBy.setVisibility(View.GONE);
		_views.CustomButtonsLayout.setVisibility(View.GONE);
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
		UpdateContractLabel();
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
		UpdateContractLabel();
	}

	@Override
	public void onClick(View v) {
		if (v == _views.OKButton) {
			String errorMessage = IsContractFilled();
			if (errorMessage != null) {
				new AlertDialog.Builder(this).setMessage(errorMessage)
						.setPositiveButton("OK", null).show();
			} else {
				Contract contract = _views.GetContract();
				Intent resultIntent = new Intent();
				try {
					byte[] byteArray = DB.GetByteArrayFromObject(contract);
					resultIntent.putExtra(ContractActivity.CONTRACT, byteArray);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				;
				setResult(RESULT_OK, resultIntent);

				finish();
			}
		} else {
			for (Button b : _views.Declarer) {
				if (v == b) {
					_views.ToggleToggleButtons(_views.Declarer, v.getId());
				}
			}
		}
		super.onClick(v);
		UpdateContractLabel();
	}

	private void UpdateContractLabel() {
		String contractString = null;
		Contract contract = _views.GetContract();
		if (contract.Level >= 0 && contract.Suit != null) {
			contractString = Mapping.GetContractString(contract, this) + " ";
			contractString += Mapping.GetStringFromDirection(contract.Player,
					this) + " ";
			if (contract.Player != null) {
				contractString += Integer.toString(Calculator
						.GetNorthSouthPoints(contract, _boardNr));
			}
		}
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
			} else {
				if (_views.GetDeclarer() == null) {
					return "You must choose a declarer.";
				}
			}
		}
		return null;
	}
}