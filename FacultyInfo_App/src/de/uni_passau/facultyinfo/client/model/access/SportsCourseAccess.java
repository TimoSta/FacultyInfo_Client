package de.uni_passau.facultyinfo.client.model.access;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import de.uni_passau.facultyinfo.client.model.connection.RestConnection;
import de.uni_passau.facultyinfo.client.model.dto.SportsCourse;
import de.uni_passau.facultyinfo.client.model.dto.SportsCourseCategory;

/**
 * Enables access to remote Sports course ressources.
 * 
 * @author Timo Staudinger
 */
public class SportsCourseAccess {
	private static final String RESSOURCE = "/sportscourse";

	private RestConnection<SportsCourse> restConnectionSportsCourse = null;
	private RestConnection<SportsCourseCategory> restConnectionSportsCourseCategory = null;

	private RestConnection<SportsCourse> getRestConnectionSportsCourse() {
		if (restConnectionSportsCourse == null) {
			restConnectionSportsCourse = new RestConnection<SportsCourse>(
					SportsCourse.class);
		}
		return restConnectionSportsCourse;
	}

	private RestConnection<SportsCourseCategory> getRestConnectionSportsCourseCategory() {
		if (restConnectionSportsCourseCategory == null) {
			restConnectionSportsCourseCategory = new RestConnection<SportsCourseCategory>(
					SportsCourseCategory.class);
		}
		return restConnectionSportsCourseCategory;
	}

	protected SportsCourseAccess() {
		super();
	}

	/**
	 * Gives a list of available Sports Courses in alphabetical order.
	 * 
	 * @return List<SportsCourseCategory>
	 */
	public List<SportsCourseCategory> getSportsCourses() {
		List<SportsCourseCategory> sportsCourseCategories = loadRemoteSportsCourses();
		return Collections.unmodifiableList(sportsCourseCategories);
	}

	/**
	 * Gives a list of Sports Courses that are currently cached locally in
	 * alphabetical order.
	 * 
	 * @return List<SportsCourseCategory>
	 */
	public List<SportsCourseCategory> getSportsCoursesFromCache() {
		return Collections
				.unmodifiableList(new ArrayList<SportsCourseCategory>());
	}

	/**
	 * Gives a list of today's Sports Courses in alphabetical order.
	 * 
	 * @return List<SportsCourseCategory>
	 */
	public List<SportsCourseCategory> getTodaysSportsCourses() {
		List<SportsCourseCategory> sportsCourseCategories = loadRemoteSportsCourses();
		List<SportsCourseCategory> todaysSportsCourseCategories = new ArrayList<SportsCourseCategory>();
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
		for (SportsCourseCategory sportsCourseCategory : sportsCourseCategories) {
			List<SportsCourse> todaysSportsCourses = new ArrayList<SportsCourse>();
			for (SportsCourse sportsCourse : sportsCourseCategory
					.getSportsCourses()) {
				sportsCourse.setCategory(null);
				if ((sportsCourse.getStartDate() == null || sportsCourse
						.getStartDate().before(today))
						&& (sportsCourse.getEndDate() == null || sportsCourse
								.getEndDate().after(today))
						&& sportsCourse.getDayOfWeek() == dayOfWeek) {
					todaysSportsCourses.add(sportsCourse);
					sportsCourse.setCategory(sportsCourseCategory);
				}
			}
			if (!todaysSportsCourses.isEmpty()) {
				sportsCourseCategory.setSportsCourses(Collections
						.unmodifiableList(todaysSportsCourses));
				todaysSportsCourseCategories.add(sportsCourseCategory);
			}
		}

		return Collections.unmodifiableList(todaysSportsCourseCategories);
	}

	/**
	 * Gives a list of today's Sports Courses that are currently cached locally
	 * in alphabetical order.
	 * 
	 * @return List<SportsCourseCategory>
	 */
	public List<SportsCourseCategory> getTodaysSportsCoursesFromCache() {
		return Collections
				.unmodifiableList(new ArrayList<SportsCourseCategory>());
	}

