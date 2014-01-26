package de.uni_passau.facultyinfo.client.model.access;

import java.text.ParseException;
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
import de.uni_passau.facultyinfo.client.model.dto.BusinessHours;
import de.uni_passau.facultyinfo.client.model.dto.BusinessHoursFacility;
import de.uni_passau.facultyinfo.client.model.dto.util.Time;
import de.uni_passau.facultyinfo.client.util.CacheHelper;

/**
 * @author Timo Staudinger
 * 
 */
@SuppressWarnings("unused")
public class BusinessHoursAccess extends Access {
	private static final String RESSOURCE = "/businesshours";

	private static final String TABLE_NAME_BUSINESSHOURS = "businesshours";
	private static final int INDEX_BUSINESSHOURS_ID = 0;
	private static final String KEY_BUSINESSHOURS_ID = "id";
	private static final int INDEX_BUSINESSHOURS_FACILITY = 1;
	private static final String KEY_BUSINESSHOURS_FACILITY = "facility";
	private static final int INDEX_BUSINESSHOURS_DAYOFWEEK = 2;
	private static final String KEY_BUSINESSHOURS_DAYOFWEEK = "dayofweek";
	private static final int INDEX_BUSINESSHOURS_PHASE = 3;
	private static final String KEY_BUSINESSHOURS_PHASE = "phase";
	private static final int INDEX_BUSINESSHOURS_STATUS = 4;
	private static final String KEY_BUSINESSHOURS_STATUS = "status";
	private static final int INDEX_BUSINESSHOURS_OPENINGTIME = 5;
	private static final String KEY_BUSINESSHOURS_OPENINGTIME = "openingtime";
	private static final int INDEX_BUSINESSHOURS_CLOSINGTIME = 6;
	private static final String KEY_BUSINESSHOURS_CLOSINGTIME = "closingtime";

	private static final String TABLE_NAME_FACILITIES = "businesshoursfacilities";
	private static final int INDEX_FACILITY_ID = 0;
	private static final String KEY_FACILITY_ID = "id";
	private static final int INDEX_FACILITY_NAME = 1;
	private static final String KEY_FACILITY_NAME = "name";
	private static final int INDEX_FACILITY_TYPE = 2;
	private static final String KEY_FACILITY_TYPE = "type";

	private static BusinessHoursAccess instance = null;

	protected static BusinessHoursAccess getInstance() {
		if (instance == null) {
			instance = new BusinessHoursAccess();
		}
		return instance;
	}

	private RestConnection<BusinessHoursFacility> restConnection = null;

	private List<BusinessHoursFacility> cachedCafeterias = null;
	private Date cachedCafeteriasTimestamp = null;
	private List<BusinessHoursFacility> cachedLibraries = null;
	private Date cachedLibrariesTimestamp = null;

	private HashMap<BusinessHoursFacility, Date> cachedFacilities = new HashMap<BusinessHoursFacility, Date>();

	private BusinessHoursAccess() {
		restConnection = new RestConnection<BusinessHoursFacility>(
				BusinessHoursFacility.class);
	}

	/**
	 * Gives a list of all Cafeterias.
	 * 
	 */
	public List<BusinessHoursFacility> getCafeterias() {
		return getCafeterias(false);
	}

	/**
	 * Gives a list of all Cafeterias.
	 * 
	 */
	public List<BusinessHoursFacility> getCafeterias(boolean forceRefresh) {
		List<BusinessHoursFacility> cafeterias = null;

		if (forceRefresh
				|| cachedCafeterias == null
				|| cachedCafeteriasTimestamp == null
				|| cachedCafeteriasTimestamp.before(CacheHelper
						.getExpiringDate())) {
			cafeterias = restConnection.getRessourceAsList(RESSOURCE
					+ "/cafeteria");

			if (cafeterias != null) {
				cachedCafeterias = cafeterias;
				cachedCafeteriasTimestamp = new Date();

				writeCache(cafeterias, BusinessHoursFacility.TYPE_CAFETERIA);
			}
		} else {
			cafeterias = cachedCafeterias;
		}

		if (cafeterias == null) {
			return null;
		}

		return Collections.unmodifiableList(cafeterias);
	}

	/**
	 * Gives a list of all Cafeterias that are cached locally.
	 * 
	 */
	public List<BusinessHoursFacility> getCafeteriasFromCache() {
		List<BusinessHoursFacility> cafeterias = readCache(BusinessHoursFacility.TYPE_CAFETERIA);
		return Collections.unmodifiableList(cafeterias);
	}

