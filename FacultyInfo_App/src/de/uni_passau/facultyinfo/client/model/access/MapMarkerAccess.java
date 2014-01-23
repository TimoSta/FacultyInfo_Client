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
import de.uni_passau.facultyinfo.client.model.connection.RestConnection;
import de.uni_passau.facultyinfo.client.model.dto.MapMarker;
import de.uni_passau.facultyinfo.client.model.dto.MapMarkerCategory;
import de.uni_passau.facultyinfo.client.util.CacheHelper;

/**
 * @author Timo Staudinger
 * 
 */
public class MapMarkerAccess extends Access {
	private static final String RESSOURCE = "/mapmarker";

	private static final String TABLE_NAME_CATEGORIES = "mapmarkercategories";
	private static final int INDEX_CATEGORIES_ID = 0;
	private static final String KEY_CATEGORIES_ID = "id";
	private static final int INDEX_CATEGORIES_CATEGORY = 1;
	private static final String KEY_CATEGORIES_CATEGORY = "category";
	private static final int INDEX_CATEGORIES_TITLE = 2;
	private static final String KEY_CATEGORIES_TITLE = "title";

	private static final String TABLE_NAME_MARKERS = "mapmarkers";
	private static final int INDEX_MARKERS_ID = 0;
	private static final String KEY_MARKERS_ID = "id";
	private static final int INDEX_MARKERS_CATEGORY = 1;
	private static final String KEY_MARKERS_CATEGORY = "category";
	private static final int INDEX_MARKERS_NAME = 2;
	private static final String KEY_MARKERS_NAME = "name";
	private static final int INDEX_MARKERS_DESCRIPTION = 3;
	private static final String KEY_MARKERS_DESCRIPTION = "description";
	private static final int INDEX_MARKERS_LATITUDE = 4;
	private static final String KEY_MARKERS_LATITUDE = "latitude";
	private static final int INDEX_MARKERS_LONGITUDE = 5;
	private static final String KEY_MARKERS_LONGITUDE = "longitude";

	private static MapMarkerAccess instance = null;

	protected static MapMarkerAccess getInstance() {
		if (instance == null) {
			instance = new MapMarkerAccess();
		}
		return instance;
	}

	private RestConnection<MapMarkerCategory> restConnection = null;

	private List<MapMarkerCategory> cachedMapMarkers = null;
	private Date cachedMapMarkersTimestamp = null;

	private RestConnection<MapMarkerCategory> getRestConnection() {
		if (restConnection == null) {
			restConnection = new RestConnection<MapMarkerCategory>(
					MapMarkerCategory.class);
		}
		return restConnection;
	}

	private MapMarkerAccess() {
		super();
	}

	/**
	 * Gives a list of available map marker categories containing sub categories
	 * and map markers.
	 * 
	 */
	public List<MapMarkerCategory> getMapMarkers() {
		return getMapMarkers(false);
	}

	/**
	 * Gives a list of available map marker categories containing sub categories
	 * and map markers.
	 * 
	 */
	public List<MapMarkerCategory> getMapMarkers(boolean forceRefresh) {
		List<MapMarkerCategory> mapMarkerCategories = null;

		if (forceRefresh
				|| cachedMapMarkers == null
				|| cachedMapMarkersTimestamp == null
				|| cachedMapMarkersTimestamp.before(CacheHelper
						.getExpiringDate())) {
			mapMarkerCategories = getRestConnection().getRessourceAsList(
					RESSOURCE);

			if (mapMarkerCategories != null) {
				for (MapMarkerCategory mapMarkerCategory : mapMarkerCategories) {
					setSuperCategoryInTree(mapMarkerCategory);
				}

				cachedMapMarkers = mapMarkerCategories;
				cachedMapMarkersTimestamp = new Date();

				writeCache(mapMarkerCategories);
			}
		} else {
			mapMarkerCategories = cachedMapMarkers;
		}

		if (mapMarkerCategories == null) {
			return null;
		}

		return Collections.unmodifiableList(mapMarkerCategories);
	}

	/**
	 * Gives a list of FAQs that are currently cached locally.
	 * 
	 */
	public List<MapMarkerCategory> getMapMarkersFromCache() {
		List<MapMarkerCategory> categories = readCache();
		return Collections.unmodifiableList(categories);
	}

	private void setSuperCategoryInTree(MapMarkerCategory superCategory) {
		if (superCategory != null) {
			if (superCategory.getMapMarkers() != null) {
				for (MapMarker marker : superCategory.getMapMarkers()) {
					marker.setCategory(superCategory);
				}
			}
			if (superCategory.getMapMarkerCategories() != null) {
				for (MapMarkerCategory mapMarkerCategory : superCategory
						.getMapMarkerCategories()) {
					mapMarkerCategory.setSuperCategory(superCategory);
					setSuperCategoryInTree(mapMarkerCategory);
				}
			}
		}
	}

	private boolean writeCache(List<MapMarkerCategory> categories) {
		boolean result = true;

		SQLiteDatabase writableDatabase = getCacheOpenHelper()
				.getWritableDatabase();
		writableDatabase.execSQL("DELETE FROM " + TABLE_NAME_MARKERS);
		writableDatabase.execSQL("DELETE FROM " + TABLE_NAME_CATEGORIES);

		for (MapMarkerCategory mapMarkerCategory : categories) {
			result = writeMapMarkerCategoryIntoCache(mapMarkerCategory,
					writableDatabase) && result;
		}

		writableDatabase.close();
		return result;
	}

