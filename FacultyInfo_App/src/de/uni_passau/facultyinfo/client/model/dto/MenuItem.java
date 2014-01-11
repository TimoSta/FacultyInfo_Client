package de.uni_passau.facultyinfo.client.model.dto;

import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuItem {
	public static final int TYPE_NOT_AVAILABLE = 0;
	public static final int TYPE_SOUP = 1;
	public static final int TYPE_APPETIZER = 2;
	public static final int TYPE_MAIN = 3;
	public static final int TYPE_DESSERT = 4;

	public static final int DAY_UNKNOWN = 0;
	public static final int MONDAY = 1;
	public static final int TUESDAY = 2;
	public static final int WEDNESDAY = 3;
	public static final int THURSDAY = 4;
	public static final int FRIDAY = 5;
	public static final int SATURDAY = 6;
	public static final int SUNDAY = 7;

	public static final double PRICE_NOT_AVAILABLE = -1.0;

	private String id;
	private Date day;
	private String name;
	private int type;
	private int attributes;
	private double priceStudent;
	private double priceEmployee;
	private double priceExternal;

	@JsonCreator
	public MenuItem(@JsonProperty("id") String id,
			@JsonProperty("day") Date day, @JsonProperty("name") String name,
			@JsonProperty("type") int type,
			@JsonProperty("attributes") int attributes,
			@JsonProperty("priceStudent") double priceStudent,
			@JsonProperty("priceEmployee") double priceEmployee,
			@JsonProperty("priceExternal") double priceExternal) {
		super();
		this.id = id;
		this.day = day;
		this.name = name;
		this.type = type;
		this.attributes = attributes;
		this.priceStudent = priceStudent;
		this.priceEmployee = priceEmployee;
		this.priceExternal = priceExternal;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getAttributes() {
		return attributes;
	}

	public void setAttributes(int attributes) {
		this.attributes = attributes;
	}

	public double getPriceStudent() {
		return priceStudent;
	}

	public void setPriceStudent(double priceStudent) {
		this.priceStudent = priceStudent;
	}

	public double getPriceEmployee() {
		return priceEmployee;
	}

	public void setPriceEmployee(double priceEmployee) {
		this.priceEmployee = priceEmployee;
	}

	public double getPriceExternal() {
		return priceExternal;
	}

	public void setPriceExternal(double priceExternal) {
		this.priceExternal = priceExternal;
	}

	public int getDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(getDay());
		int dayOfWeek = DAY_UNKNOWN;
		switch (cal.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.MONDAY:
			dayOfWeek = MONDAY;
			break;
		case Calendar.TUESDAY:
			dayOfWeek = TUESDAY;
			break;
		case Calendar.WEDNESDAY:
			dayOfWeek = WEDNESDAY;
			break;
		case Calendar.THURSDAY:
			dayOfWeek = THURSDAY;
			break;
		case Calendar.FRIDAY:
			dayOfWeek = FRIDAY;
			break;
		case Calendar.SATURDAY:
			dayOfWeek = SATURDAY;
			break;
		case Calendar.SUNDAY:
			dayOfWeek = SUNDAY;
		}
		return dayOfWeek;
	}

}
