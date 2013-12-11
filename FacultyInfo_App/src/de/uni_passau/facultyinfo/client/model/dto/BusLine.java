package de.uni_passau.facultyinfo.client.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BusLine {
	private String id;
	private String line;
	private String direction;
	private Date departure;

	@JsonCreator
	public BusLine(@JsonProperty("line") String line,
			@JsonProperty("direction") String direction,
			@JsonProperty("departure") Date departure) {
		super();
		this.line = line;
		this.direction = direction;
		this.departure = departure;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Date getDeparture() {
		return departure;
	}

	public void setDeparture(Date departure) {
		this.departure = departure;
	}
}
