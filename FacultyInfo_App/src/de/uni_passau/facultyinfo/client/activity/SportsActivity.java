package de.uni_passau.facultyinfo.client.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import de.uni_passau.facultyinfo.client.R;

public class SportsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sports);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sports, menu);
		return true;
	}

}
