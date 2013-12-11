package de.uni_passau.facultyinfo.client.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MapMarker {
	public static final int TYPE_UNI = 1;
	public static final int TYPE_CITY = 2;

	private String id;
	private String description;
	private float latitude;
	private float longitude;
	private int category;

	@JsonCreator
	public MapMarker(@JsonProperty("id") String id,
			@JsonProperty("description") String description,
			@JsonProperty("latitude") float latitude,
			@JsonProperty("longitude") float longitude,
			@JsonProperty("category") int category) {
		super();
		this.id = id;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.category = category;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}
}
