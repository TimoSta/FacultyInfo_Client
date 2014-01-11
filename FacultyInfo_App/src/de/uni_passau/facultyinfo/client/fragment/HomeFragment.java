package de.uni_passau.facultyinfo.client.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import de.uni_passau.facultyinfo.client.R;

public class HomeFragment extends Fragment {
	private View rootView;

	public HomeFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_home, container, false);

		getActivity().setTitle(R.string.title_home);

		ListView newsView = (ListView) rootView.findViewById(R.id.home_news);

		List valueList = new ArrayList<String>();

//		final ArrayList<HashMap<String, String>> homeList = new ArrayList<HashMap<String, String>>();
//
//		for (int i = 0; i < 5; i++) {
//			String iString = "" + i;
//			HashMap<String, String> temp1 = new HashMap<String, String>();
//			temp1.put("id", iString);
//			temp1.put("name", "Lehrstuhl");
//			homeList.add(temp1);
//		}
//
//		SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
//				homeList, R.layout.custom_row_view, new String[] { "name", },
//				new int[] { R.id.title });

		for (int i = 0; i < 5; i++) {
			valueList.add("News " + i);
		}

		ListAdapter adapter1 = new ArrayAdapter<String>(rootView.getContext(),
				android.R.layout.simple_list_item_1, valueList);

		newsView.setAdapter(adapter1);
		
		
		ListView busView = (ListView) rootView.findViewById(R.id.home_buslines);

		List valueListBus = new ArrayList<String>();


		for (int i = 0; i < 5; i++) {
			valueListBus.add("Bus " + i);
		}

		ListAdapter adapterbus = new ArrayAdapter<String>(rootView.getContext(),
				android.R.layout.simple_list_item_1, valueList);

		busView.setAdapter(adapterbus);
		

		return rootView;
	}

}
