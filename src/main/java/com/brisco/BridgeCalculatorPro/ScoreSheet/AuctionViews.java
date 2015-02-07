package com.brisco.BridgeCalculatorPro.ScoreSheet;

import android.app.Activity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.brisco.BridgeCalculatorPro.R;
import com.brisco.BridgeCalculatorPro.Contract.ViewsHelper;
import com.brisco.BridgeCalculatorPro.Contract.VulnerabilityView;
import com.brisco.common.Game.Suit;

public class AuctionViews extends ViewsHelper {
	public AuctionViews(Activity activity) {
		super(activity);
		// Level
		Level[0] = (Button) Find(R.id.ContractLevelPass);
		Level[1] = (Button) Find(R.id.ContractLevel1);
		Level[2] = (Button) Find(R.id.ContractLevel2);
		Level[3] = (Button) Find(R.id.ContractLevel3);
		Level[4] = (Button) Find(R.id.ContractLevel4);
		Level[5] = (Button) Find(R.id.ContractLevel5);
		Level[6] = (Button) Find(R.id.ContractLevel6);
		Level[7] = (Button) Find(R.id.ContractLevel7);

		// Suit
		Suit[0] = (Button) Find(R.id.ContractSuitClubs);
		Suit[1] = (Button) Find(R.id.ContractSuitDiamonds);
		Suit[2] = (Button) Find(R.id.ContractSuitHearts);
		Suit[3] = (Button) Find(R.id.ContractSuitSpades);
		Suit[4] = (Button) Find(R.id.ContractSuitNT);

		Vulnerability = (VulnerabilityView) Find(R.id.auctionVulnerability);

		Doubled = (ToggleButton) Find(R.id.ContractDoubled);
		ReDoubled = (ToggleButton) Find(R.id.ContractReDoubled);
		DeleteButton = (LinearLayout) Find(R.id.contractDeleteBtn);
		BidsTable = (TableLayout) Find(R.id.auctionTable);

		OKButton = (LinearLayout) Find(R.id.auctionOKBtn);
	}

	public Button[] Suit = new Button[5];
	public Button[] Level = new Button[8];
	public VulnerabilityView Vulnerability;
	public ToggleButton Doubled;
	public ToggleButton ReDoubled;
	public LinearLayout DeleteButton;
	public LinearLayout OKButton;
	public TableLayout BidsTable;

	public Suit GetSuit() {
		if (!Suit[0].isEnabled()) {
			return com.brisco.common.Game.Suit.Clubs;
		}
		if (!Suit[1].isEnabled()) {
			return com.brisco.common.Game.Suit.Diamonds;
		}
		if (!Suit[2].isEnabled()) {
			return com.brisco.common.Game.Suit.Hearts;
		}
		if (!Suit[3].isEnabled()) {
			return com.brisco.common.Game.Suit.Spades;
		}
		if (!Suit[4].isEnabled()) {
			return com.brisco.common.Game.Suit.Notrump;
		}
		return null;
	}

	public void ToggleToggleButtons(TextView[] buttons, int clickedButtonID) {
		for (TextView b : buttons) {
			if (b.getId() == clickedButtonID) {
				b.setEnabled(false);
			} else {
				b.setEnabled(true);
			}

		}
	}

	public int GetLevel() {
		for (int i = 0; i < 8; i++) {
			if (!Level[i].isEnabled()) {
				return i;
			}
		}
		return -1;
	}

	public void ClearControls() {
		for (Button b : Suit) {
			b.setEnabled(true);
		}
		for (Button b : Level) {
			b.setEnabled(true);
		}
		Doubled.setChecked(false);
		ReDoubled.setChecked(false);
	}

}
