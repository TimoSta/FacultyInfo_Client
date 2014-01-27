package de.uni_passau.facultyinfo.client.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.BusLine;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

public class BuslinesFragment extends Fragment {

	public BuslinesFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_buslines, container,
				false);

		Activity activity = getActivity();
		ActionBar actionBar = activity.getActionBar();
		actionBar.setTitle(activity.getApplicationContext().getString(
				R.string.title_buslines));
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

		(new BusLineLoader(rootView)).execute();

		return rootView;

	}

	protected class BusLineLoader extends AsyncDataLoader<List<BusLine>> {
		private BusLineLoader(View rootView) {
			super(rootView);
		}

		@Override
		protected List<BusLine> doInBackground(Void... unused) {
			AccessFacade accessFacade = new AccessFacade();

			List<BusLine> busLines = accessFacade.getBusLineAccess()
					.getNextBusLines();

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
			if (busLines != null) {
				ListView buslines = (ListView) rootView
						.findViewById(R.id.buslines);

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
						rootView.getContext(), busList,
						R.layout.custom_row_view, new String[] { "title",
								"direction", }, new int[] { R.id.title,
								R.id.description }

				);

				buslines.setAdapter(adapter);
			}
		}
	}
}
