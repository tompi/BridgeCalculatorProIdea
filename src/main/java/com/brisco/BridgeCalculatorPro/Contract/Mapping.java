package com.brisco.BridgeCalculatorPro.Contract;

import android.content.Context;

import com.brisco.BridgeCalculatorPro.R;
import com.brisco.common.Game.Contract;
import com.brisco.common.Game.Direction;
import com.brisco.common.Game.Suit;
import com.brisco.common.HTML.Mapping.TricksMapper;

public class Mapping {
	public static String GetContractString(Contract contract, Context context) {
		String ret = GetContractStringWithoutTricks(contract, context);
		if (contract == null || contract.Level == 0) {
			return ret;
		}
		return ret + TricksMapper.GetStringFromContract(contract);
	}

	public static String GetContractStringWithoutTricks(Contract contract,
			Context context) {
		if (contract == null) {
			return "";
		}
		if (contract.Level == 0) {
			return "Pass";
		}
		StringBuffer ret = new StringBuffer();
		ret.append(contract.Level);
		ret.append(GetStringFromSuit(contract.Suit, context));
		if (contract.ReDoubled) {
			ret.append("XX");
		} else if (contract.Doubled) {
			ret.append("X");
		}
		return ret.toString();
	}

	public static String GetStringFromSuit(Suit suit, Context context) {
		switch (suit) {
		case Notrump:
			return context.getString(R.string.symbol_notrump);
		case Spades:
			return context.getString(R.string.symbol_spades);
		case Hearts:
			return context.getString(R.string.symbol_hearts);
		case Diamonds:
			return context.getString(R.string.symbol_diamonds);
		default:
			return context.getString(R.string.symbol_clubs);
		}
	}

	public static String GetContractResultTricks(Contract contract) {
		if (contract.Level > 0) {
			int diff = contract.Tricks - 6 - contract.Level;
			if (diff > 0) {
				return " (+" + Integer.toString(diff) + ")";
			} else if (diff < 0) {
				return " (" + Integer.toString(diff) + ")";
			} else {
				return "=";
			}
		} else {
			return "";
		}

	}

	public static String GetStringFromDirection(Direction direction,
			Context context) {
		if (direction == null) {
			return "";
		}
		switch (direction) {
		case West:
			return context.getString(R.string.shortWest);
		case North:
			return context.getString(R.string.shortNorth);
		case East:
			return context.getString(R.string.shortEast);
		case South:
			return context.getString(R.string.shortSouth);
		default:
			return "";
		}
	}

}
