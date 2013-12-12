package de.uni_passau.facultyinfo.client.activity;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.News;

public class NewsTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_test);
		System.out.println(this);
		NewsLoader newsLoader = new NewsLoader();
		newsLoader.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		return true;
	}

	public void loadNews(View view) {
		NewsLoader newsLoader = new NewsLoader();
		newsLoader.execute();
	}

	private class NewsLoader extends AsyncTask<URL, Void, List<News>> {

		@Override
		protected List<News> doInBackground(URL... urls) {
			AccessFacade accessFacade = new AccessFacade();
			List<News> news = accessFacade.getNewsAccess().getNews();
			return news;
		}

		@Override
		protected void onPostExecute(List<News> news) {

			ArrayList<String> list = new ArrayList<String>();

			for (int i = 0; i < news.size(); i++) {
				list.add(news.get(i).getTitle() + "\n\n"
						+ news.get(i).getDescription());
			}

			ListView listView = (ListView) findViewById(R.id.list);

			final StableArrayAdapter adapter = new StableArrayAdapter(
					getApplicationContext(),
					android.R.layout.simple_list_item_1, list);
			listView.setAdapter(adapter);

			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					System.out.println("click");
					System.out.println(position);

					displayNews(position);

					/*
					 * Toast.makeText(getApplicationContext(),
					 * "Click ListItem Number " + position, Toast.LENGTH_LONG)
					 * .show();
					 */
				}

			});

		}

	}

	private void displayNews(int position) {
		Intent intent = new Intent(this, DisplayNewsActivity.class);

		intent.putExtra("newsPosition", position);
		startActivity(intent);
	}

	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects) {
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
