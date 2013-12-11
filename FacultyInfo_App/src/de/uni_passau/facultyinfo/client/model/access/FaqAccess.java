package de.uni_passau.facultyinfo.client.model.access;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.uni_passau.facultyinfo.client.model.connection.RestConnection;
import de.uni_passau.facultyinfo.client.model.dto.Faq;

public class FaqAccess {
	private static final String RESSOURCE = "/faq";

	protected FaqAccess() {
	}

	/**
	 * Gives a list of available FAQs.
	 * 
	 * @return
	 */
	public List<Faq> getFaqs() {
		RestConnection<Faq> restConnection = new RestConnection<Faq>(Faq.class);
		List<Faq> faqs = null;

		faqs = restConnection.getRessourceAsList(RESSOURCE);

		// TODO: Database operations

		if (faqs == null) {
			faqs = new ArrayList<Faq>();
		}

		return Collections.unmodifiableList(faqs);
	}

}
