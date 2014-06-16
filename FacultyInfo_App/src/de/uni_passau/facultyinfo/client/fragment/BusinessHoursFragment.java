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

public class BusinessHoursFragment extends Fragment {

	public final static String LIBRARY = "Bibliotheken";
	public final static String CAFETERIA = "Cafeterien / Mensa";
	static ActionBar.TabListener tabListener;

	static String TAB_SELECTED = null;
	private String selectedTab;

	private View rootView;

	ViewPager mViewPager;

	android.widget.SearchView searchView;

	public BusinessHoursFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_activity_business_hours,
				container, false);

		setHasOptionsMenu(true);

		ActionBar actionBar = getActivity().getActionBar();
		actionBar.setTitle(getActivity().getApplicationContext().getString(
				R.string.title_businesshours));
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction arg1) {
				if (tab.getText() == LIBRARY) {
					selectedTab = LIBRARY;
				} else if (tab.getText() == CAFETERIA) {
					selectedTab = CAFETERIA;
				}

				(new BusinessHourFacilityLoader(rootView)).execute();
			}

			@Override
			public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
			}
		};

		actionBar.removeAllTabs();
		Tab library = (Tab) actionBar.newTab().setText(LIBRARY)
				.setTabListener(tabListener);
		actionBar.addTab(library);
		Tab cafeteria = (Tab) actionBar.newTab().setText(CAFETERIA)
				.setTabListener(tabListener);
		actionBar.addTab(cafeteria);

		return rootView;

	}

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
					publishProgress(AsyncDataLoader.NO_CONNECTION_PROGRESS);
					list = accessFacade.getBusinessHoursAccess()
							.getLibrariesFromCache();
				}

			} else if (selectedTab == CAFETERIA) {
				list = accessFacade.getBusinessHoursAccess().getCafeterias();

				if (list == null) {
					publishProgress(AsyncDataLoader.NO_CONNECTION_PROGRESS);
					list = accessFacade.getBusinessHoursAccess()
							.getCafeteriasFromCache();
				}
			}

			if (list == null) {
				list = Collections
						.unmodifiableList(new ArrayList<BusinessHoursFacility>());
			}

			return list;
		}

		@Override
		protected void onPostExecute(List<BusinessHoursFacility> list) {
			ListView listView = (ListView) rootView
					.findViewById(R.id.business_hour_list);

			final ArrayList<HashMap<String, String>> facilityList = new ArrayList<HashMap<String, String>>();

			for (BusinessHoursFacility listElement : list) {
				System.out.println("for");
				HashMap<String, String> listEntry = new HashMap<String, String>();
				listEntry.put("name", listElement.getName());
				listEntry.put("facilityId", listElement.getId());
				facilityList.add(listEntry);
			}

			SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
					facilityList, R.layout.custom_row_view,
					new String[] { "name" }, new int[] { R.id.title }

			);

			listView.setAdapter(adapter);

			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
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
