package de.uni_passau.facultyinfo.client.activity;

import android.app.ActionBar;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.ContactPerson;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;
import de.uni_passau.facultyinfo.client.util.SwipeRefreshAsyncDataLoader;

public class DisplayContactPersonActivity extends SwipeRefreshLayoutActivity {

	private String personId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_threeline_threecontent);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);

		Intent intent = getIntent();
		personId = intent.getStringExtra("personId");
		String title = intent.getStringExtra("title");

		setTitle(title);

		initializeSwipeRefresh(
				(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
						.getChildAt(0), new OnRefreshListener() {

					@Override
					public void onRefresh() {
						new PersonLoader(
								(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
										.getChildAt(0), true).execute();
					}

				});

		(new PersonLoader(
				(SwipeRefreshLayout) ((ViewGroup) findViewById(android.R.id.content))
						.getChildAt(0))).execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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

	protected class PersonLoader extends
			SwipeRefreshAsyncDataLoader<ContactPerson> {
		private boolean forceRefresh = false;

		private PersonLoader(SwipeRefreshLayout rootView) {
			super(rootView);
		}

		private PersonLoader(SwipeRefreshLayout rootView, boolean forceRefresh) {
			super(rootView);
			this.forceRefresh = forceRefresh;
		}

		@Override
		protected ContactPerson doInBackground(Void... unused) {
			showLoadingAnimation(true);
			AccessFacade accessFacade = new AccessFacade();

			ContactPerson person = accessFacade.getContactPersonAccess()
					.getContactPerson(personId, forceRefresh);

			if (person == null) {
				publishProgress(AsyncDataLoader.NO_CONNECTION_PROGRESS);
			}

			return person;
		}

		@Override
		protected void onPostExecute(final ContactPerson person) {
			if (person != null) {
				super.onPostExecute(person);

				TextView name = (TextView) rootView.findViewById(R.id.title);
				name.setText(person.getName());

				TextView chair = (TextView) rootView.findViewById(R.id.header);
				chair.setText(person.getGroupTitle());

				if (person.getDescription() != null) {
					TextView description = (TextView) rootView
							.findViewById(R.id.description);
					description.setVisibility(View.VISIBLE);
					description.setText(person.getDescription());
				}

				if (person.getEmail() != null) {
					TextView email = (TextView) rootView
							.findViewById(R.id.content1);
					email.setVisibility(View.VISIBLE);
					email.setText(person.getEmail());
					email.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent emailIntent = new Intent(
									Intent.ACTION_SENDTO, Uri.parse("mailto:"
											+ Uri.encode(person.getEmail())));
							emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
							startActivity(Intent.createChooser(emailIntent,
									"Mail an " + person.getName() + "..."));
						}
					});
				}

				if (person.getOffice() != null) {
					TextView office = (TextView) rootView
							.findViewById(R.id.content2);
					office.setVisibility(View.VISIBLE);
					office.setText(person.getOffice());
					office.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent intent = new Intent(Intent.ACTION_VIEW, Uri
									.parse("geo:0,0?q="
											+ Uri.encode("Universität Passau, "
													+ person.getOffice())));
							startActivity(intent);
						}
					});
				}

				if (person.getPhone() != null) {
					TextView phone = (TextView) rootView
							.findViewById(R.id.content3);
					phone.setVisibility(View.VISIBLE);
					phone.setText(person.getPhone());
					phone.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							try {
								Intent callIntent = new Intent(
										Intent.ACTION_DIAL);
								callIntent.setData(Uri.parse("tel:"
										+ Uri.encode(person.getPhone())));
								startActivity(callIntent);
							} catch (ActivityNotFoundException e) {
								e.printStackTrace();
							}

						}
					});
				}
			}
		}
	}

}
