package de.uni_passau.facultyinfo.client.fragment;

import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import de.uni_passau.facultyinfo.client.R;

public abstract class SwipeRefreshLayoutFragment extends Fragment {
	public SwipeRefreshLayoutFragment() {
	}

	protected void initializeSwipeRefresh(SwipeRefreshLayout rootView, OnRefreshListener onRefreshListener) {
		rootView.setColorScheme(R.color.loading_indicator_1,
				R.color.loading_indicator_2, R.color.loading_indicator_3,
				R.color.loading_indicator_4);
		rootView.setOnRefreshListener(onRefreshListener);
	}
	
}
