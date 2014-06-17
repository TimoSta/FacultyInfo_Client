package de.uni_passau.facultyinfo.client.activity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.ActionBar;
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

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);

		Intent intent = getIntent();
		courseId = intent.getStringExtra("courseId");
		String title = intent.getStringExtra("title");

		setTitle(title);

		(new CourseLoader()).execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
			AccessFacade accessFacade = new AccessFacade();

			SportsCourse sportsCourse = accessFacade.getSportsCourseAccess()
					.getCourse(courseId);

			if (sportsCourse == null) {
				publishProgress(AsyncDataLoader.NO_CONNECTION_PROGRESS);
				sportsCourse = accessFacade.getSportsCourseAccess()
						.getCourseFromCache(courseId);
			}

			return sportsCourse;
		}

		@Override
		protected void onPostExecute(SportsCourse sportsCourse) {
			if (sportsCourse != null) {
				TextView number = (TextView) findViewById(R.id.number_course);
				number.setText(sportsCourse.getNumber());

				TextView details = (TextView) findViewById(R.id.description_course);
				String detailsString = sportsCourse.getDetails();
				if (detailsString == null || detailsString.isEmpty()) {
					detailsString = "Keine Beschreibung verfügbar";
				}
				details.setText(detailsString);

				TextView time = (TextView) findViewById(R.id.time_course);

				String dayOfWeek = null;
				int dayOfWeekCode = sportsCourse.getDayOfWeek();
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

				String timeString = "";
				if (dayOfWeek != null) {
					timeString = dayOfWeek;
					if (sportsCourse.getStartTime() != null) {
						timeString += " "
								+ sportsCourse.getStartTime().toString();
						if (sportsCourse.getEndTime() != null) {
							timeString += " - "
									+ sportsCourse.getEndTime().toString();
						}
					}

					time.setText(timeString);
				}

				if (sportsCourse.getStartDate() != null
						&& sportsCourse.getEndDate() != null) {
					TextView timeperiod = (TextView) findViewById(R.id.timeperiod);
					SimpleDateFormat sdf = new SimpleDateFormat(" d MMM ",
							Locale.GERMAN);
					timeperiod.setText("Zeitraum: "
							+ (sdf.format(sportsCourse.getStartDate())) + "- "
							+ (sdf.format(sportsCourse.getEndDate())));
				}

				TextView price = (TextView) findViewById(R.id.price);
				if (sportsCourse.getPrice() > 0) {
					DecimalFormat df = new DecimalFormat("#.00",
							new DecimalFormatSymbols(Locale.GERMANY));
					price.setText("Kosten: "
							+ df.format(sportsCourse.getPrice()) + " Euro");
				} else {
					price.setText("Kosten: keine");
				}

				TextView status = (TextView) findViewById(R.id.status);
				String statusString = null;
				switch (sportsCourse.getStatus()) {
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
}
