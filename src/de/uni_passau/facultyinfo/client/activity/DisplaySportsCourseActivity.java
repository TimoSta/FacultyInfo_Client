package de.uni_passau.facultyinfo.client.activity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.SportsCourse;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;
import de.uni_passau.facultyinfo.client.util.SwipeRefreshAsyncDataLoader;

public class DisplaySportsCourseActivity extends SwipeRefreshLayoutActivity {

	private String courseId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_threeline);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);

		Intent intent = getIntent();
		courseId = intent.getStringExtra("courseId");
		String title = intent.getStringExtra("title");

		setTitle(title);

		initializeSwipeRefresh(
				(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
						.getChildAt(0), new OnRefreshListener() {

					@Override
					public void onRefresh() {
						new CourseLoader(
								(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
										.getChildAt(0), true).execute();
					}

				});

		(new CourseLoader(
				(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
						.getChildAt(0))).execute();

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

	protected class CourseLoader extends
			SwipeRefreshAsyncDataLoader<SportsCourse> {
		private boolean forceRefresh = false;

		private CourseLoader(SwipeRefreshLayout rootView) {
			super(rootView);
		}

		private CourseLoader(SwipeRefreshLayout rootView, boolean forceRefresh) {
			super(rootView);
			this.forceRefresh = forceRefresh;
		}

		@Override
		protected SportsCourse doInBackground(Void... unused) {
			showLoadingAnimation(true);

			AccessFacade accessFacade = new AccessFacade();

			SportsCourse sportsCourse = accessFacade.getSportsCourseAccess()
					.getCourse(courseId, forceRefresh);

			if (sportsCourse == null) {
				publishProgress(AsyncDataLoader.NO_CONNECTION_PROGRESS);
				sportsCourse = accessFacade.getSportsCourseAccess()
						.getCourseFromCache(courseId);
			}

			return sportsCourse;
		}

		@Override
		protected void onPostExecute(SportsCourse sportsCourse) {
			super.onPostExecute(sportsCourse);
			if (sportsCourse != null) {
				// Course number
				TextView headerView = (TextView) findViewById(R.id.header);
				headerView.setText(sportsCourse.getNumber());

				// Course title
				TextView titleView = (TextView) findViewById(R.id.title);
				String detailsString = sportsCourse.getDetails();
				if (detailsString == null || detailsString.isEmpty()) {
					detailsString = "Keine Beschreibung verfügbar";
				}
				titleView.setText(detailsString);

				// Course day of week and time
				TextView timeView = (TextView) findViewById(R.id.description);
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
					timeView.setText(timeString);
				}

				// Course content
				ArrayList<String> contentParts = new ArrayList<String>();
				if (sportsCourse.getStartDate() != null
						&& sportsCourse.getEndDate() != null) {
					SimpleDateFormat sdf = new SimpleDateFormat(" d MMM ",
							Locale.GERMAN);
					contentParts.add("Zeitraum: "
							+ (sdf.format(sportsCourse.getStartDate())) + "- "
							+ (sdf.format(sportsCourse.getEndDate())));
				}

				if (sportsCourse.getPrice() != SportsCourse.PRICE_NOT_AVAILABLE) {
					if (sportsCourse.getPrice() > 0.0) {
						DecimalFormat df = new DecimalFormat("#.00",
								new DecimalFormatSymbols(Locale.GERMANY));
						contentParts.add("Kosten: "
								+ df.format(sportsCourse.getPrice()) + " Euro");
					} else {
						contentParts.add("Kosten: keine");
					}
				}

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
				contentParts.add(statusString);

				StringBuilder contentBuilder = new StringBuilder();
				boolean firstTime = true;
				for (String string : contentParts) {
					if (firstTime) {
						firstTime = false;
					} else {
						contentBuilder.append('\n');
					}
					contentBuilder.append(string);
				}
				TextView contentView = (TextView) rootView
						.findViewById(R.id.content);
				contentView.setText(contentBuilder.toString());
				contentView.setVisibility(View.VISIBLE);
			}
		}
	}
}
