package de.uni_passau.facultyinfo.client.model.access;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.uni_passau.facultyinfo.client.model.connection.RestConnection;
import de.uni_passau.facultyinfo.client.model.dto.ContactGroup;
import de.uni_passau.facultyinfo.client.model.dto.ContactPerson;

/**
 * Enables access to remote Contact person ressources.
 * 
 * @author Timo Staudinger
 */
public class ContactPersonAccess {
	private static final String RESSOURCE = "/contactperson";

	private RestConnection<ContactGroup> restConnection = null;

	private RestConnection<ContactGroup> getRestConnection() {
		if (restConnection == null) {
			restConnection = new RestConnection<ContactGroup>(
					ContactGroup.class);
		}
		return restConnection;
	}

	protected ContactPersonAccess() {
		super();
	}

	/**
	 * Gives a list of available Contact groups in alphabetical order.
	 * 
	 */
	public List<ContactGroup> getContactGroups() {
		List<ContactGroup> contactGroups = null;

		contactGroups = getRestConnection().getRessourceAsList(RESSOURCE);

		if (contactGroups == null) {
			return null;
		}

		// TODO: Database operations

		return Collections.unmodifiableList(contactGroups);
	}

	/**
	 * Gives a list of Contact Groups that are currently cached locally.
	 * 
	 */
	public List<ContactGroup> getContactGroupsFromCache() {
		return Collections.unmodifiableList(new ArrayList<ContactGroup>());
	}

	/**
	 * Gives detailed information about a single Contact Group.
	 * 
	 */
	public ContactGroup getContactGroup(String id) {
		ContactGroup contactGroup = null;

		contactGroup = getRestConnection().getRessource(RESSOURCE + "/" + id);

		if (contactGroup == null) {
			return null;
		}

		if (contactGroup.getContactPersons() != null) {
			for (ContactPerson contactPerson : contactGroup.getContactPersons()) {
				contactPerson.setContactGroup(contactGroup);
			}
		}

		// TODO: Database operations

		return contactGroup;
	}

	/**
	 * Gives detailed information about a single Contact group that is cached
	 * locally.
	 * 
	 */
	public ContactGroup getContactGroupFromCache(String id) {
		return null;
	}

	/**
	 * Gives a list of Contact Groups that meet one or more of the following
	 * criteria:
	 * <ul>
	 * <li>The title of the Contact Group contains the search string.</li>
	 * <li>The Name of the Contact Person contains the search string.</li>
	 * </ul>
	 * 
	 * The search is case insensitive.
	 * 
	 * @param input
	 *            The search parameter
	 * @return List of matching Contact Groups that contain a list of matching
	 *         Contact Persons each.
	 */
	public List<ContactGroup> find(String input) {
		if (input != null && !input.isEmpty()) {
			List<ContactGroup> contactGroups = null;

			contactGroups = getRestConnection().getRessourceAsList(
					RESSOURCE + "/find/" + input);

			if (contactGroups == null) {
				return null;
			}

			for (ContactGroup contactGroup : contactGroups) {
				if (contactGroup.getContactPersons() != null) {
					for (ContactPerson contactPerson : contactGroup
							.getContactPersons()) {
						contactPerson.setContactGroup(contactGroup);
					}
				}
			}

			return contactGroups;
		}
		return Collections.unmodifiableList(new ArrayList<ContactGroup>());
	}
}
