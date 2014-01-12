package de.uni_passau.facultyinfo.client.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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

		setHasOptionsMenu(true);

		getActivity().getActionBar().setTitle(
				getActivity().getApplicationContext().getString(
						R.string.title_faqs));
		getActivity().getActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_STANDARD);

		new FaqLoader(rootView).execute();

		return rootView;

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.faqs, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.send_mail) {
			final Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
					Uri.parse("mailto:mail@fs-wiwi.de"));
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
//			emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			getActivity().startActivity(
					Intent.createChooser(emailIntent, "Mail an die Fachschaft..."));
			return true;
		}
		return super.onOptionsItemSelected(item);
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

			ListView listView = (ListView) rootView.findViewById(R.id.faq_list);

			final ArrayList<HashMap<String, String>> faqList = new ArrayList<HashMap<String, String>>();

			for (FaqCategory faqCategory : faqs) {
				boolean first = true;
				for (Faq faq : faqCategory.getFaqs()) {
					HashMap<String, String> temp1 = new HashMap<String, String>();
					temp1.put("title", faq.getTitle());
					temp1.put("category", faqCategory.getTitle());
					temp1.put("id", faq.getId());
					temp1.put("first", first ? "true" : "false");
					faqList.add(temp1);
					first = false;
				}
			}

			SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
					faqList, R.layout.faq_row_view, new String[] { "category",
							"title" }, new int[] { R.id.faq_row_header,
							R.id.faq_title }

			) {
				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {
					View view = super.getView(position, convertView, parent);
					if (faqList.get(position).get("first").equals("true")) {

						((TextView) view.findViewById(R.id.faq_row_header))
								.setVisibility(TextView.VISIBLE);
					}

					return view;
				}
			};

			listView.setAdapter(adapter);

		}
	}

}
