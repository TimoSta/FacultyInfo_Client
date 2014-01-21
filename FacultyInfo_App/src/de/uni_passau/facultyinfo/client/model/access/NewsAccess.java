package de.uni_passau.facultyinfo.client.model.access;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.base.Joiner;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import de.uni_passau.facultyinfo.client.model.connection.RestConnection;
import de.uni_passau.facultyinfo.client.model.dto.News;
import de.uni_passau.facultyinfo.client.util.CacheHelper;

/**
 * @author Timo Staudinger
 * 
 */
public class NewsAccess extends Access {
	private static final String RESSOURCE = "/news";

	private static final String TABLE_NAME = "news";
	private static final int INDEX_ID = 0;
	private static final String KEY_ID = "id";
	private static final int INDEX_TITLE = 1;
	private static final String KEY_TITLE = "title";
	private static final int INDEX_DESCRIPTION = 2;
	private static final String KEY_DESCRIPTION = "description";
	private static final int INDEX_URL = 3;
	private static final String KEY_URL = "url";
	private static final int INDEX_TEXT = 4;
	private static final String KEY_TEXT = "text";
	private static final int INDEX_PUBLICATIONDATE = 5;
	private static final String KEY_PUBLICATIONDATE = "publishingdate";

	private static NewsAccess instance = null;

	protected static NewsAccess getInstance() {
		if (instance == null) {
			instance = new NewsAccess();
		}
		return instance;
	}

	private RestConnection<News> restConnection = null;

	private List<News> cachedNewsList = null;
	private Date cachedNewsListTimestamp = null;

	private HashMap<News, Date> cachedNews = new HashMap<News, Date>();

	private NewsAccess() {
		restConnection = new RestConnection<News>(News.class);
	}

	/**
	 * Gives a list of available News.
	 * 
	 */
	public List<News> getNews() {
		return getNews(false);
	}

	/**
	 * Gives a list of available News.
	 * 
	 */
	public List<News> getNews(boolean forceRefresh) {
		List<News> news = null;

		if (forceRefresh
				|| cachedNewsList == null
				|| cachedNewsListTimestamp == null
				|| cachedNewsListTimestamp
						.before(CacheHelper.getExpiringDate())) {

			news = restConnection.getRessourceAsList(RESSOURCE);

			if (news != null) {
				cachedNewsList = news;
				cachedNewsListTimestamp = new Date();

				writeCache(news);
			}

		} else {
			news = cachedNewsList;
		}

		if (news == null) {
			return null;
		}

		return Collections.unmodifiableList(news);
	}

	/**
	 * Gives a list of available News that are cached locally.
	 * 
	 */
	public List<News> getNewsFromCache() {
		List<News> news = readCache();

		if (news == null) {
			return null;
		}

		return Collections.unmodifiableList(news);
	}

	/**
	 * Gives detailed information about a specific News.
	 * 
	 */
	public News getNews(String newsId) {
		return getNews(newsId, false);
	}

	/**
	 * Gives detailed information about a specific News.
	 * 
	 */
	public News getNews(String newsId, boolean forceRefresh) {
		News news = null;

		if (!forceRefresh) {
			Iterator<Entry<News, Date>> iterator = cachedNews.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Entry<News, Date> entry = iterator.next();
				if (entry.getKey().getId().equals(newsId)
						&& !entry.getValue().before(
								CacheHelper.getExpiringDate())) {
					news = entry.getKey();
					break;
				}
			}
		}

		if (news == null) {
			news = restConnection.getRessource(RESSOURCE + '/' + newsId);
			if (news != null) {
				cachedNews.put(news, new Date());

				writeCache(news);
			}
		}

		return news;
	}

	/**
	 * Gives detailed information about a specific News that is cached locally.
	 * 
	 */
	public News getNewsFromCache(String newsId) {
		return readCache(newsId);
	}

	private boolean writeCache(List<News> newsList) {
		boolean result = true;

		ArrayList<String> idArray = new ArrayList<String>();
		for (News news : newsList) {
			idArray.add(news.getId());
		}
		String idList = Joiner.on(',').skipNulls()
				.join(idArray.toArray(new String[idArray.size()]));

		SQLiteDatabase writableDatabase = getCacheOpenHelper()
				.getWritableDatabase();
		writableDatabase.execSQL("DELETE FROM " + TABLE_NAME
				+ " WHERE id NOT IN (" + idList + ")");
		for (News news : newsList) {
			ContentValues values = new ContentValues();
			values.put(KEY_ID, news.getId());
			values.put(KEY_TITLE, news.getTitle());
			values.put(KEY_DESCRIPTION, news.getDescription());
			values.put(KEY_PUBLICATIONDATE, news.getPublicationDate().getTime());
			result = (writableDatabase.replace(TABLE_NAME, null, values) != -1L)
					&& result;
		}

		writableDatabase.close();
		return result;
	}

	private boolean writeCache(News news) {
		boolean result = true;
		SQLiteDatabase writableDatabase = getCacheOpenHelper()
				.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_ID, news.getId());
		values.put(KEY_TITLE, news.getTitle());
		values.put(KEY_DESCRIPTION, news.getDescription());
		values.put(KEY_URL, news.getUrl());
		values.put(KEY_TEXT, news.getText());
		values.put(KEY_PUBLICATIONDATE, news.getPublicationDate().getTime());
		result = writableDatabase.replace(TABLE_NAME, null, values) != -1L;

		writableDatabase.close();
		return result;
	}

	private List<News> readCache() {
		SQLiteDatabase db = getCacheOpenHelper().getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME
				+ " ORDER BY " + KEY_PUBLICATIONDATE + " DESC, " + KEY_TITLE,
				null);
		ArrayList<News> entries = new ArrayList<News>();
		if (cursor != null && cursor.moveToFirst()) {
			do {
				String id = cursor.getString(INDEX_ID);
				String title = cursor.getString(INDEX_TITLE);
				String description = cursor.getString(INDEX_DESCRIPTION);
				String url = null;
				String text = null;
				Date publicationDate = new Date(
						cursor.getLong(INDEX_PUBLICATIONDATE));
				News entry = new News(id, title, description, url, text,
						publicationDate);
				entries.add(entry);
			} while (cursor.moveToNext());
		}
		db.close();
		return entries;
	}

	private News readCache(String newsId) {
		News news = null;
		SQLiteDatabase db = getCacheOpenHelper().getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME
				+ " WHERE id = ?", new String[] { newsId });
		if (cursor != null && cursor.moveToFirst()) {
			String id = cursor.getString(INDEX_ID);
			String title = cursor.getString(INDEX_TITLE);
			String description = cursor.getString(INDEX_DESCRIPTION);
			String url = cursor.getString(INDEX_URL);
			String text = cursor.getString(INDEX_TEXT);
			Date publicationDate = new Date(
					cursor.getLong(INDEX_PUBLICATIONDATE));
			news = new News(id, title, description, url, text, publicationDate);
		}
		db.close();
		return news;
	}
}
