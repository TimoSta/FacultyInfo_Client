package de.uni_passau.facultyinfo.client.activity;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.fragment.CafeteriaFragment;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.News;

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
			break; 
		case 3: 
			System.out.println("busfahrplan"); 
			break; 
		case 4: 
			break; 
		case 5: 
			fragment = new CafeteriaFragment(); 
			break; 
		case 6: 
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
	public static class NewsFragment extends Fragment {

		public NewsFragment() {
			// Empty constructor required for fragment subclasses
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_news, container,
					false);

			getActivity().setTitle(R.string.title_news);

			NewsLoader newsLoader = new NewsLoader(rootView);
			newsLoader.execute();

			return rootView;
		}

		protected class NewsLoader extends AsyncTask<URL, Void, List<News>> {
			View rootView = null;

			private NewsLoader(View rootView) {
				super();
				this.rootView = rootView;
			}

			@Override
			protected List<News> doInBackground(URL... urls) {
				System.out.println("NewsActivity->doInBackground");
				AccessFacade accessFacade = new AccessFacade();
				List<News> news = accessFacade.getNewsAccess().getNews();
				return news;
			}

			@Override
			protected void onPostExecute(List<News> news) {

				System.out.println("NewsActivity->onPostExecute");

				final ArrayList<HashMap<String, String>> newsList = new ArrayList<HashMap<String, String>>();

				for (News newsElement : news) {
					HashMap<String, String> temp1 = new HashMap<String, String>();
					temp1.put("id", newsElement.getId());
					System.out.println(newsElement.getTitle());
					temp1.put("title", newsElement.getTitle());
					temp1.put("description", newsElement.getDescription());
					newsList.add(temp1);
				}

				ListView listView = (ListView) rootView.findViewById(R.id.list);

				SimpleAdapter adapter = new SimpleAdapter(
						rootView.getContext(), newsList,
						R.layout.custom_row_view, new String[] { "title",
								"description", }, new int[] { R.id.title,
								R.id.description }

				);

				listView.setAdapter(adapter);

				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						System.out.println("click");
						System.out.println(position);
						displayNews(newsList.get(position).get("id"));
					}
				});

			}

			private void displayNews(String id) {
				System.out.println("NewsActivity->displayNews()");
				Intent intent = new Intent(rootView.getContext(),
						DisplayNewsActivity.class);

				intent.putExtra("newsId", id);
				System.out.println("putExtra");
				startActivity(intent);
			}

		}

	}

	public static class FaqsFragment extends Fragment {

		public FaqsFragment() {
			// Empty constructor required for fragment subclasses
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_faqs, container,
					false);

			getActivity().setTitle(R.string.title_faqs);

			return rootView;

		}
	}
	

	

	
	
	

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