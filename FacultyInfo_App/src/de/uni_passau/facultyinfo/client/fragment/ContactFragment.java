package de.uni_passau.facultyinfo.client.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
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
	android.widget.SearchView searchView;

	public ContactFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// View rootView = inflater.inflate(R.layout.fragment_contacts,
		// container,
		// false);

		rootView = inflater.inflate(R.layout.fragment_contacts, container,
				false);

		setHasOptionsMenu(true);

		getActivity().getActionBar().setTitle(
				getActivity().getApplicationContext().getString(
						R.string.title_contacts));
		getActivity().getActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_STANDARD);

		// ListView listView = (ListView) rootView
		// .findViewById(R.id.contactChairs);
		//
		// // List valueList = new ArrayList<String>();
		//
		// final ArrayList<HashMap<String, String>> chairList = new
		// ArrayList<HashMap<String, String>>();
		//
		// for (int i=0; i<5; i++) {
		// String iString = ""+i;
		// HashMap<String, String> temp1 = new HashMap<String, String>();
		// temp1.put("id", iString);
		// temp1.put("name", "Lehrstuhl");
		// chairList.add(temp1);
		// }
		//
		// SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
		// chairList, R.layout.custom_row_view, new String[] { "name",
		// }, new int[] { R.id.title });
		//
		// // for (int i = 0; i < 5; i++) {
		// // valueList.add("Lehrstuhl " + i);
		// // }
		// //
		// // ListAdapter adapter = new
		// ArrayAdapter<String>(rootView.getContext(),
		// android.R.layout.simple_list_item_1, valueList);
		//
		// listView.setAdapter(adapter);

		(new ChairLoader(rootView)).execute();

		return rootView;

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.contacts, menu);
		searchView = (SearchView) menu.findItem(R.id.contacts_search)
				.getActionView();
		// searchView = (SearchView) rootView.findViewById(R.id.search);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.contacts_search:

			final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {

				@Override
				public boolean onQueryTextSubmit(String query) {
					// TODO Auto-generated method stub
					System.out.println("onQueryTextSubmit");
					Intent intent = new Intent(rootView.getContext(),
							SearchContactsActivity.class);
					intent.putExtra("query", query);
					startActivity(intent);

					return false;
				}

				@Override
				public boolean onQueryTextChange(String newText) {
					// TODO Auto-generated method stub
					System.out.println("onQueryTextChange");
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
		System.out.println("putExtra");
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
			ListView listView = (ListView) rootView
					.findViewById(R.id.contactChairs);
			if (groups.isEmpty()) {
				System.out.println("groups.isEmpty");
			}

			final ArrayList<HashMap<String, String>> groupList = new ArrayList<HashMap<String, String>>();

			for (ContactGroup group : groups) {
				System.out.println("for");
				HashMap<String, String> temp1 = new HashMap<String, String>();
				temp1.put("title", group.getTitle());
				temp1.put("groupId", group.getId());
				groupList.add(temp1);
			}

			SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
					groupList, R.layout.custom_row_view,
					new String[] { "title" }, new int[] { R.id.title }

			);
			System.out.println("SimpleAdapter");

			if (adapter.isEmpty()) {
				System.out.println("adapter isEmpty");
			}

			listView.setAdapter(adapter);

			System.out.println("setAdapter");

			if (listView.getAdapter().isEmpty()) {
				System.out.println("ListViewAdapter is empty");
			}

			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					System.out.println("click");
					System.out.println(position);
					displayChairContacts(
							groupList.get(position).get("groupId"), groupList
									.get(position).get("title"));

				}
			});
		}
	}
}
