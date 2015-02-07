package com.brisco.common.PBN.Mapping;

import com.brisco.common.Game.Denomination;

public class DenominationMapper {

	public static Denomination GetDenominationFromChar(char denomination) {
		if (denomination == '2') {
			return Denomination.Two;
		} else if (denomination == '3') {
			return Denomination.Three;
		} else if (denomination == '4') {
			return Denomination.Four;
		} else if (denomination == '5') {
			return Denomination.Five;
		} else if (denomination == '6') {
			return Denomination.Six;
		} else if (denomination == '7') {
			return Denomination.Seven;
		} else if (denomination == '8') {
			return Denomination.Eight;
		} else if (denomination == '9') {
			return Denomination.Nine;
		} else if (denomination == 'T') {
			return Denomination.Ten;
		} else if (denomination == 'J') {
			return Denomination.Jack;
		} else if (denomination == 'Q') {
			return Denomination.Queen;
		} else if (denomination == 'K') {
			return Denomination.King;
		} else if (denomination == 'A') {
			return Denomination.Ace;
		} else if (denomination == 'x' || denomination == 'X') {
			return Denomination.Small;
		} else {
			return Denomination.Unknown;
		}
	}

	public static char GetCharFromDenomination(Denomination denomination) {
		switch (denomination) {
		case Two:
			return '2';
		case Three:
			return '3';
		case Four:
			return '4';
		case Five:
			return '5';
		case Six:
			return '6';
		case Seven:
			return '7';
		case Eight:
			return '8';
		case Nine:
			return '9';
		case Ten:
			return 'T';
		case Jack:
			return 'J';
		case Queen:
			return 'Q';
		case King:
			return 'K';
		case Ace:
			return 'A';
		case Small:
			return 'x';
		default:
			return '?';
		}
	}
}
