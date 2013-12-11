package de.uni_passau.facultyinfo.client.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Event {
	private String id;
	private String title;
	private String subtitle;
	private String location;
	private String description;
	private Date startDate;
	private Date endDate;
	private String host;
	private String url;

	@JsonCreator
	public Event(@JsonProperty("id") String id,
			@JsonProperty("title") String title,
			@JsonProperty("subtitle") String subtitle,
			@JsonProperty("location") String location,
			@JsonProperty("description") String description,
			@JsonProperty("startDate") Date startDate,
			@JsonProperty("endDate") Date endDate,
			@JsonProperty("host") String host, @JsonProperty("url") String url) {
		super();
		this.id = id;
		this.title = title;
		this.subtitle = subtitle;
		this.location = location;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.host = host;
		this.url = url;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
