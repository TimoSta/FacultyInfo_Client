package de.uni_passau.facultyinfo.client.model.dto;

public class SportsCourseSearchResult extends TypedSearchResult {

	public static final int CATEGORY = 1;
	public static final int COURSE = 2;

	public SportsCourseSearchResult(int type, String id, String title,
			String subtitle) {
		super(type, id, title, subtitle);
	}

	public SportsCourseSearchResult(int type, String id, String title,
			String subtitle, String supertitle) {
		super(type, id, title, subtitle, supertitle);
	}

	@Override
	public String getTitle() {
		if (title != null) {
			return super.getTitle();
		}
		return super.getSupertitle();
	}
}
