package de.uni_passau.facultyinfo.client.fragment;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import de.uni_passau.facultyinfo.client.R;

//public class SportsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
//	
//	private View rootView; 
//
//	public SportsFragment() {
//		// Empty constructor required for fragment subclasses
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		rootView = inflater.inflate(R.layout.fragment_sports, container,
//				false);
//
//		getActivity().setTitle(R.string.title_sports);
//		
////		Switch slider = (Switch) rootView.findViewById(R.id.timePeriod); 
////		slider.setTextOn("A-Z"); 
////		slider.setTextOff("heute"); 
//		
//		Switch s = (Switch) rootView.findViewById(R.id.timePeriod);
//        if (s != null) {
//            s.setOnCheckedChangeListener(this);
//        }
//        
//        SportsCourseCategoryLoader sportsCategoryLoader = new SportsCourseCategoryLoader(rootView);
//		sportsCategoryLoader.execute();
//        
//
//		return rootView;
//
//	}
//	
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.action_search:
//			System.out.println("Search");
//			
//
//			
//			return true;
//		default:
//			return super.onOptionsItemSelected(item);
//		}
//	}
//	
//	 @Override
//	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//	     if(isChecked){
//	    	 System.out.println("A-Z"); 
//	    	 SportsCourseCategoryLoader sportsCategoryLoader = new SportsCourseCategoryLoader(rootView);
//	 		sportsCategoryLoader.execute();
//	     }else{
//	    	 System.out.println("heute"); 
//	    	 TodaysSportsCourseCategoryLoader sportsCategoryLoader = new TodaysSportsCourseCategoryLoader(rootView);
//	 		sportsCategoryLoader.execute();
//	     }
////		 
////		 Toast.makeText(rootView.getContext(), "Monitored switch is " + (isChecked ? "on" : "off"),
////	               Toast.LENGTH_SHORT).show();
//	    }

public class SportsFragment extends FragmentActivity implements
		ActionBar.TabListener {

	AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("SportsFragment");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_sports);
		System.out.println("SportsFragment -> onCreate");


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
		actionBar.setSelectedNavigationItem(0);
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
		public android.support.v4.app.Fragment getItem(int i) {
			System.out.println("getItem: " + i);
			android.support.v4.app.Fragment fragment = new SportsCategoryFragment();
			Bundle args = new Bundle();

			args.putInt(SportsCategoryFragment.ARG_PERIOD, i + 1);
			fragment.setArguments(args);
			return fragment;

		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			String tab = "";
			switch (position) {
			case 0:
				tab = "A-Z";
				break;
			case 1:
				tab = "heute";
				break;
			}
			return tab;
		}
	}

}