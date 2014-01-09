package de.uni_passau.facultyinfo.client.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

		GoogleMap map = ((com.google.android.gms.maps.MapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		LatLng sydney = new LatLng(-33.867, 151.206);
		map.getUiSettings().setCompassEnabled(true);
		map.getUiSettings().setZoomControlsEnabled(true);
		map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
		
		// Add a marker at San Francisco.
		map.addMarker(new MarkerOptions().title("San Francisco")
				.position(new LatLng(37.7750, 122.4183))
				.snippet("Population: 776733"));

		map.addMarker(new MarkerOptions().title("Sydney")
				.snippet("The most populous city in Australia.")
				.position(sydney));
		// .setVisible(true);

		return rootView;

	}
}
