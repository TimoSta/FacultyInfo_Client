package de.uni_passau.facultyinfo.client.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.R.layout;
import de.uni_passau.facultyinfo.client.R.menu;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.SportsCourse;
import de.uni_passau.facultyinfo.client.model.dto.SportsCourseCategory;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class DisplaySportCoursesActivity extends Activity {

	private String categoryId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_sport_courses);

		Intent intent = getIntent();
		categoryId = intent.getStringExtra("categoryId");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_sport_courses, menu);
		return true;
	}

//	protected class SportsCourseLoader extends
//			AsyncDataLoader<List<SportsCourse>> {
//		private SportsCourseLoader(View rootView) {
//			super(rootView);
//		}
//
//		@Override
//		protected List<SportsCourse> doInBackground(Void... unused) {
//			AccessFacade accessFacade = new AccessFacade();
//
//			List<SportsCourse> sportsCourse = accessFacade
//					.getSportsCourseAccess().get;
//
//			if (sportsCourse == null) {
//				publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
//				sportsCourseCategories = accessFacade.getSportsCourseAccess()
//						.getSportsCourses();
//			}
//
//			if (sportsCourseCategories == null) {
//				sportsCourseCategories = Collections
//						.unmodifiableList(new ArrayList<SportsCourseCategory>());
//			}
//
//			return sportsCourseCategories;
//		}
//
//		@Override
//		protected void onPostExecute(
//				List<SportsCourseCategory> sportsCourseCategories) {
//			ListView sportsoffer = (ListView) rootView
//					.findViewById(R.id.sportsoffer);
//
//			final ArrayList<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();
//
//			for (SportsCourseCategory category : sportsCourseCategories) {
//
//				HashMap<String, String> temp1 = new HashMap<String, String>();
//				temp1.put("id", category.getId());
//				temp1.put("title", category.getTitle());
//				categoryList.add(temp1);
//			}
//
//			SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
//					categoryList, R.layout.custom_row_view,
//					new String[] { "title", }, new int[] { R.id.title });
//
//			sportsoffer.setAdapter(adapter);
//		}
//	}

}
