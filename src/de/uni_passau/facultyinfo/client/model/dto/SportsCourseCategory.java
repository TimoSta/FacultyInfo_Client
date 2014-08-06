package de.uni_passau.facultyinfo.client.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SportsCourseCategory {
	private String id;
	private String title;

	private List<SportsCourse> sportsCourses;

	@JsonCreator
	public SportsCourseCategory(@JsonProperty("id") String id,
			@JsonProperty("title") String title) {
		super();
		this.id = id;
		this.title = title;
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

	public List<SportsCourse> getSportsCourses() {
		return sportsCourses;
	}

	public void setSportsCourses(List<SportsCourse> sportsCourses) {
		this.sportsCourses = sportsCourses;
	}

}
