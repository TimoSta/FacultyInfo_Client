package de.uni_passau.facultyinfo.client.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import de.uni_passau.facultyinfo.client.DisplayChairContactsActivity;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.activity.DisplayNewsActivity;
import de.uni_passau.facultyinfo.client.model.dto.News;

public class ContactFragment extends Fragment {
	private View rootView; 
	
	public ContactFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		View rootView = inflater.inflate(R.layout.fragment_contacts, container,
//				false);
		
		rootView = inflater.inflate(R.layout.fragment_contacts, container,
				false);

		getActivity().setTitle(R.string.title_contacts);

		ListView listView = (ListView) rootView
				.findViewById(R.id.contactChairs);
		
//		List valueList = new ArrayList<String>();
		
		final ArrayList<HashMap<String, String>> chairList = new ArrayList<HashMap<String, String>>();

		for (int i=0; i<5; i++) {
			String iString = ""+i; 
			HashMap<String, String> temp1 = new HashMap<String, String>();
			temp1.put("id", iString);
			temp1.put("name", "Lehrstuhl");
			chairList.add(temp1);
		}

		SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
				chairList, R.layout.custom_row_view, new String[] { "name",
						}, new int[] { R.id.title, });

//		for (int i = 0; i < 5; i++) {
//			valueList.add("Lehrstuhl " + i); 
//		}
//		
//		ListAdapter adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, valueList);
		
		listView.setAdapter(adapter); 
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("click");
				System.out.println(position);
				displayChairContacts(chairList.get(position).get("id"));
			}
		});

		return rootView;

	}
	private void displayChairContacts(String id){
		Intent intent = new Intent(rootView.getContext(),
				DisplayChairContactsActivity.class);

		intent.putExtra("chairId", id);
		System.out.println("putExtra");
		startActivity(intent);
	}
}
