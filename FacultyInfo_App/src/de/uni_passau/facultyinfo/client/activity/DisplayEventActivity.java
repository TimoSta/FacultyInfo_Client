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
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.Event;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;
import de.uni_passau.facultyinfo.client.util.SwipeRefreshAsyncDataLoader;

public class DisplayEventActivity extends SwipeRefreshLayoutActivity {
	String eventId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_event);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);

		Intent intent = getIntent();
		eventId = intent.getStringExtra("eventId");
		
		initializeSwipeRefresh((SwipeRefreshLayout) ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0), new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new EventLoader((SwipeRefreshLayout) ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0), true).execute();
			}

		});
		new EventLoader((SwipeRefreshLayout) ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0), true).execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.display_event, menu);
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

	protected class EventLoader extends SwipeRefreshAsyncDataLoader<Event> {
		private boolean forceRefresh = false;

		private EventLoader(SwipeRefreshLayout rootView) {
			super(rootView);
		}

		private EventLoader(SwipeRefreshLayout rootView, boolean forceRefresh) {
			super(rootView);
			this.forceRefresh = forceRefresh;
		}
		
		@Override
		protected Event doInBackground(Void... unused) {
			showLoadingAnimation(true);

			AccessFacade accessFacade = new AccessFacade();
			Event event = null;

			event = accessFacade.getEventAccess().getEvent(eventId, forceRefresh);

			if (event == null) {
				publishProgress(AsyncDataLoader.NO_CONNECTION_PROGRESS);
				event = accessFacade.getEventAccess()
						.getEventFromCache(eventId);
			}

			return event;
		}

		@Override
		protected void onPostExecute(Event event) {
			super.onPostExecute(event);
			if (event != null) {
				((TextView)findViewById(R.id.event_description)).setVisibility(View.VISIBLE);
				TextView title = (TextView) findViewById(R.id.event_title);
				title.setText(event.getTitle());

//				TextView subtitle = (TextView) findViewById(R.id.event_subtitle);
//				subtitle.setText(event.getTitle());

				TextView description = (TextView) findViewById(R.id.event_description);
				description.setText(event.getDescription());
				description.setMovementMethod(new ScrollingMovementMethod());

				TextView location = (TextView) findViewById(R.id.event_location);
				location.setText(event.getLocation());

				TextView time = (TextView) findViewById(R.id.event_time);
				SimpleDateFormat dateSDF = new SimpleDateFormat(
						"EEE, d MMM yyyy", Locale.GERMAN);
				SimpleDateFormat timeSDF = new SimpleDateFormat("HH:mm",
						Locale.GERMAN);
				if ((dateSDF.format(event.getStartDate())).equals(dateSDF
						.format(event.getEndDate()))) {
					time.setText(dateSDF.format(event.getStartDate()) + " "
							+ timeSDF.format(event.getStartDate()) + " - "
							+ timeSDF.format(event.getEndDate()));

				} else {
					time.setText(dateSDF.format(event.getStartDate()) + " - "
							+ dateSDF.format(event.getEndDate()) + " "
							+ timeSDF.format(event.getStartDate()) + " - "
							+ timeSDF.format(event.getEndDate()));
				}
			}
		}
	}

}
