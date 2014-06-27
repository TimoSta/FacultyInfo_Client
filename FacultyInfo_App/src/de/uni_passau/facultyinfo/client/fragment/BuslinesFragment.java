package de.uni_passau.facultyinfo.client.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.BusLine;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;
import de.uni_passau.facultyinfo.client.util.SwipeRefreshAsyncDataLoader;

public class BuslinesFragment extends SwipeRefreshLayoutFragment {

	public BuslinesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final SwipeRefreshLayout rootView = (SwipeRefreshLayout) inflater
				.inflate(R.layout.view_list, container, false);

		initializeSwipeRefresh(rootView, new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new BusLineLoader(rootView, true).execute();
			}
		});
		Activity activity = getActivity();
		ActionBar actionBar = activity.getActionBar();
		actionBar.setTitle(activity.getApplicationContext().getString(
				R.string.title_buslines));
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

		(new BusLineLoader(rootView)).execute();

		return rootView;

	}

	protected class BusLineLoader extends
			SwipeRefreshAsyncDataLoader<List<BusLine>> {
		private boolean forceRefresh = false;

		private BusLineLoader(SwipeRefreshLayout rootView) {
			super(rootView);
		}

		private BusLineLoader(SwipeRefreshLayout rootView, boolean forceRefresh) {
			super(rootView);
			this.forceRefresh = forceRefresh;
		}

		@Override
		protected List<BusLine> doInBackground(Void... unused) {
			showLoadingAnimation(true);

			AccessFacade accessFacade = new AccessFacade();

			List<BusLine> busLines = accessFacade.getBusLineAccess()
					.getNextBusLines(forceRefresh);

			if (busLines == null) {
				publishProgress(AsyncDataLoader.NO_CONNECTION_PROGRESS);
				busLines = accessFacade.getBusLineAccess()
						.getNextBusLinesFromCache();
			}

			if (busLines == null) {
				busLines = Collections
						.unmodifiableList(new ArrayList<BusLine>());
			}

			return busLines;
		}

		@Override
		protected void onPostExecute(List<BusLine> busLines) {
			super.onPostExecute(busLines);

			if (busLines != null) {
				ListView buslines = (ListView) rootView.findViewById(R.id.list);

				final ArrayList<HashMap<String, String>> busList = new ArrayList<HashMap<String, String>>();

				for (BusLine busLine : busLines) {
					HashMap<String, String> temp1 = new HashMap<String, String>();
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",
							Locale.GERMAN);
					HashMap<String, String> listEntry = temp1;
					listEntry.put("title", sdf.format(busLine.getDeparture())
							+ " - " + busLine.getLine());
					listEntry.put("direction", busLine.getDirection());
					busList.add(listEntry);
				}

				SimpleAdapter adapter = new SimpleAdapter(
						rootView.getContext(), busList, R.layout.row_twoline,
						new String[] { "title", "direction", }, new int[] {
								R.id.title, R.id.description }

				);

				buslines.setAdapter(adapter);
			}
		}
	}
}