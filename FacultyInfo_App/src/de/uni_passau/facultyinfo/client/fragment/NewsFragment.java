package de.uni_passau.facultyinfo.client.fragment;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.activity.DisplayNewsActivity;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.News;

public class NewsFragment extends Fragment {

	public NewsFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_news, container,
				false);

		getActivity().setTitle(R.string.title_news);

		NewsLoader newsLoader = new NewsLoader(rootView);
		newsLoader.execute();

		return rootView;
	}

	protected class NewsLoader extends AsyncTask<URL, Void, List<News>> {
		View rootView = null;

		private NewsLoader(View rootView) {
			super();
			this.rootView = rootView;
		}

		@Override
		protected List<News> doInBackground(URL... urls) {
			System.out.println("NewsActivity->doInBackground");
			AccessFacade accessFacade = new AccessFacade();
			List<News> news = accessFacade.getNewsAccess().getNews();
			return news;
		}

		@Override
		protected void onPostExecute(List<News> news) {

			System.out.println("NewsActivity->onPostExecute");

			final ArrayList<HashMap<String, String>> newsList = new ArrayList<HashMap<String, String>>();

			for (News newsElement : news) {
				HashMap<String, String> temp1 = new HashMap<String, String>();
				temp1.put("id", newsElement.getId());
				System.out.println(newsElement.getTitle());
				temp1.put("title", newsElement.getTitle());
				temp1.put("description", newsElement.getDescription());
				newsList.add(temp1);
			}

			ListView listView = (ListView) rootView.findViewById(R.id.list);

			SimpleAdapter adapter = new SimpleAdapter(
					rootView.getContext(), newsList,
					R.layout.custom_row_view, new String[] { "title",
							"description", }, new int[] { R.id.title,
							R.id.description }

			);

			listView.setAdapter(adapter);

			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					System.out.println("click");
					System.out.println(position);
					displayNews(newsList.get(position).get("id"));
				}
			});

		}

		private void displayNews(String id) {
			System.out.println("NewsActivity->displayNews()");
			Intent intent = new Intent(rootView.getContext(),
					DisplayNewsActivity.class);

			intent.putExtra("newsId", id);
			System.out.println("putExtra");
			startActivity(intent);
		}

	}

}
