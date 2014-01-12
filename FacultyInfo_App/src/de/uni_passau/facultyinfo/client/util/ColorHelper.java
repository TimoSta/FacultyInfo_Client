package de.uni_passau.facultyinfo.client.util;

import android.util.SparseArray;
import de.uni_passau.facultyinfo.client.model.dto.util.Color;

public class ColorHelper {
	SparseArray<Color> colors;

	public ColorHelper() {
		colors = new SparseArray<Color>();

		colors.put(Color.WHITE,
				new Color(Color.WHITE, "#000000", "#EEEEEE"));
		colors.put(Color.BLACK,
				new Color(Color.BLACK, "#FFFFFF", "#000000"));
		colors.put(Color.RED,
				new Color(Color.RED, "#000000", "#FA5858"));
		colors.put(Color.BLUE,
				new Color(Color.BLUE, "#FFFFFF", "#0101DF"));
		colors.put(Color.GREEN,
				new Color(Color.GREEN, "#000000", "#3ADF00"));
		colors.put(Color.YELLOW,
				new Color(Color.YELLOW, "#000000", "#F4FA58"));
		colors.put(Color.PURPLE,
				new Color(Color.PURPLE, "#FFFFFF", "#7401DF"));
		colors.put(Color.GREY,
				new Color(Color.GREY, "#FFFFFF", "#848484"));
		colors.put(Color.ORANGE,
				new Color(Color.ORANGE, "#000000", "#FF8000"));
		colors.put(Color.DARKRED,
				new Color(Color.DARKRED, "#FFFFFF", "#610B0B"));
		colors.put(Color.LIGHTBLUE,
				new Color(Color.LIGHTBLUE, "#000000", "#A9E2F3"));
		colors.put(Color.PINK,
				new Color(Color.PINK, "#000000", "#F781F3"));
	}

	public Color getColor(int colorId) {
		return colors.get(colorId, null);
	}
}
