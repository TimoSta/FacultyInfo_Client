package de.uni_passau.facultyinfo.client.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.R.layout;
import de.uni_passau.facultyinfo.client.R.menu;
import de.uni_passau.facultyinfo.client.activity.SearchContactsActivity.ChairLoader;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.Event;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class SearchEventsActivity extends Activity {

	private String query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_events);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);

		Intent intent = getIntent();
		query = intent.getStringExtra("query");

		(new EventLoader()).execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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

	protected class EventLoader extends AsyncDataLoader<List<Event>> {
		@Override
		protected List<Event> doInBackground(Void... unused) {
			AccessFacade accessFacade = new AccessFacade();
			List<Event> events = null;

			events = accessFacade.getEventAccess().find(query);

			// if (events == null) {
			// publishProgress(AsyncDataLoader.NO_CONNECTION_PROGRESS);
			// events = accessFacade.getEventAccess().find(query);
			// }

			if (events == null) {
				events = new ArrayList<Event>();
			}

			return events;
		}

		@Override
		protected void onPostExecute(List<Event> events) {
			ListView listView = (ListView) findViewById(R.id.events_search_results);

			final ArrayList<HashMap<String, String>> eventList = new ArrayList<HashMap<String, String>>();

			for (Event event : events) {
				HashMap<String, String> temp1 = new HashMap<String, String>();
				temp1.put("title", event.getTitle());
				SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy",
						Locale.GERMAN);
				temp1.put("date", sdf.format(event.getStartDate()));
				temp1.put("eventId", event.getId());
				eventList.add(temp1);
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
