package de.uni_passau.facultyinfo.client.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactSearchResponse {
	private List<ContactGroup> groups = null;
	private List<ContactPerson> persons = null;

	@JsonCreator
	public ContactSearchResponse(
			@JsonProperty("groups") List<ContactGroup> groups,
			@JsonProperty("persons") List<ContactPerson> persons) {
		this.groups = groups;
		this.persons = persons;
	}

	public List<ContactGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<ContactGroup> groups) {
		this.groups = groups;
	}

	public List<ContactPerson> getPersons() {
		return persons;
	}

	public void setPersons(List<ContactPerson> persons) {
		this.persons = persons;
	}

}
