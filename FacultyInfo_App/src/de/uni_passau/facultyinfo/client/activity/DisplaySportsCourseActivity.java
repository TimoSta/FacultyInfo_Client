package de.uni_passau.facultyinfo.client.activity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.SportsCourse;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected class CourseLoader extends AsyncDataLoader<SportsCourse> {

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
			String detailsString = sportscourse.getDetails();
			if (detailsString == null || detailsString.isEmpty()) {
				detailsString = "Keine Beschreibung verfügbar";
			}
			details.setText(detailsString);

			TextView time = (TextView) findViewById(R.id.time_course);

			String dayOfWeek = null;
			switch (sportscourse.getDayOfWeek()) {
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

			String timeString = "";
			if (dayOfWeek != null) {
				timeString = dayOfWeek;
				if (sportscourse.getStartTime() != null) {
					timeString += " " + sportscourse.getStartTime().toString();
					if (sportscourse.getEndTime() != null) {
						timeString += " - "
								+ sportscourse.getEndTime().toString();
					}
				}

				time.setText(timeString);
			}

			if (sportscourse.getStartDate() != null
					&& sportscourse.getEndDate() != null) {
				TextView timeperiod = (TextView) findViewById(R.id.timeperiod);
				SimpleDateFormat sdf = new SimpleDateFormat(" d MMM ",
						Locale.GERMAN);
				timeperiod.setText("Zeitraum: "
						+ (sdf.format(sportscourse.getStartDate())) + "- "
						+ (sdf.format(sportscourse.getEndDate())));
			}

			TextView price = (TextView) findViewById(R.id.price);
			if (sportscourse.getPrice() > 0) {
				DecimalFormat df = new DecimalFormat("#.00",
						new DecimalFormatSymbols(Locale.GERMANY));
				price.setText("Kosten: " + df.format(sportscourse.getPrice())
						+ " Euro");
			} else {
				price.setText("Kosten: keine");
			}

			TextView status = (TextView) findViewById(R.id.status);
			String statusString = null;
			switch (sportscourse.getStatus()) {
			case SportsCourse.STATUS_OPEN:
				statusString = "Anmeldungen sind möglich";
				break;
			case SportsCourse.STATUS_CLOSED:
				statusString = "Anmeldungen sind nicht mehr möglich";
				break;
			case SportsCourse.STATUS_FULL:
				statusString = "Angebot ist ausgebucht";
				break;
			case SportsCourse.STATUS_OFFICE_SIGNUP:
				statusString = "Anmeldung erfolgt über das Büro";
				break;
			case SportsCourse.STATUS_STORNO:
				statusString += "Angebot wurde storniert";
				break;
			case SportsCourse.STATUS_QUEUE:
				statusString = "Warteliste";
				break;
			case SportsCourse.STATUS_NO_SIGNUP_REQUIRED:
				statusString = "Keine Anmeldung erforderlich";
				break;
			case SportsCourse.STATUS_NO_SIGNUP_POSSIBLE:
				statusString = "Keine Anmeldung möglich";
				break;
			default:
				statusString = "";
				break;
			}

			status.setText(statusString);

		}
	}
}
