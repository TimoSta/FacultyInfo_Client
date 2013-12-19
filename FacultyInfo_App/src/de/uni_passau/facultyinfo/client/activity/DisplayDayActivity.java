package de.uni_passau.facultyinfo.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import de.uni_passau.facultyinfo.client.R;

public class DisplayDayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_day);
		System.out.println("DisplayDayActivity -> onCreate"); 

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);

		Intent intent = getIntent();
		int dayId = intent.getIntExtra("dayId", 0);
		System.out.println("getExtra"); 
		
		if(dayId==0){
			System.out.println("Montag");
		}else if(dayId==1){
			System.out.println("Dienstag");
		}else if(dayId==2){
			System.out.println("Mittwoch");
		}else if(dayId==3){
			System.out.println("Donnerstag");
		}else if(dayId==4){
			System.out.println("Freitag");
		}
		
		
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_day, menu);
		return true;
	}

}
