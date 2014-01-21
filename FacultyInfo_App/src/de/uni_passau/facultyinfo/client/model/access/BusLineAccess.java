package de.uni_passau.facultyinfo.client.model.access;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.common.base.Joiner;

import de.uni_passau.facultyinfo.client.model.connection.RestConnection;
import de.uni_passau.facultyinfo.client.model.dto.BusLine;
import de.uni_passau.facultyinfo.client.util.CacheHelper;

/**
 * @author Timo Staudinger
 * 
 */
public class BusLineAccess extends Access {
	private static final String RESSOURCE = "/busline";

	private static final String TABLE_NAME = "buslines";
	private static final int INDEX_ID = 0;
	private static final String KEY_ID = "id";
	private static final int INDEX_LINE = 1;
	private static final String KEY_LINE = "line";
	private static final int INDEX_DIRECTION = 2;
	private static final String KEY_DIRECTION = "direction";
	private static final int INDEX_DEPARTURE = 3;
	private static final String KEY_DEPARTURE = "departure";

	private static BusLineAccess instance = null;

	protected static BusLineAccess getInstance() {
		if (instance == null) {
			instance = new BusLineAccess();
		}
		return instance;
	}

	private RestConnection<BusLine> restConnection = null;

	private List<BusLine> cachedBusLines = null;
	private Date cachedBusLinesTimestamp = null;

	private BusLineAccess() {
		restConnection = new RestConnection<BusLine>(BusLine.class);
	}

	/**
	 * Gives a list of bus lines departing during the next 20 minutes, ordered
	 * by time of departure.
	 * 
	 */
	public List<BusLine> getNextBusLines() {
		return getNextBusLines(false);
	}

	/**
	 * Gives a list of bus lines departing during the next 20 minutes, ordered
	 * by time of departure.
	 * 
	 */
	public List<BusLine> getNextBusLines(boolean forceRefresh) {
		List<BusLine> busLines = null;

		if (forceRefresh
				|| cachedBusLines == null
				|| cachedBusLinesTimestamp == null
				|| cachedBusLinesTimestamp
						.before(CacheHelper.getExpiringDate())) {
			busLines = restConnection.getRessourceAsList(RESSOURCE);

			if (busLines != null) {
				cachedBusLines = busLines;
				cachedBusLinesTimestamp = new Date();

				writeCache(busLines);
			}
		} else {
			busLines = cachedBusLines;
		}

		if (busLines == null) {
			return null;
		}

		ArrayList<BusLine> nextBusLines = new ArrayList<BusLine>();
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.MINUTE, 60);
		Date soon = cal.getTime();

		for (BusLine busLine : busLines) {
			if (busLine.getDeparture().after(now)
					&& busLine.getDeparture().before(soon)) {
				nextBusLines.add(busLine);
			}
		}

		Collections.sort(nextBusLines, new Comparator<BusLine>() {
			@Override
			public int compare(BusLine lhs, BusLine rhs) {
				if (lhs.getDeparture().before(rhs.getDeparture()))
					return -1;
				else if (lhs.getDeparture().after(rhs.getDeparture()))
					return 1;
				return 0;
			}
		});

		return Collections.unmodifiableList(nextBusLines);
	}

	/**
	 * Gives a list of bus lines from local cache departing during the next 20
	 * minutes, ordered by time of departure.
	 * 
	 */
	public List<BusLine> getNextBusLinesFromCache() {
		List<BusLine> busLines = readCache();

		ArrayList<BusLine> nextBusLines = new ArrayList<BusLine>();
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.MINUTE, 60);
		Date soon = cal.getTime();

		for (BusLine busLine : busLines) {
			if (busLine.getDeparture().after(now)
					&& busLine.getDeparture().before(soon)) {
				nextBusLines.add(busLine);
			}
		}

		Collections.sort(nextBusLines, new Comparator<BusLine>() {
			@Override
			public int compare(BusLine lhs, BusLine rhs) {
				if (lhs.getDeparture().before(rhs.getDeparture()))
					return -1;
				else if (lhs.getDeparture().after(rhs.getDeparture()))
					return 1;
				return 0;
			}
		});

		return Collections.unmodifiableList(busLines);
	}

	private boolean writeCache(List<BusLine> busLineList) {
		boolean result = true;

		ArrayList<String> idArray = new ArrayList<String>();
		for (BusLine busLine : busLineList) {
			idArray.add(busLine.getId());
		}
		String idList = "'"
				+ Joiner.on("','").skipNulls()
						.join(idArray.toArray(new String[idArray.size()]))
				+ "'";

		SQLiteDatabase writableDatabase = getCacheOpenHelper()
				.getWritableDatabase();
		writableDatabase.execSQL("DELETE FROM " + TABLE_NAME
				+ " WHERE id NOT IN (" + idList + ")");
		for (BusLine busLine : busLineList) {
			ContentValues values = new ContentValues();
			values.put(KEY_ID, busLine.getId());
			values.put(KEY_LINE, busLine.getLine());
			values.put(KEY_DIRECTION, busLine.getDirection());
			values.put(KEY_DEPARTURE, busLine.getDeparture().getTime());
			result = (writableDatabase.replace(TABLE_NAME, null, values) != -1L)
					&& result;
		}

		writableDatabase.close();
		return result;
	}

	private List<BusLine> readCache() {
		SQLiteDatabase db = getCacheOpenHelper().getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME
				+ " ORDER BY " + KEY_DEPARTURE + ", " + KEY_LINE, null);
		ArrayList<BusLine> entries = new ArrayList<BusLine>();
		if (cursor != null && cursor.moveToFirst()) {
			do {
				String id = cursor.getString(INDEX_ID);
				String line = cursor.getString(INDEX_LINE);
				String direction = cursor.getString(INDEX_DIRECTION);
				Date departure = new Date(cursor.getLong(INDEX_DEPARTURE));
				BusLine entry = new BusLine(id, line, direction, departure);
				entries.add(entry);
			} while (cursor.moveToNext());
		}
		db.close();
		return entries;
	}

}
