package com.brisco.common.RBN;

public class Text extends RBNLine {
	public String Text;

	@Override
	public String GetRBNString() {
		return "{" + Text + "}";
	}

	@Override
	public void ParseRBNString(String rbn) {
		if (rbn != null && rbn.length() > 2) {
			Text = rbn.substring(1, rbn.length() - 1);
		}
	}

	@Override
	public RBNLineType GetRBNLineType() {
		return RBNLineType.Text;
	}

}
