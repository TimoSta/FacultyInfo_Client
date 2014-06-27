package de.uni_passau.facultyinfo.client.model.access;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.common.base.Joiner;

import de.uni_passau.facultyinfo.client.model.connection.RestConnection;
import de.uni_passau.facultyinfo.client.model.dto.Event;
import de.uni_passau.facultyinfo.client.model.dto.EventSearchResult;
import de.uni_passau.facultyinfo.client.util.CacheHelper;

/**
 * @author Timo Staudinger
 * 
 */
public class EventAccess extends Access {
	private static final String RESSOURCE = "/event";

	private static final String TABLE_NAME = "events";
	private static final int INDEX_ID = 0;
	private static final String KEY_ID = "id";
	private static final int INDEX_TITLE = 1;
	private static final String KEY_TITLE = "title";
	private static final int INDEX_SUBTITLE = 2;
	private static final String KEY_SUBTITLE = "subtitle";
	private static final int INDEX_LOCATION = 3;
	private static final String KEY_LOCATION = "location";
	private static final int INDEX_DESCRIPTION = 4;
	private static final String KEY_DESCRIPTION = "description";
	private static final int INDEX_STARTDATE = 5;
	private static final String KEY_STARTDATE = "startdate";
	private static final int INDEX_ENDDATE = 6;
	private static final String KEY_ENDDATE = "enddate";
	private static final int INDEX_HOST = 7;
	private static final String KEY_HOST = "host";
	private static final int INDEX_URL = 8;
	private static final String KEY_URL = "url";

	private static EventAccess instance = null;

	protected static EventAccess getInstance() {
		if (instance == null) {
			instance = new EventAccess();
		}
		return instance;
	}

	private RestConnection<Event> restConnection = null;

	private List<Event> cachedEvents = null;
	private Date cachedEventsTimestamp = null;

	private HashMap<Event, Date> cachedEventList = new HashMap<Event, Date>();

	private EventAccess() {
		restConnection = new RestConnection<Event>(Event.class);
	}

	/**
	 * Gives a list of available News.
	 * 
	 */
	public List<Event> getEvents() {
		return getEvents(false);
	}

	/**
	 * Gives a list of available News.
	 * 
	 */
	public List<Event> getEvents(boolean forceRefresh) {
		List<Event> event = null;

		if (forceRefresh || cachedEvents == null
				|| cachedEventsTimestamp == null
				|| cachedEventsTimestamp.before(CacheHelper.getExpiringDate())) {

			event = restConnection.getRessourceAsList(RESSOURCE);

			if (event != null) {
				cachedEvents = event;
				cachedEventsTimestamp = new Date();

				writeCache(event);
			}

		} else {
			event = cachedEvents;
		}

		if (event == null) {
			return null;
		}

		return Collections.unmodifiableList(event);
	}

	/**
	 * Gives a list of available News that are cached locally.
	 * 
	 */
	public List<Event> getEventsFromCache() {
		List<Event> events = readCache();

		if (events == null) {
			return null;
		}

		return Collections.unmodifiableList(events);
	}

	/**
	 * Gives detailed information about a specific News.
	 * 
	 */
	public Event getEvent(String eventId) {
		return getEvent(eventId, false);
	}

	/**
	 * Gives detailed information about a specific News.
	 * 
	 */
	public Event getEvent(String eventId, boolean forceRefresh) {
		Event event = null;

		if (!forceRefresh) {
			Iterator<Entry<Event, Date>> iterator = cachedEventList.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Entry<Event, Date> entry = iterator.next();
				if (entry.getKey().getId().equals(eventId)
						&& !entry.getValue().before(
								CacheHelper.getExpiringDate())) {
					event = entry.getKey();
					break;
				}
			}
		}

		if (event == null) {
			event = restConnection.getRessource(RESSOURCE + '/' + eventId);
			if (event != null) {
				cachedEventList.put(event, new Date());

				writeCache(event);
			}
		}

