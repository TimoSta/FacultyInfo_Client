package de.uni_passau.facultyinfo.client.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.uni_passau.facultyinfo.client.R;

public class SportsFragment extends Fragment {

	public SportsFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_sports, container,
				false);

		getActivity().setTitle(R.string.title_sports);

		return rootView;

	}
}
