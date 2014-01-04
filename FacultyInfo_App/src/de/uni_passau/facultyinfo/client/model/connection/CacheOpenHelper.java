package de.uni_passau.facultyinfo.client.model.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CacheOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "cache";
	private static final int DATABASE_VERSION = 1;

	public CacheOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS 'buslines' ('id' TEXT PRIMARY KEY NOT NULL, 'line' TEXT NOT NULL, 'direction' TEXT NOT NULL, 'departure' TEXT NOT NULL)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'events' ('id' TEXT PRIMARY KEY NOT NULL, 'title' TEXT, 'subtitle' TEXT, 'location' TEXT, 'description' TEXT, 'startdate' TEXT, 'enddate' TEXT, 'host' TEXT, 'url' TEXT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'faqcategories' ('id' TEXT PRIMARY KEY NOT NULL, 'title' TEXT NOT NULL)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'faqs' ('id' TEXT NOT NULL, 'category' TEXT NOT NULL, 'title' TEXT NOT NULL, 'text' TEXT NOT NULL)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'news' ('id' TEXT PRIMARY KEY NOT NULL, 'title' TEXT NOT NULL, 'description' TEXT NOT NULL, 'url' TEXT NOT NULL, 'text' TEXT NOT NULL, 'publishingdate' TEXT NOT NULL)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'timetable' ('id' TEXT PRIMARY KEY NOT NULL, 'title' TEXT NOT NULL, 'description' TEXT, 'location' TEXT, 'time' INTEGER NOT NULL, 'dayofweek' INTEGER NOT NULL, 'color' INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO: imlement onUpgrade
	}

}
