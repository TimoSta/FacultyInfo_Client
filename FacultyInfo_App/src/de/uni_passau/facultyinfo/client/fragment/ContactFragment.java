package de.uni_passau.facultyinfo.client.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.activity.DisplayChairContactsActivity;
import de.uni_passau.facultyinfo.client.activity.SearchContactsActivity;
import de.uni_passau.facultyinfo.client.fragment.NewsFragment.NewsLoader;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.ContactGroup;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

public class ContactFragment extends Fragment {
	private View rootView;
	private SearchView searchView;

	public ContactFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_contacts, container,
				false);

		setHasOptionsMenu(true);

		Activity activity = getActivity();
		ActionBar actionBar = activity.getActionBar();
		actionBar.setTitle(activity.getApplicationContext().getString(
				R.string.title_contacts));
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

		(new ChairLoader(rootView)).execute();

		return rootView;

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.contacts, menu);
		searchView = (SearchView) menu.findItem(R.id.contacts_search)
				.getActionView();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.contacts_search:
			final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
				@Override
				public boolean onQueryTextSubmit(String query) {
					Intent intent = new Intent(rootView.getContext(),
							SearchContactsActivity.class);
					intent.putExtra("query", query);
					startActivity(intent);

					return false;
				}

				@Override
				public boolean onQueryTextChange(String newText) {
					return false;
				}
			};
			searchView.setOnQueryTextListener(queryTextListener);

			return true;
		default:
			return false;
		}
	}

	private void displayChairContacts(String id, String title) {
		Intent intent = new Intent(rootView.getContext(),
				DisplayChairContactsActivity.class);

		intent.putExtra("chairId", id);
		intent.putExtra("title", title);
		startActivity(intent);
	}

	protected class ChairLoader extends AsyncDataLoader<List<ContactGroup>> {
		private ChairLoader(View rootView) {
			super(rootView);
		}

		@Override
		protected List<ContactGroup> doInBackground(Void... unused) {
			AccessFacade accessFacade = new AccessFacade();

			List<ContactGroup> groups = accessFacade.getContactPersonAccess()
					.getContactGroups();

			if (groups == null) {
				publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
				groups = accessFacade.getContactPersonAccess()
						.getContactGroupsFromCache();
			}

			if (groups == null) {
				groups = Collections
						.unmodifiableList(new ArrayList<ContactGroup>());
			}

			return groups;
		}

		@Override
		protected void onPostExecute(List<ContactGroup> groups) {
			if (groups != null) {
				ListView listView = (ListView) rootView
						.findViewById(R.id.contactChairs);

				final ArrayList<HashMap<String, String>> groupList = new ArrayList<HashMap<String, String>>();

				for (ContactGroup group : groups) {
					HashMap<String, String> listEntry = new HashMap<String, String>();
					listEntry.put("title", group.getTitle());
					listEntry.put("groupId", group.getId());
					groupList.add(listEntry);
				}

				SimpleAdapter adapter = new SimpleAdapter(
						rootView.getContext(), groupList,
						R.layout.custom_row_view, new String[] { "title" },
						new int[] { R.id.title }

				);

				listView.setAdapter(adapter);

				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						displayChairContacts(
								groupList.get(position).get("groupId"),
								groupList.get(position).get("title"));

					}
				});
			}
		}
	}
}
