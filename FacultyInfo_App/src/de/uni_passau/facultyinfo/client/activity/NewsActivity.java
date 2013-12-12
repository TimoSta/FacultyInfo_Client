package de.uni_passau.facultyinfo.client.activity;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.News;

public class NewsActivity extends Activity implements OnNavigationListener {
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("NewsActivity->onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(false);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.spinner_menu_news,
				android.R.layout.simple_spinner_item);
		getActionBar().setSelectedNavigationItem(1);
		getActionBar().setListNavigationCallbacks(adapter, this);
		

		NewsLoader newsLoader = new NewsLoader();
		newsLoader.execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news, menu);
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
			Intent intent = new Intent(this, TimetableActivity.class);
			startActivity(intent);
			return true;
		} else if (itemId == 3) {
			Intent intent = new Intent(this, BuslinesActivity.class);
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

	private class NewsLoader extends AsyncTask<URL, Void, List<News>> {
		
		private final ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		@Override
		protected List<News> doInBackground(URL... urls) {
			System.out.println("NewsActivity->doInBackground");
			AccessFacade accessFacade = new AccessFacade();
			List<News> news = accessFacade.getNewsAccess().getNews();
			return news;
		}

		/*
		 * @Override protected void onPostExecute(List<News> news) {
		 * 
		 * System.out.println("NewsActivity->onPostExecute");
		 * 
		 * ArrayList<String> list = new ArrayList<String>();
		 * 
		 * for (int i = 0; i < news.size(); i++) {
		 * list.add(news.get(i).getTitle() + "\n\n" +
		 * news.get(i).getDescription()); }
		 * 
		 * ListView listView = (ListView) findViewById(R.id.list);
		 * 
		 * final StableArrayAdapter adapter = new StableArrayAdapter(
		 * getApplicationContext(), android.R.layout.simple_list_item_1, list);
		 * listView.setAdapter(adapter);
		 * 
		 * listView.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long id) { System.out.println("click");
		 * System.out.println(position);
		 * 
		 * displayNews(position);
		 * 
		 * }
		 * 
		 * });
		 * 
		 * }
		 */
		@Override
		protected void onPostExecute(List<News> news) {
			
			System.out.println("NewsActivity->onPostExecute");
			
			final ArrayList<HashMap<String, String>> newsList = new ArrayList<HashMap<String, String>>();
			//ListView listView = (ListView) findViewById(R.id.list);

			// ArrayList<String> list = new ArrayList<String>();

			for (News newsElement : news) {
				HashMap<String, String> temp1 = new HashMap<String, String>();
				temp1.put("id", newsElement.getId());
				temp1.put("title", newsElement.getTitle());
				temp1.put("description", newsElement.getDescription());
				newsList.add(temp1);
			}
			
			

			setContentView(R.layout.activity_news);
			ListView listView = (ListView) findViewById(R.id.list);
			
			SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), newsList,
					R.layout.custom_row_view, new String[] { "title", "description",
							}, new int[] { R.id.title, R.id.description}

			);
			
			/*HashMap<String, String> temp1 = new HashMap<String, String>();
			temp1.put("title", "testTitel");
			temp1.put("description", "testDescription");
			list.add(temp1);
			
			HashMap<String, String> temp2 = new HashMap<String, String>();
			temp2.put("title", "testTitel2");
			temp2.put("description", "testDescription2");
			list.add(temp2);*/

			listView.setAdapter(adapter);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
				
				  @Override 
				  public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
					  System.out.println("click");
					  System.out.println(position);
					  displayNews(newsList.get(position).get("id"));
				  }
			});
				   
				 
				 
		}

	}

	private void displayNews(String id) {
		System.out.println("NewsActivity->displayNews()");
		Intent intent = new Intent(this, DisplayNewsActivity.class);

		intent.putExtra("newsId", id);
		System.out.println("putExtra");
		startActivity(intent);
	}

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
			// System.out.println("NewsActivity->StableArrayAdapter");
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			String item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}
}
