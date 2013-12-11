package de.uni_passau.facultyinfo.client.activity;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import de.uni_passau.facultyinfo.client.R;

public class BuslinesActivity extends Activity implements OnNavigationListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buslines);
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(false);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.spinner_menu_buslines,
				android.R.layout.simple_spinner_item);
		getActionBar().setSelectedNavigationItem(1);
		getActionBar().setListNavigationCallbacks(adapter, this);

	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.buslines, menu);
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
		Intent intent = new Intent(this, TimetableActivity.class);
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