package de.uni_passau.facultyinfo.client.activity;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.News;

public class DisplayNewsActivity extends Activity /*
												 * implements
												 * OnNavigationListener
												 */{

	private String newsId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("DisplayNews->onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_news);

		Intent intent = getIntent();
		newsId = intent.getStringExtra("newsId");
		System.out.println(newsId);

		// getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);

		/*
		 * ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
		 * this, R.array.spinner_menu_news,
		 * android.R.layout.simple_spinner_item);
		 * getActionBar().setSelectedNavigationItem(1);
		 * getActionBar().setListNavigationCallbacks(adapter, this);
		 */

		NewsLoader newsLoader = new NewsLoader();
		newsLoader.execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news, menu);
		return true;
	}

	/*
	 * @Override public boolean onNavigationItemSelected(int itemPosition, long
	 * itemId) {
	 * 
	 * System.out.println("NewsActivity->onNavigationItemSelected->itemId");
	 * System.out.println(itemId);
	 * 
	 * if (itemId == 1) { Intent intent = new Intent(this, MainActivity.class);
	 * startActivity(intent); return true; } else if (itemId == 2) { Intent
	 * intent = new Intent(this, TimetableActivity.class);
	 * startActivity(intent); return true; } else if (itemId == 3) { Intent
	 * intent = new Intent(this, BuslinesActivity.class); startActivity(intent);
	 * return true; } else if (itemId == 4) { Intent intent = new Intent(this,
	 * SportsActivity.class); startActivity(intent); return true; } else if
	 * (itemId == 5) { Intent intent = new Intent(this,
	 * CafeteriaActivity.class); startActivity(intent); return true; } else if
	 * (itemId == 6) { Intent intent = new Intent(this, ContactsActivity.class);
	 * startActivity(intent); return true; } else if (itemId == 7) { Intent
	 * intent = new Intent(this, FAQsActivity.class); startActivity(intent);
	 * return true; } return false; }
	 */
	private class NewsLoader extends AsyncTask<URL, Void, News> {

		@Override
		protected News doInBackground(URL... urls) {
			System.out.println("DisplayNews->doInBackground");
			System.out.println(newsId);
			AccessFacade accessFacade = new AccessFacade();
			News news = accessFacade.getNewsAccess().getNews(newsId);
			return news;
		}

		@Override
		protected void onPostExecute(News news) {

			System.out.println("DisplayNews->onPOstExecute");
			System.out.println(news.getTitle());
			System.out.println(news.getText());
			System.out.println(news.getPublicationDate());

			TextView headingView = (TextView) findViewById(R.id.newsHeading);
			headingView.setText(news.getTitle());

			TextView textView = (TextView) findViewById(R.id.newsText);
			textView.setMovementMethod(new ScrollingMovementMethod());
			textView.setText(news.getText());

			TextView dateView = (TextView) findViewById(R.id.newsDate);
			SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy",
					Locale.GERMAN);
			dateView.setText(sdf.format(news.getPublicationDate()));
		}
	}

}
