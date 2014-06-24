package de.uni_passau.facultyinfo.client.model.dto;

public abstract class Generic {
	protected int type;
	protected String id;
	protected String title;
	protected String subtitle;

	public Generic(int type, String id, String title, String subtitle) {
		super();
		this.type = type;
		this.id = id;
		this.title = title;
		this.subtitle = subtitle;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

}
