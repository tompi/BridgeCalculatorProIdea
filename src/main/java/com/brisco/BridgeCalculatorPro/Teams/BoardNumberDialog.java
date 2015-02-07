package com.brisco.BridgeCalculatorPro.Teams;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.brisco.BridgeCalculatorPro.BridgeCalculatorProApplication;
import com.brisco.BridgeCalculatorPro.R;
import com.brisco.BridgeCalculatorPro.Settings;

public class BoardNumberDialog extends Dialog implements
		android.view.View.OnClickListener {
	private TextView _boardNumberView;
	private Button _okButton;
	private int _boardNumber;
	private TextView _plus;
	private TextView _minus;
	private TeamsEvent _event;
	private BoardActivity _boardActivity;
	private int _session = 0;
	private Settings _settings;

	public BoardNumberDialog(Context context) {
		super(context);
		setContentView(R.layout.board_picker);
		BridgeCalculatorProApplication app = (BridgeCalculatorProApplication) context
				.getApplicationContext();
		_settings = app.GetSettings(context);
		setTitle("Choose board number:");
		_boardNumberView = (TextView) findViewById(R.id.boardpickerNumber);
		_okButton = (Button) findViewById(R.id.boardPickerOK);
		_plus = (TextView) findViewById(R.id.boardpickerPlus);
		_minus = (TextView) findViewById(R.id.boardpickerMinus);
		_plus.setOnClickListener(this);
		_minus.setOnClickListener(this);
		_okButton.setOnClickListener(this);
	}

	public void setBoardNumber(int boardNumber, Resources resources) {
		_boardNumber = boardNumber;
		_boardNumberView.setBackgroundDrawable(resources
				.getDrawable(BoardActivity
						.getVulnerabilityDrawable(_boardNumber)));
		_boardNumberView.setText(Integer.toString(_boardNumber));
		// Update parent view:
		_boardActivity.SetBoardNumber(_boardNumber);
		// Check that it still makes sense to click minus:
		_minus.setEnabled(_event.GetPreviousUnregisteredBoardNumber(
				boardNumber, _session) > 0);
	}

	public void setBoardActivity(BoardActivity boardActivity) {
		_boardActivity = boardActivity;
	}

	public void setEvent(TeamsEvent event) {
		_event = event;
	}

	@Override
	public void onClick(View v) {
		if (v == _plus) {
			_settings.LookDownWardsForNextFreeBoardNumber = false;
			setBoardNumber(_event.GetNextUnregisteredBoardNumber(_boardNumber,
					_session), getContext().getResources());
		} else if (v == _minus) {
			_settings.LookDownWardsForNextFreeBoardNumber = true;
			setBoardNumber(_event.GetPreviousUnregisteredBoardNumber(
					_boardNumber, _session), getContext().getResources());
		} else if (v == _okButton) {
			_settings.LastChosenBoardNumber = _boardNumber;
			dismiss();
		}
	}
}