	/**
	 * Gives a list of all Libraries.
	 * 
	 */
	public List<BusinessHoursFacility> getLibraries() {
		return getLibraries(false);
	}

	/**
	 * Gives a list of all Libraries.
	 * 
	 */
	public List<BusinessHoursFacility> getLibraries(boolean forceRefresh) {
		List<BusinessHoursFacility> libraries = null;

		if (forceRefresh
				|| cachedLibraries == null
				|| cachedLibrariesTimestamp == null
				|| cachedLibrariesTimestamp.before(CacheHelper
						.getExpiringDate())) {
			libraries = restConnection.getRessourceAsList(RESSOURCE
					+ "/library");

			if (libraries != null) {
				cachedLibraries = libraries;
				cachedLibrariesTimestamp = new Date();

				writeCache(libraries, BusinessHoursFacility.TYPE_LIBRARY);
			}
		} else {
			libraries = cachedLibraries;
		}

		if (libraries == null) {
			return null;
		}

		return Collections.unmodifiableList(libraries);
	}

	/**
	 * Gives a list of all Libraries that are cached locally.
	 * 
	 */
	public List<BusinessHoursFacility> getLibrariesFromCache() {
		List<BusinessHoursFacility> libraries = readCache(BusinessHoursFacility.TYPE_LIBRARY);
		return Collections.unmodifiableList(libraries);
	}

	/**
	 * Gives detailed information about a specific Facility.
	 * 
	 */
	public BusinessHoursFacility getFacility(String id) {
		return getFacility(id, false);
	}

	/**
	 * Gives detailed information about a specific Facility.
	 * 
	 */
	public BusinessHoursFacility getFacility(String id, boolean forceRefresh) {
		BusinessHoursFacility facility = null;

		if (!forceRefresh) {
			Iterator<Entry<BusinessHoursFacility, Date>> iterator = cachedFacilities
					.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<BusinessHoursFacility, Date> entry = iterator.next();
				if (entry.getKey().getId().equals(id)
						&& !entry.getValue().before(
								CacheHelper.getExpiringDate())) {
					facility = entry.getKey();
					break;
				}
			}
		}

		if (facility == null) {
			facility = restConnection.getRessource(RESSOURCE + '/' + id);

			if (facility != null) {
				for (BusinessHours businessHours : facility.getBusinessHours()) {
					businessHours.setAssociatedFacility(facility);
				}

				cachedFacilities.put(facility, new Date());

				writeCache(facility);
			}
		}

