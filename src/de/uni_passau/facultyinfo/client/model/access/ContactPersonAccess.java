package de.uni_passau.facultyinfo.client.model.access;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import de.uni_passau.facultyinfo.client.model.dto.ContactSearchResult;
import de.uni_passau.facultyinfo.client.model.dto.ContactGroup;
import de.uni_passau.facultyinfo.client.model.dto.ContactPerson;
import de.uni_passau.facultyinfo.client.model.dto.ContactSearchResponse;
import de.uni_passau.facultyinfo.client.util.CacheHelper;

/**
 * Enables access to remote Contact person ressources.
 * 
 * @author Timo Staudinger
 */
@SuppressWarnings("unused")
public class ContactPersonAccess extends Access {
	private static final String RESSOURCE = "/contactperson";

	private static final String TABLE_NAME_PERSONS = "contactpersons";
	private static final int INDEX_PERSONS_ID = 0;
	private static final String KEY_PERSONS_ID = "id";
	private static final int INDEX_PERSONS_CONTACTGROUP = 1;
	private static final String KEY_PERSONS_CONTACTGROUP = "contactgroup";
	private static final int INDEX_PERSONS_NAME = 2;
	private static final String KEY_PERSONS_NAME = "name";
	private static final int INDEX_PERSONS_OFFICE = 3;
	private static final String KEY_PERSONS_OFFICE = "office";
	private static final int INDEX_PERSONS_PHONE = 4;
	private static final String KEY_PERSONS_PHONE = "phone";
	private static final int INDEX_PERSONS_EMAIL = 5;
	private static final String KEY_PERSONS_EMAIL = "email";
	private static final int INDEX_PERSONS_DESCRIPTION = 6;
	private static final String KEY_PERSONS_DESCRIPTION = "description";

	private static final String TABLE_NAME_GROUPS = "contactgroups";
	private static final int INDEX_GROUP_ID = 0;
	private static final String KEY_GROUP_ID = "id";
	private static final int INDEX_GROUP_TITLE = 1;
	private static final String KEY_GROUP_TITLE = "title";
	private static final int INDEX_GROUP_DESCRIPTION = 2;
	private static final String KEY_GROUP_DESCRIPTION = "description";

	private static ContactPersonAccess instance = null;

	protected static ContactPersonAccess getInstance() {
		if (instance == null) {
			instance = new ContactPersonAccess();
		}
		return instance;
	}

	private RestConnection<ContactGroup> restConnection = null;
	private RestConnection<ContactPerson> restConnectionPerson = null;
	private RestConnection<ContactSearchResponse> restConnectionFind = null;

	private List<ContactGroup> cachedContactGroups = null;
	private Date cachedContactGroupsTimestamp = null;

	private HashMap<ContactGroup, Date> cachedContactGroupsList = new HashMap<ContactGroup, Date>();

	private RestConnection<ContactGroup> getRestConnection() {
		if (restConnection == null) {
			restConnection = new RestConnection<ContactGroup>(
					ContactGroup.class);
		}
		return restConnection;
	}

	private RestConnection<ContactPerson> getRestConnectionPerson() {
		if (restConnectionPerson == null) {
			restConnectionPerson = new RestConnection<ContactPerson>(
					ContactPerson.class);
		}
		return restConnectionPerson;
	}

	private RestConnection<ContactSearchResponse> getRestConnectionFind() {
		if (restConnectionFind == null) {
			restConnectionFind = new RestConnection<ContactSearchResponse>(
					ContactSearchResponse.class);
		}
		return restConnectionFind;
	}

	private ContactPersonAccess() {
		super();
	}

	/**
	 * Gives a list of available Contact groups in alphabetical order.
	 * 
	 */
	public List<ContactGroup> getContactGroups() {
		return getContactGroups(false);
	}

	/**
	 * Gives a list of available Contact groups in alphabetical order.
	 * 
	 */
	public List<ContactGroup> getContactGroups(boolean forceRefresh) {
		List<ContactGroup> contactGroups = null;

		if (forceRefresh
				|| cachedContactGroups == null
				|| cachedContactGroupsTimestamp == null
				|| cachedContactGroupsTimestamp.before(CacheHelper
						.getExpiringDate())) {
			contactGroups = getRestConnection().getRessourceAsList(RESSOURCE);

			if (contactGroups != null) {
				cachedContactGroups = contactGroups;
				cachedContactGroupsTimestamp = new Date();

				writeCache(contactGroups);
			}
		} else {
			contactGroups = cachedContactGroups;
		}

		if (contactGroups == null) {
			return null;
		}

		return Collections.unmodifiableList(contactGroups);
	}

	/**
	 * Gives a list of Contact Groups that are currently cached locally.
	 * 
	 */
	public List<ContactGroup> getContactGroupsFromCache() {
		List<ContactGroup> contactGroups = readCache();
		return Collections.unmodifiableList(contactGroups);
	}

