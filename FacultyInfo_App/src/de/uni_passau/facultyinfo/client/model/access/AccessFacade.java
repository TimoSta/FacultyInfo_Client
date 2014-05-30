package de.uni_passau.facultyinfo.client.model.access;

/**
 * 
 * @author Timo Staudinger
 * 
 */
public class AccessFacade {
	private TimetableAccess timetableAccess = null;

	public NewsAccess getNewsAccess() {
		return NewsAccess.getInstance();
	}

	public FaqAccess getFaqAccess() {
		return FaqAccess.getInstance();
	}

	public BusLineAccess getBusLineAccess() {
		return BusLineAccess.getInstance();
	}

	public EventAccess getEventAccess() {
		return EventAccess.getInstance();
	}

	public TimetableAccess getTimetableAccess() {
		if (timetableAccess == null) {
			timetableAccess = new TimetableAccess();
		}
		return timetableAccess;
	}

	public MapMarkerAccess getMapMarkerAccess() {
		return MapMarkerAccess.getInstance();
	}

	public SportsCourseAccess getSportsCourseAccess() {
		return SportsCourseAccess.getInstance();
	}

	public ContactPersonAccess getContactPersonAccess() {
		return ContactPersonAccess.getInstance();
	}

	public BusinessHoursAccess getBusinessHoursAccess() {
		return BusinessHoursAccess.getInstance();
	}

	public MenuAccess getMenuAccess() {
		return MenuAccess.getInstance();
	}
	
	public DashboardAccess getDashboardAccess() {
		return DashboardAccess.getInstance();
	}
}
