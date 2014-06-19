package de.uni_passau.facultyinfo.client.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.Event;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;
import de.uni_passau.facultyinfo.client.util.SwipeRefreshAsyncDataLoader;

public class SearchEventsActivity extends SwipeRefreshLayoutActivity {

	private String query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_events);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);

		Intent intent = getIntent();
		query = intent.getStringExtra("query");

		initializeSwipeRefresh(
				(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
						.getChildAt(0), new OnRefreshListener() {

					@Override
					public void onRefresh() {
						new EventLoader(
								(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
										.getChildAt(0), true).execute();
					}

				});

		(new EventLoader(
				(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
						.getChildAt(0))).execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search_events, menu);
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

	protected class EventLoader extends SwipeRefreshAsyncDataLoader<List<Event>> {
		private boolean forceRefresh = false;

		private EventLoader(SwipeRefreshLayout rootView) {
			super(rootView);
		}

		private EventLoader(SwipeRefreshLayout rootView, boolean forceRefresh) {
			super(rootView);
			this.forceRefresh = forceRefresh;
		}

		@Override
		protected List<Event> doInBackground(Void... unused) {
			showLoadingAnimation(true);
			
			AccessFacade accessFacade = new AccessFacade();
			List<Event> events = null;

			events = accessFacade.getEventAccess().find(query);

			if (events == null) {
				events = new ArrayList<Event>();
			}

			return events;
		}

		@Override
		protected void onPostExecute(List<Event> events) {
			super.onPostExecute(events);
			
			ListView listView = (ListView) findViewById(R.id.events_search_results);

			final ArrayList<HashMap<String, String>> eventList = new ArrayList<HashMap<String, String>>();

			for (Event event : events) {
				HashMap<String, String> listEntry = new HashMap<String, String>();
				listEntry.put("title", event.getTitle());
				if (event.getStartDate() != null) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"EEE, d MMM yyyy", Locale.GERMAN);
					listEntry.put("date", sdf.format(event.getStartDate()));
				}
				listEntry.put("eventId", event.getId());
				eventList.add(listEntry);
			}

			SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
					eventList, R.layout.custom_row_view, new String[] { "date",
							"title" },
					new int[] { R.id.title, R.id.description });

			listView.setAdapter(adapter);

			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					System.out.println("click");
					System.out.println(position);
					displayEvent(eventList.get(position).get("eventId"));
				}
			});
		}

		private void displayEvent(String id) {
			Intent intent = new Intent(getApplicationContext(),
					DisplayEventActivity.class);
			intent.putExtra("eventId", id);
			startActivity(intent);
		}
	}

}
