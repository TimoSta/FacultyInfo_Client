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
import de.uni_passau.facultyinfo.client.model.dto.Faq;
import de.uni_passau.facultyinfo.client.model.dto.FaqCategory;
import de.uni_passau.facultyinfo.client.util.CacheHelper;

/**
 * @author Timo Staudinger
 * 
 */
@SuppressWarnings("unused")
public class FaqAccess extends Access {
	private static final String RESSOURCE = "/faq";

	private static final String TABLE_NAME_CATEGORIES = "faqcategories";
	private static final int INDEX_CATEGORIES_ID = 0;
	private static final String KEY_CATEGORIES_ID = "id";
	private static final int INDEX_CATEGORIES_TITLE = 1;
	private static final String KEY_CATEGORIES_TITLE = "title";

	private static final String TABLE_NAME_FAQS = "faqs";
	private static final int INDEX_FAQS_ID = 0;
	private static final String KEY_FAQS_ID = "id";
	private static final int INDEX_FAQS_CATEGORY = 1;
	private static final String KEY_FAQS_CATEGORY = "category";
	private static final int INDEX_FAQS_TITLE = 2;
	private static final String KEY_FAQS_TITLE = "title";
	private static final int INDEX_FAQS_TEXT = 3;
	private static final String KEY_FAQS_TEXT = "text";

	private static FaqAccess instance = null;

	protected static FaqAccess getInstance() {
		if (instance == null) {
			instance = new FaqAccess();
		}
		return instance;
	}

	private RestConnection<Faq> restConnectionFaq = null;
	private RestConnection<FaqCategory> restConnectionFaqCategory = null;

	private List<FaqCategory> cachedCategories = null;
	private Date cachedCategoriesTimestamp = null;

	private HashMap<Faq, Date> cachedFaqs = new HashMap<Faq, Date>();

	private RestConnection<Faq> getRestConnectionFaq() {
		if (restConnectionFaq == null) {
			restConnectionFaq = new RestConnection<Faq>(Faq.class);
		}
		return restConnectionFaq;
	}

	private RestConnection<FaqCategory> getRestConnectionFaqCategory() {
		if (restConnectionFaqCategory == null) {
			restConnectionFaqCategory = new RestConnection<FaqCategory>(
					FaqCategory.class);
		}
		return restConnectionFaqCategory;
	}

	private FaqAccess() {
		super();
	}

	/**
	 * Gives a list of available FAQs.
	 * 
	 */
	public List<FaqCategory> getFaqs() {
		return getFaqs(false);
	}

	/**
	 * Gives a list of available FAQs.
	 * 
	 */
	public List<FaqCategory> getFaqs(boolean forceRefresh) {
		List<FaqCategory> faqCategories = null;

		if (forceRefresh
				|| cachedCategories == null
				|| cachedCategoriesTimestamp == null
				|| cachedCategoriesTimestamp.before(CacheHelper
						.getExpiringDate())) {
			faqCategories = getRestConnectionFaqCategory().getRessourceAsList(
					RESSOURCE);

			if (faqCategories != null) {
				cachedCategories = faqCategories;
				cachedCategoriesTimestamp = new Date();

				writeCache(faqCategories);
			}
		} else {
			faqCategories = cachedCategories;
		}

		if (faqCategories == null) {
			return null;
		}

		return Collections.unmodifiableList(faqCategories);
	}

	/**
	 * Gives a list of FAQs that are currently cached locally.
	 * 
	 */
	public List<FaqCategory> getFaqsFromCache() {
		List<FaqCategory> faqCategories = readCache();

		if (faqCategories == null) {
			return null;
		}

		return Collections.unmodifiableList(faqCategories);
	}

	/**
	 * Gives detailed information about a specific FAQ.
	 * 
	 */
	public Faq getFaq(String faqId) {
		return getFaq(faqId, false);
	}

	/**
	 * Gives detailed information about a specific FAQ.
	 * 
	 */
	public Faq getFaq(String faqId, boolean forceRefresh) {
		Faq faq = null;

		if (!forceRefresh) {
			Iterator<Entry<Faq, Date>> iterator = cachedFaqs.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Entry<Faq, Date> entry = iterator.next();
				if (entry.getKey().getId().equals(faqId)
						&& !entry.getValue().before(
								CacheHelper.getExpiringDate())) {
					faq = entry.getKey();
					break;
				}
			}
		}

		if (faq == null) {
			faq = getRestConnectionFaq().getRessource(RESSOURCE + '/' + faqId);

			if (faq != null) {
				cachedFaqs.put(faq, new Date());

				writeCache(faq);
			}
		}

