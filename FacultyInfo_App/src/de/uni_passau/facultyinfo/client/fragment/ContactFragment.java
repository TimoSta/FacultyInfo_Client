package de.uni_passau.facultyinfo.client.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.activity.DisplayChairContactsActivity;
import de.uni_passau.facultyinfo.client.activity.DisplayNewsActivity;
import de.uni_passau.facultyinfo.client.fragment.NewsFragment.NewsLoader;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.BusLine;
import de.uni_passau.facultyinfo.client.model.dto.ContactGroup;
import de.uni_passau.facultyinfo.client.model.dto.News;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

public class ContactFragment extends Fragment {
	private View rootView;

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

		getActivity().setTitle(R.string.title_contacts);

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
			ListView listView = (ListView) rootView.findViewById(R.id.contactChairs);
			if(groups.isEmpty()){
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
					new String[] { "title"}, new int[] { R.id.title }

			);
			System.out.println("SimpleAdapter"); 
			
			if(adapter.isEmpty()){
				System.out.println("adapter isEmpty");
			}

			listView.setAdapter(adapter);
			
			System.out.println("setAdapter"); 
			
			if(listView.getAdapter().isEmpty()){
				System.out.println("ListViewAdapter is empty"); 
			}

			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					System.out.println("click");
					System.out.println(position);
					displayChairContacts(groupList.get(position).get("groupId"), groupList.get(position).get("title"));
					
				}
			});
		}
	}
}
