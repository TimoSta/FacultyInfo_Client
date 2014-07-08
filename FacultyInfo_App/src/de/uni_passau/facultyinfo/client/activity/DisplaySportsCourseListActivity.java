package de.uni_passau.facultyinfo.client.activity;

import java.util.ArrayList;
import java.util.HashMap;

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
import de.uni_passau.facultyinfo.client.model.dto.SportsCourse;
import de.uni_passau.facultyinfo.client.model.dto.SportsCourseCategory;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;
import de.uni_passau.facultyinfo.client.util.SwipeRefreshAsyncDataLoader;

public class DisplaySportsCourseListActivity extends SwipeRefreshLayoutActivity {

	private String categoryId;
	private String offerTime;
	private String title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_list);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);

		Intent intent = getIntent();
		categoryId = intent.getStringExtra("categoryId");
		offerTime = intent.getStringExtra("offerTime");
		title = intent.getStringExtra("title");

		if (offerTime.equals(SportsFragment.AZ)) {
			setTitle(title);
			
			initializeSwipeRefresh(
					(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
							.getChildAt(0), new OnRefreshListener() {

						@Override
						public void onRefresh() {
							new SportsCourseLoader(
									(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
											.getChildAt(0), true).execute();
						}

					});
			(new SportsCourseLoader(
					(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
							.getChildAt(0))).execute();
		} else if (offerTime.equals(SportsFragment.TODAY)) {
			setTitle(title + " heute");
			initializeSwipeRefresh(
					(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
							.getChildAt(0), new OnRefreshListener() {

						@Override
						public void onRefresh() {
							new TodaySportsCourseLoader(
									(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
											.getChildAt(0), true).execute();
						}

					});
			(new TodaySportsCourseLoader(
					(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
							.getChildAt(0))).execute();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.display_sport_courses, menu);
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

	protected class SportsCourseLoader extends
			SwipeRefreshAsyncDataLoader<SportsCourseCategory> {
		private boolean forceRefresh = false;

		private SportsCourseLoader(SwipeRefreshLayout rootView) {
			super(rootView);
		}

		private SportsCourseLoader(SwipeRefreshLayout rootView,
				boolean forceRefresh) {
			super(rootView);
			this.forceRefresh = forceRefresh;
		}

		@Override
		protected SportsCourseCategory doInBackground(Void... unused) {
			showLoadingAnimation(true);

			AccessFacade accessFacade = new AccessFacade();

			SportsCourseCategory sportsCourseCategory = accessFacade
					.getSportsCourseAccess().getCategory(categoryId,
							forceRefresh);
			if (sportsCourseCategory == null) {
				publishProgress(AsyncDataLoader.NO_CONNECTION_PROGRESS);
				sportsCourseCategory = accessFacade.getSportsCourseAccess()
						.getCategoryFromCache(categoryId);
			}

			return sportsCourseCategory;
		}

		@Override
		protected void onPostExecute(SportsCourseCategory sportsCourseCategory) {
			super.onPostExecute(sportsCourseCategory);
			ListView sportsoffer = (ListView) findViewById(R.id.list);

			final ArrayList<HashMap<String, String>> courseList = new ArrayList<HashMap<String, String>>();

			for (SportsCourse course : sportsCourseCategory.getSportsCourses()) {

				HashMap<String, String> listEntry = new HashMap<String, String>();
				listEntry.put("id", course.getId());
				listEntry.put("number", course.getNumber());
				String details = course.getDetails();
				if (details == null || details.isEmpty()) {
					details = course.getCategory().getTitle();
				}
				listEntry.put("details", details);

				String dayOfWeek = null;
				int dayOfWeekCode = course.getDayOfWeek();
				if (dayOfWeekCode == SportsCourse.MONDAY) {
					dayOfWeek = "Mo";
				} else if (dayOfWeekCode == SportsCourse.TUESDAY) {
					dayOfWeek = "Di";
				} else if (dayOfWeekCode == SportsCourse.WEDNESDAY) {
					dayOfWeek = "Mi";
				} else if (dayOfWeekCode == SportsCourse.THURSDAY) {
					dayOfWeek = "Do";
				} else if (dayOfWeekCode == SportsCourse.FRIDAY) {
					dayOfWeek = "Fr";
				} else if (dayOfWeekCode == SportsCourse.SATURDAY) {
					dayOfWeek = "Sa";
				} else if (dayOfWeekCode == SportsCourse.SUNDAY) {
					dayOfWeek = "So";
				}

				if (dayOfWeek != null) {
					String time = dayOfWeek + " "
							+ course.getStartTime().toString() + "-"
							+ course.getEndTime().toString();
					listEntry.put("time", time);
				}

				courseList.add(listEntry);
			}

			SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
					courseList, R.layout.row_threeline, new String[] {
							"number", "details", "time" }, new int[] {
							R.id.header, R.id.title, R.id.description });

			sportsoffer.setAdapter(adapter);

			sportsoffer.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					displaySportsCourse(courseList.get(position).get("id"));
				}
			});
		}

	}

	protected class TodaySportsCourseLoader extends
			SwipeRefreshAsyncDataLoader<SportsCourseCategory> {
		private boolean forceRefresh = false;

		private TodaySportsCourseLoader(SwipeRefreshLayout rootView) {
			super(rootView);
		}

		private TodaySportsCourseLoader(SwipeRefreshLayout rootView,
				boolean forceRefresh) {
			super(rootView);
			this.forceRefresh = forceRefresh;
		}

		@Override
		protected SportsCourseCategory doInBackground(Void... unused) {
			showLoadingAnimation(true);

			AccessFacade accessFacade = new AccessFacade();

			SportsCourseCategory sportsCourseCategory = accessFacade
					.getSportsCourseAccess().getCategoryToday(categoryId,
							forceRefresh);
			if (sportsCourseCategory == null) {
				publishProgress(AsyncDataLoader.NO_CONNECTION_PROGRESS);
				sportsCourseCategory = accessFacade.getSportsCourseAccess()
						.getCategoryTodayFromCache(categoryId);
			}

			return sportsCourseCategory;
		}

		@Override
		protected void onPostExecute(SportsCourseCategory sportsCourseCategory) {
			super.onPostExecute(sportsCourseCategory);
			if (sportsCourseCategory != null) {
				ListView sportsoffer = (ListView) findViewById(R.id.list);

				final ArrayList<HashMap<String, String>> courseList = new ArrayList<HashMap<String, String>>();

				for (SportsCourse course : sportsCourseCategory
						.getSportsCourses()) {

					HashMap<String, String> listEntry = new HashMap<String, String>();
					listEntry.put("id", course.getId());
					listEntry.put("number", course.getNumber());
					String details = course.getDetails();
					if (details == null || details.isEmpty()) {
						details = "Keine Beschreibung verfügbar";
					}
					listEntry.put("details", details);

					String dayOfWeek = null;
					int dayOfWeekCode = course.getDayOfWeek();
					if (dayOfWeekCode == SportsCourse.MONDAY) {
						dayOfWeek = "Mo";
					} else if (dayOfWeekCode == SportsCourse.TUESDAY) {
						dayOfWeek = "Di";
					} else if (dayOfWeekCode == SportsCourse.WEDNESDAY) {
						dayOfWeek = "Mi";
					} else if (dayOfWeekCode == SportsCourse.THURSDAY) {
						dayOfWeek = "Do";
					} else if (dayOfWeekCode == SportsCourse.FRIDAY) {
						dayOfWeek = "Fr";
					} else if (dayOfWeekCode == SportsCourse.SATURDAY) {
						dayOfWeek = "Sa";
					} else if (dayOfWeekCode == SportsCourse.SUNDAY) {
						dayOfWeek = "So";
					}

					if (dayOfWeek != null) {
						String time = dayOfWeek + " "
								+ course.getStartTime().toString() + "-"
								+ course.getEndTime().toString();
						listEntry.put("time", time);
					}

					courseList.add(listEntry);
				}

				SimpleAdapter adapter = new SimpleAdapter(
						getApplicationContext(), courseList,
						R.layout.row_threeline, new String[] { "number",
								"details", "time" }, new int[] { R.id.header,
								R.id.title, R.id.description });

				sportsoffer.setAdapter(adapter);

				sportsoffer.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						System.out.println("click");
						System.out.println(position);
						displaySportsCourse(courseList.get(position).get("id"));
					}
				});
			}
		}

	}

	protected void displaySportsCourse(String courseId) {
		Intent intent = new Intent(getApplicationContext(),
				DisplaySportsCourseActivity.class);
		intent.putExtra("courseId", courseId);
		intent.putExtra("title", title);
		startActivity(intent);

	}
}