		return faq;
	}

	/**
	 * Gives detailed information about a specific FAQ cached locally.
	 * 
	 */
	public Faq getFaqFromCache(String faqId) {
		return readCache(faqId);
	}

	private boolean writeCache(List<FaqCategory> categories) {
		boolean result = true;

		ArrayList<String> categoryIdArray = new ArrayList<String>();
		ArrayList<String> faqIdArray = new ArrayList<String>();
		for (FaqCategory category : categories) {
			categoryIdArray.add(category.getId());
			if (category.getFaqs() != null) {
				for (Faq faq : category.getFaqs()) {
					faqIdArray.add(faq.getId());
				}
			}
		}
		String categoryIdString = "'"
				+ Joiner.on("','")
						.skipNulls()
						.join(categoryIdArray
								.toArray(new String[categoryIdArray.size()]))
				+ "'";
		String faqIdString = "'"
				+ Joiner.on("','")
						.skipNulls()
						.join(faqIdArray.toArray(new String[faqIdArray.size()]))
				+ "'";

		SQLiteDatabase writableDatabase = getCacheOpenHelper()
				.getWritableDatabase();
		writableDatabase.execSQL("DELETE FROM " + TABLE_NAME_FAQS + " WHERE "
				+ KEY_FAQS_ID + " NOT IN (" + faqIdString + ")");
		writableDatabase.execSQL("DELETE FROM " + TABLE_NAME_CATEGORIES
				+ " WHERE " + KEY_CATEGORIES_ID + " NOT IN ("
				+ categoryIdString + ")");

		for (FaqCategory category : categories) {
			ContentValues values = new ContentValues();
			values.put(KEY_CATEGORIES_ID, category.getId());
			values.put(KEY_CATEGORIES_TITLE, category.getTitle());
			result = (writableDatabase.replace(TABLE_NAME_CATEGORIES, null,
					values) != -1L) && result;
			if (category.getFaqs() != null) {
				for (Faq faq : category.getFaqs()) {
					ContentValues faqValues = new ContentValues();
					faqValues.put(KEY_FAQS_ID, faq.getId());
					faqValues.put(KEY_FAQS_CATEGORY, category.getId());
					faqValues.put(KEY_FAQS_TITLE, faq.getTitle());
					result = (writableDatabase.replace(TABLE_NAME_FAQS, null,
							faqValues) != -1L) && result;
				}
			}
		}

		writableDatabase.close();
		return result;
	}

	private boolean writeCache(Faq faq) {
		boolean result = true;
		SQLiteDatabase writableDatabase = getCacheOpenHelper()
				.getWritableDatabase();
		ContentValues faqValues = new ContentValues();
		faqValues.put(KEY_FAQS_TITLE, faq.getTitle());
		faqValues.put(KEY_FAQS_TEXT, faq.getText());
		result = (writableDatabase.update(TABLE_NAME_FAQS, faqValues,
				KEY_FAQS_ID + " = ?", new String[] { faq.getId() })) == 1
				&& result;
		writableDatabase.close();
		return result;
	}

	private List<FaqCategory> readCache() {
		SQLiteDatabase db = getCacheOpenHelper().getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_CATEGORIES
				+ " ORDER BY " + KEY_CATEGORIES_TITLE, null);
		ArrayList<FaqCategory> categories = new ArrayList<FaqCategory>();
		if (cursor != null && cursor.moveToFirst()) {
			do {
				String id = cursor.getString(INDEX_CATEGORIES_ID);
				String title = cursor.getString(INDEX_CATEGORIES_TITLE);
				FaqCategory category = new FaqCategory(id, title);

				Cursor subCursor = db.rawQuery("SELECT * FROM "
						+ TABLE_NAME_FAQS + " WHERE " + KEY_FAQS_CATEGORY
						+ " = ? ORDER BY " + KEY_FAQS_TITLE,
						new String[] { category.getId() });
				ArrayList<Faq> faqs = new ArrayList<Faq>();
				if (subCursor != null && subCursor.moveToFirst()) {
					do {
						String faqId = subCursor.getString(INDEX_FAQS_ID);
						String faqTitle = subCursor.getString(INDEX_FAQS_TITLE);
						String text = null;
						Faq faq = new Faq(faqId, faqTitle, text);
						faq.setCategory(category);
						faqs.add(faq);
					} while (subCursor.moveToNext());
				}
				category.setFaqs(faqs);
				categories.add(category);
			} while (cursor.moveToNext());
		}

		db.close();
		return categories;
	}

	private Faq readCache(String id) {
		Faq faq = null;
		SQLiteDatabase db = getCacheOpenHelper().getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_FAQS
				+ " WHERE " + KEY_FAQS_ID + " = ?", new String[] { id });
		if (cursor != null && cursor.moveToFirst()) {
			String faqId = cursor.getString(INDEX_FAQS_ID);
			String faqTitle = cursor.getString(INDEX_FAQS_TITLE);
			String text = cursor.getString(INDEX_FAQS_TEXT);
			faq = new Faq(faqId, faqTitle, text);
		}

		db.close();
		return faq;
	}
}
