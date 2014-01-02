package de.uni_passau.facultyinfo.client.model.access;

/**
 * 
 * @author Timo Staudinger
 *
 */
public class AccessFacade {
	private NewsAccess newsAccess = null;
	private FaqAccess faqAccess = null;
	private BusLineAccess busLineAccess = null;

	public NewsAccess getNewsAccess() {
		if (newsAccess == null) {
			newsAccess = new NewsAccess();
		}
		return newsAccess;
	}

	public FaqAccess getFaqAccess() {
		if (faqAccess == null) {
			faqAccess = new FaqAccess();
		}
		return faqAccess;
	}

	public BusLineAccess getBusLineAccess() {
		if (busLineAccess == null) {
			busLineAccess = new BusLineAccess();
		}
		return busLineAccess;
	}
}
