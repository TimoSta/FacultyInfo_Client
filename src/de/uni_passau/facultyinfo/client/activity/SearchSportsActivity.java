package de.uni_passau.facultyinfo.client.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.application.FacultyInfoApplication;
import de.uni_passau.facultyinfo.client.fragment.SportsFragment;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.SportsCourseSearchResult;
import de.uni_passau.facultyinfo.client.util.SwipeRefreshAsyncDataLoader;

public class SearchSportsActivity extends SwipeRefreshLayoutActivity {

	private String query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_list);

		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.searchResults);
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
										.getChildAt(0)).execute();
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
			SwipeRefreshAsyncDataLoader<List<SportsCourseSearchResult>> {

		private SportsCourseFinder(SwipeRefreshLayout rootView) {
			super(rootView);
		}

		@Override
		protected List<SportsCourseSearchResult> doInBackground(Void... unused) {
			showLoadingAnimation(true);
			AccessFacade accessFacade = new AccessFacade();

			List<SportsCourseSearchResult> results = accessFacade
					.getSportsCourseAccess().find(query);

			return results;
		}

		@Override
		protected void onPostExecute(List<SportsCourseSearchResult> results) {
			super.onPostExecute(results);
			if (results != null && !results.isEmpty()) {
				ListView list = (ListView) findViewById(R.id.list);

				final ArrayList<HashMap<String, String>> listEntries = new ArrayList<HashMap<String, String>>();

				for (SportsCourseSearchResult result : results) {
					HashMap<String, String> listEntry = new HashMap<String, String>();
					listEntry.put("id", result.getId());
					listEntry.put("title", result.getTitle());
					listEntry.put("subtitle", result.getSubtitle());
					listEntry.put("supertitle", result.getSupertitle());
					listEntry.put("type", Integer.toString(result.getType()));
					listEntries.add(listEntry);
				}

				SimpleAdapter adapter = new SimpleAdapter(
						getApplicationContext(), listEntries,
						R.layout.row_threeline, null, null) {
					@Override
					public View getView(int position, View convertView,
							ViewGroup parent) {
						HashMap<String, String> entry = listEntries
								.get(position);
						LayoutInflater inflater = (LayoutInflater) getApplicationContext()
								.getSystemService(
										Context.LAYOUT_INFLATER_SERVICE);
						View rowView = inflater.inflate(R.layout.row_threeline,
								parent, false);

						TextView titleView = (TextView) rowView
								.findViewById(R.id.title);
						if (entry.get("title") == null) {
							titleView.setText(entry.get("supertitle"));
						} else {
							Spanned title = Html.fromHtml(entry.get("title")
									.replaceAll("(?i)(" + query + ")",
											"<font color='#FF8000'>$1</font>"));
							titleView.setText(title);
						}

						TextView subtitleView = (TextView) rowView
								.findViewById(R.id.description);
						if (entry.get("subtitle") != null) {
							Spanned subtitle = Html.fromHtml(entry.get(
									"subtitle").replaceAll(
									"(?i)(" + query + ")",
									"<font color='#FF8000'>$1</font>"));
							subtitleView.setText(subtitle);
						} else {
							subtitleView.setVisibility(View.GONE);
						}

						TextView typeView = (TextView) rowView
								.findViewById(R.id.header);
						if (entry
								.get("type")
								.equals(Integer
										.toString(SportsCourseSearchResult.CATEGORY))) {
							typeView.setText(R.string.SportsCourseCategory);
						} else {
							typeView.setText(R.string.SportsCourse);

							if (entry.get("supertitle") != null) {
								typeView.append(" " + entry.get("supertitle"));
							}
						}

						return rowView;
					}
				};

				list.setAdapter(adapter);

				list.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						HashMap<String, String> entry = listEntries
								.get(position);
						if (entry
								.get("type")
								.equals(Integer
										.toString(SportsCourseSearchResult.CATEGORY))) {
							displaySportsCourseCategory(entry.get("id"),
									entry.get("title"));
						} else {
							displaySportsCourse(entry.get("id"),
									entry.get("title"));
						}
					}
				});
			} else {
				Toast toast = Toast.makeText(
						FacultyInfoApplication.getContext(),
						R.string.noSearchResults, Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}

	private void displaySportsCourseCategory(String categoryId, String title) {
		Intent intent = new Intent(getApplicationContext(),
				DisplaySportsCourseListActivity.class);
		intent.putExtra("categoryId", categoryId);
		intent.putExtra("title", title);
		intent.putExtra("offerTime", SportsFragment.AZ);
		startActivity(intent);

	}

	private void displaySportsCourse(String courseId, String title) {
		Intent intent = new Intent(getApplicationContext(),
				DisplaySportsCourseActivity.class);
		intent.putExtra("courseId", courseId);
		intent.putExtra("title", title);
		startActivity(intent);

	}

}
