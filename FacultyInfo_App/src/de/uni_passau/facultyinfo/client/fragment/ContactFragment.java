package de.uni_passau.facultyinfo.client.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.uni_passau.facultyinfo.client.R;

public class ContactFragment extends Fragment{
	public ContactFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_contacts, container,
				false);

		getActivity().setTitle(R.string.title_contacts);

		return rootView;

	}
}
