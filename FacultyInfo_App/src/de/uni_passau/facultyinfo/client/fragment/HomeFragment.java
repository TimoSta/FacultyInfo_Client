package de.uni_passau.facultyinfo.client.fragment;

import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.R.layout;
import de.uni_passau.facultyinfo.client.R.string;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

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
		return rootView; 
	}

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.fragment_home);
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.home, menu);
//		return true;
//	}

}
