package de.uni_passau.facultyinfo.client.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.activity.DisplayNewsActivity;
import de.uni_passau.facultyinfo.client.activity.MainActivity;
import de.uni_passau.facultyinfo.client.fragment.NewsFragment.NewsLoader;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.BusLine;
import de.uni_passau.facultyinfo.client.model.dto.News;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

public class HomeFragment extends Fragment {
	private View rootView;

	public HomeFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_home, container, false);

		getActivity().setTitle(R.string.title_home);

		// ListView newsView = (ListView) rootView.findViewById(R.id.home_news);
		//
		// List valueList = new ArrayList<String>();
		//
		// // final ArrayList<HashMap<String, String>> homeList = new
		// ArrayList<HashMap<String, String>>();
		// //
		// // for (int i = 0; i < 5; i++) {
		// // String iString = "" + i;
		// // HashMap<String, String> temp1 = new HashMap<String, String>();
		// // temp1.put("id", iString);
		// // temp1.put("name", "Lehrstuhl");
		// // homeList.add(temp1);
		// // }
		// //
		// // SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
		// // homeList, R.layout.custom_row_view, new String[] { "name", },
		// // new int[] { R.id.title });
		//
		// for (int i = 0; i < 5; i++) {
		// valueList.add("News " + i);
		// }
		//
		// ListAdapter adapter1 = new
		// ArrayAdapter<String>(rootView.getContext(),
		// android.R.layout.simple_list_item_1, valueList);
		//
		// newsView.setAdapter(adapter1);
		//
		//
		// ListView busView = (ListView)
		// rootView.findViewById(R.id.home_buslines);
		//
		// List valueListBus = new ArrayList<String>();
		//
		//
		// for (int i = 0; i < 5; i++) {
		// valueListBus.add("Bus " + i);
		// }
		//
		// ListAdapter adapterbus = new
		// ArrayAdapter<String>(rootView.getContext(),
		// android.R.layout.simple_list_item_1, valueList);
		//
		// busView.setAdapter(adapterbus);

		(new NewsLoader(rootView)).execute();
		(new BusLineLoader(rootView)).execute();

		return rootView;
	}

	protected class NewsLoader extends AsyncDataLoader<List<News>> {
		private NewsLoader(View rootView) {
			super(rootView);
		}

		@Override
		protected List<News> doInBackground(Void... unused) {
			System.out.println("NewsActivity->doInBackground");
			AccessFacade accessFacade = new AccessFacade();

			List<News> news = accessFacade.getNewsAccess().getNews();

			if (news == null) {
				publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
				news = accessFacade.getNewsAccess().getNewsFromCache();
			}

			if (news == null) {
				news = Collections.unmodifiableList(new ArrayList<News>());
			}

			return news;
		}

		@Override
		protected void onPostExecute(List<News> news) {
			System.out.println("NewsLoader->onPostExecute");

			TextView homeNewsTitle = (TextView) HomeFragment.this.rootView
					.findViewById(R.id.home_news_title);
			homeNewsTitle.setText(news.get(0).getTitle());

			TextView homeNewsDescription = (TextView) HomeFragment.this.rootView
					.findViewById(R.id.home_news_description);
			homeNewsDescription.setText(news.get(0).getDescription());

			OnClickListener onNewsClickListener = new OnClickListener() {

				@Override
				public void onClick(View v) {
					System.out.println("open NewsFragment");
					((MainActivity)getActivity()).selectItem(1);
					// open NewsFragment
				}
			};

			homeNewsTitle.setOnClickListener(onNewsClickListener);
			homeNewsDescription.setOnClickListener(onNewsClickListener);

		}

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
			ListView buslines = (ListView) rootView
					.findViewById(R.id.home_buslines);

			final ArrayList<HashMap<String, String>> busList = new ArrayList<HashMap<String, String>>();

			if (!busLines.isEmpty()) {
				System.out.println("busLines.isEmpty()");

				for (int i = 0; i < 2; i++) {

					BusLine busLine = busLines.get(i);
					System.out.println("for");
					HashMap<String, String> temp1 = new HashMap<String, String>();
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",
							Locale.GERMAN);
					temp1.put("title", sdf.format(busLine.getDeparture())
							+ " - " + busLine.getLine());
					temp1.put("direction", busLine.getDirection());
					busList.add(temp1);
				}
			}

			SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
					busList, R.layout.custom_row_view, new String[] { "title",
							"direction", }, new int[] { R.id.title,
							R.id.description }

			);

			buslines.setAdapter(adapter);
		}
	}

}
