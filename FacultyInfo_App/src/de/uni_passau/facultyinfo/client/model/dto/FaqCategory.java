package de.uni_passau.facultyinfo.client.model.dto;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FaqCategory {
	private String id;
	private String title;

	private List<Faq> faqs;

	@JsonCreator
	public FaqCategory(@JsonProperty("id") String id,
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

	public List<Faq> getFaqs() {
		return faqs;
	}

	public void setFaqs(List<Faq> faqs) {
		this.faqs = Collections.unmodifiableList(faqs);
	}

}
