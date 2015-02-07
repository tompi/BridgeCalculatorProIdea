package com.brisco.common.RBN;

import com.brisco.common.RBN.Mapping.ContractMapper;

public class ContractAndDeclarer extends RBNLine {
	public com.brisco.common.Game.Contract Contract;

	@Override
	public String GetRBNString() {
		return ContractMapper.GetStringFromContract(Contract);
	}

	@Override
	public void ParseRBNString(String rbn) {
		if (rbn != null && rbn.length() > 0) {
			Contract = ContractMapper.GetContractFromString(rbn);
		}
	}

	@Override
	public RBNLineType GetRBNLineType() {
		return RBNLineType.ContractAndDeclarer;
	}

}
