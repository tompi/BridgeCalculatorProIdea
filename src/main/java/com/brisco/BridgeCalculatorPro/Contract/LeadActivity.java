package com.brisco.BridgeCalculatorPro.Contract;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ToggleButton;

import com.brisco.BridgeCalculatorPro.R;
import com.brisco.BridgeCalculatorPro.persistence.DB;
import com.brisco.common.Game.Card;

public class LeadActivity extends Activity implements OnClickListener {
	public static final int GET_LEAD = 27;
	public static final String LEAD = "LEAD";
	private CardViews _views;
	private Card _lead;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leadinput);
		_views = new CardViews(this);
		Intent i = getIntent();
		if (i.hasExtra(LEAD)) {
			_lead = (Card) DB.GetObjectFromByteArray(i.getByteArrayExtra(LEAD));
			DisplayLead();
		}

		for (Button b : _views.Suit) {
			b.setOnClickListener(this);
		}
		for (ToggleButton b : _views.Denomination) {
			b.setOnClickListener(this);
		}
		_views.OKButton.setOnClickListener(this);
	}

	private void DisplayLead() {
		_views.markSelectedCard(_lead);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.dealSuitClubs:
		case R.id.dealSuitDiamonds:
		case R.id.dealSuitHearts:
		case R.id.dealSuitSpades:
			_views.ToggleToggleButtons(_views.Suit, id);
			SetSelectedLead();
			break;
		case R.id.dealAce:
		case R.id.dealKing:
		case R.id.dealQueen:
		case R.id.dealJack:
		case R.id.dealTen:
		case R.id.dealNine:
		case R.id.dealEight:
		case R.id.dealSeven:
		case R.id.dealSix:
		case R.id.dealFive:
		case R.id.dealFour:
		case R.id.dealThree:
		case R.id.dealTwo:
			_views.ToggleToggleButtons(_views.Denomination, id);
			SetSelectedLead();
			break;
		case R.id.cardInputOKBtn:
			Intent resultIntent = new Intent();
			try {
				byte[] byteArray = DB.GetByteArrayFromObject(_lead);
				resultIntent.putExtra(LEAD, byteArray);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			;
			setResult(RESULT_OK, resultIntent);
			finish();
			break;
		}
	}

	private void SetSelectedLead() {
		List<Card> cards = _views.getCards();
		if (cards != null && cards.size() > 0) {
			_lead = cards.get(0);
		}
	}

}