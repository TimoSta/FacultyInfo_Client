package de.uni_passau.facultyinfo.client.model.dto;

public abstract class SearchResult {
	protected String id;
	protected String title;
	protected String subtitle;
	protected String supertitle;

	public SearchResult(String id, String title, String subtitle) {
		super();
		this.id = id;
		this.title = title;
		this.subtitle = subtitle;
	}

	public SearchResult(String id, String title, String subtitle,
			String supertitle) {
		super();
		this.id = id;
		this.title = title;
		this.subtitle = subtitle;
		this.supertitle = supertitle;
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

	public String getSupertitle() {
		return supertitle;
	}

	public void setSupertitle(String supertitle) {
		this.supertitle = supertitle;
	}

}
