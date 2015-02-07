package com.brisco.BridgeCalculatorPro;

public class Settings {
	public float TextSize;
	public boolean SpaceBetweenCardSymbols;

	public String SymbolAce;
	public String SymbolKing;
	public String SymbolQueen;
	public String SymbolJack;
	public String SymbolTen;
	public String SymbolSmall;
	public String SymbolUnknown;

	public String SymbolSpades;
	public String SymbolHearts;
	public String SymbolDiamonds;
	public String SymbolClubs;
	public String SymbolNotrump;

	public String SymbolPass;
	public String SymbolDouble;
	public String symbolRedouble;

	public String StringNorth;
	public String StringSouth;
	public String StringEast;
	public String StringWest;

	public static final String PrefIndexAce = "Ace";
	public static final String PrefIndexKing = "King";
	public static final String PrefIndexQueen = "Queen";
	public static final String PrefIndexJack = "Jack";
	public static final String PrefIndexTen = "Ten";
	public static final String PrefIndexTextSize = "TextSize";
	public static final String PrefIndexCharSpace = "CharSpace";

	public boolean LookDownWardsForNextFreeBoardNumber;
	public int LastChosenBoardNumber;
}
