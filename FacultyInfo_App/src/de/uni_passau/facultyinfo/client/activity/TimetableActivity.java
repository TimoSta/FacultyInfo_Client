package de.uni_passau.facultyinfo.client.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import de.uni_passau.facultyinfo.client.R;

public class TimetableActivity extends Activity implements OnNavigationListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timetable);
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(false);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.spinner_menu_timetable,
				android.R.layout.simple_spinner_item);
		getActionBar().setSelectedNavigationItem(1);
		getActionBar().setListNavigationCallbacks(adapter, this);
		
		
		
		
		final ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		
		GridView gridView = (GridView) findViewById(R.id.gridView1); 
		
		SimpleAdapter timeAdapter = new SimpleAdapter(this, list,
				R.layout.custom_row_view, new String[] { "title", "time",
						}, new int[] { R.id.title, R.id.time}

		);
		
		HashMap<String, String> temp0 = new HashMap<String, String>();
		temp0.put("title", "");
		list.add(temp0);
		
		HashMap<String, String> temp1 = new HashMap<String, String>();
		temp1.put("title", "Mo");
		list.add(temp1);
		
		HashMap<String, String> temp2 = new HashMap<String, String>();
		temp2.put("title", "Di");
		list.add(temp2);
		
		HashMap<String, String> temp3 = new HashMap<String, String>();
		temp3.put("title", "Mi");
		list.add(temp3);
		
		HashMap<String, String> temp4 = new HashMap<String, String>();
		temp4.put("title", "Do");
		list.add(temp4);
		
		HashMap<String, String> temp5 = new HashMap<String, String>();
		temp5.put("title", "Fr");
		list.add(temp5);
		
		HashMap<String, String> temp6 = new HashMap<String, String>();
		temp6.put("time", "8:00-10:00");
		list.add(temp6);
		
		for(int i=0; i<5; i++){
			HashMap<String, String> temp7 = new HashMap<String, String>();
			temp7.put("time", "");
			list.add(temp7);
		}
		
		HashMap<String, String> temp8 = new HashMap<String, String>();
		temp8.put("time", "8:00-10:00");
		list.add(temp8);
		
		for(int i=0; i<5; i++){
			HashMap<String, String> temp7 = new HashMap<String, String>();
			temp7.put("time", "");
			list.add(temp7);
		}
		
		HashMap<String, String> temp9 = new HashMap<String, String>();
		temp9.put("time", "8:00-10:00");
		list.add(temp9);
		
		for(int i=0; i<5; i++){
			HashMap<String, String> temp7 = new HashMap<String, String>();
			temp7.put("time", "");
			list.add(temp7);
		}
		
		HashMap<String, String> temp10 = new HashMap<String, String>();
		temp10.put("time", "10:00-12:00");
		list.add(temp6);
		
		for(int i=0; i<5; i++){
			HashMap<String, String> temp7 = new HashMap<String, String>();
			temp7.put("time", "");
			list.add(temp7);
		}
		
		HashMap<String, String> temp11 = new HashMap<String, String>();
		temp11.put("time", "12:00-14:00");
		list.add(temp11);
		
		for(int i=0; i<5; i++){
			HashMap<String, String> temp7 = new HashMap<String, String>();
			temp7.put("time", "");
			list.add(temp7);
		}
		
		HashMap<String, String> temp12 = new HashMap<String, String>();
		temp12.put("time", "14:00-16:00");
		list.add(temp12);
		
		for(int i=0; i<5; i++){
			HashMap<String, String> temp7 = new HashMap<String, String>();
			temp7.put("time", "");
			list.add(temp7);
		}
		
		HashMap<String, String> temp13 = new HashMap<String, String>();
		temp13.put("time", "16:00-18:00");
		list.add(temp13);
		
		for(int i=0; i<5; i++){
			HashMap<String, String> temp7 = new HashMap<String, String>();
			temp7.put("time", "");
			list.add(temp7);
		}
		
		HashMap<String, String> temp14 = new HashMap<String, String>();
		temp14.put("time", "18:00-20:00");
		list.add(temp14);
		
		for(int i=0; i<5; i++){
			HashMap<String, String> temp7 = new HashMap<String, String>();
			temp7.put("time", "");
			list.add(temp7);
		}


		gridView.setAdapter(timeAdapter);
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timetable, menu);
		return true;
	}

	
	
	
	
	
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {

		System.out.println("NewsActivity->onNavigationItemSelected->itemId");
		System.out.println(itemId);

		if (itemId == 1) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			return true;
		} else if (itemId == 2) {
			Intent intent = new Intent(this, NewsActivity.class);
			startActivity(intent);
			return true;
		} else if (itemId == 3) {
			Intent intent = new Intent(this, BuslinesActivity.class);
			startActivity(intent);
			return true;
		} else if (itemId == 4) {
			Intent intent = new Intent(this, SportsActivity.class);
			startActivity(intent);
			return true;
		} else if (itemId == 5) {
			Intent intent = new Intent(this, CafeteriaActivity.class);
			startActivity(intent);
			return true;
		} else if (itemId == 6) {
			Intent intent = new Intent(this, ContactsActivity.class);
			startActivity(intent);
			return true;
		} else if (itemId == 7) {
			Intent intent = new Intent(this, FAQsActivity.class);
			startActivity(intent);
			return true;
		}
		return false;
	}
	
	
	
}
