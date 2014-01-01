package de.uni_passau.facultyinfo.client.model.access;


public class AccessFacade {
	private NewsAccess newsAccess = null;
	private FaqAccess faqAccess = null;
	
	public NewsAccess getNewsAccess() {
		if (newsAccess == null) {
			newsAccess = new NewsAccess();
		}

		return newsAccess;
	}
	
	public FaqAccess getFaqAccess() {
		if(faqAccess == null) {
			faqAccess = new FaqAccess();
		}
		return faqAccess;
	}
}
