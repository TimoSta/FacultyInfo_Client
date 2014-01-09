package de.uni_passau.facultyinfo.client.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import de.uni_passau.facultyinfo.client.R;
//import de.uni_passau.facultyinfo.client.fragment.NewsFragment.NewsLoader;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.SportsCourse;
import de.uni_passau.facultyinfo.client.model.dto.SportsCourseCategory;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

public class DisplaySportCoursesActivity extends Activity {

	private String categoryId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_sport_courses);

		Intent intent = getIntent();
		categoryId = intent.getStringExtra("categoryId");
		
		//(new SportsCourseLoader()).execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_sport_courses, menu);
		return true;
	}

	protected class SportsCourseLoader extends
			AsyncDataLoader<SportsCourseCategory> {
		private SportsCourseLoader(View rootView) {
			super(rootView);
		}

		@Override
		protected SportsCourseCategory doInBackground(Void... unused) {
			System.out.println("doInBackground"); 
			AccessFacade accessFacade = new AccessFacade();

			SportsCourseCategory sportsCourseCategory = accessFacade
					.getSportsCourseAccess().getCategory(categoryId);
			if (sportsCourseCategory == null) {
				// publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
				sportsCourseCategory = accessFacade.getSportsCourseAccess().getCategoryTodayFromCache(categoryId); 
			}

			// if (sportsCourse == null) {
			// sportsCourse = Collections
			// .unmodifiableList(new ArrayList<SportsCourseCategory>());
			// }

			return sportsCourseCategory;
		}

		@Override
		protected void onPostExecute(SportsCourseCategory sportsCourseCategory) {
			ListView sportsoffer = (ListView) rootView
					.findViewById(R.id.sportsCourses);

			final ArrayList<HashMap<String, String>> courseList = new ArrayList<HashMap<String, String>>();

			for (SportsCourse course : sportsCourseCategory.getSportsCourses()) {

				HashMap<String, String> temp1 = new HashMap<String, String>();
				temp1.put("id", course.getId());
				temp1.put("number", course.getNumber());
				temp1.put("details", course.getDetails());
				
				String dayOfWeek="Fehler"; 
				switch(course.getDayOfWeek()){
				case 1: 
					dayOfWeek="Mo"; 
					break; 
				case 2: 
					dayOfWeek="Di"; 
					break; 
				case 3: 
					dayOfWeek="Mi"; 
					break; 
				case 4: 
					dayOfWeek="Do"; 
					break; 
				case 5: 
					dayOfWeek="Fr"; 
					break;
				case 6: 
					dayOfWeek="Sa"; 
					break; 
				case 7: 
					dayOfWeek="So"; 
					break; 
				}
				temp1.put("dayOfWeek", dayOfWeek); 
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",
						Locale.GERMAN);
				temp1.put("startTime", sdf.format(course.getStartTime()));
				temp1.put("endTime", sdf.format(course.getEndTime()));
				courseList.add(temp1);
			}

			SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
					courseList, R.layout.course_view, new String[] { "numer",
							"details", "dayOfWeek"+ " " + "startTime" + "-" + "endTime" },
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
		startActivity(intent);

	}
}
