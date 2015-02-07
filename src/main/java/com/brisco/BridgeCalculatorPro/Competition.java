package com.brisco.BridgeCalculatorPro;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.brisco.common.PBN.Game.Game;

public abstract class Competition implements Serializable {
	private static final long serialVersionUID = 4909206976712565833L;
	public Date Started;
	public String Description;

	public Competition() {
		Started = Calendar.getInstance().getTime();
	}

	public abstract void AppendHTML(StringBuilder html);

	public abstract List<Game> GetPBNGames();
}
