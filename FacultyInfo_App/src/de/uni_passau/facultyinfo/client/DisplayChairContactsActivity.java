package de.uni_passau.facultyinfo.client;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class DisplayChairContactsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_chair_contacts);
		
		Intent intent = getIntent(); 
		String chairId = intent.getStringExtra("chairId"); 
		
		ListView listView = (ListView)findViewById(R.id.chairContacts);
		
		List valueList = new ArrayList<String>();

		for (int i = 0; i < 5; i++) {
			valueList.add("Lehrstuhl: " + chairId + " Kontakt " + i); 
		}
		
		ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, valueList);
		
		listView.setAdapter(adapter); 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_chair_contacts, menu);
		return true;
	}

}
