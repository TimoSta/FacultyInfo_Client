package de.uni_passau.facultyinfo.client.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.ContactGroup;
import de.uni_passau.facultyinfo.client.model.dto.ContactPerson;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

public class DisplayChairContactsActivity extends Activity {

	private String chairId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_chair_contacts);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);

		Intent intent = getIntent();
		chairId = intent.getStringExtra("chairId");
		String title = intent.getStringExtra("title");

		setTitle(title);

		(new ContactLoader()).execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.display_chair_contacts, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected class ContactLoader extends AsyncDataLoader<ContactGroup> {

		@Override
		protected ContactGroup doInBackground(Void... unused) {
			AccessFacade accessFacade = new AccessFacade();

			ContactGroup contactGroup = accessFacade.getContactPersonAccess()
					.getContactGroup(chairId);

			if (contactGroup == null) {
				publishProgress(AsyncDataLoader.NO_CONNECTION_PROGRESS);
				contactGroup = accessFacade.getContactPersonAccess()
						.getContactGroupFromCache(chairId);
			}

			return contactGroup;
		}

		@Override
		protected void onPostExecute(ContactGroup group) {
			if (group != null) {
				ListView listView = (ListView) findViewById(R.id.chairContacts);

				final ArrayList<HashMap<String, String>> personList = new ArrayList<HashMap<String, String>>();

				for (ContactPerson person : group.getContactPersons()) {
					HashMap<String, String> listEntry = new HashMap<String, String>();
					listEntry.put("name", person.getName());
					listEntry.put("office", person.getOffice());
					listEntry.put("telefon", person.getPhone());
					listEntry.put("email", person.getEmail());
					listEntry.put("description", person.getDescription());
					personList.add(listEntry);
				}

				SimpleAdapter adapter = new SimpleAdapter(
						getApplicationContext(), personList,
						R.layout.contact_view, new String[] { "name",
								"description", "office", "telefon", "email" },
						new int[] { R.id.contact_name,
								R.id.contact_description, R.id.contact_office,
								R.id.contact_phone, R.id.contact_email }

				);

				listView.setAdapter(adapter);
			}
		}

	}

}
