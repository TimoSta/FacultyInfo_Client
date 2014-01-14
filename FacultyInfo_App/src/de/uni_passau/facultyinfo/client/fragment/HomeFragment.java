package de.uni_passau.facultyinfo.client.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.activity.MainActivity;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.BusLine;
import de.uni_passau.facultyinfo.client.model.dto.MenuItem;
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

		getActivity().getActionBar().setTitle(
				getActivity().getApplicationContext().getString(
						R.string.title_home));
		getActivity().getActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_STANDARD);

		(new NewsLoader(rootView)).execute();
		(new BusLineLoader(rootView)).execute();
		(new MenuLoader(rootView)).execute();

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

			if (news != null && !news.isEmpty()) {
				TextView homeNewsTitle = (TextView) HomeFragment.this.rootView
						.findViewById(R.id.home_newstitle);
				homeNewsTitle.setText(news.get(0).getTitle());

				TextView homeNewsDescription = (TextView) HomeFragment.this.rootView
						.findViewById(R.id.home_newsdescription);
				homeNewsDescription.setText(news.get(0).getDescription());

				OnClickListener onNewsClickListener = new OnClickListener() {

					@Override
					public void onClick(View v) {
						System.out.println("open NewsFragment");
						((MainActivity) getActivity()).selectItem(1);
						// open NewsFragment
					}
				};

				homeNewsTitle.setOnClickListener(onNewsClickListener);
				homeNewsDescription.setOnClickListener(onNewsClickListener);
			}

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
			ListView buslineList = (ListView) rootView
					.findViewById(R.id.home_buslines);

			final ArrayList<HashMap<String, String>> busList = new ArrayList<HashMap<String, String>>();

			if (!busLines.isEmpty()) {
				System.out.println("busLines.isEmpty()");

				for (int i = 0; i < (busLines.size() >= 2 ? 2 : busLines.size()); i++) {

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
					busList, R.layout.home_two_line_row_view, new String[] {
							"title", "direction", }, new int[] {
							R.id.home_toprow, R.id.home_bottomrow }

			);

			buslineList.setAdapter(adapter);

			OnItemClickListener onClickListener = new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					((MainActivity) getActivity()).selectItem(3);
				}
			};

			buslineList.setOnItemClickListener(onClickListener);
		}
	}

	protected class MenuLoader extends AsyncDataLoader<List<MenuItem>> {

		public MenuLoader(View rootView) {
			super(rootView);
		}

		@Override
		protected List<MenuItem> doInBackground(Void... unused) {
			AccessFacade accessFacade = new AccessFacade();
			List<MenuItem> menu = null;

			menu = accessFacade.getMenuAccess().getMenuItems();

			if (menu == null) {
				publishProgress(AsyncDataLoader.NO_CONNECTION_PROGRESS);
				menu = accessFacade.getMenuAccess().getMenuItemsFromCache();
			}

			if (menu == null) {
				menu = new ArrayList<MenuItem>();
			}

			return menu;
		}

		@Override
		protected void onPostExecute(List<MenuItem> menu) {
			ArrayList<HashMap<String, String>> menuList = new ArrayList<HashMap<String, String>>();
			Calendar cal = Calendar.getInstance();
			cal.setFirstDayOfWeek(Calendar.MONDAY);
			cal.setTime(new Date());
			int currentDay = cal.get(Calendar.DAY_OF_WEEK);

			if (currentDay == Calendar.MONDAY) {
				currentDay = MenuItem.MONDAY;
			} else if (currentDay == Calendar.TUESDAY) {
				currentDay = MenuItem.TUESDAY;
			} else if (currentDay == Calendar.WEDNESDAY) {
				currentDay = MenuItem.WEDNESDAY;
			} else if (currentDay == Calendar.THURSDAY) {
				currentDay = MenuItem.THURSDAY;
			} else if (currentDay == Calendar.FRIDAY) {
				currentDay = MenuItem.FRIDAY;
			} else if (currentDay == Calendar.SATURDAY) {
				currentDay = MenuItem.SATURDAY;
			} else if (currentDay == Calendar.SUNDAY) {
				currentDay = MenuItem.SUNDAY;
			}

			for (MenuItem menuItem : menu) {
				if (menuItem.getDayOfWeek() == currentDay) {
					if (menuItem.getType() == MenuItem.TYPE_MAIN) {
						HashMap<String, String> hashMap = new HashMap<String, String>();
						hashMap.put("name", menuItem.getName());
						menuList.add(hashMap);
					}
				}
			}

			ListView listView = (ListView) rootView
					.findViewById(R.id.home_mensa);

			SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
					menuList, R.layout.home_menu_row_view,
					new String[] { "name" }, new int[] { R.id.menu_name });

			listView.setAdapter(adapter);
			OnItemClickListener onClickListener = new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					((MainActivity) getActivity()).selectItem(5);
				}
			};

			listView.setOnItemClickListener(onClickListener);
		}

	}

}
