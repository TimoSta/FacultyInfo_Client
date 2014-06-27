package de.uni_passau.facultyinfo.client.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SportsCourseSearchResponse {
	private List<SportsCourseCategory> categories;
	private List<SportsCourse> courses;

	@JsonCreator
	public SportsCourseSearchResponse(
			@JsonProperty("categories") List<SportsCourseCategory> categories,
			@JsonProperty("courses") List<SportsCourse> courses) {
		this.categories = categories;
		this.courses = courses;
	}

	public List<SportsCourseCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<SportsCourseCategory> categories) {
		this.categories = categories;
	}

	public List<SportsCourse> getCourses() {
		return courses;
	}

	public void setCourses(List<SportsCourse> courses) {
		this.courses = courses;
	}

}
