package de.uni_passau.facultyinfo.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;

public class EditTimeTableActivity extends Activity {

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

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_time_table, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_save: 
			System.out.println("Save"); 
			
			EditText editTextTitle = (EditText)findViewById(R.id.veranstaltungd); 
			String title = editTextTitle.getText().toString();
			System.out.println(title); 
			//setTitle(title); 
			
			EditText editTextLocation = (EditText)findViewById(R.id.locationd); 
			String location = editTextLocation.getText().toString(); 
			System.out.println(location); 
			//setLocation(location); 
			
			return true; 
		default:
            return super.onOptionsItemSelected(item);
	    }
	}

}
