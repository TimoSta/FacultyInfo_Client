package de.uni_passau.facultyinfo.client.activity;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;
//import de.uni_passau.facultyinfo.client.fragment.NewsFragment.NewsLoader;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.News;
import de.uni_passau.facultyinfo.client.util.SwipeRefreshAsyncDataLoader;

public class DisplayNewsActivity extends SwipeRefreshLayoutActivity {

	private String newsId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_news);

		Intent intent = getIntent();
		newsId = intent.getStringExtra("newsId");

		initializeSwipeRefresh((SwipeRefreshLayout) ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0), new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new NewsLoader((SwipeRefreshLayout) ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0), true).execute();
			}

		});

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);

		(new NewsLoader((SwipeRefreshLayout)((ViewGroup)findViewById(android.R.id.content)).getChildAt(0))).execute();
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

	private class NewsLoader extends SwipeRefreshAsyncDataLoader<News> {
		private boolean forceRefresh = false;

		private NewsLoader(SwipeRefreshLayout rootView) {
			super(rootView);
		}

		private NewsLoader(SwipeRefreshLayout rootView, boolean forceRefresh) {
			super(rootView);
			this.forceRefresh = forceRefresh;
		}

		@Override
		protected News doInBackground(Void... voids) {
			showLoadingAnimation(true);

			AccessFacade accessFacade = new AccessFacade();

			News news = accessFacade.getNewsAccess().getNews(newsId,
					forceRefresh);

			if (news == null) {
				publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
				news = accessFacade.getNewsAccess().getNewsFromCache(newsId);
			}

			return news;
		}

		@Override
		protected void onPostExecute(News news) {
			super.onPostExecute(news);
			if (news != null) {
				((TextView)findViewById(R.id.newsText)).setVisibility(View.VISIBLE);
				
				TextView headingView = (TextView) findViewById(R.id.newsHeading);
				headingView.setText(news.getTitle());

				TextView textView = (TextView) findViewById(R.id.newsText);
				textView.setMovementMethod(new ScrollingMovementMethod());
				textView.setText(news.getText() == null ? news.getDescription()
						: news.getText());

				TextView dateView = (TextView) findViewById(R.id.newsDate);
				SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy",
						Locale.GERMAN);
				dateView.setText(sdf.format(news.getPublicationDate()));
			}
		}
	}

}
