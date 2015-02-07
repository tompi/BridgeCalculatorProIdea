package com.brisco.common.RBN;

public class TitleAndAuthor extends RBNLine {
	public String Author;
	public String Title;

	@Override
	public String GetRBNString() {
		return Title + ":" + Author;
	}

	@Override
	public void ParseRBNString(String rbn) {
		if (rbn != null && rbn.length() > 0) {
			String[] strings = rbn.split(":");
			Title = strings[0];
			if (strings.length > 1) {
				Author = strings[1];
			} else {
				Author = null;
			}
		}
	}

	@Override
	public RBNLineType GetRBNLineType() {
		return RBNLineType.TitleAndAuthor;
	}

}
