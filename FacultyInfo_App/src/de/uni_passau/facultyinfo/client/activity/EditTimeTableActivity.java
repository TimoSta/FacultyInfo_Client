package de.uni_passau.facultyinfo.client.activity;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.application.FacultyInfoApplication;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.TimetableEntry;
import de.uni_passau.facultyinfo.client.model.dto.factory.TimetableEntryFactory;
import de.uni_passau.facultyinfo.client.model.dto.util.Color;
import de.uni_passau.facultyinfo.client.util.ColorHelper;

public class EditTimeTableActivity extends FragmentActivity {
	private int dayId;
	private int timeslotId;
	private int colorId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_time_table);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);

		Intent intent = (Intent) getIntent();
		timeslotId = intent.getIntExtra("timeslotId", 0);
		System.out.println(intent.getIntExtra("timeslotId", 0));
		dayId = intent.getIntExtra("dayId", 0);
		System.out.println(intent.getIntExtra("dayId", 0));
		colorId = intent.getIntExtra("colorId", Color.WHITE);
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

		TextView timeTextView = (TextView) findViewById(R.id.timeTT);
		timeTextView.setText(day + timeslot);

		if (!intent.getBooleanExtra("new", true)) {
			TimetableEntryLoader timetableEntryLoader = new TimetableEntryLoader();
			timetableEntryLoader.execute();
		}

		// Spinner colorSpinner = (Spinner) findViewById(R.id.colorSpinner);
		//
		// for (int i = 0; i < 12; i++) {
		// ShapeDrawable rect = new ShapeDrawable(new RectShape());
		// if (i == 0) {
		// // String color = TimetableEntry.COLOR_1;
		// rect.getPaint().setColor(Color.parseColor("#F5A9D0"));
		// } else if (i == 1) {
		// rect.getPaint().setColor(Color.parseColor("#E2A9F3"));
		// } else if (i == 2) {
		// rect.getPaint().setColor(Color.parseColor("#A9A9F5"));
		// } else if (i == 3) {
		// rect.getPaint().setColor(Color.parseColor("#A9E2F3"));
		// } else if (i == 4) {
		// rect.getPaint().setColor(Color.parseColor("#A9F5A9"));
		// }
		//
		// }

		final class ColorPickerDialogFragment extends DialogFragment {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				// Get the layout inflater
				LayoutInflater inflater = getActivity().getLayoutInflater();

				// Inflate and set the layout for the dialog
				// Pass null as the parent view because its going in the dialog
				// layout
				View colorPickerView = inflater.inflate(
						R.layout.popup_colorchoice, null);

				builder.setView(colorPickerView).setNegativeButton(
						R.string.cancel, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								ColorPickerDialogFragment.this.getDialog()
										.cancel();
							}
						});

				prepareButton(colorPickerView, R.id.color1, Color.WHITE);
				prepareButton(colorPickerView, R.id.color2, Color.BLACK);
				prepareButton(colorPickerView, R.id.color3, Color.RED);
				prepareButton(colorPickerView, R.id.color4, Color.BLUE);
				prepareButton(colorPickerView, R.id.color5, Color.GREEN);
				prepareButton(colorPickerView, R.id.color6, Color.YELLOW);
				prepareButton(colorPickerView, R.id.color7, Color.PURPLE);
				prepareButton(colorPickerView, R.id.color8, Color.GREY);
				prepareButton(colorPickerView, R.id.color9, Color.ORANGE);
				prepareButton(colorPickerView, R.id.color10, Color.DARKRED);
				prepareButton(colorPickerView, R.id.color11, Color.LIGHTBLUE);
				prepareButton(colorPickerView, R.id.color12, Color.PINK);

				return builder.create();
			}

			private void setColorAndClose(int color) {
				colorId = color;
				ColorPickerDialogFragment.this.getDialog().cancel();
				ColorHelper colorHelper = new ColorHelper();
				((Button) findViewById(R.id.colorButton))
						.setBackgroundColor(colorHelper.getColor(colorId)
								.getBackgroundColor());
			}

			private void prepareButton(View view, int buttonId, final int color) {
				ColorHelper colorHelper = new ColorHelper();
				Button color1 = (Button) view.findViewById(buttonId);
				color1.setBackgroundColor(colorHelper.getColor(color)
						.getBackgroundColor());
				color1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						setColorAndClose(color);
					}
				});
			}
		}

		final FragmentManager fragmentManager = this
				.getSupportFragmentManager();
		final Button button = (Button) findViewById(R.id.colorButton);
		button.setBackgroundColor(new ColorHelper().getColor(colorId)
				.getBackgroundColor());
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ColorPickerDialogFragment dialog = new ColorPickerDialogFragment();
				dialog.show(fragmentManager, "createEventTT");
			}
		});

		// ArrayAdapter<CharSequence> dayAdapter =
		// ArrayAdapter.createFromResource(
		// this, R.array.day_values,
		// android.R.layout.simple_spinner_item);
		//
		// Spinner daySpinner = (Spinner) findViewById(R.id.daySpinner);
		// daySpinner.setAdapter(dayAdapter);

		// TODO: nur ein Beispiel, wie das Speichern von Terminen funktioniert
		// TimetableEntrySaver saver = new TimetableEntrySaver();
		// TimetableEntry entry = TimetableEntryFactory.createTimetableEntry(
		// "Testeintrag", "Dies ist nur ein Testeintrag. Bla.",
		// "Café Aran", TimetableEntry.MONDAY,
		// TimetableEntry.FROM_10_TO_12, TimetableEntry.COLOR_12);
		// saver.execute(entry);
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

			EditText editTextLocation = (EditText) findViewById(R.id.locationd);
			String location = editTextLocation.getText().toString();

			if (title.isEmpty()) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"Veranstaltungstitel fehlt", Toast.LENGTH_SHORT);
				toast.show();
			} else if (location.isEmpty()) {
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
												save();
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
			} else {
				save();
			}

			System.out.println(location);
			// setLocation(location);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void save() {

		EditText editTextTitle = (EditText) findViewById(R.id.veranstaltungd);
		String title = editTextTitle.getText().toString();

		EditText editTextLocation = (EditText) findViewById(R.id.locationd);
		String location = editTextLocation.getText().toString();

		EditText editTextDescription = (EditText) findViewById(R.id.descriptiond);
		String description = editTextDescription.getText().toString();
		//
		// int time = 0;
		// if(timeslotId==810){
		// time = TimetableEntry.FROM_08_TO_10;
		// }else if(timeslotId==1012){
		// time = TimetableEntry.FROM_10_TO_12;
		// }else if(timeslotId==1214){
		// time = TimetableEntry.FROM_12_TO_14;
		// }else if(timeslotId==1416){
		// time = TimetableEntry.FROM_14_TO_16;
		// }else if(timeslotId==1618){
		// time = TimetableEntry.FROM_16_TO_18;
		// }else if(timeslotId==1820){
		// time = TimetableEntry.FROM_18_TO_20;
		// }

		TimetableEntrySaver saver = new TimetableEntrySaver();
		TimetableEntry entry = TimetableEntryFactory.createTimetableEntry(
				title, description, location, timeslotId, dayId, colorId);
		saver.execute(entry);

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
			if (result) {
				Toast toast = Toast.makeText(
						FacultyInfoApplication.getContext(),
						"Termin erfolgreich gespeichert", Toast.LENGTH_SHORT);
				toast.show();
				Intent intent = new Intent(getApplicationContext(),
						DisplayTimeTableEntryActivity.class);
				intent.putExtra("dayId", dayId);
				intent.putExtra("timeslotId", timeslotId);
				startActivity(intent);
			} else {
				Toast toast = Toast.makeText(
						FacultyInfoApplication.getContext(),
						"Fehler beim Speichern", Toast.LENGTH_SHORT);
				toast.show();
			}
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
				if (dayId == timetableEntry.getDayOfWeek()
						&& timeslotId == timetableEntry.getTime()) {
					EditText titleEditText = (EditText) findViewById(R.id.veranstaltungd);
					titleEditText.setText(timetableEntry.getTitle());

					EditText locationEditText = (EditText) findViewById(R.id.locationd);
					locationEditText.setText(timetableEntry.getLocation());

					EditText descriptionEditText = (EditText) findViewById(R.id.descriptiond);
					descriptionEditText
							.setText(timetableEntry.getDescription());
				}
			}
		}
	}

}
