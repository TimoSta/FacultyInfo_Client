package de.uni_passau.facultyinfo.client.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MapMarkerCategory {
	private String id;
	private String title;
	@JsonIgnore
	private MapMarkerCategory superCategory;
	private List<MapMarkerCategory> mapMarkerCategories;
	private List<MapMarker> mapMarkers;

	@JsonCreator
	public MapMarkerCategory(@JsonProperty("id") String id,
			@JsonProperty("title") String title) {
		super();
		this.id = id;
		this.title = title;
		this.superCategory = null;
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

	public MapMarkerCategory getSuperCategory() {
		return superCategory;
	}

	public void setSuperCategory(MapMarkerCategory superCategory) {
		this.superCategory = superCategory;
	}

	public List<MapMarkerCategory> getMapMarkerCategories() {
		return mapMarkerCategories;
	}

	public void setMapMarkerCategories(
			List<MapMarkerCategory> mapMarkerCategories) {
		this.mapMarkerCategories = mapMarkerCategories;
	}

	public List<MapMarker> getMapMarkers() {
		return mapMarkers;
	}

	public void setMapMarkers(List<MapMarker> mapMarkers) {
		this.mapMarkers = mapMarkers;
	}

}
