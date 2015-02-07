package com.brisco.common.PBN.Mapping;

import com.brisco.common.Game.Contract;
import com.brisco.common.Game.Suit;

public class ContractMapper {

	public static Contract GetContractFromString(String contract) {
		if (contract == null || contract.length() < 1) {
			return null;
		}
		Contract ret = new Contract();
		if (contract == "Pass") {
			ret.Level = 0;
			return ret;
		}
		ret.Level = Integer.parseInt(contract.substring(0, 0));
		if (contract.charAt(1) == 'N') {
			ret.Suit = Suit.Notrump;
		} else {
			ret.Suit = SuitMapper.GetSuitFromString(contract.substring(1, 1));
		}
		ret.ReDoubled = contract.contains("XX");
		if (ret.ReDoubled) {
			ret.Doubled = true;
		} else {
			ret.Doubled = contract.contains("X");
		}
		return ret;
	}

	public static String GetStringFromContract(Contract contract) {
		if (contract.Level == 0) {
			return "Pass";
		}
		StringBuffer ret = new StringBuffer();
		ret.append(contract.Level);
		ret.append(SuitMapper.GetStringFromSuit(contract.Suit));
		if (contract.ReDoubled) {
			ret.append("XX");
		} else if (contract.Doubled) {
			ret.append("X");
		}
		return ret.toString();
	}

}
