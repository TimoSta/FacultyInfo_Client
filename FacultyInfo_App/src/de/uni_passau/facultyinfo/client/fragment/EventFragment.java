package de.uni_passau.facultyinfo.client.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.activity.DisplayEventActivity;
import de.uni_passau.facultyinfo.client.activity.SearchContactsActivity;
import de.uni_passau.facultyinfo.client.activity.SearchEventsActivity;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.Event;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

public class EventFragment extends Fragment {
	private SearchView searchView; 
	private View rootView; 

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_event, container,
				false);

		setHasOptionsMenu(true);

		getActivity().getActionBar().setTitle(
				getActivity().getApplicationContext().getString(
						R.string.title_events));
		getActivity().getActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_STANDARD);
		
		(new EventLoader(rootView)).execute(); 

		return rootView;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.events, menu);
		searchView = (SearchView) menu.findItem(R.id.events_search)
				.getActionView();
		// searchView = (SearchView) rootView.findViewById(R.id.search);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.events_search:

			final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {

				@Override
				public boolean onQueryTextSubmit(String query) {
					// TODO Auto-generated method stub
					System.out.println("onQueryTextSubmit");
					Intent intent = new Intent(rootView.getContext(),
							SearchEventsActivity.class);
					intent.putExtra("query", query);
					startActivity(intent);

					return false;
				}

				@Override
				public boolean onQueryTextChange(String newText) {
					// TODO Auto-generated method stub
					System.out.println("onQueryTextChange");
					return false;
				}
			};
			searchView.setOnQueryTextListener(queryTextListener);

			return true;
		default:
			return false;
		}
	}

	protected class EventLoader extends AsyncDataLoader<List<Event>> {

		public EventLoader(View rootView) {
			super(rootView);
		}

		@Override
		protected List<Event> doInBackground(Void... unused) {
			AccessFacade accessFacade = new AccessFacade();
			List<Event> events = null;

			events = accessFacade.getEventAccess().getEvents();

			if (events == null) {
				publishProgress(AsyncDataLoader.NO_CONNECTION_PROGRESS);
				events = accessFacade.getEventAccess().getEventsFromCache();
			}

			if (events == null) {
				events = new ArrayList<Event>();
			}

			return events;
		}

		@Override
		protected void onPostExecute(List<Event> events) {
			ListView listView = (ListView) rootView
					.findViewById(R.id.event_list);

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

			SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
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
			Intent intent = new Intent(rootView.getContext(),DisplayEventActivity.class);

			intent.putExtra("eventId", id);
			startActivity(intent);
		}
	}

}
