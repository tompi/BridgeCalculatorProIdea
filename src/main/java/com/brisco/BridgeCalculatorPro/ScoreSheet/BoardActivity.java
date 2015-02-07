package com.brisco.BridgeCalculatorPro.ScoreSheet;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.brisco.BridgeCalculatorPro.BridgeCalculatorProApplication;
import com.brisco.BridgeCalculatorPro.R;
import com.brisco.BridgeCalculatorPro.Contract.ContractActivityBase;
import com.brisco.BridgeCalculatorPro.Contract.LeadActivity;
import com.brisco.BridgeCalculatorPro.Contract.Mapping;
import com.brisco.BridgeCalculatorPro.persistence.DB;
import com.brisco.common.Game.Auction;
import com.brisco.common.Game.Board;
import com.brisco.common.Game.Card;
import com.brisco.common.Game.Contract;
import com.brisco.common.Game.Deal;
import com.brisco.common.Game.Suit;
import com.brisco.common.Score.Calculator;

public class BoardActivity extends ContractActivityBase {
	public static final int GET_CONTRACT = 123;
	public static final int DIALOG_CHANGE_BOARDNUMBER = 0;
	public static final String BOARDNUMBER = "BOARD_NUMBER";
	private int _boardNr;
	private Event _event;
	private Result _result;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BridgeCalculatorProApplication app = (BridgeCalculatorProApplication) getApplication();
		_event = (Event) app.GetCompetition();
		_boardNr = getIntent().getExtras().getInt(BOARDNUMBER);
		_result = _event.GetResult(_boardNr);
		if (_result == null) {
			_result = new Result();
			_result.BoardNumber = _boardNr;
		}
		_views.SetContract(_result.Contract);
		FillBoardInfo();
		_views.AuctionButton.setOnClickListener(this);
		_views.CardsButton.setOnClickListener(this);
		_views.LeadButton.setOnClickListener(this);
		_views.BoardNumber.setOnClickListener(this);
		for (Button b : _views.Declarer) {
			b.setOnClickListener(this);
		}
		_views.PlayedBy.setVisibility(View.GONE);
	}

	public void SetBoardNumber(int boardNumber) {
		_boardNr = boardNumber;
		_result.BoardNumber = boardNumber;
		if (_result.Auction != null) {
			_result.Auction.Dealer = Board.GetDealer(boardNumber);
		}
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
				_result.Contract = _views.GetContract();
				_event.addResult(_result);
				setResult(RESULT_OK);
				finish();
			}
		} else if (v == _views.AuctionButton) {
			Intent i = new Intent(this, AuctionActivity.class);
			i.putExtra(BOARDNUMBER, _boardNr);
			if (_result.Auction != null) {
				try {
					i.putExtra(AuctionActivity.AUCTION,
							DB.GetByteArrayFromObject(_result.Auction));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			startActivityForResult(i, AuctionActivity.GET_AUCTION);
		} else if (v == _views.CardsButton) {
			Intent i = new Intent(this, DealActivity.class);
			i.putExtra(BOARDNUMBER, _boardNr);
			if (_result.Deal != null) {
				try {
					i.putExtra(DealActivity.DEAL,
							DB.GetByteArrayFromObject(_result.Deal));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			startActivityForResult(i, DealActivity.GET_DEAL);
		} else if (v == _views.LeadButton) {
			Intent i = new Intent(this, LeadActivity.class);
			if (_result.Contract != null && _result.Lead != null) {
				try {
					i.putExtra(LeadActivity.LEAD,
							DB.GetByteArrayFromObject(_result.Contract.Lead));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			startActivityForResult(i, LeadActivity.GET_LEAD);
		} else if (v == _views.BoardNumber) {
			showDialog(DIALOG_CHANGE_BOARDNUMBER);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case AuctionActivity.GET_AUCTION:
				Bundle extras = data.getExtras();
				if (extras.containsKey(AuctionActivity.AUCTION)) {
					_result.Auction = (Auction) DB
							.GetObjectFromByteArray(extras
									.getByteArray(AuctionActivity.AUCTION));
				}
				break;
			case DealActivity.GET_DEAL:
				_result.Deal = (Deal) DB.GetObjectFromByteArray(data
						.getByteArrayExtra(DealActivity.DEAL));
				break;
			case LeadActivity.GET_LEAD:
				Card lead = (Card) DB.GetObjectFromByteArray(data
						.getByteArrayExtra(LeadActivity.LEAD));
				if (lead != null) {
					if (_result.Contract == null) {
						_result.Contract = new Contract();
					}
					_result.Lead = lead;
					_result.Contract.Lead = lead;
				}
				break;
			}
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog d = null;
		if (id == DIALOG_CHANGE_BOARDNUMBER) {
			d = new BoardNumberDialog(this);
			((BoardNumberDialog) d).setBoardActivity(this);
			((BoardNumberDialog) d).setEvent(_event);
			((BoardNumberDialog) d).setBoardNumber(_boardNr, getResources());
		}
		return d;
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
						.GetNorthSouthPoints(contract, _result.BoardNumber));
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