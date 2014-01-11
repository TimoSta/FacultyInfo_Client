package de.uni_passau.facultyinfo.client.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.activity.DisplaySportCoursesActivity;
import de.uni_passau.facultyinfo.client.activity.SearchSportsActivity;
import de.uni_passau.facultyinfo.client.fragment.NewsFragment.NewsLoader;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.SportsCourseCategory;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

public class SportsFragment extends Fragment {

	public final static String TODAY = "heute";
	public final static String AZ = "A-Z";
	// static Tab az;
	// static Tab today;
	// static ActionBar.TabListener tabListener;

	static String TAB_SELECTED = null;

	private static boolean first = true;

	private View rootView;
	private TabHost mTabHost;
	// AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	ViewPager mViewPager;

	android.widget.SearchView searchView;

	public SportsFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// getActivity().getActionBar().setNavigationMode(
		// ActionBar.NAVIGATION_MODE_TABS);
		rootView = inflater.inflate(R.layout.fragment_sports, container, false);

		setHasOptionsMenu(true);

		getActivity().setTitle(R.string.title_sports);

		TabListener tabListener = new ActionBar.TabListener() {

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

				if (tab.getText() == AZ) {
					System.out.println("a-z");
					(new SportsCourseCategoryLoader(rootView)).execute();
				} else if (tab.getText() == TODAY) {
					System.out.println("heute");
					(new TodaysSportsCourseCategoryLoader(rootView)).execute();
				}

				// mViewPager.setCurrentItem(tab.getPosition());
				System.out.println("tabposition: " + tab.getPosition());

			}