	/**
	 * Gives detailed information about a single Contact Group.
	 * 
	 */
	public ContactGroup getContactGroup(String id) {
		return getContactGroup(id, false);
	}

	/**
	 * Gives detailed information about a single Contact Group.
	 * 
	 */
	public ContactGroup getContactGroup(String id, boolean forceRefresh) {
		ContactGroup contactGroup = null;

		if (!forceRefresh) {
			Iterator<Entry<ContactGroup, Date>> iterator = cachedContactGroupsList
					.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<ContactGroup, Date> entry = iterator.next();
				if (entry.getKey().getId().equals(id)
						&& !entry.getValue().before(
								CacheHelper.getExpiringDate())) {
					contactGroup = entry.getKey();
					break;
				}
			}
		}

		if (contactGroup == null) {
			contactGroup = restConnection.getRessource(RESSOURCE + "/" + id);

			if (contactGroup != null) {
				for (ContactPerson contactPerson : contactGroup
						.getContactPersons()) {
					contactPerson.setContactGroup(contactGroup);
				}

				cachedContactGroupsList.put(contactGroup, new Date());

				writeCache(contactGroup);
			}
		}

		return contactGroup;
	}

	/**
	 * Gives detailed information about a single Contact group that is cached
	 * locally.
	 * 
	 */
	public ContactGroup getContactGroupFromCache(String id) {
		return readCache(id);
	}

	/**
	 * Gives detailed information about a specific News.
	 * 
	 */
	public ContactPerson getContactPerson(String personId) {
		return getContactPerson(personId, false);
	}

	/**
	 * Gives detailed information about a specific News.
	 * 
	 */
	public ContactPerson getContactPerson(String personId, boolean forceRefresh) {
		ContactPerson person = null;

		if (!forceRefresh) {
			Iterator<Entry<ContactGroup, Date>> iterator = cachedContactGroupsList
					.entrySet().iterator();
			outerLoop: while (iterator.hasNext()) {
				Entry<ContactGroup, Date> entry = iterator.next();
				if (entry.getKey().getContactPersons() != null) {
					for (ContactPerson cachedPerson : entry.getKey()
							.getContactPersons()) {
						if (cachedPerson.getId().equals(personId)) {
							person = cachedPerson;
							break outerLoop;
						}
					}
				}
			}
		}

		if (person == null) {
			person = getRestConnectionPerson().getRessource(
					RESSOURCE + "/person/" + personId);
		}

		return person;
	}

	/**
	 * Gives a list of Contact Groups that meet one or more of the following
	 * criteria:
	 * <ul>
	 * <li>The title of the Contact Group contains the search string.</li>
	 * <li>The Name of the Contact Person contains the search string.</li>
	 * </ul>
	 * 
	 * The search is case insensitive.
	 * 
	 * @param input
	 *            The search parameter
	 * @return List of matching Contact Groups that contain a list of matching
	 *         Contact Persons each.
	 */
	public List<ContactSearchResult> find(String input) {
		if (input != null && !input.isEmpty()) {
			ContactSearchResponse response = null;

			response = getRestConnectionFind().getRessource(
					RESSOURCE + "/find/" + input);

			if (response == null) {
				return null;
			}

			ArrayList<ContactSearchResult> results = new ArrayList<ContactSearchResult>();

			if (response.getGroups() != null) {
				for (ContactGroup group : response.getGroups()) {
					String subtitle = group.getDescription();
					ContactSearchResult generic = new ContactSearchResult(
							ContactSearchResult.GROUP, group.getId(),
							group.getTitle(), subtitle);
					results.add(generic);
				}
			}

			if (response.getPersons() != null) {
				for (ContactPerson person : response.getPersons()) {
					String subtitle = null;
					subtitle = person.getPhone() != null ? person.getPhone()
							: subtitle;
					subtitle = person.getOffice() != null ? person.getOffice()
							: subtitle;
					subtitle = person.getEmail() != null ? person.getEmail()
							: subtitle;
					subtitle = person.getDescription() != null ? person
							.getDescription() : subtitle;
					ContactSearchResult generic = new ContactSearchResult(
							ContactSearchResult.PERSON, person.getId(),
							person.getName(), subtitle);
					results.add(generic);
				}
			}

			Collections.sort(results, new Comparator<ContactSearchResult>() {

				@Override
				public int compare(ContactSearchResult lhs, ContactSearchResult rhs) {
					return lhs.getTitle().compareToIgnoreCase(rhs.getTitle());
				}
			});

			return Collections.unmodifiableList(results);
		}
		return Collections.unmodifiableList(new ArrayList<ContactSearchResult>());
	}

	private boolean writeCache(List<ContactGroup> contactGroups) {
		boolean result = true;

		ArrayList<String> idArray = new ArrayList<String>();
		for (ContactGroup contactGroup : contactGroups) {
			idArray.add(contactGroup.getId());
		}
		String idList = "'"
				+ Joiner.on("','").skipNulls()
						.join(idArray.toArray(new String[idArray.size()]))
				+ "'";

		SQLiteDatabase writableDatabase = getCacheOpenHelper()
				.getWritableDatabase();
		writableDatabase.execSQL("DELETE FROM " + TABLE_NAME_PERSONS
				+ " WHERE contactgroup NOT IN (" + idList + ")");
		writableDatabase.execSQL("DELETE FROM " + TABLE_NAME_GROUPS
				+ " WHERE id NOT IN (" + idList + ")");

		for (ContactGroup contactGroup : contactGroups) {
			ContentValues values = new ContentValues();
			values.put(KEY_GROUP_ID, contactGroup.getId());
			values.put(KEY_GROUP_TITLE, contactGroup.getTitle());
			values.put(KEY_GROUP_DESCRIPTION, contactGroup.getDescription());
			result = (writableDatabase.replace(TABLE_NAME_GROUPS, null, values) != -1L)
					&& result;
		}

		writableDatabase.close();
		return result;
	}

	private boolean writeCache(ContactGroup contactGroup) {
		boolean result = true;
		SQLiteDatabase writableDatabase = getCacheOpenHelper()
				.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_GROUP_ID, contactGroup.getId());
		values.put(KEY_GROUP_TITLE, contactGroup.getTitle());
		values.put(KEY_GROUP_DESCRIPTION, contactGroup.getDescription());
		result = (writableDatabase.replace(TABLE_NAME_GROUPS, null, values) != -1L)
				&& result;
		if (result && contactGroup.getContactPersons() != null) {
			for (ContactPerson contactPerson : contactGroup.getContactPersons()) {
				ContentValues personValues = new ContentValues();
				personValues.put(KEY_PERSONS_ID, contactPerson.getId());
				personValues.put(KEY_PERSONS_CONTACTGROUP, contactPerson
						.getContactGroup().getId());
				personValues.put(KEY_PERSONS_NAME, contactPerson.getName());
				personValues.put(KEY_PERSONS_OFFICE, contactPerson.getOffice());
				personValues.put(KEY_PERSONS_PHONE, contactPerson.getPhone());
				personValues.put(KEY_PERSONS_EMAIL, contactPerson.getEmail());
				personValues.put(KEY_PERSONS_DESCRIPTION,
						contactPerson.getDescription());
				result = (writableDatabase.replace(TABLE_NAME_PERSONS, null,
						personValues) != -1L) && result;
			}
		}

		writableDatabase.close();
		return result;
	}

	private List<ContactGroup> readCache() {
		SQLiteDatabase db = getCacheOpenHelper().getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_GROUPS
				+ " ORDER BY " + KEY_GROUP_TITLE, null);
		ArrayList<ContactGroup> contactGroups = new ArrayList<ContactGroup>();
		if (cursor != null && cursor.moveToFirst()) {
			do {
				String id = cursor.getString(INDEX_GROUP_ID);
				String title = cursor.getString(INDEX_GROUP_TITLE);
				String description = cursor.getString(INDEX_GROUP_DESCRIPTION);
				ContactGroup contactGroup = new ContactGroup(id, title,
						description);
				contactGroups.add(contactGroup);
			} while (cursor.moveToNext());
		}

		db.close();
		return contactGroups;
	}

	private ContactGroup readCache(String id) {
		ContactGroup contactGroup = null;
		SQLiteDatabase db = getCacheOpenHelper().getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_GROUPS
				+ " WHERE id = ?", new String[] { id });
		if (cursor != null && cursor.moveToFirst()) {
			String title = cursor.getString(INDEX_GROUP_TITLE);
			String description = cursor.getString(INDEX_GROUP_DESCRIPTION);
			contactGroup = new ContactGroup(id, title, description);

			cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_PERSONS
					+ " WHERE " + KEY_PERSONS_CONTACTGROUP + " = ?",
					new String[] { id });
			ArrayList<ContactPerson> contactPersons = new ArrayList<ContactPerson>();
			if (cursor != null && cursor.moveToFirst()) {
				do {
					String personId = cursor.getString(INDEX_PERSONS_ID);
					String name = cursor.getString(INDEX_PERSONS_NAME);
					String office = cursor.getString(INDEX_PERSONS_OFFICE);
					String phone = cursor.getString(INDEX_PERSONS_PHONE);
					String email = cursor.getString(INDEX_PERSONS_EMAIL);
					String personDescription = cursor
							.getString(INDEX_PERSONS_DESCRIPTION);
					ContactPerson contactPerson = new ContactPerson(personId,
							name, office, phone, email, personDescription);
					contactPerson.setContactGroup(contactGroup);
					contactPersons.add(contactPerson);
				} while (cursor.moveToNext());
			}
			contactGroup.setContactPersons(contactPersons);
		}

		db.close();
		return contactGroup;
	}
}
