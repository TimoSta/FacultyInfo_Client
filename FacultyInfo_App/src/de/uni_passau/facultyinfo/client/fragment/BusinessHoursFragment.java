package de.uni_passau.facultyinfo.client.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.activity.DisplayBusinessHoursActivity;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.BusinessHoursFacility;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

//import de.uni_passau.facultyinfo.client.fragment.NewsFragment.NewsLoader;

public class BusinessHoursFragment extends Fragment {

	public final static String LIBRARY = "Bibliotheken";
	public final static String CAFETERIA = "Cafeterien / Mensa";
	// static Tab library;
	// static Tab cafeteria;
	static ActionBar.TabListener tabListener;

	static String TAB_SELECTED = null;
	private String selectedTab;

	private View rootView;

	ViewPager mViewPager;

	android.widget.SearchView searchView;

	public BusinessHoursFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// getActivity().getActionBar().setNavigationMode(
		// ActionBar.NAVIGATION_MODE_TABS);
		rootView = inflater.inflate(R.layout.fragment_activity_business_hours,
				container, false);

		setHasOptionsMenu(true);

		getActivity().getActionBar().setTitle(
				getActivity().getApplicationContext().getString(
						R.string.title_businesshours));
		getActivity().getActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_TABS);

		tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				System.out.println("onTabUnselected");

			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				System.out.println("onTabSelected");
				// TextView textView = null;

				if (tab.getText() == LIBRARY) {
					System.out.println("Bib");
					selectedTab = LIBRARY;
				} else if (tab.getText() == CAFETERIA) {
					System.out.println("Cafete");
					selectedTab = CAFETERIA;
				}

				(new BusinessHourFacilityLoader(rootView)).execute();

				// mViewPager.setCurrentItem(tab.getPosition());
				System.out.println("tabposition: " + tab.getPosition());

			}

			@Override
			public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				System.out.println("onTabReselected");

			}
		};

		// if (first == true) {
		// System.out.println("first==true");
		// library = (Tab) getActivity().getActionBar().newTab()
		// .setText(LIBRARY).setTabListener(tabListener);
		// getActivity().getActionBar().addTab(library);
		//
		// cafeteria = getActivity().getActionBar().newTab()
		// .setText(CAFETERIA).setTabListener(tabListener);
		// getActivity().getActionBar().addTab(cafeteria);
		// first = false;
		// } else {
		// library.setTabListener(tabListener);
		// cafeteria.setTabListener(tabListener);
		// }

		getActivity().getActionBar().removeAllTabs();
		Tab library = (Tab) getActivity().getActionBar().newTab()
				.setText(LIBRARY).setTabListener(tabListener);
		getActivity().getActionBar().addTab(library);
		Tab cafeteria = (Tab) getActivity().getActionBar().newTab()
				.setText(CAFETERIA).setTabListener(tabListener);
		getActivity().getActionBar().addTab(cafeteria);

		return rootView;

	}

	// @Override
	// public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	// // TODO Auto-generated method stub
	// super.onCreateOptionsMenu(menu, inflater);
	// inflater.inflate(R.menu.sports, menu);
	// searchView = (SearchView) menu.findItem(R.id.search).getActionView();
	// // searchView = (SearchView) rootView.findViewById(R.id.search);
	//
	// }

	protected class BusinessHourFacilityLoader extends
			AsyncDataLoader<List<BusinessHoursFacility>> {
		private BusinessHourFacilityLoader(View rootView) {
			super(rootView);
		}

		@Override
		protected List<BusinessHoursFacility> doInBackground(Void... unused) {
			AccessFacade accessFacade = new AccessFacade();
			List<BusinessHoursFacility> list = null;

			if (selectedTab == LIBRARY) {
				list = accessFacade.getBusinessHoursAccess().getLibraries();

				if (list == null) {
					// publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
					list = accessFacade.getBusinessHoursAccess()
							.getLibrariesFromCache();
				}

				if (list == null) {
					list = Collections
							.unmodifiableList(new ArrayList<BusinessHoursFacility>());
				}
			} else if (selectedTab == CAFETERIA) {
				list = accessFacade.getBusinessHoursAccess().getCafeterias();

				if (list == null) {
					// publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
					list = accessFacade.getBusinessHoursAccess()
							.getCafeteriasFromCache();
				}

				if (list == null) {
					list = Collections
							.unmodifiableList(new ArrayList<BusinessHoursFacility>());
				}
			}

			return list;
		}

		@Override
		protected void onPostExecute(List<BusinessHoursFacility> list) {
			ListView listView = (ListView) rootView
					.findViewById(R.id.business_hour_list);
			if (list.isEmpty()) {
				System.out.println("libraries.isEmpty");
			}

			final ArrayList<HashMap<String, String>> facilityList = new ArrayList<HashMap<String, String>>();

			for (BusinessHoursFacility listElement : list) {
				System.out.println("for");
				HashMap<String, String> temp1 = new HashMap<String, String>();
				temp1.put("name", listElement.getName());
				temp1.put("facilityId", listElement.getId());
				facilityList.add(temp1);
			}

			SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
					facilityList, R.layout.custom_row_view,
					new String[] { "name" }, new int[] { R.id.title }

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
					displayBusinessHours(
							facilityList.get(position).get("facilityId"),
							facilityList.get(position).get("name"));
				}
			});
		}
	}

	void displayBusinessHours(String facilityId, String name) {
		Intent intent = new Intent(rootView.getContext(),
				DisplayBusinessHoursActivity.class);
		intent.putExtra("facilityId", facilityId);
		intent.putExtra("name", name);
		startActivity(intent);
	}

}
