package de.uni_passau.facultyinfo.client.model.access;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.uni_passau.facultyinfo.client.model.connection.RestConnection;
import de.uni_passau.facultyinfo.client.model.dto.Faq;
import de.uni_passau.facultyinfo.client.model.dto.FaqCategory;

public class FaqAccess {
	private static final String RESSOURCE = "/faq";

	private RestConnection<Faq> restConnectionFaq = null;
	private RestConnection<FaqCategory> restConnectionFaqCategory = null;

	private RestConnection<Faq> getRestConnectionFaq() {
		if (restConnectionFaq == null) {
			restConnectionFaq = new RestConnection<Faq>(Faq.class);
		}
		return restConnectionFaq;
	}

	private RestConnection<FaqCategory> getRestConnectionFaqCategory() {
		if (restConnectionFaqCategory == null) {
			restConnectionFaqCategory = new RestConnection<FaqCategory>(
					FaqCategory.class);
		}
		return restConnectionFaqCategory;
	}

	protected FaqAccess() {
		super();
	}

	/**
	 * Gives a list of available FAQs.
	 * 
	 * @return List<FaqCategory>
	 * @author Timo Staudinger
	 */
	public List<FaqCategory> getFaqs() {
		List<FaqCategory> faqCategories = null;

		faqCategories = getRestConnectionFaqCategory().getRessourceAsList(
				RESSOURCE);

		if (faqCategories == null) {
			return null;
		}

		for (FaqCategory faqCategory : faqCategories) {
			for (Faq faq : faqCategory.getFaqs()) {
				faq.setCategory(faqCategory);
			}
		}

		// TODO: Database operations

		return Collections.unmodifiableList(faqCategories);
	}

	/**
	 * Gives a list of FAQs that are currently cached locally.
	 * 
	 * @return List<FaqCategory>
	 * @author Timo Staudinger
	 */
	public List<FaqCategory> getFaqsFromCache() {
		return Collections.unmodifiableList(new ArrayList<FaqCategory>());
	}

	/**
	 * Gives detailed information about a specific News.
	 * 
	 * @param newsId
	 * @return
	 */
	public Faq getFaq(String faqId) {
		Faq faq = null;

		faq = getRestConnectionFaq().getRessource(RESSOURCE + '/' + faqId);

		if (faq == null) {
			return null;
		}

		// TODO: Database operations

		return faq;
	}

}
