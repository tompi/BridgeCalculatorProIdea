package com.brisco.BridgeCalculatorPro.ScoreSheet;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.brisco.BridgeCalculatorPro.BridgeCalculatorProApplication;
import com.brisco.BridgeCalculatorPro.R;
import com.brisco.BridgeCalculatorPro.Settings;
import com.brisco.BridgeCalculatorPro.persistence.DB;
import com.brisco.common.Game.Deal;
import com.brisco.common.Game.Direction;
import com.brisco.common.Game.Hand;
import com.brisco.common.Game.HandMissing;
import com.brisco.common.Game.Suit;

public class DealActivity extends Activity implements OnClickListener {
	public static final int GET_DEAL = 24;
	public static final String DEAL = "DEAL";
	private int _boardNr;
	private Deal _deal;
	private DealViews _views;
	private Settings _settings;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dealinput);
		_views = new DealViews(this);
		BridgeCalculatorProApplication app = (BridgeCalculatorProApplication) getApplication();
		Intent i = getIntent();
		_boardNr = i.getExtras().getInt("BOARD_NUMBER");
		if (i.hasExtra(DEAL)) {
			_deal = (Deal) DB.GetObjectFromByteArray(i.getByteArrayExtra(DEAL));
		}
		if (_deal == null) {
			_deal = new Deal();
		}
		_settings = app.GetSettings(this);
		DisplayDeal();
		for (Button b : _views.Suit) {
			b.setOnClickListener(this);
		}
		for (ToggleButton b : _views.Denomination) {
			b.setOnClickListener(this);
		}
		_views.HandWest.setOnClickListener(this);
		_views.HandNorth.setOnClickListener(this);
		_views.HandEast.setOnClickListener(this);
		_views.HandSouth.setOnClickListener(this);
		_views.OKButton.setOnClickListener(this);

		SetActiveHand(_views.HandNorth);
	}

	private void DisplayDeal() {
		_views.Vulnerability.setBoardNumber(_boardNr);

		FillHand(_deal.West, _views.HandWest);
		FillHand(_deal.North, _views.HandNorth);
		FillHand(_deal.East, _views.HandEast);
		FillHand(_deal.South, _views.HandSouth);
	}

	private void FillHand(Hand hand, LinearLayout handView) {
		AddSuit(handView, hand, Suit.Spades);
		AddSuit(handView, hand, Suit.Hearts);
		AddSuit(handView, hand, Suit.Diamonds);
		AddSuit(handView, hand, Suit.Clubs);
	}

	private void AddSuit(LinearLayout handView, Hand hand, Suit suit) {
		String handText = Mapping.GetCardsString(hand, suit, _settings);
		_views.GetSuitTextView(handView, suit).setText(handText);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.dealWestCell:
		case R.id.dealNorthCell:
		case R.id.dealEastCell:
		case R.id.dealSouthCell:
			SetActiveHand(v);
			break;
		case R.id.dealSuitClubs:
		case R.id.dealSuitDiamonds:
		case R.id.dealSuitHearts:
		case R.id.dealSuitSpades:
			_views.ToggleToggleButtons(_views.Suit, id);
			UpdateSelectedCards();
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
			CardPressed((ToggleButton) v);
			break;
		case R.id.cardInputOKBtn:
			Intent resultIntent = new Intent();
			try {
				byte[] byteArray = DB.GetByteArrayFromObject(_deal);
				resultIntent.putExtra(DEAL, byteArray);
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

	private void UpdateSelectedCards() {
		Suit suit = _views.GetSuit();
		Direction direction = _views.getDirection();
		_views.markSelectedCards(suit, _deal, direction);
	}

	private void SetActiveHand(View v) {
		_views.ToggleToggleButtons(_views.Suit, R.id.dealSuitSpades);
		for (LinearLayout l : _views.Hands) {
			boolean selected = l == v;
			l.setSelected(selected);
		}
		UpdateSelectedCards();
	}

	private void RefreshHandDisplay(Direction direction) {
		switch (direction) {
		case West:
			FillHand(_deal.West, _views.HandWest);
			break;
		case North:
			FillHand(_deal.North, _views.HandNorth);
			break;
		case East:
			FillHand(_deal.East, _views.HandEast);
			break;
		case South:
			FillHand(_deal.South, _views.HandSouth);
			break;
		}
	}

	private void CardPressed(ToggleButton cardButton) {
		Direction direction = _views.getDirection();
		Hand h = _deal.getHand(direction);
		if (h != null) {
			if (cardButton.isChecked()) {
				// Don't allow selection after hand contains 13 cards:
				if (h.isComplete()) {
					cardButton.setChecked(false);
					return;
				}
			}
		}
		Suit suit = _views.GetSuit();
		if (h == null) {
			h = new Hand();
			_deal.setHand(direction, h);
		} else {
			h.removeSuit(suit);
		}
		h.addCards(_views.getCards());
		// If last card, move focus to next hand and maybe fill missing hand(if
		// one is missing):
		if (h.isComplete()) {
			FindMissingHand(direction, h);
			switch (direction) {
			case North:
				SetActiveHand(_views.HandEast);
				break;
			case East:
				SetActiveHand(_views.HandSouth);
				break;
			case South:
				SetActiveHand(_views.HandWest);
				break;
			case West:
				SetActiveHand(_views.HandNorth);
				break;
			}
		}
		RefreshHandDisplay(direction);
	}

	private void FindMissingHand(Direction directionChecked, Hand handChecked) {
		// If we have 3 complete hands, we should calculate the last:
		Direction missingHand = null;
		ArrayList<Hand> completeHands = new ArrayList<Hand>();
		completeHands.add(handChecked);
		if (HandIsMissing(completeHands, directionChecked, Direction.North)) {
			missingHand = Direction.North;
		}
		if (HandIsMissing(completeHands, directionChecked, Direction.South)) {
			missingHand = Direction.South;
		}
		if (HandIsMissing(completeHands, directionChecked, Direction.East)) {
			missingHand = Direction.East;
		}
		if (HandIsMissing(completeHands, directionChecked, Direction.West)) {
			missingHand = Direction.West;
		}
		if (completeHands.size() == 3) {
			Hand hand = HandMissing.GetHandMissing(completeHands.get(0),
					completeHands.get(1), completeHands.get(2));
			_deal.setHand(missingHand, hand);
			RefreshHandDisplay(missingHand);
		}
	}

	private boolean HandIsMissing(ArrayList<Hand> completeHands,
			Direction directionChecked, Direction direction) {
		if (direction != directionChecked) {
			Hand hand = _deal.getHand(direction);
			if (hand != null && hand.isComplete()) {
				completeHands.add(hand);
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
}