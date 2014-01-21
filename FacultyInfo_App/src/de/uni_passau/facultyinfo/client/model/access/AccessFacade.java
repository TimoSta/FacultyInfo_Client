package de.uni_passau.facultyinfo.client.model.access;

/**
 * 
 * @author Timo Staudinger
 * 
 */
public class AccessFacade {
	private FaqAccess faqAccess = null;
	private EventAccess eventAccess = null;
	private TimetableAccess timetableAccess = null;
	private MapMarkerAccess mapMarkerAccess = null;
	private SportsCourseAccess sportsCourseAccess = null;
	private ContactPersonAccess contactPersonAccess = null;
	private MenuAccess menuAccess = null;

	public NewsAccess getNewsAccess() {
		return NewsAccess.getInstance();
	}

	public FaqAccess getFaqAccess() {
		if (faqAccess == null) {
			faqAccess = new FaqAccess();
		}
		return faqAccess;
	}

	public BusLineAccess getBusLineAccess() {
		return BusLineAccess.getInstance();
	}

	public EventAccess getEventAccess() {
		if (eventAccess == null) {
			eventAccess = new EventAccess();
		}
		return eventAccess;
	}

	public TimetableAccess getTimetableAccess() {
		if (timetableAccess == null) {
			timetableAccess = new TimetableAccess();
		}
		return timetableAccess;
	}

	public MapMarkerAccess getMapMarkerAccess() {
		if (mapMarkerAccess == null) {
			mapMarkerAccess = new MapMarkerAccess();
		}
		return mapMarkerAccess;
	}

	public SportsCourseAccess getSportsCourseAccess() {
		if (sportsCourseAccess == null) {
			sportsCourseAccess = new SportsCourseAccess();
		}
		return sportsCourseAccess;
	}

	public ContactPersonAccess getContactPersonAccess() {
		if (contactPersonAccess == null) {
			contactPersonAccess = new ContactPersonAccess();
		}
		return contactPersonAccess;
	}

	public BusinessHoursAccess getBusinessHoursAccess() {
		return BusinessHoursAccess.getInstance();
	}

	public MenuAccess getMenuAccess() {
		if (menuAccess == null) {
			menuAccess = new MenuAccess();
		}
		return menuAccess;
	}
}
