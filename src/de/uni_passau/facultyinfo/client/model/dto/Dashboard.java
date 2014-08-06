package de.uni_passau.facultyinfo.client.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Dashboard {
	private News news;
	private List<BusLine> busLines;
	private List<MenuItem> menuItems;

	public Dashboard(@JsonProperty("news") News news,
			@JsonProperty("busLines") List<BusLine> busLines,
			@JsonProperty("menuItems") List<MenuItem> menuItems) {
		super();
		this.news = news;
		this.busLines = busLines;
		this.menuItems = menuItems;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public List<BusLine> getBusLines() {
		return busLines;
	}

	public void setBusLines(List<BusLine> busLines) {
		this.busLines = busLines;
	}

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

}