			@Override
			public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				System.out.println("onTabReselected");

			}
		};

		getActivity().getActionBar().removeAllTabs();
		Tab az = (Tab) getActivity().getActionBar().newTab().setText(AZ)
				.setTabListener(tabListener);
		getActivity().getActionBar().addTab(az);
		Tab today = (Tab) getActivity().getActionBar().newTab().setText(TODAY)
				.setTabListener(tabListener);
		getActivity().getActionBar().addTab(today);

		// if (first == true) {
		// System.out.println("first==true");
		// az = (Tab) getActivity().getActionBar().newTab().setText(AZ)
		// .setTabListener(tabListener);
		// getActivity().getActionBar().addTab(az);
		//
		// today = getActivity().getActionBar().newTab().setText(TODAY)
		// .setTabListener(tabListener);
		// getActivity().getActionBar().addTab(today);
		// first = false;
		// } else {
		// az.setTabListener(tabListener);
		// today.setTabListener(tabListener);
		// }

		// mTabHost = (TabHost) rootView.findViewById(android.R.id.tabhost);
		// System.out.println("mTabHost");
		// setupTab(new TextView(rootView.getContext()), "A-Z");
		// setupTab(new TextView(rootView.getContext()), "heute");
		// System.out.println("setupTab");

		// Switch slider = (Switch) rootView.findViewById(R.id.timePeriod);
		// slider.setTextOn("A-Z");
		// slider.setTextOff("heute");

		// Switch s = (Switch) rootView.findViewById(R.id.timePeriod);
		// if (s != null) {
		// s.setOnCheckedChangeListener(this);
		// }

		// SportsCourseCategoryLoader sportsCategoryLoader = new
		// SportsCourseCategoryLoader(
		// rootView);
		// sportsCategoryLoader.execute();
		//
		// TodaysSportsCourseCategoryLoader todayssportsCategoryLoader = new
		// TodaysSportsCourseCategoryLoader(
		// rootView);
		// todayssportsCategoryLoader.execute();

		// SearchView searchView =
		// (SearchView)rootView.findViewById(R.id.search);
		// searchView.setOnQueryTextListener(new OnQueryTextListener() {
		//
		// @Override
		// public boolean onQueryTextSubmit(String query) {
		// getActivity().onSearchRequested();
		// return false;
		// }
		//
		// @Override
		// public boolean onQueryTextChange(String newText) {
		// // TODO Auto-generated method stub
		// return false;
		// }
		// });

		return rootView;

	}

	// @Override
	// protected void onNewIntent(Intent intent) {
	// // Because this activity has set launchMode="singleTop", the system calls
	// this method
	// // to deliver the intent if this activity is currently the foreground
	// activity when
	// // invoked again (when the user executes a search from this activity, we
	// don't create
	// // a new instance of this activity, so the system delivers the search
	// intent here)
	// handleIntent(intent);
	// }

	// private void handleIntent(Intent intent) {
	// String query = intent.getStringExtra(SearchManager.QUERY);
	// System.out.println(query);
	//
	// }
	//
	// /**
	// * Searches the dictionary and displays results for the given query.
	// * @param query The search query
	// */
	// private void showResults(String query) {
	//
	// System.out.println("showResult");
	// //new Activity -> display Results
	// }
	//
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.sports, menu);
		searchView = (SearchView) menu.findItem(R.id.search).getActionView();
		// searchView = (SearchView) rootView.findViewById(R.id.search);

	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// MenuInflater inflater = getMenuInflater();
	// inflater.inflate(R.menu.options_menu, menu);
	//
	// if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
	// SearchManager searchManager = (SearchManager)
	// getSystemService(Context.SEARCH_SERVICE);
	// SearchView searchView = (SearchView)
	// menu.findItem(R.id.search).getActionView();
	// searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	// searchView.setIconifiedByDefault(false);
	// }
	//
	// return true;
	// }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.search:
			// getActivity().onSearchRequested();
			// search();
			// searchView.setOnSearchClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View view) {
			// System.out.println("click");
			// System.out.println(searchView.getQuery());
			// // TODO Auto-generated method stub
			// // Intent intent = new Intent(rootView.getContext(),
			// SearchSportsActivity.class);
			// // intent.putExtra("query", searchView.getQuery());
			// // startActivity(intent);
			// }
			// });
			//
			// searchView.setOnCloseListener(new OnCloseListener() {
			//
			// @Override
			// public boolean onClose() {
			// System.out.println("onclose");
			// // TODO Auto-generated method stub
			// return false;
			// }
			// });
			//
			// searchView.setOnQueryTextFocusChangeListener(new
			// OnFocusChangeListener() {
			//
			// @Override
			// public void onFocusChange(View arg0, boolean arg1) {
			// System.out.println("onFocusChange");
			// // TODO Auto-generated method stub
			//
			// }
			// });
			//
			// searchView.setOnQueryTextFocusChangeListener(new
			// OnFocusChangeListener() {
			//
			// @Override
			// public void onFocusChange(View arg0, boolean arg1) {
			// System.out.println("onQueryTextFocusChangeListener");
			// // TODO Auto-generated method stub
			//
			// }
			// });
			//
			final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {

				@Override
				public boolean onQueryTextSubmit(String query) {
					// TODO Auto-generated method stub
					System.out.println("onQueryTextSubmit");
					Intent intent = new Intent(rootView.getContext(),
							SearchSportsActivity.class);
					intent.putExtra("query", query);
					startActivity(intent);

					return false;
				}

				@Override
				public boolean onQueryTextChange(String newText) {
					// TODO Auto-generated method stub
					System.out.println("onQueryTextChange");
					return false;
				}
			};
			searchView.setOnQueryTextListener(queryTextListener);

			return true;
		default:
			return false;
		}
	}

	// void search(){
	// System.out.println("test");
	// SearchView searchView = (SearchView) rootView.findViewById(R.id.search);
	// System.out.println(searchView.getQuery());
	// searchView.setOnSearchClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View view) {
	// System.out.println("click");
	// // TODO Auto-generated method stub
	//
	// }
	// });

	//
	//
	// private void setupTab(final View view, final String tag) {
	// System.out.println("setupTab");
	// View tabview = createTabView(mTabHost.getContext(), tag);
	// System.out.println("setConentent");
	// TabSpec setContent =
	// mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(new
	// TabContentFactory() {
	// public View createTabContent(String tag) {return view;}
	// });
	// System.out.println("setContent");
	// mTabHost.addTab(setContent);
	// System.out.println("addTab");
	// }
	//
	// private static View createTabView(final Context context, final String
	// text) {
	// System.out.println("createTabView");
	// View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
	// TextView tv = (TextView) view.findViewById(R.id.tabsText);
	// tv.setText(text);
	// return view;
	// }

	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case R.id.action_search:
	// System.out.println("Search");
	//
	// return true;
	// default:
	// return super.onOptionsItemSelected(item);
	// }
	// }
	//
	// @Override
	// public void onCheckedChanged(CompoundButton buttonView, boolean
	// isChecked) {
	// if (isChecked) {
	// System.out.println("A-Z");
	// SportsCourseCategoryLoader sportsCategoryLoader = new
	// SportsCourseCategoryLoader(
	// rootView);
	// sportsCategoryLoader.execute();
	// } else {
	// System.out.println("heute");
	// TodaysSportsCourseCategoryLoader sportsCategoryLoader = new
	// TodaysSportsCourseCategoryLoader(
	// rootView);
	// sportsCategoryLoader.execute();
	// }
	//
	// Toast.makeText(rootView.getContext(), "Monitored switch is " +
	// (isChecked ? "on" : "off"),
	// Toast.LENGTH_SHORT).show();

	// public class SportsFragment extends FragmentActivity implements
	// ActionBar.TabListener {
	//
	// AppSectionsPagerAdapter mAppSectionsPagerAdapter;
	//
	// ViewPager mViewPager;
	//
	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// System.out.println("SportsFragment");
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.fragment_sports);
	// System.out.println("SportsFragment -> onCreate");
	//
	// mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(
	// getSupportFragmentManager());
	//
	// final ActionBar actionBar = getActionBar();
	// actionBar.setDisplayHomeAsUpEnabled(true);
	// actionBar.setDisplayShowHomeEnabled(true);
	// actionBar.setDisplayShowTitleEnabled(true);
	//
	// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	// mViewPager = (ViewPager) findViewById(R.id.pager);
	// mViewPager.setAdapter(mAppSectionsPagerAdapter);
	// mViewPager
	// .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
	// @Override
	// public void onPageSelected(int position) {
	// System.out.println("position: " + position);
	// actionBar.setSelectedNavigationItem(position);
	// }
	// });
	//
	// // For each of the sections in the app, add a tab to the action bar.
	// for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
	// System.out.println("i: " + i);
	// // Create a tab with text corresponding to the page title
	// // defined by
	// // the adapter.
	// // Also specify this Activity object, which implements the
	// // TabListener interface, as the
	// // listener for when this tab is selected.
	// actionBar.addTab(actionBar.newTab()
	// .setText(mAppSectionsPagerAdapter.getPageTitle(i))
	// .setTabListener(this));
	// // mAppSectionsPagerAdapter.getItem(i);
	// }
	// actionBar.setSelectedNavigationItem(0);
	// }

	protected class SportsCourseCategoryLoader extends
			AsyncDataLoader<List<SportsCourseCategory>> {

		private AccessFacade accessFacade;

		private SportsCourseCategoryLoader(View rootView) {
			super(rootView);
		}

		@Override
		protected List<SportsCourseCategory> doInBackground(Void... unused) {
			System.out.println("SportsCourseCategoryLoader->doInBackground()");
			accessFacade = new AccessFacade();

			List<SportsCourseCategory> sportsCourseCategories = accessFacade
					.getSportsCourseAccess().getCategories();

			if (sportsCourseCategories == null) {
				publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
				sportsCourseCategories = accessFacade.getSportsCourseAccess()
						.getCategoriesFromCache();
			}

			if (sportsCourseCategories == null) {
				sportsCourseCategories = Collections
						.unmodifiableList(new ArrayList<SportsCourseCategory>());
			}

			return sportsCourseCategories;
		}

		@Override
		protected void onPostExecute(
				List<SportsCourseCategory> sportsCourseCategories) {
			ListView sportsoffer = (ListView) rootView
					.findViewById(R.id.sportscategories);

			System.out.println("SportsCourseCategoryLoader->onPostExecute()");

			final ArrayList<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();

			for (SportsCourseCategory category : sportsCourseCategories) {

				HashMap<String, String> temp1 = new HashMap<String, String>();
				temp1.put("id", category.getId());
				temp1.put("title", category.getTitle());
				categoryList.add(temp1);
			}

			SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
					categoryList, R.layout.custom_row_view,
					new String[] { "title", }, new int[] { R.id.title });

			sportsoffer.setAdapter(adapter);

			sportsoffer.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					System.out.println("click");
					System.out.println(position);
					displaySportsCourses(categoryList.get(position).get("id"),
							categoryList.get(position).get("title"), AZ);
				}

			});

		}

	}

	private void displaySportsCourses(String categoryId, String title,
			String offerTime) {
		Intent intent = new Intent(rootView.getContext(),
				DisplaySportCoursesActivity.class);
		intent.putExtra("categoryId", categoryId);
		intent.putExtra("title", title);
		intent.putExtra("offerTime", offerTime);
		startActivity(intent);

	}

	protected class TodaysSportsCourseCategoryLoader extends
			AsyncDataLoader<List<SportsCourseCategory>> {
		private TodaysSportsCourseCategoryLoader(View rootView) {
			super(rootView);
		}

		@Override
		protected List<SportsCourseCategory> doInBackground(Void... unused) {
			System.out
					.println("TodaysSportsCourseCategoryLoader->doInBackground()");
			AccessFacade accessFacade = new AccessFacade();

			List<SportsCourseCategory> sportsCourseCategories = accessFacade
					.getSportsCourseAccess().getCategoriesToday();

			if (sportsCourseCategories == null) {
				publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
				sportsCourseCategories = accessFacade.getSportsCourseAccess()
						.getCategoriesTodayFromCache();
			}

			if (sportsCourseCategories == null) {
				sportsCourseCategories = Collections
						.unmodifiableList(new ArrayList<SportsCourseCategory>());
			}

			return sportsCourseCategories;
		}

		@Override
		protected void onPostExecute(
				List<SportsCourseCategory> sportsCourseCategories) {
			ListView sportsoffer = (ListView) rootView
					.findViewById(R.id.sportscategories);

			System.out
					.println("TodaysSportsCourseCategoryLoader->onPostExecute()");

			final ArrayList<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();

			for (SportsCourseCategory category : sportsCourseCategories) {

				HashMap<String, String> temp1 = new HashMap<String, String>();
				temp1.put("id", category.getId());
				temp1.put("title", category.getTitle());
				categoryList.add(temp1);
			}

			SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
					categoryList, R.layout.custom_row_view,
					new String[] { "title", }, new int[] { R.id.title });

			sportsoffer.setAdapter(adapter);

			sportsoffer.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					System.out.println("click");
					System.out.println(position);
					displaySportsCourses(categoryList.get(position).get("id"),
							categoryList.get(position).get("title"), TODAY);
				}

			});

		}
	}
}

