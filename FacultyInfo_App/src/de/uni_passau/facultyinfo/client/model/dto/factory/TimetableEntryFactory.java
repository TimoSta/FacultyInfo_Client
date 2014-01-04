package de.uni_passau.facultyinfo.client.model.dto.factory;

import java.util.UUID;

import de.uni_passau.facultyinfo.client.model.dto.TimetableEntry;

public abstract class TimetableEntryFactory {
	public static TimetableEntry createTimetableEntry(String title,
			String description, String location, int time, int dayOfWeek,
			int color) {
		String id = UUID.randomUUID().toString();
		return new TimetableEntry(id, title, description, location, time,
				dayOfWeek, color);
	}
}