		return facility;
	}

	/**
	 * Gives detailed information about a specific Facility that is cached
	 * locally.
	 * 
	 */
	public BusinessHoursFacility getFacilityFromCache(String id) {
		BusinessHoursFacility facility = readCache(id);
		return facility;
	}

	private boolean writeCache(List<BusinessHoursFacility> facilities, int type) {
		boolean result = true;

		ArrayList<String> idArray = new ArrayList<String>();
		for (BusinessHoursFacility facility : facilities) {
			idArray.add(facility.getId());
		}
		String idList = "'"
				+ Joiner.on("','").skipNulls()
						.join(idArray.toArray(new String[idArray.size()]))
				+ "'";

		SQLiteDatabase writableDatabase = getCacheOpenHelper()
				.getWritableDatabase();
		writableDatabase.execSQL("DELETE FROM " + TABLE_NAME_BUSINESSHOURS
				+ " WHERE facility NOT IN (" + idList
				+ ") AND id IN (SELECT id FROM " + TABLE_NAME_FACILITIES
				+ " WHERE type = " + type + ")");
		writableDatabase.execSQL("DELETE FROM " + TABLE_NAME_FACILITIES
				+ " WHERE type = " + type + " AND id NOT IN (" + idList + ")");

		for (BusinessHoursFacility facility : facilities) {
			ContentValues values = new ContentValues();
			values.put(KEY_FACILITY_ID, facility.getId());
			values.put(KEY_FACILITY_NAME, facility.getName());
			values.put(KEY_FACILITY_TYPE, facility.getType());
			result = (writableDatabase.replace(TABLE_NAME_FACILITIES, null,
					values) != -1L) && result;
		}

		writableDatabase.close();
		return result;
	}

	private boolean writeCache(BusinessHoursFacility facility) {
		boolean result = true;
		SQLiteDatabase writableDatabase = getCacheOpenHelper()
				.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_FACILITY_ID, facility.getId());
		values.put(KEY_FACILITY_NAME, facility.getName());
		values.put(KEY_FACILITY_TYPE, facility.getType());
		result = (writableDatabase.replace(TABLE_NAME_FACILITIES, null, values) != -1L)
				&& result;
		if (result && facility.getBusinessHours() != null) {
			for (BusinessHours businessHours : facility.getBusinessHours()) {
				ContentValues businessHoursValues = new ContentValues();
				businessHoursValues.put(KEY_BUSINESSHOURS_ID,
						businessHours.getId());
				businessHoursValues.put(KEY_BUSINESSHOURS_FACILITY,
						businessHours.getAssociatedFacility().getId());
				businessHoursValues.put(KEY_BUSINESSHOURS_DAYOFWEEK,
						businessHours.getDayOfWeek());
				businessHoursValues.put(KEY_BUSINESSHOURS_PHASE,
						businessHours.getPhase());
				businessHoursValues.put(KEY_BUSINESSHOURS_STATUS,
						businessHours.getStatus());
				businessHoursValues.put(KEY_BUSINESSHOURS_OPENINGTIME,
						businessHours.getOpeningTime() != null ? businessHours
								.getOpeningTime().toStringWithSeconds() : null);
				businessHoursValues.put(KEY_BUSINESSHOURS_CLOSINGTIME,
						businessHours.getClosingTime() != null ? businessHours
								.getClosingTime().toStringWithSeconds() : null);
				result = (writableDatabase.replace(TABLE_NAME_BUSINESSHOURS,
						null, businessHoursValues) != -1L) && result;
			}
		}

		writableDatabase.close();
		return result;
	}

	private List<BusinessHoursFacility> readCache(int type) {
		SQLiteDatabase db = getCacheOpenHelper().getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_FACILITIES
				+ " WHERE type = " + type + " ORDER BY " + KEY_FACILITY_NAME,
				null);
		ArrayList<BusinessHoursFacility> facilities = new ArrayList<BusinessHoursFacility>();
		if (cursor != null && cursor.moveToFirst()) {
			do {
				String id = cursor.getString(INDEX_FACILITY_ID);
				String name = cursor.getString(INDEX_FACILITY_NAME);
				int facilityType = cursor.getInt(INDEX_FACILITY_TYPE);
				BusinessHoursFacility facility = new BusinessHoursFacility(id,
						name, facilityType);
				facilities.add(facility);
			} while (cursor.moveToNext());
		}

		db.close();
		return facilities;
	}

	private BusinessHoursFacility readCache(String id) {
		BusinessHoursFacility facility = null;
		SQLiteDatabase db = getCacheOpenHelper().getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_FACILITIES
				+ " WHERE id = ?", new String[] { id });
		if (cursor != null && cursor.moveToFirst()) {
			String name = cursor.getString(INDEX_FACILITY_NAME);
			int facilityType = cursor.getInt(INDEX_FACILITY_TYPE);
			facility = new BusinessHoursFacility(id, name, facilityType);

			cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_BUSINESSHOURS
					+ " WHERE facility = ?", new String[] { id });
			ArrayList<BusinessHours> businessHoursList = new ArrayList<BusinessHours>();
			if (cursor != null && cursor.moveToFirst()) {
				do {
					try {
						String businessHoursId = cursor
								.getString(INDEX_BUSINESSHOURS_ID);
						int dayOfWeek = cursor
								.getInt(INDEX_BUSINESSHOURS_DAYOFWEEK);
						int phase = cursor.getInt(INDEX_BUSINESSHOURS_PHASE);
						int status = cursor.getInt(INDEX_BUSINESSHOURS_STATUS);
						Time openingTime = cursor
								.getString(INDEX_BUSINESSHOURS_OPENINGTIME) != null ? new Time(
								cursor.getString(INDEX_BUSINESSHOURS_OPENINGTIME))
								: null;
						Time closingTime = cursor
								.getString(INDEX_BUSINESSHOURS_CLOSINGTIME) != null ? new Time(
								cursor.getString(INDEX_BUSINESSHOURS_CLOSINGTIME))
								: null;
						BusinessHours businessHours = new BusinessHours(
								businessHoursId, dayOfWeek, phase, status,
								openingTime, closingTime);
						businessHours.setAssociatedFacility(facility);
						businessHoursList.add(businessHours);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} while (cursor.moveToNext());
			}
			facility.setBusinessHours(businessHoursList);
		}

		db.close();
		return facility;
	}
}
