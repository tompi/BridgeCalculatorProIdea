package com.brisco.common.Game;

import java.io.Serializable;
import java.util.ArrayList;

public class Auction implements Serializable {
	private static final long serialVersionUID = -8518125397217040743L;
	public ArrayList<Bid> Bids;
	public ArrayList<String> Explanations;
	public Direction Dealer;

	public Auction(Direction dealer) {
		Dealer = dealer;
		Bids = new ArrayList<Bid>();
		Explanations = new ArrayList<String>();
	}
}
