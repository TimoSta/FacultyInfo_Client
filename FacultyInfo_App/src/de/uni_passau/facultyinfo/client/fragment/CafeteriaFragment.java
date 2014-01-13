package de.uni_passau.facultyinfo.client.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
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

public class CafeteriaFragment extends Fragment {

	private final static String MONDAY = "Montag";
	private final static String TUESDAY = "Dienstag";
	private final static String WEDNESDAY = "Mittwoch";
	private final static String THURSDAY = "Donnerstag";
	private final static String FRIDAY = "Freitag";

	private final static int STUDENT = 1;
	private final static int EMPLOYEE = 2;
	private final static int EXTERNAL = 3;

	private int daySelected;

	private int priceSelected;

	private View rootView;
	
	private ArrayList<HashMap<String, String>> menuList = new ArrayList<HashMap<String, String>>();

	public CafeteriaFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_cafeteria, container,
				false);

		setHasOptionsMenu(true);

		getActivity().getActionBar().setTitle(
				getActivity().getApplicationContext().getString(
						R.string.title_cafeteria));
		getActivity().getActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_TABS);

		priceSelected = STUDENT;

		TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				System.out.println("onTabUnselected");

			}

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				System.out.println("onTabSelected");
				// TextView textView = null;

				if (tab.getText() == MONDAY) {
					System.out.println("Montag");
					daySelected = MenuItem.MONDAY;
					(new MenuLoader(rootView)).execute();
					fillList(priceSelected); 
				} else if (tab.getText() == TUESDAY) {
					System.out.println("Dienstag");
					daySelected = MenuItem.TUESDAY;
					(new MenuLoader(rootView)).execute();
					fillList(priceSelected); 
				} else if (tab.getText() == WEDNESDAY) {
					daySelected = MenuItem.WEDNESDAY;
					(new MenuLoader(rootView)).execute();
					fillList(priceSelected); 
				} else if (tab.getText() == THURSDAY) {
					daySelected = MenuItem.THURSDAY;
					(new MenuLoader(rootView)).execute();
					fillList(priceSelected); 
				} else if (tab.getText() == FRIDAY) {
					daySelected = MenuItem.FRIDAY;
					(new MenuLoader(rootView)).execute();
					fillList(priceSelected); 
				}

				// mViewPager.setCurrentItem(tab.getPosition());
				System.out.println("tabposition: " + tab.getPosition());
				// fillList(STUDENT);

			}

			@Override
			public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				System.out.println("onTabReselected");

			}
		};

		getActivity().getActionBar().removeAllTabs();
		Tab mon = (Tab) getActivity().getActionBar().newTab().setText(MONDAY)
				.setTabListener(tabListener);
		getActivity().getActionBar().addTab(mon);

		Tab tue = (Tab) getActivity().getActionBar().newTab().setText(TUESDAY)
				.setTabListener(tabListener);
		getActivity().getActionBar().addTab(tue);

		Tab wed = (Tab) getActivity().getActionBar().newTab()
				.setText(WEDNESDAY).setTabListener(tabListener);
		getActivity().getActionBar().addTab(wed);

		Tab thu = (Tab) getActivity().getActionBar().newTab().setText(THURSDAY)
				.setTabListener(tabListener);
		getActivity().getActionBar().addTab(thu);

		Tab fri = (Tab) getActivity().getActionBar().newTab().setText(FRIDAY)
				.setTabListener(tabListener);
		getActivity().getActionBar().addTab(fri);

		return rootView;

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.cafeteria, menu);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onPrepareOptionsMenu(menu);
		android.view.MenuItem menuItem;
		menuItem = menu.findItem(R.id.student);
		switch (priceSelected) {
		case STUDENT:
			menuItem = menu.findItem(R.id.student);
			priceSelected=STUDENT; 
			fillList(priceSelected); 
			break;
		case EMPLOYEE:
			menuItem = menu.findItem(R.id.employee);
			priceSelected=EMPLOYEE; 
			fillList(priceSelected); 
			break;
		case EXTERNAL:
			menuItem = menu.findItem(R.id.external);
			priceSelected=EXTERNAL; 
			fillList(priceSelected); 
			break;
		}
		menuItem.setVisible(false);
	}


	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.student:
			return true;
		case R.id.external:
			return true;
		case R.id.employee:
			return true;
		default:
			return false;
		}
	}

	protected class MenuLoader extends AsyncDataLoader<List<MenuItem>> {

		public MenuLoader(View rootView) {
			super(rootView);
		}

		@Override
		protected List<MenuItem> doInBackground(Void... unused) {
			System.out.println(daySelected);
			AccessFacade accessFacade = new AccessFacade();
			List<MenuItem> menu = null;

			menu = accessFacade.getMenuAccess().getMenuItems(daySelected);

			if (menu == null) {
				publishProgress(AsyncDataLoader.NO_CONNECTION_PROGRESS);
				menu = accessFacade.getMenuAccess().getMenuItemsFromCache(
						daySelected);
			}

			if (menu == null) {
				menu = new ArrayList<MenuItem>();
			}

			return menu;
		}

		@Override
		protected void onPostExecute(List<MenuItem> menu) {
			System.out.println("onPostExecute");
			int type = 0;

//			final ArrayList<HashMap<String, String>> menuList = new ArrayList<HashMap<String, String>>();
//menuList.removeAll(); 
//			menuList=null; 
			menuList.clear(); 
			for (MenuItem menuItem : menu) {
				System.out.println(menuItem.getName());
				System.out.println(menuItem.getType());
				String courseType = null;
				boolean first = false;
				if (menuItem.getType() != type) {
					first = true;
					type = menuItem.getType();
				}

				HashMap<String, String> temp1 = new HashMap<String, String>();
				temp1.put("name", menuItem.getName());

				temp1.put("studentPrice",
						String.valueOf(menuItem.getPriceStudent()));
				temp1.put("employeePrice",
						String.valueOf(menuItem.getPriceEmployee()));
				temp1.put("externalPrice",
						String.valueOf(menuItem.getPriceEmployee()));
				temp1.put("first", first ? "true" : "false");

				if (menuItem.getType() == MenuItem.TYPE_SOUP) {
					courseType = "Suppen";
				} else if (menuItem.getType() == MenuItem.TYPE_MAIN) {
					courseType = "Hauptspeise";
				} else if (menuItem.getType() == MenuItem.TYPE_APPETIZER) {
					courseType = "Beilagen";
				} else if (menuItem.getType() == MenuItem.TYPE_DESSERT) {
					courseType = "Nachspeisen";
				}

				temp1.put("type", courseType);

				menuList.add(temp1);
			}

//			fillList(menuList, STUDENT);

		}

	}

//	void fillList(final ArrayList<HashMap<String, String>> menuList, int price) {
		void fillList(int price) {

		ListView listView = (ListView) rootView
				.findViewById(R.id.cafeteria_list);

		SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
				menuList, R.layout.menu_row_view, new String[] { "type",
						"name", "studentPrice" }, new int[] {
						R.id.menu_row_header, R.id.menu_name, R.id.menu_price }

		) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				if (menuList.get(position).get("first").equals("true")) {

					((TextView) view.findViewById(R.id.menu_row_header))
							.setVisibility(TextView.VISIBLE);
				}

				return view;
			}
		};

		listView.setAdapter(adapter);

	}
}