package de.uni_passau.facultyinfo.client.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.activity.MainActivity;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.BusLine;
import de.uni_passau.facultyinfo.client.model.dto.Dashboard;
import de.uni_passau.facultyinfo.client.model.dto.MenuItem;
import de.uni_passau.facultyinfo.client.model.dto.News;
import de.uni_passau.facultyinfo.client.util.SwipeRefreshAsyncDataLoader;

public class HomeFragment extends SwipeRefreshLayoutFragment {

	public HomeFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final SwipeRefreshLayout rootView = (SwipeRefreshLayout) inflater
				.inflate(R.layout.fragment_home, container, false);

		rootView.setColorScheme(R.color.loading_indicator_1,
				R.color.loading_indicator_2, R.color.loading_indicator_3,
				R.color.loading_indicator_4);
		rootView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new DashboardLoader(rootView, true).execute();
			}
		});

		initializeSwipeRefresh(rootView, new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new DashboardLoader(rootView, true).execute();

			}
		});

		Activity activity = getActivity();
		ActionBar actionBar = activity.getActionBar();
		actionBar.setTitle(activity.getApplicationContext().getString(
				R.string.title_home));
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

		(new DashboardLoader(rootView)).execute();

		return rootView;
	}

	protected class DashboardLoader extends
			SwipeRefreshAsyncDataLoader<Dashboard> {
		private boolean forceRefresh = false;

		private DashboardLoader(SwipeRefreshLayout rootView) {
			super(rootView);
		}

		private DashboardLoader(SwipeRefreshLayout rootView,
				boolean forceRefresh) {
			super(rootView);
			this.forceRefresh = forceRefresh;
		}

		@Override
		protected Dashboard doInBackground(Void... unused) {
			AccessFacade accessFacade = new AccessFacade();

			Dashboard dashboard = accessFacade.getDashboardAccess()
					.getDashboard(forceRefresh);

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
						.getMenuItemsFromCache(currentDay, MenuItem.TYPE_MAIN);

				dashboard = new Dashboard(news, busLines, menuItems);

			}

			return dashboard;
		}

		@Override
		protected void onPostExecute(Dashboard dashboard) {
			super.onPostExecute(dashboard);

			if (dashboard != null) {
				if (dashboard.getNews() != null) {
					TextView homeNewsTitle = (TextView) this.rootView
							.findViewById(R.id.home_newstitle);
					homeNewsTitle.setText(dashboard.getNews().getTitle());

					TextView homeNewsDescription = (TextView) this.rootView
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

					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",
							Locale.GERMAN);
					OnClickListener onClickListener = new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							((MainActivity) getActivity())
									.selectItem(MainActivity.BUSLINES);

						}
					};

					LinearLayout buslineFirst = (LinearLayout) rootView
							.findViewById(R.id.buslineFirst);
					buslineFirst.setVisibility(View.VISIBLE);
					TextView buslineFirstTop = (TextView) rootView
							.findViewById(R.id.buslineFirstTop);
					BusLine busline = busLines.get(0);
					buslineFirstTop.setText(sdf.format(busline.getDeparture())
							+ " - " + busline.getLine());
					TextView buslineFirstBottom = (TextView) rootView
							.findViewById(R.id.buslineFirstBottom);
					buslineFirstBottom.setText(busline.getDirection());
					buslineFirst.setOnClickListener(onClickListener);

					if (busLines.size() > 1) {

						View buslineSecond = rootView
								.findViewById(R.id.buslineSecond);
						buslineSecond.setVisibility(View.VISIBLE);
						TextView buslineSecondTop = (TextView) rootView
								.findViewById(R.id.buslineSecondTop);
						busline = busLines.get(1);
						buslineSecondTop.setText(sdf.format(busline
								.getDeparture()) + " - " + busline.getLine());
						TextView buslineSecondBottom = (TextView) rootView
								.findViewById(R.id.buslineSecondBottom);
						buslineSecondBottom.setText(busline.getDirection());
						buslineSecond.setOnClickListener(onClickListener);
					}

				}

				if (true || dashboard.getMenuItems() != null
						&& dashboard.getMenuItems().size() > 0) {
					List<MenuItem> menuItems = dashboard.getMenuItems();

					menuItems = new ArrayList<MenuItem>();
					menuItems.add(new MenuItem("123", null, "Test", 0, 00, 0,
							0, 0));
					menuItems.add(new MenuItem("1232", null, "Test2", 0, 00, 0,
							0, 0));
					menuItems.add(new MenuItem("12qwe32", null, "Test3", 0, 00,
							0, 0, 0));
					menuItems.add(new MenuItem("1sd232", null, "Test4", 0, 00,
							0, 0, 0));
					menuItems.add(new MenuItem("12fdsg32", null, "Test5", 0,
							00, 0, 0, 0));

					OnClickListener onClickListener = new OnClickListener() {

						@Override
						public void onClick(View v) {
							((MainActivity) getActivity())
									.selectItem(MainActivity.MENU);

						}
					};

					MenuItem item = menuItems.get(0);
					TextView menuView = (TextView) rootView
							.findViewById(R.id.mensaFirst);
					menuView.setVisibility(View.VISIBLE);
					menuView.setText(item.getName());
					menuView.setOnClickListener(onClickListener);
					if (menuItems.size() > 1) {
						item = menuItems.get(1);
						menuView = (TextView) rootView
								.findViewById(R.id.mensaSecond);
						menuView.setVisibility(View.VISIBLE);
						menuView.setText(item.getName());
						menuView.setOnClickListener(onClickListener);
						if (menuItems.size() > 2) {
							item = menuItems.get(2);
							menuView = (TextView) rootView
									.findViewById(R.id.mensaThird);
							menuView.setVisibility(View.VISIBLE);
							menuView.setText(item.getName());
							menuView.setOnClickListener(onClickListener);
							if (menuItems.size() > 3) {
								item = menuItems.get(3);
								menuView = (TextView) rootView
										.findViewById(R.id.mensaFourth);
								menuView.setVisibility(View.VISIBLE);
								menuView.setText(item.getName());
								menuView.setOnClickListener(onClickListener);
								if (menuItems.size() > 4) {
									item = menuItems.get(4);
									menuView = (TextView) rootView
											.findViewById(R.id.mensaFifth);
									menuView.setVisibility(View.VISIBLE);
									menuView.setText(item.getName());
									menuView.setOnClickListener(onClickListener);
								}
							}
						}
					}
				}
			}
		}
	}

}
