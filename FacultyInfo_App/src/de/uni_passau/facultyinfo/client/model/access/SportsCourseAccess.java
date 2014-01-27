package de.uni_passau.facultyinfo.client.model.access;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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
import de.uni_passau.facultyinfo.client.model.dto.SportsCourse;
import de.uni_passau.facultyinfo.client.model.dto.SportsCourseCategory;
import de.uni_passau.facultyinfo.client.model.dto.util.Time;
import de.uni_passau.facultyinfo.client.util.CacheHelper;

/**
 * Enables access to remote Sports course ressources.
 * 
 * @author Timo Staudinger
 */
@SuppressWarnings("unused")
public class SportsCourseAccess extends Access {
	private static final String RESSOURCE = "/sportscourse";

	private static final String TABLE_NAME_COURSES = "sportscourses";
	private static final int INDEX_COURSES_ID = 0;
	private static final String KEY_COURSES_ID = "id";
	private static final int INDEX_COURSES_CATEGORY = 1;
	private static final String KEY_COURSES_CATEGORY = "category";
	private static final int INDEX_COURSES_NUMBER = 2;
	private static final String KEY_COURSES_NUMBER = "number";
	private static final int INDEX_COURSES_DETAILS = 3;
	private static final String KEY_COURSES_DETAILS = "details";
	private static final int INDEX_COURSES_DAYOFWEEK = 4;
	private static final String KEY_COURSES_DAYOFWEEK = "dayofweek";
	private static final int INDEX_COURSES_STARTTIME = 5;
	private static final String KEY_COURSES_STARTTIME = "starttime";
	private static final int INDEX_COURSES_ENDTIME = 6;
	private static final String KEY_COURSES_ENDTIME = "endtime";
	private static final int INDEX_COURSES_LOCATION = 7;
	private static final String KEY_COURSES_LOCATION = "location";
	private static final int INDEX_COURSES_STARTDATE = 8;
	private static final String KEY_COURSES_STARTDATE = "startdate";
	private static final int INDEX_COURSES_ENDDATE = 9;
	private static final String KEY_COURSES_ENDDATE = "enddate";
	private static final int INDEX_COURSES_HOST = 10;
	private static final String KEY_COURSES_HOST = "host";
	private static final int INDEX_COURSES_PRICE = 11;
	private static final String KEY_COURSES_PRICE = "price";
	private static final int INDEX_COURSES_STATUS = 12;
	private static final String KEY_COURSES_STATUS = "status";

	private static final String TABLE_NAME_CATEGORIES = "sportscoursecategories";
	private static final int INDEX_CATEGORY_ID = 0;
	private static final String KEY_CATEGORY_ID = "id";
	private static final int INDEX_CATEGORY_TITLE = 1;
	private static final String KEY_CATEGORY_TITLE = "title";

	private static SportsCourseAccess instance = null;

	protected static SportsCourseAccess getInstance() {
		if (instance == null) {
			instance = new SportsCourseAccess();
		}
		return instance;
	}

	private RestConnection<SportsCourse> restConnectionSportsCourse = null;
	private RestConnection<SportsCourseCategory> restConnectionSportsCourseCategory = null;

	private List<SportsCourseCategory> cachedSportsCourseCategoriesList = null;
	private Date cachedSportsCourseCategoriesListTimestamp = null;
	private List<SportsCourseCategory> cachedSportsCourseCategoriesListToday = null;
	private Date cachedSportsCourseCategoriesListTodayTimestamp = null;

	private HashMap<SportsCourseCategory, Date> cachedSportsCourseCategories = new HashMap<SportsCourseCategory, Date>();
	private HashMap<SportsCourse, Date> cachedSportsCourses = new HashMap<SportsCourse, Date>();

	private RestConnection<SportsCourse> getRestConnectionSportsCourse() {
		if (restConnectionSportsCourse == null) {
			restConnectionSportsCourse = new RestConnection<SportsCourse>(
					SportsCourse.class);
		}
		return restConnectionSportsCourse;
	}

