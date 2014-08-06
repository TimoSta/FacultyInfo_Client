
package de.uni_passau.facultyinfo.client.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.MapMarker;
import de.uni_passau.facultyinfo.client.model.dto.MapMarkerCategory;
import de.uni_passau.facultyinfo.client.util.SwipeRefreshAsyncDataLoader;

public class MapFragment extends SwipeRefreshLayoutFragment {

	List<MapMarkerCategory> markers = null;

	GoogleMap map;

	public MapFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final SwipeRefreshLayout rootView = (SwipeRefreshLayout) inflater
				.inflate(R.layout.fragment_map, container, false);
		rootView.setEnabled(false);

		initializeSwipeRefresh(rootView, new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new MarkerLoader(rootView, true).execute();
			}
		});

		Activity activity = getActivity();
		ActionBar actionBar = activity.getActionBar();
		actionBar.setTitle(activity.getApplicationContext().getString(
				R.string.title_map));
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

		map = ((com.google.android.gms.maps.MapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		map.setInfoWindowAdapter(new PopupAdapter(activity.getLayoutInflater()));
		map.getUiSettings().setCompassEnabled(true);
		map.getUiSettings().setZoomControlsEnabled(true);
		map.setMyLocationEnabled(true);

		buildMarkerTree(rootView, null, null, null);
		((NumberPicker) rootView.findViewById(R.id.numberPicker1))
				.setOnValueChangedListener(new OnValueChangeListener() {
					@Override
					public void onValueChange(NumberPicker picker, int oldVal,
							int newVal) {
						buildMarkerTree(rootView, newVal, null, null);
					}
				});
		((NumberPicker) rootView.findViewById(R.id.numberPicker2))
				.setOnValueChangedListener(new OnValueChangeListener() {
					@Override
					public void onValueChange(NumberPicker picker, int oldVal,
							int newVal) {
						buildMarkerTree(rootView, ((NumberPicker) rootView
								.findViewById(R.id.numberPicker1)).getValue(),
								newVal, null);
					}
				});
		((NumberPicker) rootView.findViewById(R.id.numberPicker3))
				.setOnValueChangedListener(new OnValueChangeListener() {
					@Override
					public void onValueChange(NumberPicker picker, int oldVal,
							int newVal) {
						buildMarkerTree(rootView, ((NumberPicker) rootView
								.findViewById(R.id.numberPicker1)).getValue(),
								((NumberPicker) rootView
										.findViewById(R.id.numberPicker2))
										.getValue(), newVal);
					}
				});
		new MarkerLoader(rootView).execute();

		return rootView;

	}

	protected class MarkerLoader extends
			SwipeRefreshAsyncDataLoader<List<MapMarkerCategory>> {
		private boolean forceRefresh = false;

		private MarkerLoader(SwipeRefreshLayout rootView) {
			super(rootView);
		}
		private MarkerLoader(SwipeRefreshLayout rootView, boolean forceRefresh){
			super(rootView); 
			this.forceRefresh=forceRefresh; 
		}

		@Override
		protected List<MapMarkerCategory> doInBackground(Void... unused) {
			AccessFacade accessFacade = new AccessFacade();

			List<MapMarkerCategory> markers = accessFacade.getMapMarkerAccess()
					.getMapMarkers(forceRefresh);

			if (markers == null) {
				publishProgress(MarkerLoader.NO_CONNECTION_PROGRESS);
				markers = accessFacade.getMapMarkerAccess()
						.getMapMarkersFromCache();
			}

			if (markers == null) {
				markers = Collections
						.unmodifiableList(new ArrayList<MapMarkerCategory>());
			}

			return markers;
		}

		@Override
		protected void onPostExecute(List<MapMarkerCategory> markers) {
			super.onPostExecute(markers);
			MapFragment.this.markers = markers;
			buildMarkerTree(rootView, null, null, null);
		}
	}

	private void buildMarkerTree(View rootView, Integer position1,
			Integer position2, Integer position3) {
		NumberPicker np1 = (NumberPicker) rootView
				.findViewById(R.id.numberPicker1);
		NumberPicker np2 = (NumberPicker) rootView
				.findViewById(R.id.numberPicker2);
		NumberPicker np3 = (NumberPicker) rootView
				.findViewById(R.id.numberPicker3);

		if (markers != null && !markers.isEmpty()) {
			np1.setMinValue(0);
			ArrayList<String> np1Values = new ArrayList<String>();
			for (MapMarkerCategory category : markers) {
				np1Values.add(category.getTitle() != null ? category.getTitle()
						.length() > 13 ? category.getTitle().substring(0, 11)
						+ "." : category.getTitle() : "");
			}
			if (np1.getMaxValue() < markers.size()) {
				np1.setDisplayedValues(np1Values.toArray(new String[np1Values
						.size()]));
				np1.setMaxValue(markers.size() - 1);
			} else {
				np1.setMaxValue(markers.size() - 1);
				np1.setDisplayedValues(np1Values.toArray(new String[np1Values
						.size()]));
			}
			np1.setValue(position1 == null || position1 >= markers.size() ? 0
					: position1);

			if (markers
					.get(position1 == null || position1 >= markers.size() ? 0
							: position1) != null
					&& markers
							.get(position1 == null
									|| position1 >= markers.size() ? 0
									: position1).getMapMarkerCategories() != null) {
				List<MapMarkerCategory> level2 = markers.get(
						position1 == null || position1 >= markers.size() ? 0
								: position1).getMapMarkerCategories();

				if (level2 != null && !level2.isEmpty()) {
					np2.setMinValue(0);
					ArrayList<String> np2Values = new ArrayList<String>();
					for (MapMarkerCategory category : level2) {
						np2Values.add(category.getTitle() != null ? category
								.getTitle().length() > 13 ? category.getTitle()
								.substring(0, 11) + "." : category.getTitle()
								: "");
					}
					if (np2.getMaxValue() < level2.size()) {
						np2.setDisplayedValues(np2Values
								.toArray(new String[np2Values.size()]));
						np2.setMaxValue(level2.size() - 1);
					} else {
						np2.setMaxValue(level2.size() - 1);
						np2.setDisplayedValues(np2Values
								.toArray(new String[np2Values.size()]));
					}
					np2.setValue(position2 == null
							|| position2 >= level2.size() ? 0 : position2);

					if (level2.get(position2 == null
							|| position2 >= level2.size() ? 0 : position2) != null
							&& level2.get(
									position2 == null
											|| position2 >= level2.size() ? 0
											: position2).getMapMarkers() != null) {
						List<MapMarker> level3 = level2
								.get(position2 == null
										|| position2 >= level2.size() ? 0
										: position2).getMapMarkers();

						if (level3 != null && !level3.isEmpty()) {
							np3.setMinValue(0);
							ArrayList<String> np3Values = new ArrayList<String>();
							for (MapMarker mapMarker : level3) {
								np3Values
										.add(mapMarker.getName() != null ? mapMarker
												.getName().length() > 13 ? mapMarker
												.getName().substring(0, 11)
												+ "." : mapMarker.getName()
												: "");
							}
							if (np3.getMaxValue() < level3.size()) {
								np3.setDisplayedValues(np3Values
										.toArray(new String[np3Values.size()]));
								np3.setMaxValue(level3.size() > 0 ? level3
										.size() - 1 : 0);
							} else {
								np3.setMaxValue(level3.size() > 0 ? level3
										.size() - 1 : 0);
								np3.setDisplayedValues(np3Values
										.toArray(new String[np3Values.size()]));
							}

							np3.setValue(position3 == null
									|| position3 >= level3.size() ? 0
									: position3);

							if (level3.size() > 1
									&& level3.get(position3 == null
											|| position3 >= level3.size() ? 0
											: position3) != null) {
								MapMarker currentMarker = level3
										.get(position3 == null
												|| position3 >= level3.size() ? 0
												: position3);
								displayMarker(
										currentMarker.getName() != null ? currentMarker.getName()
												: "",
										currentMarker.getDescription() != null ? currentMarker
												.getDescription() : "",
										currentMarker.getLatitude(),
										currentMarker.getLongitude());
							}
						}
					}
				}
			}
		} else {
			np1.setMinValue(0);
			np1.setMaxValue(0);
			np1.setDisplayedValues(new String[] { " " });
			np2.setMinValue(0);
			np2.setMaxValue(0);
			np2.setDisplayedValues(new String[] { " " });
			np3.setMinValue(0);
			np3.setMaxValue(0);
			np3.setDisplayedValues(new String[] { " " });
		}

	}

	private void displayMarker(String name, String description, double lat,
			double lng) {

		map.clear();
		LatLng markerLatLng = new LatLng(lat, lng);
		map.animateCamera(CameraUpdateFactory
				.newLatLngZoom(markerLatLng, 16.0f));
		map.addMarker(new MarkerOptions().title(name).position(markerLatLng)
				.snippet(description));

	}

	@Override
	public void onDestroyView() {
		com.google.android.gms.maps.MapFragment mapFragment = ((com.google.android.gms.maps.MapFragment) getActivity()
				.getFragmentManager().findFragmentById(R.id.map));

		if (mapFragment != null) {
			FragmentManager fM = getFragmentManager();
			fM.beginTransaction().remove(mapFragment).commit();
		}
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		com.google.android.gms.maps.MapFragment mapFragment = ((com.google.android.gms.maps.MapFragment) getActivity()
				.getFragmentManager().findFragmentById(R.id.map));

		if (mapFragment != null) {
			FragmentManager fM = getFragmentManager();
			fM.beginTransaction().remove(mapFragment).commit();
		}
		super.onDetach();
	}

	class PopupAdapter implements InfoWindowAdapter {
		LayoutInflater inflater = null;

		PopupAdapter(LayoutInflater inflater) {
			this.inflater = inflater;
		}

		@Override
		public View getInfoWindow(Marker marker) {
			return (null);
		}

		@Override
		public View getInfoContents(Marker marker) {
			View popup = inflater.inflate(R.layout.popup_maps, null);

			TextView tv = (TextView) popup.findViewById(R.id.maps_popup_title);
			tv.setText(marker.getTitle());
			tv = (TextView) popup.findViewById(R.id.maps_popup_snippet);
			tv.setText(marker.getSnippet());

			return (popup);
		}
	}
}