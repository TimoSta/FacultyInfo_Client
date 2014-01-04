package de.uni_passau.facultyinfo.client.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.application.FacultyInfoApplication;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.TimetableEntry;
import de.uni_passau.facultyinfo.client.model.dto.factory.TimetableEntryFactory;

public class EditTimeTableActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_time_table);

		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);

		Intent intent = (Intent) getIntent();
		int timeslotId = intent.getIntExtra("timeslotId", 0);
		System.out.println(intent.getIntExtra("timeslotId", 0));
		int dayId = intent.getIntExtra("dayId", 0);
		System.out.println(intent.getIntExtra("dayId", 0));
		String day = "";
		String timeslot = "";

		if (dayId == 0) {
			day = "Montag, ";
		} else if (dayId == 1) {
			day = "Dienstag, ";
		} else if (dayId == 2) {
			day = "Mittwoch, ";
		} else if (dayId == 3) {
			day = "Donnerstag, ";
		} else if (dayId == 4) {
			day = "Freitag, ";
		}

		if (timeslotId == 810) {
			timeslot = "08:00-10:00";
		} else if (timeslotId == 1012) {
			timeslot = "10:00-12:00";
		} else if (timeslotId == 1214) {
			timeslot = "12:00-14:00";
		} else if (timeslotId == 1416) {
			timeslot = "14:00-16:00";
		} else if (timeslotId == 1618) {
			timeslot = "16:00-18:00";
		} else if (timeslotId == 1820) {
			timeslot = "18:00-20:00";
		}

		TextView timeTextView = (TextView) findViewById(R.id.timeTT);
		timeTextView.setText(day + timeslot);

		// ArrayAdapter<CharSequence> dayAdapter =
		// ArrayAdapter.createFromResource(
		// this, R.array.day_values,
		// android.R.layout.simple_spinner_item);
		//
		// Spinner daySpinner = (Spinner) findViewById(R.id.daySpinner);
		// daySpinner.setAdapter(dayAdapter);

		// TODO: nur ein Beispiel, wie das Speichern von Terminen funktioniert
		TimetableEntrySaver saver = new TimetableEntrySaver();
		TimetableEntry entry = TimetableEntryFactory.createTimetableEntry(
				"Testeintrag", "Dies ist nur ein Testeintrag. Bla.",
				"Café Aran", TimetableEntry.MONDAY,
				TimetableEntry.FROM_10_TO_12, TimetableEntry.COLOR_12);
		saver.execute(entry);
		// ENDE Beispiel

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_time_table, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_save:
			System.out.println("Save");

			EditText editTextTitle = (EditText) findViewById(R.id.veranstaltungd);
			String title = editTextTitle.getText().toString();
			System.out.println(title);

			if (title.isEmpty()) {
				Toast toast = Toast
						.makeText(
								getApplicationContext(),
								"Die Veranstaltung konnte nicht gespeichert werden, da kein Veranstaltungstitel vergeben wurde!",
								10);
				toast.show();
			}
			// setTitle(title);

			EditText editTextLocation = (EditText) findViewById(R.id.locationd);
			String location = editTextLocation.getText().toString();
			if (location.isEmpty()) {
				final class CreateEventTT extends DialogFragment {
					@Override
					public Dialog onCreateDialog(Bundle savedInstanceState) {
						// Use the Builder class for convenient dialog
						// construction
						AlertDialog.Builder builder = new AlertDialog.Builder(
								getActivity());
						builder.setMessage(
								R.string.create_event_no_location_dialog)
								.setPositiveButton(R.string.create2,
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
												System.out
														.println("Veranstaltung anlegen");
											}
										})
								.setNegativeButton(R.string.cancel,
										new DialogInterface.OnClickListener() {
											public void onClick(
													DialogInterface dialog,
													int id) {
												System.out.println("Abbruch");
												// User cancelled the dialog
											}
										});
						// Create the AlertDialog object and return it
						return builder.create();
					}
				}
				CreateEventTT dialog = new CreateEventTT();
				dialog.show(getSupportFragmentManager(),
						"createEventTTnoLocation");
			}

			System.out.println(location);
			// setLocation(location);

			return true;
		default:
			return super.onOptionsItemSelected(item);
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

			boolean result = true;
			for (TimetableEntry timetableEntry : timetableEntries) {
				result = accessFacade.getTimetableAccess()
						.createOrUpdateTimetableEntry(timetableEntry) && result;
			}

			return result;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			String resultText = null;
			if (result) {
				resultText = "Termin erfolgreich gespeichert";
			} else {
				resultText = "Fehler beim Speichern";
			}
			Toast toast = Toast.makeText(FacultyInfoApplication.getContext(),
					resultText, Toast.LENGTH_SHORT);
			toast.show();
		}
	}

}
