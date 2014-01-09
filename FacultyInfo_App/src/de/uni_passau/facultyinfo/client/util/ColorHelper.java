package de.uni_passau.facultyinfo.client.util;

import android.util.SparseArray;
import de.uni_passau.facultyinfo.client.model.dto.util.Color;

public class ColorHelper {
	SparseArray<Color> colors;

	public ColorHelper() {
		colors = new SparseArray<Color>();

		colors.put(Color.COLOR_1,
				new Color(Color.COLOR_1, "#000000", "#FFFFFF"));
		colors.put(Color.COLOR_2,
				new Color(Color.COLOR_2, "#FFFFFF", "#000000"));
		// TODO: define more colors
	}

	public Color getColor(int colorId) {
		return colors.get(colorId, null);
	}
}
