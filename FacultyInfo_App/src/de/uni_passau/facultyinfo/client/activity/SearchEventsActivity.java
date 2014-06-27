package de.uni_passau.facultyinfo.client.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.EventSearchResult;
import de.uni_passau.facultyinfo.client.util.SwipeRefreshAsyncDataLoader;

public class SearchEventsActivity extends SwipeRefreshLayoutActivity {

	private String query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_list);

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
										.getChildAt(0)).execute();
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

	protected class EventLoader extends
			SwipeRefreshAsyncDataLoader<List<EventSearchResult>> {

		private EventLoader(SwipeRefreshLayout rootView) {
			super(rootView);
		}

		@Override
		protected List<EventSearchResult> doInBackground(Void... unused) {
			showLoadingAnimation(true);

			AccessFacade accessFacade = new AccessFacade();
			List<EventSearchResult> results = null;

			results = accessFacade.getEventAccess().find(query);

			return results;
		}

		@Override
		protected void onPostExecute(List<EventSearchResult> results) {
			super.onPostExecute(results);

			ListView listView = (ListView) findViewById(R.id.list);

			final ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();

			for (EventSearchResult result : results) {
				HashMap<String, String> listEntry = new HashMap<String, String>();
				listEntry.put("title", result.getTitle());
				listEntry.put("eventId", result.getId());
				listEntry.put("subtitle", result.getSubtitle());
				resultList.add(listEntry);
			}

			SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
					resultList, R.layout.row_twoline, new String[] { "title",
							"subtitle" }, new int[] { R.id.title,
							R.id.description }) {
				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {
					LayoutInflater inflater = (LayoutInflater) getApplicationContext()
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					View rowView = inflater.inflate(R.layout.row_twoline,
							parent, false);
					TextView titleView = (TextView) rowView
							.findViewById(R.id.title);
					Spanned title = Html.fromHtml(resultList
							.get(position)
							.get("title")
							.replaceAll("(?i)(" + query + ")",
									"<font color='#FF8000'>$1</font>"));
					titleView.setText(title);
					TextView subtitleView = (TextView) rowView
							.findViewById(R.id.description);
					if (resultList.get(position).get("subtitle") != null) {
						Spanned subtitle = Html.fromHtml(resultList
								.get(position)
								.get("subtitle")
								.replaceAll("(?i)(" + query + ")",
										"<font color='#FF8000'>$1</font>"));
						subtitleView.setText(subtitle);
					} else {
						subtitleView.setVisibility(View.GONE);
					}

					return rowView;
				}
			};

			listView.setAdapter(adapter);

			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					displayEvent(resultList.get(position).get("eventId"));
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
