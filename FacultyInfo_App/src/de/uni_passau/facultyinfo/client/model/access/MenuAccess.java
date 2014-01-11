package de.uni_passau.facultyinfo.client.model.access;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.uni_passau.facultyinfo.client.model.connection.RestConnection;
import de.uni_passau.facultyinfo.client.model.dto.MenuItem;
import de.uni_passau.facultyinfo.client.model.dto.News;

/**
 * @author Timo Staudinger
 * 
 */
public class MenuAccess {
	private static final String RESSOURCE = "/menu";

	private RestConnection<MenuItem> restConnection = null;

	protected MenuAccess() {
		restConnection = new RestConnection<MenuItem>(MenuItem.class);
	}

	/**
	 * Gives a list of all Menu items of this week.
	 * 
	 */
	public List<MenuItem> getMenuItems() {
		return getMenuItems(null);
	}

	/**
	 * Gives a list of all Menu items of this week.
	 * 
	 */
	public List<MenuItem> getMenuItems(Integer dayOfWeek) {
		List<MenuItem> menuItems = null;

		menuItems = restConnection.getRessourceAsList(RESSOURCE);

		// TODO: Database operations

		if (menuItems == null) {
			return null;
		}

		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(new Date());
		int currentWeek = cal.get(Calendar.WEEK_OF_YEAR);

		List<MenuItem> filteredMenuItems = new ArrayList<MenuItem>();
		for (MenuItem menuItem : menuItems) {
			cal.setTime(menuItem.getDay());
			if (cal.get(Calendar.WEEK_OF_YEAR) == currentWeek
					&& (dayOfWeek == null || dayOfWeek == menuItem
							.getDayOfWeek())) {
				filteredMenuItems.add(menuItem);
			}
		}

		return Collections.unmodifiableList(filteredMenuItems);
	}

	/**
	 * Gives a list of all Menu items that are cached locally.
	 * 
	 */
	public List<News> getMenuItemsFromCache() {
		return getMenuItemsFromCache(null);
	}

	/**
	 * Gives a list of all Menu items that are cached locally.
	 * 
	 */
	public List<News> getMenuItemsFromCache(Integer dayOfWeek) {
		// TODO: Load cached data
		return Collections.unmodifiableList(new ArrayList<News>());
	}

	/**
	 * Gives detailed information about a specific Menu item.
	 * 
	 */
	public MenuItem getNews(String id) {
		MenuItem menuItem = null;

		menuItem = restConnection.getRessource(RESSOURCE + '/' + id);

		// TODO: Database operations

		return menuItem;
	}

	/**
	 * Gives detailed information about a specific Menu item that is cached locally.
	 * 
	 */
	public MenuItem getNewsFromCache(String newsId) {
		// TODO: load cached data
		return null;
	}

}
