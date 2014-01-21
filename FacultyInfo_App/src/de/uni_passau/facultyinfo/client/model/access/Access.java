package de.uni_passau.facultyinfo.client.model.access;

import de.uni_passau.facultyinfo.client.application.FacultyInfoApplication;
import de.uni_passau.facultyinfo.client.model.connection.CacheOpenHelper;

public abstract class Access {
	protected static final int PREVIEW_TRUE = 1;
	protected static final int PREVIEW_FALSE = 0;

	private CacheOpenHelper cacheOpenHelper = null;

	protected CacheOpenHelper getCacheOpenHelper() {
		if (cacheOpenHelper == null) {
			cacheOpenHelper = new CacheOpenHelper(
					FacultyInfoApplication.getContext());
		}
		return cacheOpenHelper;
	}
}
