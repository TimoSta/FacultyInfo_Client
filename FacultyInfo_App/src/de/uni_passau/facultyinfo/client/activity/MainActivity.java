package de.uni_passau.facultyinfo.client.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.fragment.BusinessHoursFragment;
import de.uni_passau.facultyinfo.client.fragment.BuslinesFragment;
import de.uni_passau.facultyinfo.client.fragment.CafeteriaFragment;
import de.uni_passau.facultyinfo.client.fragment.ContactFragment;
import de.uni_passau.facultyinfo.client.fragment.EventFragment;
import de.uni_passau.facultyinfo.client.fragment.FaqsFragment;
import de.uni_passau.facultyinfo.client.fragment.HomeFragment;
import de.uni_passau.facultyinfo.client.fragment.MapFragment;
import de.uni_passau.facultyinfo.client.fragment.NewsFragment;
import de.uni_passau.facultyinfo.client.fragment.SportsFragment;
import de.uni_passau.facultyinfo.client.fragment.TimetableFragment;

public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private String[] drawerValues;

	private int selectedItem = 0;

	public final static int HOME = 0;
	public final static int TIMETABLE = 1;
	public final static int NEWS = 2;
	public final static int EVENTS = 3;
	public final static int MAP = 4;
	public final static int BUSLINES = 5;
	public final static int MENU = 6;
	public final static int SPORTS = 7;
	public final static int BUSINESSHOURS = 8;
	public final static int CONTACTS = 9;
	public final static int FAQ = 10;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();

		setContentView(R.layout.activity_main);

		drawerValues = getResources().getStringArray(R.array.drawer_values);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, drawerValues));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (intent == null) {
			selectItem(0);
		} else {
			selectItem(intent.getIntExtra("module", 0));
		}
	}

	@Override
	public void onBackPressed() {
		if (selectedItem == 0) {
			super.onBackPressed();
		} else {
			selectItem(0);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	public void selectItem(int position) {
		if (position == 0 || position != selectedItem) {
			Fragment fragment = null;

			switch (position) {
			case HOME:
				fragment = new HomeFragment();
				break;
			case NEWS:
				fragment = new NewsFragment();
				break;
			case TIMETABLE:
				fragment = new TimetableFragment();
				break;
			case BUSLINES:
				fragment = new BuslinesFragment();
				break;
			case SPORTS:
				fragment = new SportsFragment();
				break;
			case MENU:
				fragment = new CafeteriaFragment();
				break;
			case CONTACTS:
				fragment = new ContactFragment();
				break;
			case FAQ:
				fragment = new FaqsFragment();
				break;
			case MAP:
				fragment = new MapFragment();
				break;
			case BUSINESSHOURS:
				fragment = new BusinessHoursFragment();
				break;
			case EVENTS:
				fragment = new EventFragment();
				break;
			}

			android.app.FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.content_frame, fragment).commit();
			selectedItem = position;
			mDrawerList.setItemChecked(position, true);
		}
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	public void showDay(View view) {
		System.out.println(view.getId());
		int viewId = view.getId();
		int dayId = 0;

		if (viewId == (findViewById(R.id.mo).getId())
				|| viewId == (findViewById(R.id.mo810).getId())
				|| viewId == (findViewById(R.id.mo1012).getId())
				|| viewId == (findViewById(R.id.mo1214).getId())
				|| viewId == (findViewById(R.id.mo1416).getId())
				|| viewId == (findViewById(R.id.mo1618).getId())
				|| viewId == (findViewById(R.id.mo1820).getId())) {
			dayId = 0;
		} else if (viewId == (findViewById(R.id.di).getId())
				|| viewId == (findViewById(R.id.di810).getId())
				|| viewId == (findViewById(R.id.di1012).getId())
				|| viewId == (findViewById(R.id.di1214).getId())
				|| viewId == (findViewById(R.id.di1416).getId())
				|| viewId == (findViewById(R.id.di1618).getId())
				|| viewId == (findViewById(R.id.di1820).getId())) {
			dayId = 1;
		} else if (viewId == (findViewById(R.id.mi).getId())
				|| viewId == (findViewById(R.id.mi810).getId())
				|| viewId == (findViewById(R.id.mi1012).getId())
				|| viewId == (findViewById(R.id.mi1214).getId())
				|| viewId == (findViewById(R.id.mi1416).getId())
				|| viewId == (findViewById(R.id.mi1618).getId())
				|| viewId == (findViewById(R.id.mi1820).getId())) {
			dayId = 2;
		} else if (viewId == (findViewById(R.id.don).getId())
				|| viewId == (findViewById(R.id.don810).getId())
				|| viewId == (findViewById(R.id.don1012).getId())
				|| viewId == (findViewById(R.id.don1214).getId())
				|| viewId == (findViewById(R.id.don1416).getId())
				|| viewId == (findViewById(R.id.don1618).getId())
				|| viewId == (findViewById(R.id.don1820).getId())) {
			dayId = 3;
		} else if (viewId == (findViewById(R.id.fr).getId())
				|| viewId == (findViewById(R.id.fr810).getId())
				|| viewId == (findViewById(R.id.fr1012).getId())
				|| viewId == (findViewById(R.id.fr1214).getId())
				|| viewId == (findViewById(R.id.fr1416).getId())
				|| viewId == (findViewById(R.id.fr1618).getId())
				|| viewId == (findViewById(R.id.fr1820).getId())) {
			dayId = 4;
		}

		Intent intent = new Intent(this, DisplayDayActivity.class);

		intent.putExtra("dayId", dayId);
		startActivity(intent);
	}
}