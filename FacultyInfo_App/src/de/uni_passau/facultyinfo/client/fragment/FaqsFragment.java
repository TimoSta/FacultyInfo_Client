package de.uni_passau.facultyinfo.client.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.Faq;
import de.uni_passau.facultyinfo.client.model.dto.FaqCategory;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

public class FaqsFragment extends Fragment {

	public FaqsFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_faqs, container,
				false);

		getActivity().setTitle(R.string.title_faqs);

		return rootView;

	}

	protected class FaqLoader extends AsyncDataLoader<List<FaqCategory>> {

		public FaqLoader(View rootView) {
			super(rootView);
		}

		@Override
		protected List<FaqCategory> doInBackground(Void... unused) {
			AccessFacade accessFacade = new AccessFacade();
			List<FaqCategory> faqs = null;

			faqs = accessFacade.getFaqAccess().getFaqs();

			if (faqs == null) {
				publishProgress(AsyncDataLoader.NO_CONNECTION_PROGRESS);
				faqs = accessFacade.getFaqAccess().getFaqsFromCache();
			}

			if (faqs == null) {
				faqs = new ArrayList<FaqCategory>();
			}

			return faqs;
		}

		@Override
		protected void onPostExecute(List<FaqCategory> faqs) {
			for (FaqCategory faqCategory : faqs) {
				System.out.println(faqCategory.getTitle());
				for (Faq faq : faqCategory.getFaqs()) {
					System.out.println("-- " + faq.getTitle());
				}
			}

		}
	}
}
