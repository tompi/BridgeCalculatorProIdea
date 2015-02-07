package com.brisco.BridgeCalculatorPro.ScoreSheet;

import com.brisco.BridgeCalculatorPro.Settings;
import com.brisco.common.Game.Denomination;

public class DenominationMapper {

	public static String GetCharFromDenomination(Denomination denomination,
			Settings settings) {
		switch (denomination) {
		case Two:
			return "2";
		case Three:
			return "3";
		case Four:
			return "4";
		case Five:
			return "5";
		case Six:
			return "6";
		case Seven:
			return "7";
		case Eight:
			return "8";
		case Nine:
			return "9";
		case Ten:
			return settings.SymbolTen;
		case Jack:
			return settings.SymbolJack;
		case Queen:
			return settings.SymbolQueen;
		case King:
			return settings.SymbolKing;
		case Ace:
			return settings.SymbolAce;
		case Small:
			return settings.SymbolSmall;
		default:
			return settings.SymbolUnknown;
		}
	}
}
