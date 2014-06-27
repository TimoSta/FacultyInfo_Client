package de.uni_passau.facultyinfo.client.model.dto;

public abstract class TypedSearchResult extends SearchResult {

	protected int type;

	public TypedSearchResult(int type, String id, String title, String subtitle) {
		super(id, title, subtitle);
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
