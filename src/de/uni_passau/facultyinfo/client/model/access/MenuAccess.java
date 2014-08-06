package de.uni_passau.facultyinfo.client.model.access;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import de.uni_passau.facultyinfo.client.model.connection.RestConnection;
import de.uni_passau.facultyinfo.client.model.dto.MenuItem;
import de.uni_passau.facultyinfo.client.util.CacheHelper;

/**
 * @author Timo Staudinger
 * 
 */
public class MenuAccess extends Access {
	private static final String RESSOURCE = "/menu";

	private static final String TABLE_NAME = "menuitems";
	private static final int INDEX_ID = 0;
	private static final String KEY_ID = "id";
	private static final int INDEX_DAY = 1;
	private static final String KEY_DAY = "day";
	private static final int INDEX_NAME = 2;
	private static final String KEY_NAME = "name";
	private static final int INDEX_TYPE = 3;
	private static final String KEY_TYPE = "type";
	private static final int INDEX_ATTRIBUTES = 4;
	private static final String KEY_ATTRIBUTES = "attributes";
	private static final int INDEX_PRICESTUDENT = 5;
	private static final String KEY_PRICESTUDENT = "pricestudent";
	private static final int INDEX_PRICEEMPLOYEE = 6;
	private static final String KEY_PRICEEMPLOYEE = "priceemployee";
	private static final int INDEX_PRICEEXTERNAL = 7;
	private static final String KEY_PRICEEXTERNAL = "priceexternal";

	private List<MenuItem> cachedMenuItems = null;
	private Date cachedMenuItemsTimestamp = null;

	private static MenuAccess instance = null;

	protected static MenuAccess getInstance() {
		if (instance == null) {
			instance = new MenuAccess();
		}
		return instance;
	}

	private RestConnection<MenuItem> restConnection = null;

	private MenuAccess() {
		restConnection = new RestConnection<MenuItem>(MenuItem.class);
	}

	/**
	 * Gives a list of all Menu items of this week.
	 * 
	 */
	public List<MenuItem> getMenuItems() {
		return getMenuItems(null, false);
	}

	/**
	 * Gives a list of all Menu items of this week.
	 * 
	 */
	public List<MenuItem> getMenuItems(boolean forceRefresh) {
		return getMenuItems(null, forceRefresh);
	}

	/**
	 * Gives a list of all Menu items of this week.
	 * 
	 */
	public List<MenuItem> getMenuItems(Integer dayOfWeek) {
		return getMenuItems(dayOfWeek, false);
	}

	/**
	 * Gives a list of all Menu items of this week.
	 * 
	 */
	public List<MenuItem> getMenuItems(Integer dayOfWeek, boolean forceRefresh) {
		List<MenuItem> menuItems = null;

		if (forceRefresh
				|| cachedMenuItems == null
				|| cachedMenuItemsTimestamp == null
				|| cachedMenuItemsTimestamp.before(CacheHelper
						.getExpiringDate())) {

			menuItems = restConnection.getRessourceAsList(RESSOURCE);

			if (menuItems != null) {
				cachedMenuItems = menuItems;
				cachedMenuItemsTimestamp = new Date();

				writeCache(menuItems);
			}

		} else {
			menuItems = cachedMenuItems;
		}

		if (menuItems == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(new Date());
		int currentWeek = cal.get(Calendar.WEEK_OF_YEAR);

		List<MenuItem> filteredMenuItems = new ArrayList<MenuItem>();
		for (MenuItem menuItem : menuItems) {
			cal.setTime(menuItem.getDay());
			if (cal.get(Calendar.WEEK_OF_YEAR) == currentWeek
					&& (dayOfWeek == null || dayOfWeek == menuItem
							.getDayOfWeek())) {
				filteredMenuItems.add(menuItem);
			}
		}

		return Collections.unmodifiableList(filteredMenuItems);
	}

	/**
	 * Gives a list of all Menu items that are cached locally.
	 * 
	 */
	public List<MenuItem> getMenuItemsFromCache() {
		return getMenuItemsFromCache(null, null);
	}

	/**
	 * Gives a list of all Menu items that are cached locally.
	 * 
	 */
	public List<MenuItem> getMenuItemsFromCache(Integer dayOfWeek, Integer type) {
		List<MenuItem> menuItems = readCache();

		if (menuItems == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(new Date());
		int currentWeek = cal.get(Calendar.WEEK_OF_YEAR);

		List<MenuItem> filteredMenuItems = new ArrayList<MenuItem>();
		for (MenuItem menuItem : menuItems) {
			cal.setTime(menuItem.getDay());
			if (cal.get(Calendar.WEEK_OF_YEAR) == currentWeek
					&& (dayOfWeek == null || dayOfWeek == menuItem
							.getDayOfWeek()
							&& (type == null || menuItem.getType() == type))) {
				filteredMenuItems.add(menuItem);
			}
		}

		return Collections.unmodifiableList(filteredMenuItems);
	}

	private boolean writeCache(List<MenuItem> menuItems) {
		boolean result = true;

		SQLiteDatabase writableDatabase = getCacheOpenHelper()
				.getWritableDatabase();
		writableDatabase.execSQL("DELETE FROM " + TABLE_NAME);

		for (MenuItem menuItem : menuItems) {
			ContentValues values = new ContentValues();
			values.put(KEY_ID, menuItem.getId());
			values.put(KEY_DAY, menuItem.getDay().getTime());
			values.put(KEY_NAME, menuItem.getName());
			values.put(KEY_TYPE, menuItem.getType());
			values.put(KEY_ATTRIBUTES, menuItem.getAttributes());
			values.put(KEY_PRICESTUDENT, menuItem.getPriceStudent());
			values.put(KEY_PRICEEMPLOYEE, menuItem.getPriceEmployee());
			values.put(KEY_PRICEEXTERNAL, menuItem.getPriceExternal());
			result = (writableDatabase.replace(TABLE_NAME, null, values) != -1L)
					&& result;
		}

		writableDatabase.close();
		return result;
	}

	private List<MenuItem> readCache() {
		SQLiteDatabase db = getCacheOpenHelper().getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME
				+ " ORDER BY " + KEY_DAY + ", " + KEY_TYPE + ", " + KEY_NAME,
				null);
		ArrayList<MenuItem> entries = new ArrayList<MenuItem>();
		if (cursor != null && cursor.moveToFirst()) {
			do {
				String id = cursor.getString(INDEX_ID);
				Date day = new Date(cursor.getLong(INDEX_DAY));
				String name = cursor.getString(INDEX_NAME);
				int type = cursor.getInt(INDEX_TYPE);
				int attributes = cursor.getInt(INDEX_ATTRIBUTES);
				double priceStudent = cursor.getDouble(INDEX_PRICESTUDENT);
				double priceEmployee = cursor.getDouble(INDEX_PRICEEMPLOYEE);
				double priceExternal = cursor.getDouble(INDEX_PRICEEXTERNAL);
				MenuItem entry = new MenuItem(id, day, name, type, attributes,
						priceStudent, priceEmployee, priceExternal);
				entries.add(entry);
			} while (cursor.moveToNext());
		}
		db.close();
		return entries;
	}

}
