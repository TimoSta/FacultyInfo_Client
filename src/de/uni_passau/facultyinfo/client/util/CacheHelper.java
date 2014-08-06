package de.uni_passau.facultyinfo.client.util;

import java.util.Calendar;
import java.util.Date;

public class CacheHelper {
	public static Date getExpiringDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, -10);
		return cal.getTime();
	}
}
