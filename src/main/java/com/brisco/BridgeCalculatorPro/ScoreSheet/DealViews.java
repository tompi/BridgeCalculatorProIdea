package com.brisco.BridgeCalculatorPro.ScoreSheet;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brisco.BridgeCalculatorPro.R;
import com.brisco.BridgeCalculatorPro.Contract.CardViews;
import com.brisco.BridgeCalculatorPro.Contract.VulnerabilityView;
import com.brisco.common.Game.Direction;
import com.brisco.common.Game.Suit;

public class DealViews extends CardViews {
	public DealViews(Activity activity) {
		super(activity);

		Vulnerability = (VulnerabilityView) Find(R.id.dealVulnerability);

		HandWest = (LinearLayout) Find(R.id.dealWestCell);
		Hands[0] = HandWest;
		HandNorth = (LinearLayout) Find(R.id.dealNorthCell);
		Hands[1] = HandNorth;
		HandEast = (LinearLayout) Find(R.id.dealEastCell);
		Hands[2] = HandEast;
		HandSouth = (LinearLayout) Find(R.id.dealSouthCell);
		Hands[3] = HandSouth;

		OKButton = (LinearLayout) Find(R.id.cardInputOKBtn);
	}

	public LinearLayout[] Hands = new LinearLayout[4];
	public VulnerabilityView Vulnerability;
	public LinearLayout HandWest;
	public LinearLayout HandNorth;
	public LinearLayout HandEast;
	public LinearLayout HandSouth;

	public Direction getDirection() {
		if (HandWest.isSelected())
			return Direction.West;
		if (HandNorth.isSelected())
			return Direction.North;
		if (HandEast.isSelected())
			return Direction.East;
		if (HandSouth.isSelected())
			return Direction.South;
		return null;

	}

	public TextView GetSuitTextView(LinearLayout handLayout, Suit suit) {
		int id = 0;
		switch (suit) {
		case Spades:
			id = R.id.dealHandSpades;
			break;
		case Hearts:
			id = R.id.dealHandHearts;
			break;
		case Diamonds:
			id = R.id.dealHandDiamonds;
			break;
		case Clubs:
			id = R.id.dealHandClubs;
			break;
		case Notrump:
			break;
		}
		return (TextView) handLayout.findViewById(id);
	}
}
