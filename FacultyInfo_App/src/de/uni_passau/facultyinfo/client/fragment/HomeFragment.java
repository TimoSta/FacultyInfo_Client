package de.uni_passau.facultyinfo.client.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
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
import de.uni_passau.facultyinfo.client.model.dto.Dashboard;
import de.uni_passau.facultyinfo.client.model.dto.MenuItem;
import de.uni_passau.facultyinfo.client.model.dto.News;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

public class HomeFragment extends Fragment {
	private View rootView;

	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_home, container, false);

		Activity activity = getActivity();
		ActionBar actionBar = activity.getActionBar();
		actionBar.setTitle(activity.getApplicationContext().getString(
				R.string.title_home));
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

		(new DashboardLoader(rootView)).execute();

		return rootView;
	}

	protected class DashboardLoader extends AsyncDataLoader<Dashboard> {
		private DashboardLoader(View rootView) {
			super(rootView);
		}

		@Override
		protected Dashboard doInBackground(Void... unused) {
			AccessFacade accessFacade = new AccessFacade();

			Dashboard dashboard = accessFacade.getDashboardAccess()
					.getDashboard();

			if (dashboard == null) {
				publishProgress(DashboardLoader.NO_CONNECTION_PROGRESS);
				List<News> newsList = accessFacade.getNewsAccess()
						.getNewsFromCache();
				News news = newsList != null && newsList.size() > 0 ? newsList
						.get(0) : null;

				List<BusLine> busLines = accessFacade.getBusLineAccess()
						.getNextBusLinesFromCache();
				if (busLines != null && busLines.size() > 0) {
					busLines = busLines.subList(0, busLines.size() > 1 ? 1 : 0);
				}

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
				List<MenuItem> menuItems = accessFacade.getMenuAccess()
						.getMenuItemsFromCache(currentDay);
				for (MenuItem menuItem : menuItems) {
					if (menuItem.getType() != MenuItem.TYPE_MAIN) {
						menuItems.remove(menuItem);
					}
				}

				dashboard = new Dashboard(news, busLines, menuItems);

			}

			return dashboard;
		}

		@Override
		protected void onPostExecute(Dashboard dashboard) {

			if (dashboard != null) {
				if (dashboard.getNews() != null) {
					TextView homeNewsTitle = (TextView) HomeFragment.this.rootView
							.findViewById(R.id.home_newstitle);
					homeNewsTitle.setText(dashboard.getNews().getTitle());

					TextView homeNewsDescription = (TextView) HomeFragment.this.rootView
							.findViewById(R.id.home_newsdescription);
					homeNewsDescription.setText(dashboard.getNews()
							.getDescription());

					OnClickListener onNewsClickListener = new OnClickListener() {
						@Override
						public void onClick(View v) {
							((MainActivity) getActivity())
									.selectItem(MainActivity.NEWS);
						}
					};

					homeNewsTitle.setOnClickListener(onNewsClickListener);
					homeNewsDescription.setOnClickListener(onNewsClickListener);
				}

				if (dashboard.getBusLines() != null
						&& dashboard.getBusLines().size() > 0) {
					List<BusLine> busLines = dashboard.getBusLines();

					ListView buslineList = (ListView) rootView
							.findViewById(R.id.home_buslines);

					final ArrayList<HashMap<String, String>> busList = new ArrayList<HashMap<String, String>>();

					if (!busLines.isEmpty()) {
						for (int i = 0; i < (busLines.size() >= 2 ? 2
								: busLines.size()); i++) {

							BusLine busLine = busLines.get(i);
							HashMap<String, String> listEntry = new HashMap<String, String>();
							SimpleDateFormat sdf = new SimpleDateFormat(
									"HH:mm", Locale.GERMAN);
							listEntry.put("title",
									sdf.format(busLine.getDeparture()) + " - "
											+ busLine.getLine());
							listEntry.put("direction", busLine.getDirection());
							busList.add(listEntry);
						}
					}

					SimpleAdapter adapter = new SimpleAdapter(
							rootView.getContext(), busList,
							R.layout.home_two_line_row_view, new String[] {
									"title", "direction", }, new int[] {
									R.id.home_toprow, R.id.home_bottomrow }

					);

					buslineList.setAdapter(adapter);

					OnItemClickListener onClickListener = new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							((MainActivity) getActivity())
									.selectItem(MainActivity.BUSLINES);
						}
					};

					buslineList.setOnItemClickListener(onClickListener);
				}

				if (dashboard.getMenuItems() != null
						&& dashboard.getMenuItems().size() > 0) {
					List<MenuItem> menuItems = dashboard.getMenuItems();

					ArrayList<HashMap<String, String>> menuList = new ArrayList<HashMap<String, String>>();

					for (MenuItem menuItem : menuItems) {
						HashMap<String, String> hashMap = new HashMap<String, String>();
						hashMap.put("name", menuItem.getName());
						menuList.add(hashMap);
					}

					ListView listView = (ListView) rootView
							.findViewById(R.id.home_mensa);

					SimpleAdapter adapter = new SimpleAdapter(
							rootView.getContext(), menuList,
							R.layout.home_menu_row_view,
							new String[] { "name" },
							new int[] { R.id.menu_name });

					listView.setAdapter(adapter);
					OnItemClickListener onClickListener = new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							((MainActivity) getActivity())
									.selectItem(MainActivity.MENU);
						}
					};

					listView.setOnItemClickListener(onClickListener);
				}
			}
		}
	}

}
