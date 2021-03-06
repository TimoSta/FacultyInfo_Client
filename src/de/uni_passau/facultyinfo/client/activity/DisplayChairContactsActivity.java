package de.uni_passau.facultyinfo.client.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.ContactGroup;
import de.uni_passau.facultyinfo.client.model.dto.ContactPerson;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;
import de.uni_passau.facultyinfo.client.util.SwipeRefreshAsyncDataLoader;

public class DisplayChairContactsActivity extends SwipeRefreshLayoutActivity {

	private String chairId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_list);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);

		Intent intent = getIntent();
		chairId = intent.getStringExtra("chairId");
		String title = intent.getStringExtra("title");

		setTitle(title);

		initializeSwipeRefresh(
				(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
						.getChildAt(0), new OnRefreshListener() {

					@Override
					public void onRefresh() {
						new ContactLoader(
								(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
										.getChildAt(0), true).execute();
					}

				});

		(new ContactLoader(
				(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
						.getChildAt(0))).execute();

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

	protected class ContactLoader extends
			SwipeRefreshAsyncDataLoader<ContactGroup> {
		private boolean forceRefresh = false;

		private ContactLoader(SwipeRefreshLayout rootView) {
			super(rootView);
		}

		private ContactLoader(SwipeRefreshLayout rootView, boolean forceRefresh) {
			super(rootView);
			this.forceRefresh = forceRefresh;
		}

		@Override
		protected ContactGroup doInBackground(Void... unused) {
			showLoadingAnimation(true);
			AccessFacade accessFacade = new AccessFacade();

			ContactGroup contactGroup = accessFacade.getContactPersonAccess()
					.getContactGroup(chairId, forceRefresh);

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
				super.onPostExecute(group);
				ListView listView = (ListView) findViewById(R.id.list);

				final ArrayList<HashMap<String, String>> personList = new ArrayList<HashMap<String, String>>();

				for (ContactPerson person : group.getContactPersons()) {
					HashMap<String, String> listEntry = new HashMap<String, String>();
					listEntry.put("name", person.getName());
					listEntry.put("office", person.getOffice());
					listEntry.put("telefon", person.getPhone());
					listEntry.put("email", person.getEmail());
					listEntry.put("description", person.getDescription());
					listEntry.put("id", person.getId());
					personList.add(listEntry);
				}

				SimpleAdapter adapter = new SimpleAdapter(
						getApplicationContext(), personList,
						R.layout.row_twoline, new String[] { "name",
								"description" }, new int[] { R.id.title,
								R.id.description }

				);

				listView.setAdapter(adapter);

				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						HashMap<String, String> values = personList
								.get(position);
						displayPerson(values.get("id"), values.get("name"));

					}
				});
			}
		}

		private void displayPerson(String id, String name) {
			Intent intent = new Intent(rootView.getContext(),
					DisplayContactPersonActivity.class);
			intent.putExtra("personId", id);
			intent.putExtra("title", name);
			startActivity(intent);
		}
	}

}
