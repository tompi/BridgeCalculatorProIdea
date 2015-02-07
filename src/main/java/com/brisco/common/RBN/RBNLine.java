package com.brisco.common.RBN;

public abstract class RBNLine {
	public abstract RBNLineType GetRBNLineType();

	public abstract String GetRBNString();

	public abstract void ParseRBNString(String rbn);
}
