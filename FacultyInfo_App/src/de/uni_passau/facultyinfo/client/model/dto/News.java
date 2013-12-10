package de.uni_passau.facultyinfo.client.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class News {
	private String id;
	private String title;
	private String description;
	private String url;
	private String text;
	private Date publicationDate;

	@JsonCreator
	public News(@JsonProperty("id") String id,
			@JsonProperty("title") String title,
			@JsonProperty("description") String description,
			@JsonProperty("url") String url, @JsonProperty("text") String text,
			@JsonProperty("publicationDate") Date publicationDate) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.url = url;
		this.text = text;
		this.publicationDate = publicationDate;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

}
