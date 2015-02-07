package com.brisco.common.HTML.Mapping;

import com.brisco.common.Game.Contract;

public class ContractMapper {

	public static String GetStringFromContract(Contract contract) {
		if (contract == null) {
			return "";
		}
		if (contract.Level == 0 || contract.Suit == null) {
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
