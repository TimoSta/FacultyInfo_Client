package de.uni_passau.facultyinfo.client.model.dto.util;

public class Color {
	public static final int WHITE = 1;
	public static final int BLACK = 2;
	public static final int RED = 3;
	public static final int BLUE = 4;
	public static final int GREEN = 5;
	public static final int YELLOW = 6;
	public static final int PURPLE = 7;
	public static final int GREY = 8;
	public static final int ORANGE = 9;
	public static final int DARKRED = 10;
	public static final int LIGHTBLUE = 11;
	public static final int PINK = 12;

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

	public int getFontColor() {
		return android.graphics.Color.parseColor(fontColor);
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public int getBackgroundColor() {
		return android.graphics.Color.parseColor(backgroundColor);
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

}