	private RestConnection<SportsCourseCategory> getRestConnectionSportsCourseCategory() {
		if (restConnectionSportsCourseCategory == null) {
			restConnectionSportsCourseCategory = new RestConnection<SportsCourseCategory>(
					SportsCourseCategory.class);
		}
		return restConnectionSportsCourseCategory;
	}

	private SportsCourseAccess() {
		super();
	}

	/**
	 * Gives a list of all Sports Course Categories in alphabetical order.
	 * 
	 */
	public List<SportsCourseCategory> getCategories() {
		return getCategories(false);
	}

	/**
	 * Gives a list of all Sports Course Categories in alphabetical order.
	 * 
	 */
	public List<SportsCourseCategory> getCategories(boolean forceRefresh) {
		List<SportsCourseCategory> sportsCourseCategories = null;

		if (forceRefresh
				|| cachedSportsCourseCategoriesList == null
				|| cachedSportsCourseCategoriesListTimestamp == null
				|| cachedSportsCourseCategoriesListTimestamp.before(CacheHelper
						.getExpiringDate())) {
			sportsCourseCategories = getRestConnectionSportsCourseCategory()
					.getRessourceAsList(RESSOURCE);

			if (sportsCourseCategories != null) {
				cachedSportsCourseCategoriesList = sportsCourseCategories;
				cachedSportsCourseCategoriesListTimestamp = new Date();

				writeCache(sportsCourseCategories);
			}
		} else {
			sportsCourseCategories = cachedSportsCourseCategoriesList;
		}

		if (sportsCourseCategories == null) {
			return null;
		}

		return Collections.unmodifiableList(sportsCourseCategories);
	}

	/**
	 * Gives a list of all Sports Course Categories in alphabetical order.
	 * 
	 * Only data cached locally is used.
	 * 
	 */
	public List<SportsCourseCategory> getCategoriesFromCache() {
		List<SportsCourseCategory> categories = readCache();
		return Collections.unmodifiableList(categories);
	}

	/**
	 * Gives a list of all Sports Course Categories in alphabetical order that
	 * have a course that takes place today.
	 * 
	 */
	public List<SportsCourseCategory> getCategoriesToday() {
		return getCategoriesToday(false);
	}

	/**
	 * Gives a list of all Sports Course Categories in alphabetical order that
	 * have a course that takes place today.
	 * 
	 */
	public List<SportsCourseCategory> getCategoriesToday(boolean forceRefresh) {
		List<SportsCourseCategory> sportsCourseCategories = null;

		if (forceRefresh
				|| cachedSportsCourseCategoriesListToday == null
				|| cachedSportsCourseCategoriesListTodayTimestamp == null
				|| cachedSportsCourseCategoriesListTodayTimestamp
						.before(CacheHelper.getExpiringDate())) {
			sportsCourseCategories = getRestConnectionSportsCourseCategory()
					.getRessourceAsList(RESSOURCE + "/today");

			if (sportsCourseCategories != null) {
				cachedSportsCourseCategoriesListToday = sportsCourseCategories;
				cachedSportsCourseCategoriesListTodayTimestamp = new Date();
			}
		} else {
			sportsCourseCategories = cachedSportsCourseCategoriesListToday;
		}

		if (sportsCourseCategories == null) {
			return null;
		}

		return Collections.unmodifiableList(sportsCourseCategories);
	}

