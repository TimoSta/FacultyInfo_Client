package de.uni_passau.facultyinfo.client.model.dto.util;

public class Color {
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

	private int id;
	private String fontColor;
	private String backgroundColor;

	public Color(int id, String fontColor, String backgroundColor) {
		super();
		this.id = id;
		this.fontColor = fontColor;
		this.backgroundColor = backgroundColor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

}
