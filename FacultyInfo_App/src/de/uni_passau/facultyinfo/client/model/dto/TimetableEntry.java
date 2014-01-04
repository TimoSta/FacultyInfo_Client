package de.uni_passau.facultyinfo.client.model.dto;

public class TimetableEntry {

	public static final int MONDAY = 1;
	public static final int TUESDAY = 2;
	public static final int WEDNESDAY = 3;
	public static final int THURSDAY = 4;
	public static final int FRIDAY = 5;
	public static final int SATURDAY = 6;
	public static final int SUNDAY = 7;

	public static final int FROM_00_TO_02 = 1;
	public static final int FROM_02_TO_04 = 2;
	public static final int FROM_04_TO_06 = 3;
	public static final int FROM_06_TO_08 = 4;
	public static final int FROM_08_TO_10 = 5;
	public static final int FROM_10_TO_12 = 6;
	public static final int FROM_12_TO_14 = 7;
	public static final int FROM_14_TO_16 = 8;
	public static final int FROM_16_TO_18 = 9;
	public static final int FROM_18_TO_20 = 10;
	public static final int FROM_20_TO_22 = 11;
	public static final int FROM_22_TO_24 = 12;

	public static final int COLOR_1 = 1;
	public static final int COLOR_2 = 2;
	public static final int COLOR_3 = 3;
	public static final int COLOR_4 = 4;
	public static final int COLOR_5 = 5;
	public static final int COLOR_6 = 6;
	public static final int COLOR_7 = 7;
	public static final int COLOR_8 = 8;
	public static final int COLOR_9 = 9;
	public static final int COLOR_10 = 10;
	public static final int COLOR_11 = 11;
	public static final int COLOR_12 = 12;

	private String id;
	private String title;
	private String description;
	private String location;
	private int time;
	private int dayOfWeek;
	private int color;

	public TimetableEntry(String id, String title, String description,
			String location, int time, int dayOfWeek, int color) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.location = location;
		this.time = time;
		this.dayOfWeek = dayOfWeek;
		this.color = color;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

}
