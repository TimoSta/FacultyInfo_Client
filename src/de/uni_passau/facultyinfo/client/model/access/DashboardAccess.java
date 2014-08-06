package de.uni_passau.facultyinfo.client.model.access;

import java.util.Date;

import de.uni_passau.facultyinfo.client.model.connection.RestConnection;
import de.uni_passau.facultyinfo.client.model.dto.Dashboard;
import de.uni_passau.facultyinfo.client.util.CacheHelper;

/**
 * @author Timo Staudinger
 * 
 */
public class DashboardAccess extends Access {
	private static final String RESSOURCE = "/dashboard";

	private static DashboardAccess instance = null;

	protected static DashboardAccess getInstance() {
		if (instance == null) {
			instance = new DashboardAccess();
		}
		return instance;
	}

	private RestConnection<Dashboard> restConnection = null;

	private Dashboard cachedDashboard = null;
	private Date cachedDashboardTimestamp = null;

	private DashboardAccess() {
		restConnection = new RestConnection<Dashboard>(Dashboard.class);
	}

	/**
	 * Gives the Dashboard
	 * 
	 */
	public Dashboard getDashboard() {
		return getDashboard(false);
	}

	/**
	 * Gives the Dashboard
	 * 
	 */
	public Dashboard getDashboard(boolean forceRefresh) {
		Dashboard dashboard = null;

		if (forceRefresh
				|| cachedDashboard == null
				|| cachedDashboardTimestamp == null
				|| cachedDashboardTimestamp.before(CacheHelper
						.getExpiringDate())) {

			dashboard = restConnection.getRessource(RESSOURCE);

			if (dashboard != null) {
				cachedDashboard = dashboard;
				cachedDashboardTimestamp = new Date();
			}

		} else {
			dashboard = cachedDashboard;
		}

		if (dashboard == null) {
			return null;
		}

		return dashboard;
	}
}
