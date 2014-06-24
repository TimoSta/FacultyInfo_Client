package de.uni_passau.facultyinfo.client.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
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
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.activity.DisplaySportsCourseListActivity;
import de.uni_passau.facultyinfo.client.activity.SearchSportsActivity;
import de.uni_passau.facultyinfo.client.fragment.NewsFragment.NewsLoader;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.SportsCourseCategory;
import de.uni_passau.facultyinfo.client.util.SwipeRefreshAsyncDataLoader;

public class SportsFragment extends SwipeRefreshLayoutFragment {

	public final static String TODAY = "heute";
	public final static String AZ = "A-Z";

	static String TAB_SELECTED = null;

	SwipeRefreshLayout rootView;

	ViewPager mViewPager;

	android.widget.SearchView searchView;

	public SportsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = (SwipeRefreshLayout) inflater.inflate(R.layout.view_list,
				container, false);

		setHasOptionsMenu(true);

		Activity activity = getActivity();
		ActionBar actionBar = activity.getActionBar();
		actionBar.setTitle(activity.getApplicationContext().getString(
				R.string.title_sports));
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction arg1) {
				if (tab.getText() == AZ) {
					initializeSwipeRefresh(rootView, new OnRefreshListener() {
						@Override
						public void onRefresh() {
							new SportsCourseCategoryLoader(rootView, true)
									.execute();
						}
					});
					(new SportsCourseCategoryLoader(rootView)).execute();
				} else if (tab.getText() == TODAY) {
					initializeSwipeRefresh(rootView, new OnRefreshListener() {
						@Override
						public void onRefresh() {
							new TodaysSportsCourseCategoryLoader(rootView, true)
									.execute();
						}
					});
					(new TodaysSportsCourseCategoryLoader(rootView)).execute();
				}
			}

			@Override
			public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
			}
		};

		actionBar.removeAllTabs();
		Tab az = (Tab) actionBar.newTab().setText(AZ)
				.setTabListener(tabListener);
		actionBar.addTab(az);
		Tab today = (Tab) actionBar.newTab().setText(TODAY)
				.setTabListener(tabListener);
		actionBar.addTab(today);

		return rootView;

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.sports, menu);
		searchView = (SearchView) menu.findItem(R.id.search).getActionView();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.search:
			final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {

				@Override
				public boolean onQueryTextSubmit(String query) {
					Intent intent = new Intent(rootView.getContext(),
							SearchSportsActivity.class);
					intent.putExtra("query", query);
					startActivity(intent);

					return false;
				}

				@Override
				public boolean onQueryTextChange(String newText) {
					return false;
				}
			};
			searchView.setOnQueryTextListener(queryTextListener);

			return true;
		default:
			return false;
		}
	}

	protected class SportsCourseCategoryLoader extends
			SwipeRefreshAsyncDataLoader<List<SportsCourseCategory>> {
		private boolean forceRefresh = false;
		private AccessFacade accessFacade;

		private SportsCourseCategoryLoader(SwipeRefreshLayout rootView) {
			super(rootView);
		}

		private SportsCourseCategoryLoader(SwipeRefreshLayout rootView,
				boolean forceRefresh) {
			super(rootView);
			this.forceRefresh = forceRefresh;
		}

		@Override
		protected List<SportsCourseCategory> doInBackground(Void... unused) {
			showLoadingAnimation(true);

			accessFacade = new AccessFacade();

			List<SportsCourseCategory> sportsCourseCategories = accessFacade
					.getSportsCourseAccess().getCategories(forceRefresh);

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
			super.onPostExecute(sportsCourseCategories);

			ListView sportsCourseList = (ListView) rootView
					.findViewById(R.id.list);

			final ArrayList<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();

			for (SportsCourseCategory category : sportsCourseCategories) {

				HashMap<String, String> listEntry = new HashMap<String, String>();
				listEntry.put("id", category.getId());
				listEntry.put("title", category.getTitle());
				categoryList.add(listEntry);
			}

			SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
					categoryList, R.layout.row_oneline,
					new String[] { "title", }, new int[] { R.id.title });

			sportsCourseList.setAdapter(adapter);

			sportsCourseList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					displaySportsCourses(categoryList.get(position).get("id"),
							categoryList.get(position).get("title"), AZ);
				}
			});
		}
	}

	private void displaySportsCourses(String categoryId, String title,
			String offerTime) {
		Intent intent = new Intent(rootView.getContext(),
				DisplaySportsCourseListActivity.class);
		intent.putExtra("categoryId", categoryId);
		intent.putExtra("title", title);
		intent.putExtra("offerTime", offerTime);
		startActivity(intent);

	}

	protected class TodaysSportsCourseCategoryLoader extends
			SwipeRefreshAsyncDataLoader<List<SportsCourseCategory>> {
		private boolean forceRefresh = false;

		private TodaysSportsCourseCategoryLoader(SwipeRefreshLayout rootView) {
			super(rootView);
		}

		private TodaysSportsCourseCategoryLoader(SwipeRefreshLayout rootView,
				boolean forceRefresh) {
			super(rootView);
			this.forceRefresh = forceRefresh;
		}

		@Override
		protected List<SportsCourseCategory> doInBackground(Void... unused) {
			showLoadingAnimation(true);

			AccessFacade accessFacade = new AccessFacade();

			List<SportsCourseCategory> sportsCourseCategories = accessFacade
					.getSportsCourseAccess().getCategoriesToday(forceRefresh);

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
			super.onPostExecute(sportsCourseCategories);
			if (sportsCourseCategories != null) {
				ListView sportsCourseList = (ListView) rootView
						.findViewById(R.id.list);

				final ArrayList<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();

				for (SportsCourseCategory category : sportsCourseCategories) {

					HashMap<String, String> listEntry = new HashMap<String, String>();
					listEntry.put("id", category.getId());
					listEntry.put("title", category.getTitle());
					categoryList.add(listEntry);
				}

				SimpleAdapter adapter = new SimpleAdapter(
						rootView.getContext(), categoryList,
						R.layout.row_oneline, new String[] { "title" },
						new int[] { R.id.title });

				sportsCourseList.setAdapter(adapter);

				sportsCourseList
						.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								displaySportsCourses(categoryList.get(position)
										.get("id"), categoryList.get(position)
										.get("title"), TODAY);
							}
						});
			}
		}
	}
}