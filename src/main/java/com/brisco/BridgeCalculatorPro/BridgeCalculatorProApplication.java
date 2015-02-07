package com.brisco.BridgeCalculatorPro;

import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.brisco.BridgeCalculatorPro.persistence.DB;

public class BridgeCalculatorProApplication extends android.app.Application {
	private IAddContractHandler _addContractHandler;
	private Competition _competition;
	private DB _db;
	private int _competitionID;
	private Settings _settings;

	public IAddContractHandler GetIAddContractHandler() {
		return _addContractHandler;
	}

	public void SetIAddContractHandler(IAddContractHandler addContractHandler) {
		_addContractHandler = addContractHandler;
	}

	public Competition GetCompetition() {
		return _competition;
	}

	public void SetCompetition(Competition competition) {
		_competition = competition;
	}

	public DB GetDB() {
		if (_db == null) {
			_db = new DB(this);
		}
		return _db;
	}

	public void set_competitionID(int competitionID) {
		_competitionID = competitionID;
		if (competitionID != 0) {
			_competition = GetDB().GetCompetition(_competitionID);
		} else {
			_competition = null;
		}
	}

	public int get_competitionID() {
		return _competitionID;
	}

	public void SaveCompetition() throws IOException {
		DB db = GetDB();
		if (_competitionID == 0) {
			_competitionID = (int) db.insert(_competition);
		} else {
			db.update(_competition, _competitionID);
		}
	}

	public void ClearCompetitionID() {
		_competitionID = 0;
	}

	public Settings GetSettings(Context context) {
		if (_settings == null) {
			_settings = new Settings();

			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(context);
			_settings.SpaceBetweenCardSymbols = prefs.getBoolean(
					Settings.PrefIndexCharSpace, false);
			_settings.TextSize = prefs.getFloat(Settings.PrefIndexTextSize, -1);
			if (_settings.TextSize == -1) {
				TextView tv = new TextView(context);
				_settings.TextSize = tv.getTextSize();
			}
			_settings.SymbolAce = prefs.getString(Settings.PrefIndexAce, null);
			if (isNullOrEmpty(_settings.SymbolAce)) {
				_settings.SymbolAce = context.getText(R.string.ace).toString();
			}
			_settings.SymbolKing = prefs
					.getString(Settings.PrefIndexKing, null);
			if (isNullOrEmpty(_settings.SymbolKing)) {
				_settings.SymbolKing = context.getText(R.string.king)
						.toString();
			}
			_settings.SymbolQueen = prefs.getString(Settings.PrefIndexQueen,
					null);
			if (isNullOrEmpty(_settings.SymbolQueen)) {
				_settings.SymbolQueen = context.getText(R.string.queen)
						.toString();
			}
			_settings.SymbolJack = prefs
					.getString(Settings.PrefIndexJack, null);
			if (isNullOrEmpty(_settings.SymbolJack)) {
				_settings.SymbolJack = context.getText(R.string.jack)
						.toString();
			}
			_settings.SymbolTen = prefs.getString(Settings.PrefIndexTen, null);
			if (isNullOrEmpty(_settings.SymbolTen)) {
				_settings.SymbolTen = context.getText(R.string.ten).toString();
			}

			_settings.SymbolSmall = context.getText(R.string.small).toString();
			_settings.SymbolUnknown = context.getText(R.string.unknowncard)
					.toString();

			_settings.SymbolSpades = context.getText(R.string.symbol_spades)
					.toString();
			_settings.SymbolHearts = context.getText(R.string.symbol_hearts)
					.toString();
			_settings.SymbolDiamonds = context
					.getText(R.string.symbol_diamonds).toString();
			_settings.SymbolClubs = context.getText(R.string.symbol_clubs)
					.toString();
			_settings.SymbolNotrump = context.getText(R.string.symbol_notrump)
					.toString();

			_settings.SymbolPass = context.getText(R.string.bidPass).toString();
			_settings.SymbolDouble = context.getText(R.string.bidDouble)
					.toString();
			_settings.symbolRedouble = context.getText(R.string.bidRedouble)
					.toString();
			_settings.StringNorth = context.getText(R.string.North).toString();
			_settings.StringSouth = context.getText(R.string.South).toString();
			_settings.StringEast = context.getText(R.string.East).toString();
			_settings.StringWest = context.getText(R.string.West).toString();
		}
		return _settings;
	}

	public void InValidateSettings() {
		_settings = null;
	}

	private boolean isNullOrEmpty(String s) {
		if (s == null) {
			return true;
		}
		return s.trim().length() == 0;
	}

}
