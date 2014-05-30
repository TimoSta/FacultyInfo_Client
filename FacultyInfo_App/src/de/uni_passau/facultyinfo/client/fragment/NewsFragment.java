package de.uni_passau.facultyinfo.client.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
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
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

public class NewsFragment extends Fragment {

	public NewsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final SwipeRefreshLayout rootView = (SwipeRefreshLayout) inflater
				.inflate(R.layout.fragment_news, container, false);
		rootView.setColorScheme(R.color.loading_indicator_1,
				R.color.loading_indicator_2, R.color.loading_indicator_3,
				R.color.loading_indicator_4);
		rootView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new NewsLoader(rootView, true).execute();
			}
		});

		Activity activity = getActivity();
		ActionBar actionBar = activity.getActionBar();
		actionBar.setTitle(activity.getApplicationContext().getString(
				R.string.title_news));
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

		(new NewsLoader(rootView)).execute();

		return rootView;
	}

	protected class NewsLoader extends AsyncDataLoader<List<News>> {
		private boolean forceRefresh = false;

		private NewsLoader(View rootView) {
			super(rootView);
			showLoadingAnimation(true);
		}

		private NewsLoader(View rootView, boolean forceRefresh) {
			super(rootView);
			showLoadingAnimation(true);
			this.forceRefresh = forceRefresh;
		}

		private void showLoadingAnimation(boolean showAnimation) {
			((SwipeRefreshLayout) rootView).setRefreshing(showAnimation);
		}

		@Override
		protected List<News> doInBackground(Void... unused) {
			AccessFacade accessFacade = new AccessFacade();

			List<News> news = accessFacade.getNewsAccess()
					.getNews(forceRefresh);

			if (news == null) {
				publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
				news = accessFacade.getNewsAccess().getNewsFromCache();
			}

			if (news == null) {
				news = Collections.unmodifiableList(new ArrayList<News>());
			}

			return news;
		}

		@Override
		protected void onPostExecute(List<News> news) {

			final ArrayList<HashMap<String, String>> newsList = new ArrayList<HashMap<String, String>>();

			for (News newsElement : news) {
				HashMap<String, String> listEntry = new HashMap<String, String>();
				listEntry.put("id", newsElement.getId());
				listEntry.put("title", newsElement.getTitle());
				listEntry.put("description", newsElement.getDescription());
				newsList.add(listEntry);
			}

			ListView listView = (ListView) rootView.findViewById(R.id.list);

			SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
					newsList, R.layout.custom_row_view, new String[] { "title",
							"description", }, new int[] { R.id.title,
							R.id.description }

			);

			listView.setAdapter(adapter);

			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					displayNews(newsList.get(position).get("id"));
				}
			});

			showLoadingAnimation(false);

		}

		private void displayNews(String id) {
			Intent intent = new Intent(rootView.getContext(),
					DisplayNewsActivity.class);
			intent.putExtra("newsId", id);
			startActivity(intent);
		}

	}

}
