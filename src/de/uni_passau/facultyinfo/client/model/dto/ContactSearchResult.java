package de.uni_passau.facultyinfo.client.model.dto;

public class ContactSearchResult extends TypedSearchResult {

	public static final int GROUP = 1;
	public static final int PERSON = 2;

	public ContactSearchResult(int type, String id, String title, String subtitle) {
		super(type, id, title, subtitle);
	}

}
