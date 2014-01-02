package de.uni_passau.facultyinfo.client.model.access;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import de.uni_passau.facultyinfo.client.model.connection.RestConnection;
import de.uni_passau.facultyinfo.client.model.dto.BusLine;

/**
 * @author Timo Staudinger
 * 
 */
public class BusLineAccess {
	private static final String RESSOURCE = "/busline";

	private RestConnection<BusLine> restConnection = null;

	protected BusLineAccess() {
		restConnection = new RestConnection<BusLine>(BusLine.class);
	}

	/**
	 * Gives a list of bus lines departing during the next 20 minutes, ordered
	 * by time of departure.
	 * 
	 * @return List<BusLine>
	 */
	public List<BusLine> getNextBusLines() {
		List<BusLine> busLines = null;

		busLines = restConnection.getRessourceAsList(RESSOURCE);

		if (busLines == null) {
			return null;
		}

		// TODO: Database operations

		ArrayList<BusLine> nextBusLines = new ArrayList<BusLine>();
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.MINUTE, 20);
		Date soon = cal.getTime();

		for (BusLine busLine : busLines) {
			if (busLine.getDeparture().after(now)
					&& busLine.getDeparture().before(soon)) {
				nextBusLines.add(busLine);
			}
		}

		Collections.sort(nextBusLines, new Comparator<BusLine>() {
			@Override
			public int compare(BusLine lhs, BusLine rhs) {
				if (lhs.getDeparture().before(rhs.getDeparture()))
					return -1;
				else if (lhs.getDeparture().after(rhs.getDeparture()))
					return 1;
				return 0;
			}
		});

		return Collections.unmodifiableList(nextBusLines);
	}

	/**
	 * Gives a list of bus lines from local cache departing during the next 20
	 * minutes, ordered by time of departure.
	 * 
	 * @return List<BusLine>
	 */
	public List<BusLine> getNextBusLinesFromCache() {
		// TODO: Load cached data
		return Collections.unmodifiableList(new ArrayList<BusLine>());
	}
}
