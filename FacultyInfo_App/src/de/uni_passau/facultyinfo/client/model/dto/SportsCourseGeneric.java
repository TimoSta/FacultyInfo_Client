package de.uni_passau.facultyinfo.client.model.dto;

public class SportsCourseGeneric extends Generic {

	public static final int CATEGORY = 1;
	public static final int COURSE = 2;

	public SportsCourseGeneric(int type, String id, String title,
			String subtitle) {
		super(type, id, title, subtitle);
	}

}
