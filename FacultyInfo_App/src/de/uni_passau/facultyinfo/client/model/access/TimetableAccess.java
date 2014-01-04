package de.uni_passau.facultyinfo.client.model.access;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import de.uni_passau.facultyinfo.client.application.FacultyInfoApplication;
import de.uni_passau.facultyinfo.client.model.connection.CacheOpenHelper;
import de.uni_passau.facultyinfo.client.model.dto.TimetableEntry;

public class TimetableAccess {

	private static final String TABLE_NAME = "timetable";
	private static final int INDEX_ID = 0;
	private static final String KEY_ID = "id";
	private static final int INDEX_TITLE = 1;
	private static final String KEY_TITLE = "title";
	private static final int INDEX_DESCRIPTION = 2;
	private static final String KEY_DESCRIPTION = "description";
	private static final int INDEX_LOCATION = 3;
	private static final String KEY_LOCATION = "location";
	private static final int INDEX_TIME = 4;
	private static final String KEY_TIME = "time";
	private static final int INDEX_DAYOFWEEK = 5;
	private static final String KEY_DAYOFWEEK = "dayofweek";
	private static final int INDEX_COLOR = 6;
	private static final String KEY_COLOR = "color";

	CacheOpenHelper cacheOpenHelper = null;

	private CacheOpenHelper getCacheOpenHelper() {
		if (cacheOpenHelper == null) {
			cacheOpenHelper = new CacheOpenHelper(FacultyInfoApplication.getContext());
		}
		return cacheOpenHelper;
	}

	protected TimetableAccess() {
	}

	public List<TimetableEntry> getTimetableEntries() {
		SQLiteDatabase db = getCacheOpenHelper().getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		ArrayList<TimetableEntry> entries = new ArrayList<TimetableEntry>();
		if (cursor != null && cursor.moveToFirst()) {
			do {
				String id = cursor.getString(INDEX_ID);
				String title = cursor.getString(INDEX_TITLE);
				String description = cursor.getString(INDEX_DESCRIPTION);
				String location = cursor.getString(INDEX_LOCATION);
				int time = cursor.getInt(INDEX_TIME);
				int dayOfWeek = cursor.getInt(INDEX_DAYOFWEEK);
				int color = cursor.getInt(INDEX_COLOR);
				TimetableEntry entry = new TimetableEntry(id, title,
						description, location, time, dayOfWeek, color);
				entries.add(entry);
			} while (cursor.moveToNext());
		}
		db.close();
		return entries;
	}

	public boolean createOrUpdateTimetableEntry(TimetableEntry entry) {
		SQLiteDatabase readableDatabase = getCacheOpenHelper()
				.getReadableDatabase();
		Cursor cursor = readableDatabase.query(
				TABLE_NAME,
				new String[] { "id" },
				"time = ? AND dayofweek = ?",
				new String[] { Integer.toString(entry.getTime()),
						Integer.toString(entry.getDayOfWeek()) }, null, null,
				null);
		boolean result = false;
		if (cursor == null || cursor.getCount() < 1 || !cursor.moveToFirst()) {
			ContentValues values = new ContentValues();
			values.put(KEY_ID, UUID.randomUUID().toString());
			values.put(KEY_TITLE, entry.getTitle());
			values.put(KEY_DESCRIPTION, entry.getDescription());
			values.put(KEY_LOCATION, entry.getLocation());
			values.put(KEY_TIME, entry.getTime());
			values.put(KEY_DAYOFWEEK, entry.getDayOfWeek());
			values.put(KEY_COLOR, entry.getColor());
			SQLiteDatabase writableDatabase = getCacheOpenHelper()
					.getWritableDatabase();
			writableDatabase = getCacheOpenHelper().getWritableDatabase();
			result = writableDatabase.insert(TABLE_NAME, null, values) != -1L;
			writableDatabase.close();
		} else {
			ContentValues values = new ContentValues();
			values.put(KEY_TITLE, entry.getTitle());
			values.put(KEY_DESCRIPTION, entry.getDescription());
			values.put(KEY_LOCATION, entry.getLocation());
			values.put(KEY_TIME, entry.getTime());
			values.put(KEY_DAYOFWEEK, entry.getDayOfWeek());
			values.put(KEY_COLOR, entry.getColor());
			SQLiteDatabase writableDatabase = getCacheOpenHelper()
					.getWritableDatabase();
			writableDatabase = getCacheOpenHelper().getWritableDatabase();
			result = writableDatabase.update(TABLE_NAME, values, KEY_ID
					+ " = ?", new String[] { entry.getId() }) > 0;
			writableDatabase.close();
		}
		readableDatabase.close();
		return result;
	}

	public boolean deleteTimetableEntry(String id) {
		SQLiteDatabase db = getCacheOpenHelper().getWritableDatabase();
		long result = db.delete(TABLE_NAME, KEY_ID + " = ?",
				new String[] { id });
		db.close();
		return result > 0L;
	}
}
