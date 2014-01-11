package de.uni_passau.facultyinfo.client.activity;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.R.id;
import de.uni_passau.facultyinfo.client.R.layout;
import de.uni_passau.facultyinfo.client.R.menu;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.SportsCourse;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

//import de.uni_passau.facultyinfo.client.fragment.NewsFragment.NewsLoader;

public class DisplaySportsCourseActivity extends Activity {

	private String courseId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_sports_course);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);

		Intent intent = getIntent();
		courseId = intent.getStringExtra("courseId");
		
		setTitle(intent.getStringExtra("title")); 

		(new CourseLoader()).execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_sports_course, menu);
		return true;
	}

	protected class CourseLoader extends AsyncDataLoader<SportsCourse> {
		// private CourseLoader(View rootView) {
		// super(rootView);
		// }

		@Override
		protected SportsCourse doInBackground(Void... unused) {
			System.out
					.println("TodaysSportsCourseCategoryLoader->doInBackground()");
			AccessFacade accessFacade = new AccessFacade();

			SportsCourse sportsCourse = accessFacade.getSportsCourseAccess()
					.getCourse(courseId);

			if (sportsCourse == null) {
				// publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
				sportsCourse = accessFacade.getSportsCourseAccess()
						.getCourseFromCache(courseId);
			}

			// if (sportsCourse == null) {
			// sportsCourse = Collections
			// .unmodifiableList(new ArrayList<SportsCourseCategory>());
			// }

			return sportsCourse;
		}

		@Override
		protected void onPostExecute(SportsCourse sportscourse) {
			TextView number = (TextView) findViewById(R.id.number_course);
			number.setText(sportscourse.getNumber());

			TextView details = (TextView) findViewById(R.id.description_course);
			details.setText(sportscourse.getDetails());

			TextView time = (TextView) findViewById(R.id.time_course);

			String dayOfWeek = "";
			switch (sportscourse.getDayOfWeek()) {
			case 1:
				dayOfWeek = "Mo";
				break;
			case 2:
				dayOfWeek = "Di";
				break;
			case 3:
				dayOfWeek = "Mi";
				break;
			case 4:
				dayOfWeek = "Do";
				break;
			case 5:
				dayOfWeek = "Fr";
				break;
			case 6:
				dayOfWeek = "Sa";
				break;
			case 7:
				dayOfWeek = "So";
				break;
			}

			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.GERMAN);
			// time.setText(dayOfWeek +
			// (sdf.format(sportscourse.getStartTime())) +
			// (sdf.format(sportscourse.getEndTime())) +" "+
			// sportscourse.getLocation());

//			String time_string = dayOfWeek + " "
//					+ sportscourse.getStartTime().getHour() + ":"
//					+ sportscourse.getStartTime().getMinute() + "-"
//					+ sportscourse.getEndTime().getHour() + "-"
//					+ sportscourse.getEndTime().getMinute();
			
			String time_string = dayOfWeek + " "
					+ sportscourse.getStartTime().toString() +  "-"
					+ sportscourse.getEndTime().toString();
			System.out.println(time_string);

			time.setText(time_string);

			TextView timeperiod = (TextView) findViewById(R.id.timeperiod);
			SimpleDateFormat sdf2 = new SimpleDateFormat(" d MMM ",
					Locale.GERMAN);
			timeperiod.setText("Zeitraum: "
					+ (sdf2.format(sportscourse.getStartDate())) + "- "
					+ (sdf2.format(sportscourse.getEndDate())));

			// timeperiod.setText(sportscourse.getStartDate().getDate() + "." +
			// sportscourse.getStartDate().getMonth() + "-" +
			// sportscourse.getEndDate().getDate() + "." +
			// sportscourse.getEndDate().getMonth());

			TextView price = (TextView) findViewById(R.id.price);
			price.setText("Kosten: " + sportscourse.getPrice());

			TextView status = (TextView) findViewById(R.id.status);

			String status_string = "Angebot ist zur Anmeldung";
			switch (sportscourse.getStatus()) {
			case 1:
				status_string += "offen";
				break;
			case 2:
				status_string += "geschlossen";
				break;
			case 3:
				status_string = "Angebot ist ausgebucht";
				break;
			case 4:
				status_string = "Angebot muss im Büro angemeldet werden";
				break;
			case 5:
				status_string += "Angebot ist storniert";
				break;
			case 6:
				status_string = "Warteliste";
				break;
			case 7:
				status_string = "Keine Anmeldung erforderlich";
				break;
			case 8:
				status_string = "Keine Anmeldung möglich";
				break;
			default:
				status_string = "";
				break; 
			}

			status.setText(status_string);

		}
	}
}
