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
import android.widget.TabHost;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.fragment.DisplayDayFragment;

public class DisplayDayActivity extends FragmentActivity implements
		ActionBar.TabListener {

	AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("DisplayDayActivity");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_day);
		System.out.println("DisplayDayActivity -> onCreate");

		Intent intent = getIntent();
		int dayId = intent.getIntExtra("dayId", 0);
		System.out.println("Intent -> getExtra");

		if (dayId == 0) {
			System.out.println("Montag");
		} else if (dayId == 1) {
			System.out.println("Dienstag");
		} else if (dayId == 2) {
			System.out.println("Mittwoch");
		} else if (dayId == 3) {
			System.out.println("Donnerstag");
		} else if (dayId == 4) {
			System.out.println("Freitag");
		}

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
						System.out.println("position: " + position);
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
			System.out.println("i: " + i);
			// Create a tab with text corresponding to the page title defined by
			// the adapter.
			// Also specify this Activity object, which implements the
			// TabListener interface, as the
			// listener for when this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mAppSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
			// mAppSectionsPagerAdapter.getItem(i);
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
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
		System.out.println("tabposition: " + tab.getPosition()); 
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) { 
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the primary sections of the app.
	 */
	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

		public AppSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			System.out.println("getItem: " + i);
			Fragment fragment = new DisplayDayFragment();
			Bundle args = new Bundle();
			
			args.putInt(DisplayDayFragment.ARG_DAY, i+1);
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
			switch (position) {
			case 0:
				day = "Montag";
				break;
			case 1:
				day = "Dienstag";
				break;
			case 2:
				day = "Mittwoch";
				break;
			case 3:
				day = "Donnerstag";
				break;
			case 4:
				day = "Freitag";
				break;
			}
			return day;
		}
	}

	/**
	 * A fragment that launches other parts of the demo application.
	 */

	// public class DisplaySpecificDayFragment extends Fragment {
	//
	// public DisplaySpecificDayFragment(){
	//
	// }
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// View rootView = inflater.inflate(R.layout.fragment_display_day,
	// container,
	// false);
	//
	//
	//
	// return rootView;
	//
	// }
	//
	// }

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */

	// Intent intent = getIntent();
	// int dayId = intent.getIntExtra("dayId", 0);
	// System.out.println("getExtra");
	//
	// if(dayId==0){
	// System.out.println("Montag");
	// }else if(dayId==1){
	// System.out.println("Dienstag");
	// }else if(dayId==2){
	// System.out.println("Mittwoch");
	// }else if(dayId==3){
	// System.out.println("Donnerstag");
	// }else if(dayId==4){
	// System.out.println("Freitag");
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
