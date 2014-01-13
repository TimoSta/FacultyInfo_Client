package de.uni_passau.facultyinfo.client.activity;

import java.util.ArrayList;
import java.util.HashMap;

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
import de.uni_passau.facultyinfo.client.model.dto.SportsCourse;
import de.uni_passau.facultyinfo.client.model.dto.SportsCourseCategory;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

public class DisplaySportCoursesActivity extends Activity {

	private String categoryId;
	private String offerTime;
	private String title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_sport_courses);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);

		Intent intent = getIntent();
		categoryId = intent.getStringExtra("categoryId");
		System.out.println(categoryId);
		offerTime = intent.getStringExtra("offerTime");
		System.out.println(offerTime);

		title = intent.getStringExtra("title");
		setTitle(title);

		if (offerTime.equals(SportsFragment.AZ)) {
			System.out.println("AZ");
			(new SportsCourseLoader()).execute();
		} else if (offerTime.equals(SportsFragment.TODAY)) {
			System.out.println("today");
			(new TodaySportsCourseLoader()).execute();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
			AsyncDataLoader<SportsCourseCategory> {

		@Override
		protected SportsCourseCategory doInBackground(Void... unused) {
			System.out.println("doInBackground");
			AccessFacade accessFacade = new AccessFacade();

			SportsCourseCategory sportsCourseCategory = accessFacade
					.getSportsCourseAccess().getCategory(categoryId);
			if (sportsCourseCategory == null) {
				// publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
				sportsCourseCategory = accessFacade.getSportsCourseAccess()
						.getCategoryFromCache(categoryId);
			}

			// if (sportsCourse == null) {
			// sportsCourse = Collections
			// .unmodifiableList(new ArrayList<SportsCourseCategory>());
			// }

			return sportsCourseCategory;
		}

		@Override
		protected void onPostExecute(SportsCourseCategory sportsCourseCategory) {
			ListView sportsoffer = (ListView) findViewById(R.id.sportsCourses);

			if (sportsCourseCategory == null) {
				System.out.println("sportsCourseCategory==null");
			} else {
				System.out.println("sportsCourseCategory!=null");
			}

			final ArrayList<HashMap<String, String>> courseList = new ArrayList<HashMap<String, String>>();

			for (SportsCourse course : sportsCourseCategory.getSportsCourses()) {

				HashMap<String, String> temp1 = new HashMap<String, String>();
				temp1.put("id", course.getId());
				temp1.put("number", course.getNumber());
				System.out.println("Number: " + course.getNumber());
				String details = course.getDetails();
				if (details == null || details.isEmpty()) {
					details = "Keine Beschreibung verfügbar";
				}
				temp1.put("details", details);

				String dayOfWeek = null;
				switch (course.getDayOfWeek()) {
				case SportsCourse.MONDAY:
					dayOfWeek = "Mo";
					break;
				case SportsCourse.TUESDAY:
					dayOfWeek = "Di";
					break;
				case SportsCourse.WEDNESDAY:
					dayOfWeek = "Mi";
					break;
				case SportsCourse.THURSDAY:
					dayOfWeek = "Do";
					break;
				case SportsCourse.FRIDAY:
					dayOfWeek = "Fr";
					break;
				case SportsCourse.SATURDAY:
					dayOfWeek = "Sa";
					break;
				case SportsCourse.SUNDAY:
					dayOfWeek = "So";
					break;
				}

				if (dayOfWeek != null) {
					String time = dayOfWeek + " "
							+ course.getStartTime().toString() + "-"
							+ course.getEndTime().toString();
					temp1.put("time", time);
				}

				courseList.add(temp1);
			}

			SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
					courseList, R.layout.course_view, new String[] { "number",
							"details", "time" },
					new int[] { R.id.number, R.id.details_sports_course,
							R.id.time_sports_course });

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

	protected class TodaySportsCourseLoader extends
			AsyncDataLoader<SportsCourseCategory> {
		// private SportsCourseLoader(View rootView) {
		// super(rootView);
		// }

		@Override
		protected SportsCourseCategory doInBackground(Void... unused) {
			System.out.println("doInBackground");
			AccessFacade accessFacade = new AccessFacade();

			SportsCourseCategory sportsCourseCategory = accessFacade
					.getSportsCourseAccess().getCategoryToday(categoryId);
			if (sportsCourseCategory == null) {
				// publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
				sportsCourseCategory = accessFacade.getSportsCourseAccess()
						.getCategoryTodayFromCache(categoryId);
			}

			// if (sportsCourse == null) {
			// sportsCourse = Collections
			// .unmodifiableList(new ArrayList<SportsCourseCategory>());
			// }

			return sportsCourseCategory;
		}

		@Override
		protected void onPostExecute(SportsCourseCategory sportsCourseCategory) {
			ListView sportsoffer = (ListView) findViewById(R.id.sportsCourses);

			if (sportsCourseCategory == null) {
				System.out.println("sportsCourseCategory==null");
			} else {
				System.out.println("sportsCourseCategory!=null");
			}

			final ArrayList<HashMap<String, String>> courseList = new ArrayList<HashMap<String, String>>();

			for (SportsCourse course : sportsCourseCategory.getSportsCourses()) {

				HashMap<String, String> temp1 = new HashMap<String, String>();
				temp1.put("id", course.getId());
				temp1.put("number", course.getNumber());
				System.out.println("Number: " + course.getNumber());
				String details = course.getDetails();
				if (details == null || details.isEmpty()) {
					details = "Keine Beschreibung verfügbar";
				}
				temp1.put("details", details);

				String dayOfWeek = null;
				switch (course.getDayOfWeek()) {
				case SportsCourse.MONDAY:
					dayOfWeek = "Mo";
					break;
				case SportsCourse.TUESDAY:
					dayOfWeek = "Di";
					break;
				case SportsCourse.WEDNESDAY:
					dayOfWeek = "Mi";
					break;
				case SportsCourse.THURSDAY:
					dayOfWeek = "Do";
					break;
				case SportsCourse.FRIDAY:
					dayOfWeek = "Fr";
					break;
				case SportsCourse.SATURDAY:
					dayOfWeek = "Sa";
					break;
				case SportsCourse.SUNDAY:
					dayOfWeek = "So";
					break;
				}

				if (dayOfWeek != null) {
					String time = dayOfWeek + " "
							+ course.getStartTime().toString() + "-"
							+ course.getEndTime().toString();
					temp1.put("time", time);
				}

				courseList.add(temp1);
			}

			SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
					courseList, R.layout.course_view, new String[] { "number",
							"details", "time" },
					new int[] { R.id.number, R.id.details_sports_course,
							R.id.time_sports_course });

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

	protected void displaySportsCourse(String courseId) {
		Intent intent = new Intent(getApplicationContext(),
				DisplaySportsCourseActivity.class);
		intent.putExtra("courseId", courseId);
		intent.putExtra("title", title);
		startActivity(intent);

	}
}
