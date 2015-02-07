package com.brisco.BridgeCalculatorPro.persistence;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brisco.BridgeCalculatorPro.Competition;

public class DB {

	private Context _context;
	private SQLiteDatabase _db;

	private static final String TABLE_NAME = "competition";
	/* Format used in stored strings in database */
	@SuppressLint("SimpleDateFormat")
	private static SimpleDateFormat _dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public DB(Context context) {
		_context = context;
		DBOpenHelper openHelper = new DBOpenHelper(_context);
		_db = openHelper.getWritableDatabase();
	}

	private ContentValues GetContentValues(Competition competition)
			throws IOException {
		ContentValues values = new ContentValues();
		values.put("description", competition.Description);
		values.put("date", GetStringFromDate(competition.Started));
		values.put("data", GetByteArrayFromTournamen(competition));
		return values;
	}

	public long insert(Competition competition) throws IOException {

		return _db.insert(TABLE_NAME, null, GetContentValues(competition));
	}

	public void update(Competition competition, int id) throws IOException {
		_db.update(TABLE_NAME, GetContentValues(competition), "id=?",
				new String[] { Integer.toString(id) });
	}

	public void delete(int competitionID) {
		_db.delete(TABLE_NAME, "id=?",
				new String[] { Integer.toString(competitionID) });
	}

	public static Object GetObjectFromByteArray(byte[] byteArray) {
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(new ByteArrayInputStream(byteArray));
			try {
				return in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Competition GetCompetitionFromByteArray(byte[] byteArray) {
		return (Competition) GetObjectFromByteArray(byteArray);
	}

	public static byte[] GetByteArrayFromObject(Object object)
			throws IOException {
		// Serialize to a byte array
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bos);
		out.writeObject(object);
		out.close();
		return bos.toByteArray();
	}

	public static byte[] GetByteArrayFromTournamen(Competition competition)
			throws IOException {
		return GetByteArrayFromObject(competition);
	}

	private String GetStringFromDate(Date date) {
		return _dateFormat.format(date);
	}

	private Date GetDateFromString(String string) throws ParseException {
		return _dateFormat.parse(string);
	}

	public void deleteAll() {
		_db.delete("competition", null, null);
	}

	public Competition GetCompetition(int id) {
		Cursor cursor = _db.query(TABLE_NAME, new String[] { "data" }, "id=?",
				new String[] { Integer.toString(id) }, null, null, null);
		if (cursor.moveToFirst()) {
			return GetCompetitionFromByteArray(cursor.getBlob(0));
		} else {
			return null;
		}
	}

	public ArrayList<CompetitionView> GetAllCompetitions()
			throws ParseException {
		ArrayList<CompetitionView> list = new ArrayList<CompetitionView>();
		Cursor cursor = _db.query(TABLE_NAME, new String[] { "description",
				"id", "date" }, null, null, null, null, "date desc");
		if (cursor.moveToFirst()) {
			do {
				CompetitionView tv = new CompetitionView();
				tv.Description = cursor.getString(0);
				tv.ID = cursor.getInt(1);
				tv.Date = GetDateFromString(cursor.getString(2));
				list.add(tv);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
}
