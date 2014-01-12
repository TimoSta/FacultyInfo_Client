package de.uni_passau.facultyinfo.client.model.access;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.uni_passau.facultyinfo.client.model.connection.RestConnection;
import de.uni_passau.facultyinfo.client.model.dto.BusinessHoursFacility;

/**
 * @author Timo Staudinger
 * 
 */
public class BusinessHoursAccess {
	private static final String RESSOURCE = "/businesshours";

	private RestConnection<BusinessHoursFacility> restConnection = null;

	protected BusinessHoursAccess() {
		restConnection = new RestConnection<BusinessHoursFacility>(
				BusinessHoursFacility.class);
	}

	/**
	 * Gives a list of all Cafeterias.
	 * 
	 */
	public List<BusinessHoursFacility> getCafeterias() {
		List<BusinessHoursFacility> cafeterias = null;

		cafeterias = restConnection
				.getRessourceAsList(RESSOURCE + "/cafeteria");

		// TODO: Database operations

		if (cafeterias == null) {
			return null;
		}

		return Collections.unmodifiableList(cafeterias);
	}

	/**
	 * Gives a list of all Cafeterias that are cached locally.
	 * 
	 */
	public List<BusinessHoursFacility> getCafeteriasFromCache() {
		// TODO: Load cached data
		return Collections
				.unmodifiableList(new ArrayList<BusinessHoursFacility>());
	}

	/**
	 * Gives a list of all Libraries.
	 * 
	 */
	public List<BusinessHoursFacility> getLibraries() {
		List<BusinessHoursFacility> libraries = null;

		libraries = restConnection.getRessourceAsList(RESSOURCE + "/library");

		// TODO: Database operations

		if (libraries == null) {
			return null;
		}

		return Collections.unmodifiableList(libraries);
	}

	/**
	 * Gives a list of all Libraries that are cached locally.
	 * 
	 */
	public List<BusinessHoursFacility> getLibrariesFromCache() {
		// TODO: Load cached data
		return Collections
				.unmodifiableList(new ArrayList<BusinessHoursFacility>());
	}

	/**
	 * Gives detailed information about a specific Facility.
	 * 
	 */
	public BusinessHoursFacility getFacility(String id) {
		BusinessHoursFacility facility = null;

		facility = restConnection.getRessource(RESSOURCE + '/' + id);

		// TODO: Database operations

		return facility;
	}

	/**
	 * Gives detailed information about a specific Facility that is cached
	 * locally.
	 * 
	 */
	public BusinessHoursFacility getFacilityFromCache(String id) {
		// TODO: load cached data
		return null;
	}
}
