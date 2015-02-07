package com.brisco.BridgeCalculatorPro.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	public DBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "bridgecalcpro";

	private static final String TABLE_CREATE = "CREATE TABLE competition "
			+ "(id INTEGER PRIMARY KEY AUTOINCREMENT," + " description TEXT,"
			+ " date TEXT," + " data BLOB)";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	DBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
