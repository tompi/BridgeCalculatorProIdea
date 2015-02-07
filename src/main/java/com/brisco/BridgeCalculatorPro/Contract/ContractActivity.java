package com.brisco.BridgeCalculatorPro.Contract;

import java.io.IOException;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import com.brisco.BridgeCalculatorPro.BridgeCalculatorProApplication;
import com.brisco.BridgeCalculatorPro.IAddContractHandler;
import com.brisco.BridgeCalculatorPro.persistence.DB;
import com.brisco.common.Game.Contract;
import com.brisco.common.Game.Suit;

public class ContractActivity extends ContractActivityBase {
	public static final int GET_CONTRACT = 123;
	public static final String CONTRACT = "CONTRACT";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_views.CustomButtonsLayout.setVisibility(View.GONE);
		_views.DeclarerLayout.setVisibility(View.GONE);
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
				IAddContractHandler addContractHandler = ((BridgeCalculatorProApplication) getApplication())
						.GetIAddContractHandler();
				Contract contract = _views.GetContract();
				if (addContractHandler != null) {
					addContractHandler.AddContract(contract);
					setResult(RESULT_OK);
				} else {
					Intent resultIntent = new Intent();
					try {
						byte[] byteArray = DB.GetByteArrayFromObject(contract);
						resultIntent.putExtra(CONTRACT, byteArray);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					;
					setResult(RESULT_OK, resultIntent);
				}

				finish();
			}
		}
		super.onClick(v);
		UpdateContractLabel();
	}

	private void UpdateContractLabel() {
		String contractString = null;
		Contract contract = _views.GetContract();
		if (contract.Level >= 0 && contract.Suit != null) {
			contractString = Mapping.GetContractString(contract, this);
			if (contract.Level > 0) {
				contractString += " by "
						+ (_views.PlayedBy.isChecked() ? "us" : "them");
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
			}
		}
		return null;
	}
}