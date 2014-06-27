package de.uni_passau.facultyinfo.client.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.fragment.SportsFragment;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.SportsCourseCategory;
import de.uni_passau.facultyinfo.client.util.SwipeRefreshAsyncDataLoader;

public class SearchSportsActivity extends SwipeRefreshLayoutActivity {

	private String query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_list);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);

		Intent intent = getIntent();
		query = intent.getStringExtra("query");

		initializeSwipeRefresh(
				(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
						.getChildAt(0), new OnRefreshListener() {

					@Override
					public void onRefresh() {
						new SportsCourseFinder(
								(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
										.getChildAt(0), true).execute();
					}

				});

		(new SportsCourseFinder(
				(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
						.getChildAt(0))).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search_sports, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected class SportsCourseFinder extends
			SwipeRefreshAsyncDataLoader<List<SportsCourseCategory>> {
		private boolean forceRefresh = false;

		private SportsCourseFinder(SwipeRefreshLayout rootView) {
			super(rootView);
		}

		private SportsCourseFinder(SwipeRefreshLayout rootView,
				boolean forceRefresh) {
			super(rootView);
			this.forceRefresh = forceRefresh;
		}

		@Override
		protected List<SportsCourseCategory> doInBackground(Void... unused) {
			showLoadingAnimation(true);
			AccessFacade accessFacade = new AccessFacade();

			List<SportsCourseCategory> sportsCourseCategories = accessFacade
					.getSportsCourseAccess().find(query);

			return sportsCourseCategories;
		}

		@Override
		protected void onPostExecute(
				List<SportsCourseCategory> sportsCourseCategories) {
			super.onPostExecute(sportsCourseCategories);
			if (sportsCourseCategories != null) {
				ListView sportsoffer = (ListView) findViewById(R.id.list);

				final ArrayList<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();

				for (SportsCourseCategory category : sportsCourseCategories) {
					HashMap<String, String> listEntry = new HashMap<String, String>();
					listEntry.put("id", category.getId());
					listEntry.put("title", category.getTitle());
					categoryList.add(listEntry);
				}

				SimpleAdapter adapter = new SimpleAdapter(
						getApplicationContext(), categoryList,
						R.layout.row_threeline, new String[] { "title", },
						new int[] { R.id.title });

				sportsoffer.setAdapter(adapter);

				sportsoffer.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						displaySportsCourses(
								categoryList.get(position).get("id"),
								categoryList.get(position).get("title"));
					}
				});
			}
		}
	}

	private void displaySportsCourses(String categoryId, String title) {
		Intent intent = new Intent(getApplicationContext(),
				DisplaySportsCourseListActivity.class);
		intent.putExtra("categoryId", categoryId);
		intent.putExtra("title", title);
		intent.putExtra("offerTime", SportsFragment.AZ);
		startActivity(intent);

	}

}