	private boolean writeMapMarkerCategoryIntoCache(MapMarkerCategory category,
			SQLiteDatabase writableDatabase) {
		boolean result = true;

		ContentValues values = new ContentValues();
		values.put(KEY_CATEGORIES_ID, category.getId());
		values.put(KEY_CATEGORIES_CATEGORY,
				category.getSuperCategory() != null ? category
						.getSuperCategory().getId() : null);
		values.put(KEY_CATEGORIES_TITLE, category.getTitle());
		result = (writableDatabase.replace(TABLE_NAME_CATEGORIES, null, values) != -1L)
				&& result;

		if (category.getMapMarkers() != null) {
			for (MapMarker marker : category.getMapMarkers()) {
				values = new ContentValues();
				values.put(KEY_MARKERS_ID, marker.getId());
				values.put(KEY_MARKERS_CATEGORY, marker.getCategory().getId());
				values.put(KEY_MARKERS_NAME, marker.getName());
				values.put(KEY_MARKERS_DESCRIPTION, marker.getDescription());
				values.put(KEY_MARKERS_LATITUDE, marker.getLatitude());
				values.put(KEY_MARKERS_LONGITUDE, marker.getLongitude());
				result = (writableDatabase.replace(TABLE_NAME_MARKERS, null,
						values) != -1L) && result;
			}
		}
		if (category.getMapMarkerCategories() != null) {
			for (MapMarkerCategory subCategory : category
					.getMapMarkerCategories()) {
				result = writeMapMarkerCategoryIntoCache(subCategory,
						writableDatabase) && result;
			}
		}

		return result;
	}

	private List<MapMarkerCategory> readCache() {
		SQLiteDatabase readableDatabase = getCacheOpenHelper()
				.getReadableDatabase();
		Cursor cursor = readableDatabase.rawQuery("SELECT * FROM "
				+ TABLE_NAME_CATEGORIES + " ORDER BY " + KEY_CATEGORIES_TITLE,
				null);
		ArrayList<MapMarkerCategory> superCategories = new ArrayList<MapMarkerCategory>();
		HashMap<MapMarkerCategory, String> subCategories = new HashMap<MapMarkerCategory, String>();
		if (cursor != null && cursor.moveToFirst()) {
			do {
				String id = cursor.getString(INDEX_CATEGORIES_ID);
				String title = cursor.getString(INDEX_CATEGORIES_TITLE);
				String superCategoryId = cursor
						.getString(INDEX_CATEGORIES_CATEGORY);

				MapMarkerCategory category = new MapMarkerCategory(id, title);
				if (superCategoryId == null || superCategoryId.isEmpty()) {
					superCategories.add(category);
				} else {
					subCategories.put(category, superCategoryId);
				}
			} while (cursor.moveToNext());
		}

		buildCategoryTreeAndLoadMapMarkers(superCategories, subCategories,
				readableDatabase);

		readableDatabase.close();
		return superCategories;
	}

	private void buildCategoryTreeAndLoadMapMarkers(
			List<MapMarkerCategory> superCategories,
			HashMap<MapMarkerCategory, String> subCategories,
			SQLiteDatabase readableDatabase) {
		if (superCategories != null) {
			for (MapMarkerCategory mapMarkerCategory : superCategories) {
				Iterator<Entry<MapMarkerCategory, String>> iterator = subCategories
						.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<MapMarkerCategory, String> entry = iterator.next();
					if (mapMarkerCategory.getId().equals(entry.getValue())) {
						mapMarkerCategory.addMapMarkerCategory(entry.getKey());
						entry.getKey().setSuperCategory(mapMarkerCategory);
					}
				}

				Cursor cursor = readableDatabase.rawQuery("SELECT * FROM "
						+ TABLE_NAME_MARKERS + " WHERE " + KEY_MARKERS_CATEGORY
						+ " = ? ORDER BY " + KEY_MARKERS_NAME,
						new String[] { mapMarkerCategory.getId() });
				List<MapMarker> mapMarkers = new ArrayList<MapMarker>();
				if (cursor != null && cursor.moveToFirst()) {
					do {
						String id = cursor.getString(INDEX_MARKERS_ID);
						String name = cursor.getString(INDEX_MARKERS_NAME);
						String description = cursor
								.getString(INDEX_MARKERS_DESCRIPTION);
						double latitude = cursor.getInt(INDEX_MARKERS_LATITUDE);
						double longitude = cursor
								.getInt(INDEX_MARKERS_LONGITUDE);
						MapMarker mapMarker = new MapMarker(id, name,
								description, latitude, longitude);
						mapMarker.setCategory(mapMarkerCategory);
						mapMarkers.add(mapMarker);
					} while (cursor.moveToNext());

				}

				mapMarkerCategory.setMapMarkers(mapMarkers);

				buildCategoryTreeAndLoadMapMarkers(
						mapMarkerCategory.getMapMarkerCategories(),
						subCategories, readableDatabase);
			}
		}
	}

}