	/**
	 * Gives a list of all Sports Course Categories in alphabetical order that
	 * have a course that takes place today.
	 * 
	 * Only data cached locally is used.
	 * 
	 */
	public List<SportsCourseCategory> getCategoriesTodayFromCache() {
		List<SportsCourseCategory> allSportsCourseCategories = readCacheFull();
		List<SportsCourseCategory> sportsCourseCategories = new ArrayList<SportsCourseCategory>();
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
		for (SportsCourseCategory sportsCourseCategory : allSportsCourseCategories) {
			boolean hasSportsCourseToday = false;
			for (SportsCourse sportsCourse : sportsCourseCategory
					.getSportsCourses()) {
				sportsCourse.setCategory(null);
				if ((sportsCourse.getStartDate() == null || sportsCourse
						.getStartDate().before(today))
						&& (sportsCourse.getEndDate() == null || sportsCourse
								.getEndDate().after(today))
						&& sportsCourse.getDayOfWeek() == dayOfWeek) {
					hasSportsCourseToday = true;
					break;
				}
			}
			if (hasSportsCourseToday) {
				sportsCourseCategory.setSportsCourses(null);
				sportsCourseCategories.add(sportsCourseCategory);
			}
		}
		return Collections.unmodifiableList(sportsCourseCategories);
	}

	/**
	 * Gives a single Sports course category that contains all associated sports
	 * courses.
	 * 
	 */
	public SportsCourseCategory getCategory(String id) {
		return getCategory(id, false);
	}

	/**
	 * Gives a single Sports course category that contains all associated sports
	 * courses.
	 * 
	 */
	public SportsCourseCategory getCategory(String id, boolean forceRefresh) {
		SportsCourseCategory sportsCourseCategory = null;

		if (!forceRefresh) {
			Iterator<Entry<SportsCourseCategory, Date>> iterator = cachedSportsCourseCategories
					.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<SportsCourseCategory, Date> entry = iterator.next();
				if (entry.getKey().getId().equals(id)
						&& !entry.getValue().before(
								CacheHelper.getExpiringDate())) {
					sportsCourseCategory = entry.getKey();
					break;
				}
			}
		}

		if (sportsCourseCategory == null) {
			sportsCourseCategory = getRestConnectionSportsCourseCategory()
					.getRessource(RESSOURCE + "/" + id);

			if (sportsCourseCategory != null) {
				for (SportsCourse sportsCourse : sportsCourseCategory
						.getSportsCourses()) {
					sportsCourse.setCategory(sportsCourseCategory);
				}

				cachedSportsCourseCategories.put(sportsCourseCategory,
						new Date());

				writeCache(sportsCourseCategory);
			}
		}

