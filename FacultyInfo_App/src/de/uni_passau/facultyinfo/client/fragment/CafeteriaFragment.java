package de.uni_passau.facultyinfo.client.fragment;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.MenuItem;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;
import de.uni_passau.facultyinfo.client.util.SwipeRefreshAsyncDataLoader;

public class CafeteriaFragment extends SwipeRefreshLayoutFragment {

	private final static String MONDAY = "Montag";
	private final static String TUESDAY = "Dienstag";
	private final static String WEDNESDAY = "Mittwoch";
	private final static String THURSDAY = "Donnerstag";
	private final static String FRIDAY = "Freitag";

	private final static int STUDENT = 1;
	private final static int EMPLOYEE = 2;
	private final static int EXTERNAL = 3;

	private SwipeRefreshLayout rootView;

	private int currentDay = MenuItem.MONDAY;
	private int currentPrice = STUDENT;

	private List<MenuItem> menuItems = null;

	public CafeteriaFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = (SwipeRefreshLayout) inflater.inflate(R.layout.view_list,
				container, false);

		initializeSwipeRefresh(rootView, new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new MenuLoader(rootView, true).execute();
			}
		});

		setHasOptionsMenu(true);

		Activity activity = getActivity();
		ActionBar actionBar = activity.getActionBar();
		actionBar.removeAllTabs();
		actionBar.setTitle(activity.getApplicationContext().getString(
				R.string.title_cafeteria));
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		TabListener tabListener = new ActionBar.TabListener() {
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction arg1) {
				if (tab.getText() == MONDAY) {
					currentDay = MenuItem.MONDAY;
				} else if (tab.getText() == TUESDAY) {
					currentDay = MenuItem.TUESDAY;
				} else if (tab.getText() == WEDNESDAY) {
					currentDay = MenuItem.WEDNESDAY;
				} else if (tab.getText() == THURSDAY) {
					currentDay = MenuItem.THURSDAY;
				} else if (tab.getText() == FRIDAY) {
					currentDay = MenuItem.FRIDAY;
				}
				updateValues();
			}

			@Override
			public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			}
		};

		actionBar.removeAllTabs();
		Tab mon = (Tab) actionBar.newTab().setText(MONDAY)
				.setTabListener(tabListener);
		actionBar.addTab(mon);

		Tab tue = (Tab) actionBar.newTab().setText(TUESDAY)
				.setTabListener(tabListener);
		actionBar.addTab(tue);

		Tab wed = (Tab) actionBar.newTab().setText(WEDNESDAY)
				.setTabListener(tabListener);
		actionBar.addTab(wed);

		Tab thu = (Tab) actionBar.newTab().setText(THURSDAY)
				.setTabListener(tabListener);
		actionBar.addTab(thu);

		Tab fri = (Tab) actionBar.newTab().setText(FRIDAY)
				.setTabListener(tabListener);
		actionBar.addTab(fri);

		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(new Date());
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == Calendar.MONDAY) {
			actionBar.selectTab(mon);
		} else if (dayOfWeek == Calendar.TUESDAY) {
			actionBar.selectTab(tue);
		} else if (dayOfWeek == Calendar.WEDNESDAY) {
			actionBar.selectTab(wed);
		} else if (dayOfWeek == Calendar.THURSDAY) {
			actionBar.selectTab(thu);
		} else if (dayOfWeek == Calendar.FRIDAY) {
			actionBar.selectTab(fri);
		}

		(new MenuLoader(rootView)).execute();

		return rootView;

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.cafeteria, menu);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);

		switch (currentPrice) {
		case STUDENT:
			setMenuPriceSelectionVisibility(menu, false, true, true);
			break;
		case EMPLOYEE:
			setMenuPriceSelectionVisibility(menu, true, false, true);
			break;
		case EXTERNAL:
			setMenuPriceSelectionVisibility(menu, true, true, false);
			break;
		}
	}

	private void setMenuPriceSelectionVisibility(Menu menu, boolean student,
			boolean employee, boolean external) {
		menu.findItem(R.id.student).setVisible(student);
		menu.findItem(R.id.employee).setVisible(employee);
		menu.findItem(R.id.external).setVisible(external);
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		switch (item.getItemId()) {
		case R.id.student:
			currentPrice = STUDENT;
			break;
		case R.id.external:
			currentPrice = EXTERNAL;
			break;
		case R.id.employee:
			currentPrice = EMPLOYEE;
			break;
		}

		updateValues();
		return true;
	}

	protected class MenuLoader extends
			SwipeRefreshAsyncDataLoader<List<MenuItem>> {
		private boolean forceRefresh = false;

		public MenuLoader(SwipeRefreshLayout rootView) {
			super(rootView);
		}

		private MenuLoader(SwipeRefreshLayout rootView, boolean forceRefresh) {
			super(rootView);
			this.forceRefresh = forceRefresh;
		}

		@Override
		protected List<MenuItem> doInBackground(Void... unused) {
			showLoadingAnimation(true);

			AccessFacade accessFacade = new AccessFacade();
			List<MenuItem> menu = null;

			menu = accessFacade.getMenuAccess().getMenuItems(forceRefresh);

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
			System.out.println("onPostExecute");
			if (menu == null) {
				System.out.println("onPostExecute: menu==null");
			}
			super.onPostExecute(menu);
			menuItems = menu;
			if (menuItems == null) {
				System.out.println("onPostExecute: menuItems==null");
			}
			updateValues();
		}

	}

	private void updateValues() {
		if (menuItems != null) {
			System.out.println("updateValues: menuItems!=null");
			ArrayList<HashMap<String, String>> menuList = new ArrayList<HashMap<String, String>>();
			boolean first = true;
			for (MenuItem menuItem : menuItems) {
				System.out.println("menuItem");
				if (menuItem.getDayOfWeek() == currentDay) {
					double price = currentPrice == STUDENT ? menuItem
							.getPriceStudent()
							: currentPrice == EMPLOYEE ? menuItem
									.getPriceEmployee() : menuItem
									.getPriceExternal();
					if (menuItem.getType() == MenuItem.TYPE_SOUP) {
						menuList.add(generateHashMap(menuItem.getName(), price,
								MenuItem.TYPE_SOUP, first));
						first = false;
					}
				}
			}
			first = true;
			for (MenuItem menuItem : menuItems) {
				if (menuItem.getDayOfWeek() == currentDay) {
					double price = currentPrice == STUDENT ? menuItem
							.getPriceStudent()
							: currentPrice == EMPLOYEE ? menuItem
									.getPriceEmployee() : menuItem
									.getPriceExternal();
					if (menuItem.getType() == MenuItem.TYPE_APPETIZER) {
						menuList.add(generateHashMap(menuItem.getName(), price,
								MenuItem.TYPE_APPETIZER, first));
						first = false;
					}
				}
			}
			first = true;
			for (MenuItem menuItem : menuItems) {
				if (menuItem.getDayOfWeek() == currentDay) {
					double price = currentPrice == STUDENT ? menuItem
							.getPriceStudent()
							: currentPrice == EMPLOYEE ? menuItem
									.getPriceEmployee() : menuItem
									.getPriceExternal();
					if (menuItem.getType() == MenuItem.TYPE_MAIN) {
						menuList.add(generateHashMap(menuItem.getName(), price,
								MenuItem.TYPE_MAIN, first));
						first = false;
					}
				}
			}
			first = true;
			for (MenuItem menuItem : menuItems) {
				if (menuItem.getDayOfWeek() == currentDay) {
					double price = currentPrice == STUDENT ? menuItem
							.getPriceStudent()
							: currentPrice == EMPLOYEE ? menuItem
									.getPriceEmployee() : menuItem
									.getPriceExternal();
					if (menuItem.getType() == MenuItem.TYPE_DESSERT) {
						menuList.add(generateHashMap(menuItem.getName(), price,
								MenuItem.TYPE_DESSERT, first));
						first = false;
					}
				}
			}

			fillList(menuList);
		} else {
			System.out.println("menu==null");
		}
	}

	private HashMap<String, String> generateHashMap(String name, double price,
			int type, boolean first) {
		System.out.println(name);
		DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(
				Locale.GERMANY));
		String priceString = df.format(price) + " €";
		String typeString = "";
		if (type == MenuItem.TYPE_SOUP) {
			typeString = "Suppen";
		} else if (type == MenuItem.TYPE_MAIN) {
			typeString = "Hauptspeise";
		} else if (type == MenuItem.TYPE_APPETIZER) {
			typeString = "Beilagen";
		} else if (type == MenuItem.TYPE_DESSERT) {
			typeString = "Nachspeisen";
		}

		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("name", name);
		hashMap.put("price", priceString);
		hashMap.put("first", first ? "true" : "false");
		hashMap.put("type", typeString);

		return hashMap;
	}

	private void fillList(final List<HashMap<String, String>> menuList) {
		System.out.println("fillList");
		ListView listView = (ListView) rootView.findViewById(R.id.list);

		SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
				menuList, R.layout.row_twoline_special, new String[] { "type",
						"name", "price" }, new int[] { R.id.group, R.id.title,
						R.id.description }

		) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				if (menuList.get(position).get("first").equals("true")) {

					((TextView) view.findViewById(R.id.group))
							.setVisibility(TextView.VISIBLE);
				} else {
					((TextView) view.findViewById(R.id.group))
							.setVisibility(TextView.GONE);
				}

				return view;
			}
		};

		listView.setAdapter(adapter);
	}
}