// public class SportsFragment extends Fragment implements
// ActionBar.TabListener {
//
// AppSectionsPagerAdapter mAppSectionsPagerAdapter;
//
// ViewPager mViewPager;
//
// View rootView;
//
// @Override
// public View onCreateView(LayoutInflater inflater, ViewGroup container,
// Bundle savedInstanceState) {
// System.out.println("SportsFragment");
// super.onCreate(savedInstanceState);
// //setContentView(R.layout.fragment_sports);
// rootView=inflater.inflate(R.layout.fragment_sports, container,
// false);
//
// System.out.println("SportsFragment -> onCreate");
//
// mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getFragmentManager());
//
// final ActionBar actionBar = getActionBar();
// actionBar.setDisplayHomeAsUpEnabled(true);
// actionBar.setDisplayShowHomeEnabled(true);
// actionBar.setDisplayShowTitleEnabled(true);
//
// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
// mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
// mViewPager.setAdapter(mAppSectionsPagerAdapter);
// mViewPager
// .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
// @Override
// public void onPageSelected(int position) {
// System.out.println("position: " + position);
// actionBar.setSelectedNavigationItem(position);
// }
// });
//
// // For each of the sections in the app, add a tab to the action bar.
// for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
// System.out.println("i: " + i);
// // Create a tab with text corresponding to the page title defined by
// // the adapter.
// // Also specify this Activity object, which implements the
// // TabListener interface, as the
// // listener for when this tab is selected.
// actionBar.addTab(actionBar.newTab()
// .setText(mAppSectionsPagerAdapter.getPageTitle(i))
// .setTabListener(this));
// // mAppSectionsPagerAdapter.getItem(i);
// }
// actionBar.setSelectedNavigationItem(0);
// }
//
// @Override
// public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater)
// {
// inflater.inflate(R.menu.sports, menu);
// super.onCreateOptionsMenu(menu, inflater);
// }
//
// @Override
// public void onTabUnselected(ActionBar.Tab tab,
// FragmentTransaction fragmentTransaction) {
// }
//
// @Override
// public void onTabSelected(ActionBar.Tab tab,
// FragmentTransaction fragmentTransaction) {
// // When the given tab is selected, switch to the corresponding page in
// // the ViewPager.
// mViewPager.setCurrentItem(tab.getPosition());
// System.out.println("tabposition: " + tab.getPosition());
// }
//
// @Override
// public void onTabReselected(ActionBar.Tab tab,
// FragmentTransaction fragmentTransaction) {
// }
//
//
//
/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one
 * of the primary sections of the app.
 */
// public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {
//
// public AppSectionsPagerAdapter(FragmentManager fragmentManager) {
// super(fragmentManager);
// }
//
// @Override
// public android.support.v4.app.Fragment getItem(int i) {
// System.out.println("getItem: " + i);
// android.support.v4.app.Fragment fragment = new SportsCategoryFragment();
// Bundle args = new Bundle();
//
// args.putInt(SportsCategoryFragment.ARG_PERIOD, i + 1);
// fragment.setArguments(args);
// return fragment;
//
// }
//
// @Override
// public int getCount() {
// return 2;
// }
//
// @Override
// public CharSequence getPageTitle(int position) {
// String tab = "";
// switch (position) {
// case 0:
// tab = "A-Z";
// break;
// case 1:
// tab = "heute";
// break;
// }
// return tab;
// }
// }
// }
//
// }