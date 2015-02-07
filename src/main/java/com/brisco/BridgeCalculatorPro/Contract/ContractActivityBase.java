package com.brisco.BridgeCalculatorPro.Contract;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.brisco.BridgeCalculatorPro.R;

public class ContractActivityBase extends Activity implements
		OnSeekBarChangeListener, OnCheckedChangeListener, OnClickListener {
	protected ContractViews _views = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contract);
		_views = new ContractViews(this);
		_views.Tricks.setOnSeekBarChangeListener(this);
		_views.Doubled.setOnClickListener(this);
		_views.ReDoubled.setOnClickListener(this);
		_views.PlayedBy.setOnClickListener(this);
		_views.OKButton.setOnClickListener(this);

		for (TextView tv : _views.Suit) {
			tv.setOnClickListener(this);
		}
		for (TextView tv : _views.Level) {
			tv.setOnClickListener(this);
		}

		_views.TricksMinus.setOnClickListener(this);
		_views.TricksPlus.setOnClickListener(this);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (seekBar.getId() == _views.Tricks.getId()) {
			_views.TricksLabel.setText("Tricks: " + Integer.toString(progress));
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onCheckedChanged(CompoundButton btn, boolean checked) {
	}

	@Override
	public void onClick(View v) {
		if (v == _views.TricksPlus) {
			_views.Tricks.incrementProgressBy(1);
		} else if (v == _views.TricksMinus) {
			_views.Tricks.incrementProgressBy(-1);
		} else {
			int id = v.getId();
			int tricks = 13;
			switch (id) {
			case R.id.ContractLevelPass:
				tricks = 6;
			case R.id.ContractLevel1:
				tricks--;
			case R.id.ContractLevel2:
				tricks--;
			case R.id.ContractLevel3:
				tricks--;
			case R.id.ContractLevel4:
				tricks--;
			case R.id.ContractLevel5:
				tricks--;
			case R.id.ContractLevel6:
				tricks--;
			case R.id.ContractLevel7:
				// Disabled checked, and uncheck others(when unchecking we
				// trigger
				// oncheckedchanged, but do nothing in that case...)
				_views.ToggleToggleButtons(_views.Level, id);
				_views.Tricks.setProgress(tricks);
				break;
			case R.id.ContractSuitClubs:
			case R.id.ContractSuitDiamonds:
			case R.id.ContractSuitHearts:
			case R.id.ContractSuitSpades:
			case R.id.ContractSuitNT:
				_views.ToggleToggleButtons(_views.Suit, id);
				break;
			}

		}
	}
}