package com.brisco.BridgeCalculatorPro.Teams;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.brisco.BridgeCalculatorPro.BridgeCalculatorProApplication;
import com.brisco.BridgeCalculatorPro.R;
import com.brisco.BridgeCalculatorPro.Settings;
import com.brisco.BridgeCalculatorPro.Contract.ContractActivity;
import com.brisco.BridgeCalculatorPro.ScoreSheet.Mapping;
import com.brisco.BridgeCalculatorPro.persistence.DB;
import com.brisco.common.Game.Board;
import com.brisco.common.Game.Contract;
import com.brisco.common.Score.Calculator;
import com.brisco.common.Score.IMPCalculator;

public class BoardActivity extends Activity implements OnClickListener {
	public static final int GET_CONTRACT_OPEN = 12;
	public static final int GET_CONTRACT_CLOSED = 13;
	public static final int DIALOG_CHANGE_BOARDNUMBER = 0;
	public static final String BOARDNUMBER = "BOARD_NUMBER";
	public static final String REQUEST_CODE = "REQUEST_CODE";
	private int _boardNr;
	private TeamsEvent _event;
	private TeamsResult _result;
	private BoardViews _views;
	private Settings _settings;
	private int _session = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teams_board);
		BridgeCalculatorProApplication app = (BridgeCalculatorProApplication) getApplication();
		_settings = app.GetSettings(this);
		_event = (TeamsEvent) app.GetCompetition();
		_boardNr = getIntent().getExtras().getInt(BOARDNUMBER);
		_result = _event.GetResult(_boardNr, _session);
		if (_result == null) {
			_result = new TeamsResult();
			_result.BoardNumber = _boardNr;
		}
		_views = new BoardViews(this);
		_views.OpenRoomLayout.setOnClickListener(this);
		_views.ClosedRoomLayout.setOnClickListener(this);
		_views.OKButton.setOnClickListener(this);
		_views.SwapButton.setOnClickListener(this);
		_views.BoardNumberButton.setOnClickListener(this);
		_views.ClosedDeleteButton.setOnClickListener(this);
		_views.OpenDeleteButton.setOnClickListener(this);
		FillBoardInfo();
	}

	public void SetBoardNumber(int boardNumber) {
		_boardNr = boardNumber;
		_result.BoardNumber = boardNumber;
		if (_result.ClosedAuction != null) {
			_result.ClosedAuction.Dealer = Board.GetDealer(boardNumber);
		}
		if (_result.OpenAuction != null) {
			_result.OpenAuction.Dealer = Board.GetDealer(boardNumber);
		}
		FillBoardInfo();
	}

	private void FillBoardInfo() {
		int vulnDrawable = getVulnerabilityDrawable(_boardNr);
		Resources res = getResources();
		_views.BoardNumberButton.setBackgroundDrawable(res
				.getDrawable(vulnDrawable));
		_views.BoardNumberButton.setText(Integer.toString(_boardNr));
		_views.DealerTextView.setText(Mapping.GetStringFromDirection(
				Board.GetDealer(_boardNr), _settings));
		FillRoom(true, _result.OpenContract);
		_views.OpenRoomLayout.setBackgroundColor(res.getColor(R.color.blue));
		FillRoom(false, _result.ClosedContract);
		_views.ClosedRoomLayout
				.setBackgroundColor(res.getColor(R.color.brown2));
		if (_result.OpenContract != null && _result.ClosedContract != null) {
			int nsScore = IMPCalculator.GetNorthSouthIMP(_boardNr,
					_result.OpenContract, _result.ClosedContract);
			_views.ScoreTextView.setText("NS: " + Integer.toString(nsScore)
					+ " IMP");
		}
	}

	private void FillRoom(boolean openRoom, Contract contract) {
		if (contract != null) {
			_views.GetRoomNameView(openRoom).setText(
					openRoom ? "Open room:" : "Closed room:");
			_views.GetRoomContractView(openRoom).setText(
					com.brisco.BridgeCalculatorPro.Contract.Mapping
							.GetContractStringWithoutTricks(contract, this));
			_views.GetRoomTricksView(openRoom).setText(
					Integer.toString(contract.Tricks));
			_views.GetRoomDeclarerView(openRoom).setText(
					com.brisco.BridgeCalculatorPro.Contract.Mapping
							.GetStringFromDirection(contract.Player, this));
			_views.GetRoomScoreView(openRoom).setText(
					Integer.toString(Calculator.GetNorthSouthPoints(contract,
							_boardNr)));
		} else {
			String text = "Press to enter score for "
					+ (openRoom ? "open" : "closed") + " room.";
			_views.GetRoomNameView(openRoom).setText(text);
			_views.GetRoomContractView(openRoom).setText("");
			_views.GetRoomTricksView(openRoom).setText("");
			_views.GetRoomDeclarerView(openRoom).setText("");
			_views.GetRoomScoreView(openRoom).setText("");
		}
		_views.GetRoomIconView(openRoom).setImageDrawable(
				getResources().getDrawable(
						openRoom ? R.drawable.ic_medium_open
								: R.drawable.ic_medium_closed));
	}

	static int getVulnerabilityDrawable(int boardNumber) {
		return com.brisco.BridgeCalculatorPro.ScoreSheet.BoardActivity
				.getVulnerabilityDrawable(boardNumber);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.teamsBoardOpenRoom:
			Intent i = new Intent(this, TeamsContractActivity.class);
			i.putExtra(TeamsContractActivity.BOARDNUMBER, _boardNr);
			i.putExtra(REQUEST_CODE, GET_CONTRACT_OPEN);
			startActivityForResult(i, GET_CONTRACT_OPEN);
			break;
		case R.id.teamsBoardClosedRoom:
			i = new Intent(this, TeamsContractActivity.class);
			i.putExtra(TeamsContractActivity.BOARDNUMBER, _boardNr);
			i.putExtra(REQUEST_CODE, GET_CONTRACT_CLOSED);
			startActivityForResult(i, GET_CONTRACT_CLOSED);
			break;
		case R.id.teamsBoardSwapBtn:
			Contract tmp = _result.ClosedContract;
			_result.ClosedContract = _result.OpenContract;
			_result.OpenContract = tmp;
			FillBoardInfo();
			break;
		case R.id.teamsBoardClosedDeleteBtn:
			ClearRoom(false);
			break;
		case R.id.teamsBoardOpenDeleteBtn:
			ClearRoom(true);
			break;
		case R.id.teamsBoardOKBtn:
			setResult(RESULT_OK);
			_event.addResult(_result, _session);
			finish();
			break;
		case R.id.teamsBoardNumberBtn:
			showDialog(DIALOG_CHANGE_BOARDNUMBER);
			break;
		}
	}

	private void ClearRoom(final boolean openRoom) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"Are you sure you want to clear results from "
						+ (openRoom ? "open" : "closed") + " room?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								if (openRoom) {
									_result.OpenContract = null;
								} else {
									_result.ClosedContract = null;
								}
								FillBoardInfo();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case GET_CONTRACT_OPEN:
			case GET_CONTRACT_CLOSED:
				Bundle extras = data.getExtras();
				if (extras.containsKey(ContractActivity.CONTRACT)) {
					Contract contract = (Contract) DB
							.GetObjectFromByteArray(extras
									.getByteArray(ContractActivity.CONTRACT));
					if (requestCode == GET_CONTRACT_OPEN) {
						_result.OpenContract = contract;
					} else {
						_result.ClosedContract = contract;
					}
					_event.addResult(_result, _session);
				}
				break;
			}
			FillBoardInfo();
		}
	}

}