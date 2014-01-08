package de.uni_passau.facultyinfo.client.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.fragment.NewsFragment.NewsLoader;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.BusLine;
import de.uni_passau.facultyinfo.client.model.dto.News;
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
			ListView buslines = (ListView) rootView.findViewById(R.id.buslines); 
			
			final ArrayList<HashMap<String, String>> busList = new ArrayList<HashMap<String, String>>();

			for (BusLine busLine: busLines) {
				System.out.println("for"); 
				HashMap<String, String> temp1 = new HashMap<String, String>();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",
						Locale.GERMAN);
				temp1.put("title", sdf.format(busLine.getDeparture()) + " - " + busLine.getLine());
				temp1.put("direction", busLine.getDirection()); 
				busList.add(temp1);
			}


			SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
					busList, R.layout.custom_row_view, new String[] { "title",
							"direction", }, new int[] { R.id.title,
							R.id.description }

			);
			
			buslines.setAdapter(adapter);
			
			
//			TableLayout buslines = (TableLayout) rootView
//					.findViewById(R.id.buslines);
//			TableRow heading = new TableRow(rootView.getContext());
//
//			TextView departureHeading = new TextView(rootView.getContext());
//			departureHeading.setText("Abfahrt");
//			departureHeading.setBackgroundColor(Color.parseColor("#FFAA5A"));
//			departureHeading.setLayoutParams(new TableRow.LayoutParams(0,
//					android.view.ViewGroup.LayoutParams.FILL_PARENT, 2));
//			heading.addView(departureHeading, 0);
//
//			TextView lineHeading = new TextView(rootView.getContext());
//			lineHeading.setText("Nr");
//			lineHeading.setBackgroundColor(Color.parseColor("#FFAA5A"));
//			lineHeading.setLayoutParams(new TableRow.LayoutParams(0,
//					android.view.ViewGroup.LayoutParams.FILL_PARENT, 1));
//			heading.addView(lineHeading, 1);
//
//			TextView directionHeading = new TextView(rootView.getContext());
//			directionHeading.setText("Richtung");
//			directionHeading.setBackgroundColor(Color.parseColor("#FFAA5A"));
//			departureHeading.setLayoutParams(new TableRow.LayoutParams(0,
//					android.view.ViewGroup.LayoutParams.FILL_PARENT, 3));
//			heading.addView(directionHeading, 2);
//
//			buslines.addView(heading);
			
			if(busLines.isEmpty()){
				System.out.println("busLines empty"); 
			}
		

//			for (BusLine bus : busLines) {
//				System.out.println("foreach"); 
//				TableRow busRow = new TableRow(rootView.getContext());
//				TextView departure = new TextView(rootView.getContext());
//				SimpleDateFormat sdf = new SimpleDateFormat("kk:mm",
//						Locale.GERMAN);
//				departure.setText(sdf.format(bus.getDeparture()));
//				departure.setLayoutParams(new TableRow.LayoutParams(0,
//						android.view.ViewGroup.LayoutParams.FILL_PARENT, 2));
//				// departure.setLayoutParams(bus.LayoutParams(3));
//				busRow.addView(departure, 0);
//
//				TextView line = new TextView(rootView.getContext());
//				line.setText(bus.getLine());
//				line.setLayoutParams(new TableRow.LayoutParams(0,
//						android.view.ViewGroup.LayoutParams.FILL_PARENT, 1));
//				busRow.addView(line, 1);
//
//				TextView direction = new TextView(rootView.getContext());
//				direction.setText(bus.getDirection());
//				departure.setLayoutParams(new TableRow.LayoutParams(0,
//						android.view.ViewGroup.LayoutParams.FILL_PARENT, 3));
//				busRow.addView(direction, 2);
//
//				buslines.addView(busRow);
//			}
			
		}
	}
}