		return event;
	}

	/**
	 * Gives detailed information about a specific News that is cached locally.
	 * 
	 * @param eventId
	 * @return Event
	 */
	public Event getEventFromCache(String eventId) {
		return readCache(eventId);
	}

	/**
	 * Gives a list of Events that contain the search string in one or more of
	 * the following attributes:
	 * <ul>
	 * <li>Title</li>
	 * <li>Subtitle</li>
	 * <li>Description</li>
	 * <li>Host</li>
	 * <li>Location</li>
	 * </ul>
	 * 
	 * The search is case insensitive.
	 * 
	 * @param input
	 *            The search parameter
	 */
	public List<EventSearchResult> find(String input) {
		if (input != null && !input.isEmpty()) {
			List<Event> events = null;

			events = restConnection.getRessourceAsList(RESSOURCE + "/find/"
					+ input);

			ArrayList<EventSearchResult> results = new ArrayList<EventSearchResult>();

			for (Event event : events) {
				String subtitle = null;
				subtitle = event.getLocation() != null ? event.getLocation()
						: subtitle;
				subtitle = event.getDescription() != null ? event
						.getDescription() : subtitle;
				subtitle = event.getHost() != null ? event.getHost() : subtitle;
				subtitle = event.getSubtitle() != null ? event.getSubtitle()
						: subtitle;
				EventSearchResult result = new EventSearchResult(event.getId(),
						event.getTitle(), subtitle);
				results.add(result);
			}

			return Collections.unmodifiableList(results);
		}
		return Collections.unmodifiableList(new ArrayList<EventSearchResult>());
	}

	private boolean writeCache(List<Event> events) {
		boolean result = true;

		ArrayList<String> idArray = new ArrayList<String>();
		for (Event event : events) {
			idArray.add(event.getId());
		}
		String idList = "'"
				+ Joiner.on("','").skipNulls()
						.join(idArray.toArray(new String[idArray.size()]))
				+ "'";

		SQLiteDatabase writableDatabase = getCacheOpenHelper()
				.getWritableDatabase();
		writableDatabase.execSQL("DELETE FROM " + TABLE_NAME
				+ " WHERE id NOT IN (" + idList + ")");
		for (Event event : events) {
			ContentValues values = new ContentValues();
			values.put(KEY_ID, event.getId());
			values.put(KEY_TITLE, event.getTitle());
			values.put(KEY_STARTDATE, event.getStartDate().getTime());
			values.put(KEY_ENDDATE, event.getEndDate().getTime());
			values.put(KEY_HOST, event.getHost());
			result = (writableDatabase.replace(TABLE_NAME, null, values) != -1L)
					&& result;
		}

		writableDatabase.close();
		return result;
	}

	private boolean writeCache(Event event) {
		boolean result = true;
		SQLiteDatabase writableDatabase = getCacheOpenHelper()
				.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_ID, event.getId());
		values.put(KEY_TITLE, event.getTitle());
		values.put(KEY_SUBTITLE, event.getSubtitle());
		values.put(KEY_LOCATION, event.getLocation());
		values.put(KEY_DESCRIPTION, event.getDescription());
		values.put(KEY_STARTDATE, event.getStartDate().getTime());
		values.put(KEY_ENDDATE, event.getEndDate().getTime());
		values.put(KEY_HOST, event.getHost());
		values.put(KEY_URL, event.getUrl());
		result = writableDatabase.replace(TABLE_NAME, null, values) != -1L;

		writableDatabase.close();
		return result;
	}

	private List<Event> readCache() {
		SQLiteDatabase db = getCacheOpenHelper().getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME
				+ " ORDER BY " + KEY_STARTDATE + ", " + KEY_TITLE, null);
		ArrayList<Event> entries = new ArrayList<Event>();
		if (cursor != null && cursor.moveToFirst()) {
			do {
				String id = cursor.getString(INDEX_ID);
				String title = cursor.getString(INDEX_TITLE);
				String subtitle = null;
				String location = null;
				String description = null;
				Date startDate = new Date(cursor.getLong(INDEX_STARTDATE));
				Date endDate = new Date(cursor.getLong(INDEX_ENDDATE));
				String host = cursor.getString(INDEX_HOST);
				String url = null;
				Event entry = new Event(id, title, subtitle, location,
						description, startDate, endDate, host, url);
				entries.add(entry);
			} while (cursor.moveToNext());
		}
		db.close();
		return entries;
	}

	private Event readCache(String eventId) {
		Event event = null;
		SQLiteDatabase db = getCacheOpenHelper().getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME
				+ " WHERE id = ?", new String[] { eventId });
		if (cursor != null && cursor.moveToFirst()) {
			String id = cursor.getString(INDEX_ID);
			String title = cursor.getString(INDEX_TITLE);
			String subtitle = cursor.getString(INDEX_SUBTITLE);
			String location = cursor.getString(INDEX_LOCATION);
			String description = cursor.getString(INDEX_DESCRIPTION);
			Date startDate = new Date(cursor.getLong(INDEX_STARTDATE));
			Date endDate = new Date(cursor.getLong(INDEX_ENDDATE));
			String host = cursor.getString(INDEX_HOST);
			String url = cursor.getString(INDEX_URL);
			event = new Event(id, title, subtitle, location, description,
					startDate, endDate, host, url);
		}
		db.close();
		return event;
	}
}
