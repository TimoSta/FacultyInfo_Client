package de.uni_passau.facultyinfo.client.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.uni_passau.facultyinfo.client.R;

public class MapFragment extends Fragment {

	public MapFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_map, container,
				false);

		getActivity().setTitle(R.string.title_activity_map);

		/*
		 * // Get a handle to the Map Fragment GoogleMap map = ((MapFragment)
		 * getFragmentManager() .findFragmentById(R.id.map)).getMap();
		 * 
		 * LatLng sydney = new LatLng(-33.867, 151.206);
		 * 
		 * map.setMyLocationEnabled(true);
		 * map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
		 * 
		 * map.addMarker(new MarkerOptions() .title("Sydney")
		 * .snippet("The most populous city in Australia.") .position(sydney));
		 */
		return rootView;

	}
}
