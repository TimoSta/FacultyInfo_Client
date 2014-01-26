package de.uni_passau.facultyinfo.client.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.fragment.SportsFragment;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.SportsCourseCategory;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

public class SearchSportsActivity extends Activity {

	private String query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_sports);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);

		Intent intent = getIntent();
		query = intent.getStringExtra("query");

		(new SportsCourseFinder()).execute();
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
			AsyncDataLoader<List<SportsCourseCategory>> {

		@Override
		protected List<SportsCourseCategory> doInBackground(Void... unused) {

			AccessFacade accessFacade = new AccessFacade();

			List<SportsCourseCategory> sportsCourseCategories = accessFacade
					.getSportsCourseAccess().find(query);

			return sportsCourseCategories;
		}

		@Override
		protected void onPostExecute(
				List<SportsCourseCategory> sportsCourseCategories) {
			if (sportsCourseCategories != null) {
				ListView sportsoffer = (ListView) findViewById(R.id.sports_search_result);

				final ArrayList<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();

				for (SportsCourseCategory category : sportsCourseCategories) {
					HashMap<String, String> listEntry = new HashMap<String, String>();
					listEntry.put("id", category.getId());
					listEntry.put("title", category.getTitle());
					categoryList.add(listEntry);
				}

				SimpleAdapter adapter = new SimpleAdapter(
						getApplicationContext(), categoryList,
						R.layout.custom_row_view, new String[] { "title", },
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
