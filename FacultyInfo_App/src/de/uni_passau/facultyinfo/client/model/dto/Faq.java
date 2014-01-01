package de.uni_passau.facultyinfo.client.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Faq {
	private String id;
	@JsonIgnore
	private FaqCategory category;
	private String title;
	private String text;

	@JsonCreator
	public Faq(@JsonProperty("id") String id,
			@JsonProperty("title") String title,
			@JsonProperty("text") String text) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FaqCategory getCategory() {
		return category;
	}

	public void setCategory(FaqCategory category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
