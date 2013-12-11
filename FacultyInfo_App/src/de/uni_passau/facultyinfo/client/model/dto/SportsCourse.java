package de.uni_passau.facultyinfo.client.model.dto;

import java.sql.Time;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SportsCourse {
	private String id;
	private String title;
	private String description;
	private String number;
	private String details;
	private int dayOfWeek;
	private Time startTime;
	private Time endTime;
	private String location;
	private Date startDate;
	private Date endDate;
	private String host;
	private float price;
	private boolean placesAvailable;

	@JsonCreator
	public SportsCourse(@JsonProperty("id") String id,
			@JsonProperty("title") String title,
			@JsonProperty("description") String description,
			@JsonProperty("number") String number,
			@JsonProperty("details") String details,
			@JsonProperty("dayOfWeek") int dayOfWeek,
			@JsonProperty("startTime") Time startTime,
			@JsonProperty("endTime") Time endTime,
			@JsonProperty("location") String location,
			@JsonProperty("startDate") Date startDate,
			@JsonProperty("endDate") Date endDate,
			@JsonProperty("host") String host,
			@JsonProperty("price") float price,
			@JsonProperty("placesAvailable") boolean placesAvailable) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.number = number;
		this.details = details;
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
		this.host = host;
		this.price = price;
		this.placesAvailable = placesAvailable;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public boolean isPlacesAvailable() {
		return placesAvailable;
	}

	public void setPlacesAvailable(boolean placesAvailable) {
		this.placesAvailable = placesAvailable;
	}
}
