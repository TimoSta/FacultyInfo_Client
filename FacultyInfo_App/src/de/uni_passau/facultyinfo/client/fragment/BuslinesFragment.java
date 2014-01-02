package de.uni_passau.facultyinfo.client.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.fragment.NewsFragment.NewsLoader;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.BusLine;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

public class BuslinesFragment extends Fragment {

	public BuslinesFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_buslines, container,
				false);

		getActivity().setTitle(R.string.title_buslines);

		new BusLineLoader(rootView).execute();

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
				publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
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
			// TODO: use bus line information
		}
	}
}
