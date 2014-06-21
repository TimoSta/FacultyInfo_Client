package de.uni_passau.facultyinfo.client.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.fragment.DisplayDayFragment;
import de.uni_passau.facultyinfo.client.model.dto.TimetableEntry;

public class DisplayDayActivity extends FragmentActivity implements
		ActionBar.TabListener {

	AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_day);

		Intent intent = getIntent();
		int dayId = intent.getIntExtra("dayId", 0);

		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(
				getSupportFragmentManager());

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mAppSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		actionBar.setSelectedNavigationItem(dayId);
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

		public AppSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int item) {

			int day = TimetableEntry.MONDAY;
			if (item == 0) {
				day = TimetableEntry.MONDAY;
			} else if (item == 1) {
				day = TimetableEntry.TUESDAY;
			} else if (item == 2) {
				day = TimetableEntry.WEDNESDAY;
			} else if (item == 3) {
				day = TimetableEntry.THURSDAY;
			} else if (item == 4) {
				day = TimetableEntry.FRIDAY;
			}

			Fragment fragment = new DisplayDayFragment();
			Bundle args = new Bundle();

			args.putInt(DisplayDayFragment.ARG_DAY, day);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			String day = "";
			if (position == 0) {
				day = "Montag";
			} else if (position == 1) {
				day = "Dienstag";
			} else if (position == 2) {
				day = "Mittwoch";
			} else if (position == 3) {
				day = "Donnerstag";
			} else if (position == 4) {
				day = "Freitag";
			}
			return day;
		}
	}

	@Override
	public void onBackPressed(){
		Intent intent = new Intent(getApplicationContext(), MainActivity.class); 
		intent.putExtra("module", MainActivity.TIMETABLE); 
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.display_day, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
