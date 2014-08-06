package de.uni_passau.facultyinfo.client.util;

import android.support.v4.widget.SwipeRefreshLayout;

abstract public class SwipeRefreshAsyncDataLoader<T> extends AsyncDataLoader<T> {

	public SwipeRefreshAsyncDataLoader() {
		super();
	}

	public SwipeRefreshAsyncDataLoader(SwipeRefreshLayout rootView) {
		super(rootView);
	}

	abstract protected T doInBackground(Void... unused);


	protected void showLoadingAnimation(boolean showAnimation) {
		((SwipeRefreshLayout) rootView).setRefreshing(showAnimation);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		showLoadingAnimation(true);
	}
	
	@Override
	protected void onPostExecute(T result) {
		showLoadingAnimation(false);
	}

}
