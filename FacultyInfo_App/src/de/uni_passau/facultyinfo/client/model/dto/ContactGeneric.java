package de.uni_passau.facultyinfo.client.model.dto;

public class ContactGeneric extends Generic {

	public static final int GROUP = 1;
	public static final int PERSON = 2;

	public ContactGeneric(int type, String id, String title, String subtitle) {
		super(type, id, title, subtitle);
	}

}
