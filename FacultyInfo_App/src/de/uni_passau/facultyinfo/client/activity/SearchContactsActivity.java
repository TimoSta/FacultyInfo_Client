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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.ContactGeneric;
import de.uni_passau.facultyinfo.client.util.SwipeRefreshAsyncDataLoader;

public class SearchContactsActivity extends SwipeRefreshLayoutActivity {

	private String query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_contacts);

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
						new ChairLoader(
								(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
										.getChildAt(0)).execute();
					}

				});
		(new ChairLoader(
				(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
						.getChildAt(0))).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search_contacts, menu);
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

	protected class ChairLoader extends
			SwipeRefreshAsyncDataLoader<List<ContactGeneric>> {

		private ChairLoader(SwipeRefreshLayout rootView) {
			super(rootView);
		}

		@Override
		protected List<ContactGeneric> doInBackground(Void... unused) {
			showLoadingAnimation(true);

			AccessFacade accessFacade = new AccessFacade();

			List<ContactGeneric> groups = accessFacade.getContactPersonAccess()
					.find(query);

			return groups;
		}

		@Override
		protected void onPostExecute(List<ContactGeneric> results) {
			super.onPostExecute(results);
			if (results != null) {
				ListView listView = (ListView) findViewById(R.id.contacts_search_results);

				final ArrayList<HashMap<String, String>> groupList = new ArrayList<HashMap<String, String>>();

				for (ContactGeneric generic : results) {
					HashMap<String, String> listEntry = new HashMap<String, String>();
					listEntry.put("title", generic.getTitle());
					listEntry.put("subtitle", generic.getSubtitle());
					listEntry.put("type", Integer.toString(generic.getType()));
					listEntry.put("id", generic.getId());
					groupList.add(listEntry);
				}

				listView.setAdapter(new CustomAdapter(getApplicationContext(),
						groupList));

				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						HashMap<String, String> values = groupList
								.get(position);
						if (values.get("type").equals(
								Integer.toString(ContactGeneric.GROUP))) {
							displayChairContacts(values.get("id"),
									values.get("title"));
						} else {
							displayPerson(values.get("id"), values.get("title"));
						}
					}
				});
			}

		}

		private class CustomAdapter extends ArrayAdapter<String> {
			private final Context context;
			private final List<HashMap<String, String>> values;

			public CustomAdapter(Context context,
					List<HashMap<String, String>> groupList) {
				super(context, R.layout.row_threeline);
				this.context = context;
				this.values = groupList;
			}

			@Override
			public int getCount() {
				return values.size();
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View rowView = inflater.inflate(R.layout.row_threeline, parent,
						false);
				TextView titleView = (TextView) rowView
						.findViewById(R.id.title);
				Spanned title = Html.fromHtml(values
						.get(position)
						.get("title")
						.replaceAll("(?i)(" + query + ")",
								"<font color='#FF8000'>$1</font>"));
				titleView.setText(title);
				TextView subtitleView = (TextView) rowView
						.findViewById(R.id.description);
				if (values.get(position).get("subtitle") != null) {
					Spanned subtitle = Html.fromHtml(values
							.get(position)
							.get("subtitle")
							.replaceAll("(?i)(" + query + ")",
									"<font color='#FF8000'>$1</font>"));
					subtitleView.setText(subtitle);
				} else {
					subtitleView.setVisibility(View.GONE);
				}

				TextView typeView = (TextView) rowView
						.findViewById(R.id.header);
				typeView.setText(values.get(position).get("type")
						.equals(Integer.toString(ContactGeneric.GROUP)) ? R.string.ContactGroup
						: R.string.ContactPerson);

				return rowView;
			}
		}
	}

	private void displayChairContacts(String id, String title) {
		Intent intent = new Intent(getApplicationContext(),
				DisplayChairContactsActivity.class);
		intent.putExtra("chairId", id);
		intent.putExtra("title", title);
		startActivity(intent);
	}

	private void displayPerson(String id, String title) {
		Intent intent = new Intent(getApplicationContext(),
				DisplayContactPersonActivity.class);
		intent.putExtra("personId", id);
		intent.putExtra("title", title);
		startActivity(intent);
	}

}
