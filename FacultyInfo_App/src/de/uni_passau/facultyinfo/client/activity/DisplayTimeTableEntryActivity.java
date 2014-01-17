package de.uni_passau.facultyinfo.client.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.application.FacultyInfoApplication;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.TimetableEntry;

public class DisplayTimeTableEntryActivity extends Activity {
	private int timeslotId;
	private int dayId;
	private String entryId;
	private int colorId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_time_table_entry);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);

		Intent intent = (Intent) getIntent();
		timeslotId = intent.getIntExtra("timeslotId", 0);
		dayId = intent.getIntExtra("dayId", 0);
		String day = "";
		String timeslot = "";

		if (dayId == TimetableEntry.MONDAY) {
			day = "Montag, ";
		} else if (dayId == TimetableEntry.TUESDAY) {
			day = "Dienstag, ";
		} else if (dayId == TimetableEntry.WEDNESDAY) {
			day = "Mittwoch, ";
		} else if (dayId == TimetableEntry.THURSDAY) {
			day = "Donnerstag, ";
		} else if (dayId == TimetableEntry.FRIDAY) {
			day = "Freitag, ";
		}

		if (timeslotId == TimetableEntry.FROM_08_TO_10) {
			timeslot = "08:00-10:00";
		} else if (timeslotId == TimetableEntry.FROM_10_TO_12) {
			timeslot = "10:00-12:00";
		} else if (timeslotId == TimetableEntry.FROM_12_TO_14) {
			timeslot = "12:00-14:00";
		} else if (timeslotId == TimetableEntry.FROM_14_TO_16) {
			timeslot = "14:00-16:00";
		} else if (timeslotId == TimetableEntry.FROM_16_TO_18) {
			timeslot = "16:00-18:00";
		} else if (timeslotId == TimetableEntry.FROM_18_TO_20) {
			timeslot = "18:00-20:00";
		}

		TextView timeTextView = (TextView) findViewById(R.id.timeTTdisplay);
		timeTextView.setText(day + timeslot);

	}

	@Override
	protected void onResume() {
		System.out.println("onResume");
		(new TimetableEntryLoader()).execute();
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_time_table_entry, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_edit:
			Intent intent = new Intent(getApplicationContext(),
					EditTimeTableActivity.class);
			intent.putExtra("dayId", dayId);
			intent.putExtra("timeslotId", timeslotId);
			intent.putExtra("new", false);
			startActivity(intent);
			return true;
		case R.id.action_delete:
			(new TimetableEntrySaver()).execute(); 
		case android.R.id.home:
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected class TimetableEntryLoader extends
			AsyncTask<Void, Void, List<TimetableEntry>> {

		private AccessFacade accessFacade;

		@Override
		protected List<TimetableEntry> doInBackground(Void... unused) {
			// AccessFacade accessFacade = new AccessFacade();
			accessFacade = new AccessFacade();

			List<TimetableEntry> timetableEntries = accessFacade
					.getTimetableAccess().getTimetableEntries();

			return timetableEntries;
		}

		@Override
		protected void onPostExecute(List<TimetableEntry> timetableEntries) {
			for (TimetableEntry timetableEntry : timetableEntries) {
				System.out.println("onPostExecute->for");
				if (dayId == timetableEntry.getDayOfWeek()
						&& timeslotId == timetableEntry.getTime()) {
					System.out.println("onPostExecute->if");
					EditText titleEditText = (EditText) findViewById(R.id.veranstaltungddisplay);
					titleEditText.setText(timetableEntry.getTitle());
					titleEditText.setKeyListener(null);

					EditText locationEditText = (EditText) findViewById(R.id.locationddisplay);
					locationEditText.setText(timetableEntry.getLocation());
					locationEditText.setKeyListener(null);

					EditText descriptionEditText = (EditText) findViewById(R.id.descriptionddisplay);
					descriptionEditText
							.setText(timetableEntry.getDescription());
					descriptionEditText.setKeyListener(null);

					colorId = timetableEntry.getColor();
					entryId = timetableEntry.getId();
				}
			}
		}
	}

	protected class TimetableEntrySaver extends
			AsyncTask<TimetableEntry, Void, Boolean> {
		// View rootView = null;
		//
		// public TimetableEntrySaver(View rootView) {
		// super();
		// this.rootView = rootView;
		// }

		@Override
		protected Boolean doInBackground(TimetableEntry... timetableEntries) {
			AccessFacade accessFacade = new AccessFacade();

//			boolean result = true;
//			for (TimetableEntry timetableEntry : timetableEntries) {
//				result = accessFacade.getTimetableAccess()
//						.deleteTimetableEntry(entryId) && result;
//			}
			boolean result = accessFacade.getTimetableAccess()
					.deleteTimetableEntry(entryId);

			return result;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				Toast toast = Toast.makeText(
						FacultyInfoApplication.getContext(),
						"Termin erfolgreich gelöscht", Toast.LENGTH_SHORT);
				toast.show();
				Intent intent = new Intent(getApplicationContext(),
						DisplayDayActivity.class);
				// if(toOverview){
				// intent.putExtra("displayDay", true);
				// }
				int day = TimetableEntry.MONDAY;
				if (dayId == TimetableEntry.MONDAY) {
					day = 0;
				} else if (dayId == TimetableEntry.TUESDAY) {
					day = 1;
				} else if (dayId == TimetableEntry.WEDNESDAY) {
					day = 2;
				} else if (dayId == TimetableEntry.THURSDAY) {
					day = 3;
				} else if (dayId == TimetableEntry.FRIDAY) {
					day = 4;
				}
				
				
				

				intent.putExtra("dayId", day);
				intent.putExtra("timeslotId", timeslotId);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			} else {
				Toast toast = Toast.makeText(
						FacultyInfoApplication.getContext(),
						"Fehler beim Löschen", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}
}
