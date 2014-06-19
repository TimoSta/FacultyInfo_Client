package de.uni_passau.facultyinfo.client.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.Faq;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;
import de.uni_passau.facultyinfo.client.util.SwipeRefreshAsyncDataLoader;

public class DisplayFAQActivity extends SwipeRefreshLayoutActivity {

	private String faqId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_faq);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);

		Intent intent = getIntent();
		faqId = intent.getStringExtra("faqId");
		String category = intent.getStringExtra("category");

		setTitle(category);

		initializeSwipeRefresh(
				(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
						.getChildAt(0), new OnRefreshListener() {

					@Override
					public void onRefresh() {
						new FaqLoader(
								(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
										.getChildAt(0), true).execute();
					}

				});

		(new FaqLoader(
				(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
						.getChildAt(0))).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.display_faq, menu);
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

	protected class FaqLoader extends SwipeRefreshAsyncDataLoader<Faq> {
		private boolean forceRefresh = false;

		private FaqLoader(SwipeRefreshLayout rootView) {
			super(rootView);
		}

		private FaqLoader(SwipeRefreshLayout rootView, boolean forceRefresh) {
			super(rootView);
			this.forceRefresh = forceRefresh;
		}

		@Override
		protected Faq doInBackground(Void... unused) {
			showLoadingAnimation(true);

			AccessFacade accessFacade = new AccessFacade();

			Faq faq = accessFacade.getFaqAccess().getFaq(faqId, forceRefresh);

			if (faq == null) {
				publishProgress(AsyncDataLoader.NO_CONNECTION_PROGRESS);
				faq = accessFacade.getFaqAccess().getFaqFromCache(faqId);
			}

			return faq;
		}

		@Override
		protected void onPostExecute(Faq faq) {
			super.onPostExecute(faq);

			if (faq != null) {
				TextView headingView = (TextView) findViewById(R.id.faqHeading);
				headingView.setText(faq.getTitle());

				TextView textView = (TextView) findViewById(R.id.faqText);
				textView.setMovementMethod(new ScrollingMovementMethod());
				textView.setText(faq.getText());
			}
		}
	}

}
