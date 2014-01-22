package de.uni_passau.facultyinfo.client.model.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CacheOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "cache";
	private static final int DATABASE_VERSION = 10;

	public CacheOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS 'buslines' ('id' TEXT PRIMARY KEY NOT NULL, 'line' TEXT, 'direction' TEXT, 'departure' INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'events' ('id' TEXT PRIMARY KEY NOT NULL, 'title' TEXT, 'subtitle' TEXT, 'location' TEXT, 'description' TEXT, 'startdate' INTEGER, 'enddate' INTEGER, 'host' TEXT, 'url' TEXT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'faqcategories' ('id' TEXT PRIMARY KEY NOT NULL, 'title' TEXT NOT NULL)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'faqs' ('id' TEXT PRIMARY KEY NOT NULL, 'category' TEXT NOT NULL, 'title' TEXT NOT NULL, 'text' TEXT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'news' ('id' TEXT PRIMARY KEY NOT NULL, 'title' TEXT NOT NULL, 'description' TEXT, 'url' TEXT, 'text' TEXT, 'publishingdate' INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'businesshours' ('id' TEXT PRIMARY KEY NOT NULL, 'facility' TEXT NOT NULL, 'dayofweek' INTEGER, 'phase' INTEGER, 'status' INTEGER, 'openingtime' TEXT, 'closingtime' TEXT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'businesshoursfacilities' ('id' TEXT PRIMARY KEY NOT NULL, 'name' TEXT, 'type' INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'contactgroups' ('id' TEXT PRIMARY KEY NOT NULL, 'title' TEXT, 'description' TEXT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'contactpersons' ('id' TEXT PRIMARY KEY NOT NULL, 'contactgroup' TEXT NOT NULL, 'name' TEXT, 'office' TEXT, 'phone' TEXT, 'email' TEXT, 'description' TEXT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'mapmarkers' ('id' TEXT PRIMARY KEY NOT NULL, 'category' TEXT, 'name' TEXT, 'description' TEXT, 'latitude' REAL, 'longitude' REAL)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'mapmarkercategories' ('id' TEXT PRIMARY KEY NOT NULL, 'category' TEXT, 'title' TEXT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'timetable' ('id' TEXT PRIMARY KEY NOT NULL, 'title' TEXT NOT NULL, 'description' TEXT, 'location' TEXT, 'time' INTEGER NOT NULL, 'dayofweek' INTEGER NOT NULL, 'color' INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS 'timetable'");
		db.execSQL("DROP TABLE IF EXISTS 'news'");
		db.execSQL("DROP TABLE IF EXISTS 'events'");
		db.execSQL("DROP TABLE IF EXISTS 'faqs'");
		db.execSQL("DROP TABLE IF EXISTS 'faqcategories'");
		db.execSQL("DROP TABLE IF EXISTS 'buslines'");
		db.execSQL("DROP TABLE IF EXISTS 'businesshours'");
		db.execSQL("DROP TABLE IF EXISTS 'businesshoursfacilities'");
		db.execSQL("DROP TABLE IF EXISTS 'contactpersons'");
		db.execSQL("DROP TABLE IF EXISTS 'contactgroups'");
		db.execSQL("DROP TABLE IF EXISTS 'mapmarkers'");
		db.execSQL("DROP TABLE IF EXISTS 'mapmarkercategories'");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'buslines' ('id' TEXT PRIMARY KEY NOT NULL, 'line' TEXT, 'direction' TEXT, 'departure' INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'events' ('id' TEXT PRIMARY KEY NOT NULL, 'title' TEXT, 'subtitle' TEXT, 'location' TEXT, 'description' TEXT, 'startdate' INTEGER, 'enddate' INTEGER, 'host' TEXT, 'url' TEXT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'faqcategories' ('id' TEXT PRIMARY KEY NOT NULL, 'title' TEXT NOT NULL)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'faqs' ('id' TEXT PRIMARY KEY NOT NULL, 'category' TEXT NOT NULL, 'title' TEXT NOT NULL, 'text' TEXT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'news' ('id' TEXT PRIMARY KEY NOT NULL, 'title' TEXT NOT NULL, 'description' TEXT, 'url' TEXT, 'text' TEXT, 'publishingdate' INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'businesshours' ('id' TEXT PRIMARY KEY NOT NULL, 'facility' TEXT NOT NULL, 'dayofweek' INTEGER, 'phase' INTEGER, 'status' INTEGER, 'openingtime' TEXT, 'closingtime' TEXT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'businesshoursfacilities' ('id' TEXT PRIMARY KEY NOT NULL, 'name' TEXT, 'type' INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'contactgroups' ('id' TEXT PRIMARY KEY NOT NULL, 'title' TEXT, 'description' TEXT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'contactpersons' ('id' TEXT PRIMARY KEY NOT NULL, 'contactgroup' TEXT NOT NULL, 'name' TEXT, 'office' TEXT, 'phone' TEXT, 'email' TEXT, 'description' TEXT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'mapmarkers' ('id' TEXT PRIMARY KEY NOT NULL, 'category' TEXT, 'name' TEXT, 'description' TEXT, 'latitude' REAL, 'longitude' REAL)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'mapmarkercategories' ('id' TEXT PRIMARY KEY NOT NULL, 'category' TEXT, 'title' TEXT)");
		db.execSQL("CREATE TABLE IF NOT EXISTS 'timetable' ('id' TEXT PRIMARY KEY NOT NULL, 'title' TEXT NOT NULL, 'description' TEXT, 'location' TEXT, 'time' INTEGER NOT NULL, 'dayofweek' INTEGER NOT NULL, 'color' INTEGER)");
	}

}