	/**
	 * Gives a list of Sports Courses that meet one or more of the following
	 * criteria:
	 * <ul>
	 * <li>The title of the course's category contains the search string.</li>
	 * <li>The description of the course contains the search string.</li>
	 * <li>The host of the course contains the search string.</li>
	 * </ul>
	 * 
	 * The search is case insensitive.
	 * 
	 * @param input
	 *            The search parameter
	 * @return List of matching Sports Course Categories that contain a list of
	 *         matching Sports Courses each.
	 */
	public List<SportsCourseCategory> findSportsCourses(String input) {
		if (input != null && !input.isEmpty()) {
			List<SportsCourseCategory> sportsCourseCategories = loadRemoteSportsCourses();
			List<SportsCourseCategory> foundSportsCourseCategories = new ArrayList<SportsCourseCategory>();
			Pattern pattern = Pattern.compile(input, Pattern.CASE_INSENSITIVE
					+ Pattern.LITERAL);
			for (SportsCourseCategory sportsCourseCategory : sportsCourseCategories) {
				if (sportsCourseCategory.getTitle() != null
						&& pattern.matcher(sportsCourseCategory.getTitle())
								.find()) {
					foundSportsCourseCategories.add(sportsCourseCategory);
				} else {
					List<SportsCourse> foundSportsCourses = new ArrayList<SportsCourse>();
					for (SportsCourse sportsCourse : sportsCourseCategory
							.getSportsCourses()) {
						sportsCourse.setCategory(null);
						if ((sportsCourse.getDetails() != null && pattern
								.matcher(sportsCourse.getDetails()).find())
								|| (sportsCourse.getHost() != null && pattern
										.matcher(sportsCourse.getHost()).find())) {
							foundSportsCourses.add(sportsCourse);
							sportsCourse.setCategory(sportsCourseCategory);
						}
					}
					if (!foundSportsCourses.isEmpty()) {
						sportsCourseCategory.setSportsCourses(Collections
								.unmodifiableList(foundSportsCourses));
						foundSportsCourseCategories.add(sportsCourseCategory);
					}
				}
			}

			return Collections.unmodifiableList(foundSportsCourseCategories);
		}
		return Collections
				.unmodifiableList(new ArrayList<SportsCourseCategory>());
	}

	/**
	 * Gives a list of Sports Courses that are cached and meet one or more of
	 * the following criteria:
	 * <ul>
	 * <li>The title of the course's category contains the search string.</li>
	 * <li>The description of the course contains the search string.</li>
	 * <li>The host of the course contains the search string.</li>
	 * </ul>
	 * 
	 * The search is case insensitive.
	 * 
	 * @param input
	 *            The search parameter
	 * @return List of matching Sports Course Categories that contain a list of
	 *         matching Sports Courses each.
	 */
	public List<SportsCourseCategory> findSportsCoursesFromCache(String input) {
		return Collections
				.unmodifiableList(new ArrayList<SportsCourseCategory>());
	}

	/**
	 * Gives detailed information about a single Sports Course.
	 * 
	 * @param id
	 * @return The sports course matching the id.
	 */
	public SportsCourse getSportsCourse(String id) {
		SportsCourse sportsCourse = null;

		sportsCourse = getRestConnectionSportsCourse().getRessource(
				RESSOURCE + '/' + id);

		if (sportsCourse == null) {
			return null;
		}

		// TODO: Database operations

		return sportsCourse;
	}

	private List<SportsCourseCategory> loadRemoteSportsCourses() {
		List<SportsCourseCategory> sportsCourseCategories = null;

		sportsCourseCategories = getRestConnectionSportsCourseCategory()
				.getRessourceAsList(RESSOURCE);

		if (sportsCourseCategories == null) {
			return null;
		}

		for (SportsCourseCategory sportsCourseCategory : sportsCourseCategories) {
			for (SportsCourse sportsCourse : sportsCourseCategory
					.getSportsCourses()) {
				sportsCourse.setCategory(sportsCourseCategory);
			}
		}

		// TODO: Database operations

		return sportsCourseCategories;
	}

}
