package de.uni_passau.facultyinfo.client.activity;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.News;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

public class DisplayNewsActivity extends Activity {

	private String newsId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_display_news);

		Intent intent = getIntent();
		newsId = intent.getStringExtra("newsId");
		System.out.println(newsId);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);

		new NewsLoader().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.news, menu);
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
	private class NewsLoader extends AsyncDataLoader<News> {

		@Override
		protected News doInBackground(Void... voids) {
			AccessFacade accessFacade = new AccessFacade();

			News news = accessFacade.getNewsAccess().getNews(newsId);

			if (news == null) {
				publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
				news = accessFacade.getNewsAccess().getNewsFromCache(newsId);
			}

			return news;
		}

		@Override
		protected void onPostExecute(News news) {
			if (news != null) {
				TextView headingView = (TextView) findViewById(R.id.newsHeading);
				headingView.setText(news.getTitle());

				TextView textView = (TextView) findViewById(R.id.newsText);
				textView.setMovementMethod(new ScrollingMovementMethod());
				textView.setText(news.getText() == null ? news.getDescription() : news.getText());

				TextView dateView = (TextView) findViewById(R.id.newsDate);
				SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy",
						Locale.GERMAN);
				dateView.setText(sdf.format(news.getPublicationDate()));
			}
		}
	}

}
