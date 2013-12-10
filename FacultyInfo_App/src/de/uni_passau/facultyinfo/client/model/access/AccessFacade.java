package de.uni_passau.facultyinfo.client.model.access;


public class AccessFacade {
	private NewsAccess newsAccess = null;

	public NewsAccess getNewsAccess() {
		if (newsAccess == null) {
			newsAccess = new NewsAccess();
		}

		return newsAccess;
	}
}
