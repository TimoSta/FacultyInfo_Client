package de.uni_passau.facultyinfo.client.model.dto;

import java.sql.Time;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BusinessHours {
	public static final int TYPE_CAFETERIA = 1;
	public static final int TYPE_LIBRARY = 2;

	private String id;
	private Date day;
	private String facility;
	private int type;
	private Time openingTime;
	private Time closingTime;

	@JsonCreator
	public BusinessHours(@JsonProperty("id") String id,
			@JsonProperty("day") Date day,
			@JsonProperty("facility") String facility,
			@JsonProperty("type") int type,
			@JsonProperty("openingTime") Time openingTime,
			@JsonProperty("closingTime") Time closingTime) {
		super();
		this.id = id;
		this.day = day;
		this.facility = facility;
		this.type = type;
		this.openingTime = openingTime;
		this.closingTime = closingTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Time getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(Time openingTime) {
		this.openingTime = openingTime;
	}

	public Time getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(Time closingTime) {
		this.closingTime = closingTime;
	}
}
