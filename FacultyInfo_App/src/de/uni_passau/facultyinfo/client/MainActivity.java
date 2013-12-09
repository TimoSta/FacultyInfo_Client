package de.uni_passau.facultyinfo.client;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(false);
		
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.spinner_menu, android.R.layout.simple_spinner_item);
		getActionBar().setListNavigationCallbacks(adapter, null);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
