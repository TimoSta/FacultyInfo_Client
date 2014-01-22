package de.uni_passau.facultyinfo.client.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MapMarker {
	public static final int TYPE_UNI = 1;
	public static final int TYPE_CITY = 2;

	private String id;
	private String name;
	private String description;
	private double latitude;
	private double longitude;
	@JsonIgnore
	private MapMarkerCategory category;

	@JsonCreator
	public MapMarker(@JsonProperty("id") String id,
			@JsonProperty("name") String name,
			@JsonProperty("description") String description,
			@JsonProperty("latitude") double latitude,
			@JsonProperty("longitude") double longitude) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public MapMarkerCategory getCategory() {
		return category;
	}

	public void setCategory(MapMarkerCategory category) {
		this.category = category;
	}
}