		return sportsCourseCategory;
	}

	/**
	 * Gives a single Sports course category that contains all associated sports
	 * courses.
	 * 
	 * Only data cached locally is used.
	 * 
	 * @return The SportsCourseCategory if it is cached, otherwise null.
	 * 
	 */
	public SportsCourseCategory getCategoryFromCache(String id) {
		return readCacheSportsCourseCategory(id);
	}

	/**
	 * Gives a single Sports course category that contains all associated sports
	 * courses that take place today.
	 * 
	 */
	public SportsCourseCategory getCategoryToday(String id) {
		return getCategoryToday(id, false);
	}

	/**
	 * Gives a single Sports course category that contains all associated sports
	 * courses that take place today.
	 * 
	 */
	public SportsCourseCategory getCategoryToday(String id, boolean forceRefresh) {
		SportsCourseCategory sportsCourseCategory = null;

		if (!forceRefresh) {
			Iterator<Entry<SportsCourseCategory, Date>> iterator = cachedSportsCourseCategories
					.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<SportsCourseCategory, Date> entry = iterator.next();
				if (entry.getKey().getId().equals(id)
						&& !entry.getValue().before(
								CacheHelper.getExpiringDate())) {
					sportsCourseCategory = entry.getKey();

					Date today = new Date();
					Calendar cal = Calendar.getInstance();
					cal.setTime(today);
					int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
					if (sportsCourseCategory.getSportsCourses() != null) {
						ArrayList<SportsCourse> todaysSportsCourses = new ArrayList<SportsCourse>();
						for (SportsCourse sportsCourse : sportsCourseCategory
								.getSportsCourses()) {
							if ((sportsCourse.getStartDate() == null || sportsCourse
									.getStartDate().before(today))
									&& (sportsCourse.getEndDate() == null || sportsCourse
											.getEndDate().after(today))
									&& sportsCourse.getDayOfWeek() == dayOfWeek) {
								todaysSportsCourses.add(sportsCourse);
							}
						}
						sportsCourseCategory
								.setSportsCourses(todaysSportsCourses);
					}

					break;
				}
			}
		}

		if (sportsCourseCategory == null) {
			sportsCourseCategory = getRestConnectionSportsCourseCategory()
					.getRessource(RESSOURCE + "/today/" + id);

			if (sportsCourseCategory != null) {
				for (SportsCourse sportsCourse : sportsCourseCategory
						.getSportsCourses()) {
					sportsCourse.setCategory(sportsCourseCategory);
				}
			}
		}

		return sportsCourseCategory;
	}

	/**
	 * Gives a single Sports course category that contains all associated sports
	 * courses.
	 * 
	 * Only data cached locally is used.
	 * 
	 * 
	 */
	public SportsCourseCategory getCategoryTodayFromCache(String id) {
		SportsCourseCategory category = readCacheSportsCourseCategory(id);

		if (category == null) {
			return null;
		}

		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (category.getSportsCourses() != null) {
			ArrayList<SportsCourse> todaysSportsCourses = new ArrayList<SportsCourse>();
			for (SportsCourse sportsCourse : category.getSportsCourses()) {
				if ((sportsCourse.getStartDate() == null || sportsCourse
						.getStartDate().before(today))
						&& (sportsCourse.getEndDate() == null || sportsCourse
								.getEndDate().after(today))
						&& sportsCourse.getDayOfWeek() == dayOfWeek) {
					todaysSportsCourses.add(sportsCourse);
				}
			}
			category.setSportsCourses(todaysSportsCourses);
		}
		return category;
	}

	/**
	 * Gives detailed information about a single Sports Course.
	 * 
	 */
	public SportsCourse getCourse(String id) {
		return getCourse(id, false);
	}

	/**
	 * Gives detailed information about a single Sports Course.
	 * 
	 */
	public SportsCourse getCourse(String id, boolean forceRefresh) {
		SportsCourse sportsCourse = null;

		if (!forceRefresh) {
			Iterator<Entry<SportsCourse, Date>> iterator = cachedSportsCourses
					.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<SportsCourse, Date> entry = iterator.next();
				if (entry.getKey().getId().equals(id)
						&& !entry.getValue().before(
								CacheHelper.getExpiringDate())) {
					sportsCourse = entry.getKey();
					break;
				}
			}
		}

		if (sportsCourse == null) {
			sportsCourse = getRestConnectionSportsCourse().getRessource(
					RESSOURCE + "/course/" + id);

			if (sportsCourse != null) {
				cachedSportsCourses.put(sportsCourse, new Date());

				// writeCache(sportsCourse);
			}
		}

		return sportsCourse;
	}

	/**
	 * Gives detailed information about a single Sports Course that is cached
	 * locally.
	 * 
	 * @return The sports course matching the id.
	 */
	public SportsCourse getCourseFromCache(String id) {
		return readCacheSportsCourse(id);
	}

	/**
	 * Gives a list of Sports Courses that meet one or more of the following
	 * criteria:
	 * <ul>
	 * <li>The title of the course's category contains the search string.</li>
	 * <li>The description of the course contains the search string.</li>
	 * <li>The host of the course contains the search string.</li>
	 * </ul>
	 * 
	 * The search is case insensitive.
	 * 
	 * @param input
	 *            The search parameter
	 * @return List of matching Sports Course Categories that contain a list of
	 *         matching Sports Courses each.
	 */
	public List<SportsCourseCategory> find(String input) {
		if (input != null && !input.isEmpty()) {
			List<SportsCourseCategory> sportsCourseCategories = null;

			sportsCourseCategories = getRestConnectionSportsCourseCategory()
					.getRessourceAsList(RESSOURCE + "/find/" + input);

			if (sportsCourseCategories == null) {
				return null;
			}

			for (SportsCourseCategory sportsCourseCategory : sportsCourseCategories) {
				if (sportsCourseCategory.getSportsCourses() != null) {
					for (SportsCourse sportsCourse : sportsCourseCategory
							.getSportsCourses()) {
						sportsCourse.setCategory(sportsCourseCategory);
					}
				}
			}

			return sportsCourseCategories;
		}
		return Collections
				.unmodifiableList(new ArrayList<SportsCourseCategory>());
	}

	private boolean writeCache(List<SportsCourseCategory> categories) {
		boolean result = true;

		ArrayList<String> idArray = new ArrayList<String>();
		for (SportsCourseCategory category : categories) {
			idArray.add(category.getId());
		}
		String idList = "'"
				+ Joiner.on("','").skipNulls()
						.join(idArray.toArray(new String[idArray.size()]))
				+ "'";

		SQLiteDatabase writableDatabase = getCacheOpenHelper()
				.getWritableDatabase();
		writableDatabase
				.execSQL("DELETE FROM " + TABLE_NAME_COURSES + " WHERE "
						+ KEY_COURSES_CATEGORY + " NOT IN (" + idList + ")");
		writableDatabase.execSQL("DELETE FROM " + TABLE_NAME_CATEGORIES
				+ " WHERE " + KEY_CATEGORY_ID + " NOT IN (" + idList + ")");

		for (SportsCourseCategory category : categories) {
			ContentValues values = new ContentValues();
			values.put(KEY_CATEGORY_ID, category.getId());
			values.put(KEY_CATEGORY_TITLE, category.getTitle());
			result = (writableDatabase.replace(TABLE_NAME_CATEGORIES, null,
					values) != -1L) && result;
		}

		writableDatabase.close();
		return result;
	}

	private boolean writeCache(SportsCourseCategory category) {
		boolean result = true;
		SQLiteDatabase writableDatabase = getCacheOpenHelper()
				.getWritableDatabase();

		writableDatabase.delete(TABLE_NAME_COURSES, KEY_COURSES_CATEGORY
				+ " = ?", new String[] { category.getId() });

		ContentValues values = new ContentValues();
		values.put(KEY_CATEGORY_ID, category.getId());
		values.put(KEY_CATEGORY_TITLE, category.getTitle());
		result = (writableDatabase.replace(TABLE_NAME_CATEGORIES, null, values) != -1L)
				&& result;
		if (result && category.getSportsCourses() != null) {
			for (SportsCourse course : category.getSportsCourses()) {
				values = new ContentValues();
				values.put(KEY_COURSES_ID, course.getId());
				values.put(KEY_COURSES_CATEGORY, course.getCategory().getId());
				values.put(KEY_COURSES_NUMBER, course.getNumber());
				values.put(KEY_COURSES_DETAILS, course.getDetails());
				values.put(KEY_COURSES_DAYOFWEEK, course.getDayOfWeek());
				values.put(KEY_COURSES_STARTTIME, course.getStartTime()
						.toStringWithSeconds());
				values.put(KEY_COURSES_ENDTIME, course.getEndTime()
						.toStringWithSeconds());
				values.put(KEY_COURSES_LOCATION, course.getLocation());
				values.put(KEY_COURSES_STARTDATE, course.getStartDate()
						.getTime());
				values.put(KEY_COURSES_ENDDATE, course.getEndDate().getTime());
				values.put(KEY_COURSES_HOST, course.getHost());
				values.put(KEY_COURSES_PRICE, course.getPrice());
				values.put(KEY_COURSES_STATUS, course.getStatus());
				result = (writableDatabase.replace(TABLE_NAME_COURSES, null,
						values) != -1L) && result;
			}
		}

		writableDatabase.close();
		return result;
	}

	private boolean writeCache(SportsCourse course) {
		boolean result = true;
		SQLiteDatabase writableDatabase = getCacheOpenHelper()
				.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_COURSES_ID, course.getId());
		values.put(KEY_COURSES_NUMBER, course.getNumber());
		values.put(KEY_COURSES_DETAILS, course.getDetails());
		values.put(KEY_COURSES_DAYOFWEEK, course.getDayOfWeek());
		values.put(KEY_COURSES_STARTTIME, course.getStartTime()
				.toStringWithSeconds());
		values.put(KEY_COURSES_ENDTIME, course.getEndTime()
				.toStringWithSeconds());
		values.put(KEY_COURSES_LOCATION, course.getLocation());
		values.put(KEY_COURSES_STARTDATE, course.getStartDate().getTime());
		values.put(KEY_COURSES_ENDDATE, course.getEndDate().getTime());
		values.put(KEY_COURSES_HOST, course.getHost());
		values.put(KEY_COURSES_PRICE, course.getPrice());
		values.put(KEY_COURSES_STATUS, course.getStatus());
		result = (writableDatabase.replace(TABLE_NAME_COURSES, null, values) != -1L)
				&& result;

		writableDatabase.close();
		return result;
	}

	private List<SportsCourseCategory> readCache() {
		SQLiteDatabase db = getCacheOpenHelper().getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_CATEGORIES
				+ " ORDER BY " + KEY_CATEGORY_TITLE, null);
		ArrayList<SportsCourseCategory> categories = new ArrayList<SportsCourseCategory>();
		if (cursor != null && cursor.moveToFirst()) {
			do {
				String id = cursor.getString(INDEX_CATEGORY_ID);
				String title = cursor.getString(INDEX_CATEGORY_TITLE);
				SportsCourseCategory category = new SportsCourseCategory(id,
						title);
				categories.add(category);
			} while (cursor.moveToNext());
		}

		db.close();
		return categories;
	}

	private List<SportsCourseCategory> readCacheFull() {
		SQLiteDatabase db = getCacheOpenHelper().getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_CATEGORIES
				+ " ORDER BY " + KEY_CATEGORY_TITLE, null);
		ArrayList<SportsCourseCategory> categories = new ArrayList<SportsCourseCategory>();
		if (cursor != null && cursor.moveToFirst()) {
			do {
				String id = cursor.getString(INDEX_CATEGORY_ID);
				String title = cursor.getString(INDEX_CATEGORY_TITLE);
				SportsCourseCategory category = new SportsCourseCategory(id,
						title);

				Cursor subCursor = db.rawQuery("SELECT * FROM "
						+ TABLE_NAME_COURSES + " WHERE " + KEY_COURSES_CATEGORY
						+ "  = ?", new String[] { id });
				ArrayList<SportsCourse> courses = new ArrayList<SportsCourse>();
				if (subCursor != null && subCursor.moveToFirst()) {
					do {
						try {
							String number = subCursor
									.getString(INDEX_COURSES_NUMBER);
							String details = subCursor
									.getString(INDEX_COURSES_DETAILS);
							int dayOfWeek = subCursor
									.getInt(INDEX_COURSES_DAYOFWEEK);
							Time startTime = new Time(
									subCursor
											.getString(INDEX_COURSES_STARTTIME));
							Time endTime = new Time(
									subCursor.getString(INDEX_COURSES_ENDTIME));
							String location = subCursor
									.getString(INDEX_COURSES_LOCATION);
							Date startDate = new Date(
									subCursor.getLong(INDEX_COURSES_STARTDATE));
							Date endDate = new Date(
									subCursor.getLong(INDEX_COURSES_ENDDATE));
							String host = subCursor
									.getString(INDEX_COURSES_HOST);
							double price = subCursor
									.getDouble(INDEX_COURSES_PRICE);
							int status = subCursor.getInt(INDEX_COURSES_STATUS);

							SportsCourse course = new SportsCourse(id, number,
									details, dayOfWeek, startTime, endTime,
									location, startDate, endDate, host, price,
									status);
							course.setCategory(category);
							courses.add(course);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} while (subCursor.moveToNext());
				}
				category.setSportsCourses(courses);

				categories.add(category);
			} while (cursor.moveToNext());
		}

		db.close();
		return categories;
	}

	private SportsCourseCategory readCacheSportsCourseCategory(String id) {
		SportsCourseCategory category = null;
		SQLiteDatabase db = getCacheOpenHelper().getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_CATEGORIES
				+ " WHERE id = ?", new String[] { id });
		if (cursor != null && cursor.moveToFirst()) {
			String title = cursor.getString(INDEX_CATEGORY_TITLE);
			category = new SportsCourseCategory(id, title);

			cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_COURSES
					+ " WHERE " + KEY_COURSES_CATEGORY + "  = ?",
					new String[] { id });
			ArrayList<SportsCourse> courses = new ArrayList<SportsCourse>();
			if (cursor != null && cursor.moveToFirst()) {
				do {
					try {
						String number = cursor.getString(INDEX_COURSES_NUMBER);
						String details = cursor
								.getString(INDEX_COURSES_DETAILS);
						int dayOfWeek = cursor.getInt(INDEX_COURSES_DAYOFWEEK);
						Time startTime = cursor
								.getString(INDEX_COURSES_STARTTIME) != null ? new Time(
								cursor.getString(INDEX_COURSES_STARTTIME))
								: null;
						Time endTime = cursor.getString(INDEX_COURSES_ENDTIME) != null ? new Time(
								cursor.getString(INDEX_COURSES_ENDTIME)) : null;
						String location = cursor
								.getString(INDEX_COURSES_LOCATION);
						Date startDate = new Date(
								cursor.getLong(INDEX_COURSES_STARTDATE));
						Date endDate = new Date(
								cursor.getLong(INDEX_COURSES_ENDDATE));
						String host = cursor.getString(INDEX_COURSES_HOST);
						double price = cursor.getDouble(INDEX_COURSES_PRICE);
						int status = cursor.getInt(INDEX_COURSES_STATUS);

						SportsCourse course = new SportsCourse(id, number,
								details, dayOfWeek, startTime, endTime,
								location, startDate, endDate, host, price,
								status);
						course.setCategory(category);
						courses.add(course);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} while (cursor.moveToNext());
			}
			category.setSportsCourses(courses);
		}

		db.close();
		return category;
	}

	private SportsCourse readCacheSportsCourse(String id) {
		SportsCourse course = null;
		SQLiteDatabase db = getCacheOpenHelper().getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_COURSES
				+ " WHERE id = ?", new String[] { id });
		if (cursor != null && cursor.moveToFirst()) {
			try {
				String number = cursor.getString(INDEX_COURSES_NUMBER);
				String details = cursor.getString(INDEX_COURSES_DETAILS);
				int dayOfWeek = cursor.getInt(INDEX_COURSES_DAYOFWEEK);
				Time startTime = cursor.getString(INDEX_COURSES_STARTTIME) != null ? new Time(
						cursor.getString(INDEX_COURSES_STARTTIME)) : null;
				Time endTime = cursor.getString(INDEX_COURSES_ENDTIME) != null ? new Time(
						cursor.getString(INDEX_COURSES_ENDTIME)) : null;
				String location = cursor.getString(INDEX_COURSES_LOCATION);
				Date startDate = new Date(
						cursor.getLong(INDEX_COURSES_STARTDATE));
				Date endDate = new Date(cursor.getLong(INDEX_COURSES_ENDDATE));
				String host = cursor.getString(INDEX_COURSES_HOST);
				double price = cursor.getDouble(INDEX_COURSES_PRICE);
				int status = cursor.getInt(INDEX_COURSES_STATUS);

				course = new SportsCourse(id, number, details, dayOfWeek,
						startTime, endTime, location, startDate, endDate, host,
						price, status);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		db.close();
		return course;
	}
}
