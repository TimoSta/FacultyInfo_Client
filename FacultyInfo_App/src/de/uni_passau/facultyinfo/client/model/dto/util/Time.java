package de.uni_passau.facultyinfo.client.model.dto.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Time {
	private int hour;
	private int minute;

	@SuppressLint("SimpleDateFormat")
	@JsonCreator
	public Time(String timeString) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new SimpleDateFormat("H:m:s").parse(timeString));
		this.hour = cal.get(Calendar.HOUR_OF_DAY);
		this.minute = cal.get(Calendar.MINUTE);
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		if (hour < 0 || hour > 23) {
			throw new RuntimeException("Invalid input for parameter hour");
		}
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		if (hour < 0 || hour > 60) {
			throw new RuntimeException("Invalid input for parameter minute");
		}
		this.minute = minute;
	}

	public String toString() {
		return String.format("%2s", Integer.toString(hour)).replace(" ", "0")
				+ ":"
				+ String.format("%2s", Integer.toString(minute)).replace(" ",
						"0");
	}

	public String toStringWithSeconds() {
		return String.format("%2s", Integer.toString(hour)).replace(" ", "0")
				+ ":"
				+ String.format("%2s", Integer.toString(minute)).replace(" ",
						"0") + ":00";
	}

}
