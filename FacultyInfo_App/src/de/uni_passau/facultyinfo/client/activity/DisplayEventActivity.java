package de.uni_passau.facultyinfo.client.activity;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.Event;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

public class DisplayEventActivity extends Activity {
	String eventId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_event);

		Intent intent = getIntent();
		eventId = intent.getStringExtra("eventId");
		
		(new EventLoader()).execute(); 

	}

	//
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu., menu);
	// return true;
	// }
	//
	protected class EventLoader extends AsyncDataLoader<Event> {
		@Override
		protected Event doInBackground(Void... unused) {
			AccessFacade accessFacade = new AccessFacade();
			Event event = null;

			event = accessFacade.getEventAccess().getEvent(eventId);

			if (event == null) {
				publishProgress(AsyncDataLoader.NO_CONNECTION_PROGRESS);
				event = accessFacade.getEventAccess()
						.getEventFromCache(eventId);
			}

			return event;
		}

		@Override
		protected void onPostExecute(Event event) {
			TextView title = (TextView) findViewById(R.id.event_title);
			title.setText(event.getTitle());

			TextView subtitle = (TextView) findViewById(R.id.event_subtitle);
			subtitle.setText(event.getTitle());

			TextView description = (TextView) findViewById(R.id.event_description);
			description.setText(event.getDescription());

			TextView location = (TextView) findViewById(R.id.event_location);
			location.setText(event.getLocation());

			TextView time = (TextView) findViewById(R.id.event_time);
			SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy",
					Locale.GERMAN);
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.GERMAN);
			if ((sdf.format(event.getStartDate())).equals(sdf.format(event
					.getEndDate()))) {
				time.setText(sdf.format(event.getStartDate()) + " "
						+ sdf2.format(event.getStartDate()) + " - "
						+ sdf2.format(event.getEndDate()));

			} else {
				time.setText(sdf.format(event.getStartDate()) + " - "
						+ sdf.format(event.getEndDate()) + " "
						+ sdf2.format(event.getStartDate()) + " - "
						+ sdf2.format(event.getEndDate()));
			}

		}
	}

}
