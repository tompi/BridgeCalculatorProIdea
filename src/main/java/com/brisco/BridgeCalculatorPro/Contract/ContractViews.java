package com.brisco.BridgeCalculatorPro.Contract;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.brisco.BridgeCalculatorPro.R;
import com.brisco.common.Game.Bid;
import com.brisco.common.Game.Contract;
import com.brisco.common.Game.Direction;
import com.brisco.common.Game.Suit;

public class ContractViews extends ViewsHelper {
	public ContractViews(Activity activity) {
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

		// Declarer
		Declarer[0] = (Button) Find(R.id.ContractDeclarerWest);
		Declarer[1] = (Button) Find(R.id.ContractDeclarerNorth);
		Declarer[2] = (Button) Find(R.id.ContractDeclarerEast);
		Declarer[3] = (Button) Find(R.id.ContractDeclarerSouth);

		Doubled = (ToggleButton) Find(R.id.ContractDoubled);
		ReDoubled = (ToggleButton) Find(R.id.ContractReDoubled);
		PlayedBy = (ToggleButton) Find(R.id.ContractPlayedBy);
		BoardNumber = (Button) Find(R.id.ContractBoardNumber);
		Tricks = (SeekBar) Find(R.id.TricksSeekBar);
		TricksLabel = (TextView) Find(R.id.TricksLabel);
		ContractLabel = (TextView) Find(R.id.contractLabel);
		OKButton = (LinearLayout) Find(R.id.contractOKBtn);
		TricksPlus = (Button) Find(R.id.contractPlusTrick);
		TricksMinus = (Button) Find(R.id.contractMinusTrick);
		TricksLayout = (LinearLayout) Find(R.id.contractTricksLinearLayout);

		HCP = (SeekBar) Find(R.id.HCPSeekBar);
		HCPLabel = (TextView) Find(R.id.HCPLabel);
		HCPPlus = (Button) Find(R.id.contractPlusHCP);
		HCPMinus = (Button) Find(R.id.contractMinusHCP);
		HCPLayout = (LinearLayout) Find(R.id.contractHCPLinearLayout);
		RussianLabel = (TextView) Find(R.id.contractRussianLabel);

		DeclarerLayout = (LinearLayout) Find(R.id.contractDeclarerLayout);
		CustomButtonsLayout = (LinearLayout) Find(R.id.contractCustomButtons);
		AuctionButton = (Button) Find(R.id.ContractAuction);
		CardsButton = (Button) Find(R.id.ContractCards);
		LeadButton = (Button) Find(R.id.ContractLead);
		Vulnerability = (VulnerabilityView) Find(R.id.contractVulnerability);
	}

	public Button[] Level = new Button[8];
	public Button[] Suit = new Button[5];
	public Button[] Declarer = new Button[4];
	public ToggleButton Doubled;
	public ToggleButton ReDoubled;
	public ToggleButton PlayedBy;
	public Button BoardNumber;
	public SeekBar Tricks;
	public Button TricksPlus;
	public Button TricksMinus;
	public TextView TricksLabel;
	public LinearLayout HCPLayout;
	public SeekBar HCP;
	public Button HCPPlus;
	public Button HCPMinus;
	public TextView HCPLabel;
	public TextView RussianLabel;
	public TextView ContractLabel;
	public LinearLayout OKButton;
	public Button AuctionButton;
	public Button CardsButton;
	public Button LeadButton;
	public VulnerabilityView Vulnerability;
	public LinearLayout CustomButtonsLayout;
	public LinearLayout TricksLayout;
	public LinearLayout DeclarerLayout;

	public Direction GetDeclarer() {
		if (!Declarer[0].isEnabled()) {
			return Direction.West;
		}
		if (!Declarer[1].isEnabled()) {
			return Direction.North;
		}
		if (!Declarer[2].isEnabled()) {
			return Direction.East;
		}
		if (!Declarer[3].isEnabled()) {
			return Direction.South;
		}
		return null;
	}

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

	public Contract GetContract() {
		Contract ret = new Contract();
		ret.Doubled = Doubled.isChecked();
		ret.ReDoubled = ReDoubled.isChecked();
		ret.Tricks = Tricks.getProgress();
		if (PlayedBy.getVisibility() == View.VISIBLE) {
			ret.Player = PlayedBy.isChecked() ? Direction.North
					: Direction.East;
		} else {
			ret.Player = GetDeclarer();
		}
		ret.Level = GetLevel();
		ret.Suit = GetSuit();
		return ret;
	}

	public void SetContract(Contract contract) {
		if (contract == null) {
			ClearControls();
		} else {
			Doubled.setChecked(contract.Doubled);
			ReDoubled.setChecked(contract.ReDoubled);
			Tricks.setProgress(contract.Tricks);
			SetLevel(contract.Level);
			SetSuit(contract.Suit);
			SetDeclarer(contract.Player);
		}
	}

	private void SetDeclarer(Direction declarer) {
		int declarerID = -1;
		if (declarer != null) {
			switch (declarer) {
			case West:
				declarerID = R.id.ContractDeclarerWest;
				break;
			case North:
				declarerID = R.id.ContractDeclarerNorth;
				break;
			case East:
				declarerID = R.id.ContractDeclarerEast;
				break;
			case South:
				declarerID = R.id.ContractDeclarerSouth;
				break;
			default:
				declarerID = -1;
			}
		}
		ToggleToggleButtons(Declarer, declarerID);
	}

	private void SetSuit(com.brisco.common.Game.Suit suit) {
		int suitID = -1;
		if (suit != null) {
			switch (suit) {
			case Clubs:
				suitID = R.id.ContractSuitClubs;
				break;
			case Diamonds:
				suitID = R.id.ContractSuitDiamonds;
				break;
			case Hearts:
				suitID = R.id.ContractSuitHearts;
				break;
			case Spades:
				suitID = R.id.ContractSuitSpades;
				break;
			case Notrump:
				suitID = R.id.ContractSuitNT;
				break;
			default:
				suitID = -1;
			}
		}
		ToggleToggleButtons(Suit, suitID);
	}

	private void SetLevel(int level) {
		int levelID = -1;
		if (level > -1) {
			levelID = Level[level].getId();
		}
		ToggleToggleButtons(Level, levelID);
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
		for (Button b : Level) {
			b.setEnabled(true);
		}
		for (Button b : Suit) {
			b.setEnabled(true);
		}
		for (Button b : Declarer) {
			b.setEnabled(true);
		}
		Doubled.setChecked(false);
		ReDoubled.setChecked(false);
	}

	public Bid GetBid() {
		Bid b = new Bid();
		b.Double = Doubled.isChecked();
		b.ReDouble = ReDoubled.isChecked();
		b.Level = GetLevel();
		if (b.Level == 0) {
			b.Pass = true;
		}
		b.Suit = GetSuit();
		return b;
	}
}
