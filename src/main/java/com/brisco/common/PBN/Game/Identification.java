package com.brisco.common.PBN.Game;

import java.util.Date;

import com.brisco.common.Game.Direction;
import com.brisco.common.Game.Vulnerability;

public class Identification {
	public Identification() {
		Board = -1;
	}

	/**
	 * The name of the tournament or match
	 */
	public String Event;

	/**
	 * the location of the event
	 */
	public String Site;

	/**
	 * The starting date of the game
	 */
	public Date Date;

	/**
	 * The board number
	 */
	public int Board;

	/**
	 * The west player
	 */
	public String West;

	/**
	 * The north player
	 */
	public String North;

	/**
	 * The east player
	 */
	public String East;

	/**
	 * The south player
	 */
	public String South;

	/**
	 * The dealer
	 */
	public Direction Dealer;

	/**
	 * The situation of vulnerability
	 */
	public Vulnerability Vulnerable;

	/**
	 * The dealt cards
	 */
	public com.brisco.common.Game.Deal Deal;

	/**
	 * The scoring method
	 */
	public String Scoring;

	/**
	 * The declarer of the contract
	 */
	public Direction Declarer;

	/**
	 * The contract
	 */
	public com.brisco.common.Game.Contract Contract;

	/**
	 * The number of tricks taken by declarer
	 */
	public int Result;

	public static final class Tags {
		public static final String Event = "Event";
		public static final String Site = "Site";
		public static final String Date = "Date";
		public static final String Board = "Board";
		public static final String West = "West";
		public static final String North = "North";
		public static final String East = "East";
		public static final String South = "South";
		public static final String Dealer = "Dealer";
		public static final String Vulnerable = "Vulnerable";
		public static final String Deal = "Deal";
		public static final String Scoring = "Scoring";
		public static final String Declarer = "Declarer";
		public static final String Contract = "Contract";
		public static final String Result = "Result";
	}

	/*
	 * private static final SimpleDateFormat _simpleDateFormat = new
	 * SimpleDateFormat( "yyyy.MM.dd");
	 * 
	 * private Date GetDateFromString(String value) throws ParseException {
	 * return _simpleDateFormat.parse(value); }
	 * 
	 * private String GetStringFromDate(Date value) { return
	 * _simpleDateFormat.format(value); }
	 */
}
