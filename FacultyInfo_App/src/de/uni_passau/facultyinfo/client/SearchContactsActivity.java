package de.uni_passau.facultyinfo.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import de.uni_passau.facultyinfo.client.activity.DisplayChairContactsActivity;
//import de.uni_passau.facultyinfo.client.fragment.NewsFragment.NewsLoader;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.ContactGroup;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class SearchContactsActivity extends Activity {
	
	private String query; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_contacts);
		
		Intent intent = getIntent(); 
		query=intent.getStringExtra("query"); 
		
		(new ChairLoader()).execute(); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_contacts, menu);
		return true;
	}
	
	
	protected class ChairLoader extends AsyncDataLoader<List<ContactGroup>> {

		@Override
		protected List<ContactGroup> doInBackground(Void... unused) {
			AccessFacade accessFacade = new AccessFacade();

			List<ContactGroup> groups = accessFacade.getContactPersonAccess()
					.getContactGroups();

//			if (groups == null) {
//				publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
//				groups = accessFacade.getContactPersonAccess()
//						.getContactGroupsFromCache();
//			}
//
//			if (groups == null) {
//				groups = Collections
//						.unmodifiableList(new ArrayList<ContactGroup>());
//			}

			return groups;
		}

		@Override
		protected void onPostExecute(List<ContactGroup> groups) {
			ListView listView = (ListView) findViewById(R.id.contacts_search_results);
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

			SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
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
	
	private void displayChairContacts(String id, String title) {
		Intent intent = new Intent(getApplicationContext(),
				DisplayChairContactsActivity.class);

		intent.putExtra("chairId", id);
		intent.putExtra("title", title);
		startActivity(intent);
	}

}
