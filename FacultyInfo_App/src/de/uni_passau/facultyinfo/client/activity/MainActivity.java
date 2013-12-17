package de.uni_passau.facultyinfo.client.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.fragment.BuslinesFragment;
import de.uni_passau.facultyinfo.client.fragment.CafeteriaFragment;
import de.uni_passau.facultyinfo.client.fragment.ContactFragment;
import de.uni_passau.facultyinfo.client.fragment.FaqsFragment;
import de.uni_passau.facultyinfo.client.fragment.NewsFragment;
import de.uni_passau.facultyinfo.client.fragment.SportsFragment;
import de.uni_passau.facultyinfo.client.fragment.TimetableFragment;

public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] drawerValues;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();
		drawerValues = getResources().getStringArray(R.array.drawer_values);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, drawerValues));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		// getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		// getActionBar().setDisplayHomeAsUpEnabled(false);
		// getActionBar().setDisplayShowHomeEnabled(true);
		// getActionBar().setDisplayShowTitleEnabled(false);
		//
		// ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
		// this, R.array.spinner_menu,
		// android.R.layout.simple_spinner_item);
		// getActionBar().setListNavigationCallbacks(adapter, this);
	}

	@Override
	protected void onResume() {

		isGooglePlayServicesAvailable();
		super.onResume();
	}

	private void isGooglePlayServicesAvailable() {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		Fragment fragment=null; 
		
		
		// update the main content by replacing fragments
		switch (position){
		case 0: 
			System.out.println("home"); 
			break; 
		case 1: 
			System.out.println("news"); 
			fragment = new NewsFragment(); 
			break; 
		case 2: 
			System.out.println("timetable"); 
			fragment = new TimetableFragment(); 
			break; 
		case 3: 
			System.out.println("busfahrplan"); 
			fragment = new BuslinesFragment(); 
			break; 
		case 4: 
			fragment = new SportsFragment(); 
			break; 
		case 5: 
			fragment = new CafeteriaFragment(); 
			break; 
		case 6: 
			fragment = new ContactFragment(); 
			break; 
		case 7: 
			fragment = new FaqsFragment(); 
			break; 
		}
		
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(drawerValues[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	/**
	 * Fragment that appears in the "content_frame", shows a planet
	 */
	


	

	
	
	

	// @Override
	// public boolean onNavigationItemSelected(int itemPosition, long itemId) {
	//
	// System.out.println("MainActivity->onNavigationItemSelected->itemId");
	// System.out.println(itemId);
	//
	// if (itemId == 1) {
	// System.out.println("itemId==1");
	// Intent intent = new Intent(this, NewsActivity.class);
	// startActivity(intent);
	// return true;
	// } else if (itemId == 2) {
	// Intent intent = new Intent(this, TimetableActivity.class);
	// startActivity(intent);
	// return true;
	// } else if (itemId == 3) {
	// Intent intent = new Intent(this, BuslinesActivity.class);
	// startActivity(intent);
	// return true;
	// } else if (itemId == 4) {
	// Intent intent = new Intent(this, SportsActivity.class);
	// startActivity(intent);
	// return true;
	// } else if (itemId == 5) {
	// Intent intent = new Intent(this, CafeteriaActivity.class);
	// startActivity(intent);
	// return true;
	// } else if (itemId == 6) {
	// Intent intent = new Intent(this, ContactsActivity.class);
	// startActivity(intent);
	// return true;
	// } else if (itemId == 7) {
	// Intent intent = new Intent(this, FAQsActivity.class);
	// startActivity(intent);
	// return true;
	// }
	// return false;
	// }

}