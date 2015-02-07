package com.brisco.common.RBN;

// NB: Custom extension to RBN
public class BidExplanation extends RBNLine {
	public String Explanation;

	@Override
	public String GetRBNString() {
		return "";
	}

	@Override
	public void ParseRBNString(String rbn) {
		Explanation = rbn;
	}

	@Override
	public RBNLineType GetRBNLineType() {
		return RBNLineType.BidExplanation;
	}

}
