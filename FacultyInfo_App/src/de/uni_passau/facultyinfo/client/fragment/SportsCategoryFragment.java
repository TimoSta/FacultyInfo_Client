package de.uni_passau.facultyinfo.client.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.activity.DisplaySportCoursesActivity;
import de.uni_passau.facultyinfo.client.fragment.NewsFragment.NewsLoader;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.SportsCourseCategory;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

public class SportsCategoryFragment extends Fragment {
	public static final String ARG_PERIOD = "period";
	private View rootView;
	private int tab;

	public SportsCategoryFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_sports_category, container,
				false);
//		Bundle args = getArguments();
//		tab = args.getInt(ARG_PERIOD);
		
//		if(tab==0){
//			System.out.println("tab 0"); 
//			(new SportsCourseCategoryLoader(rootView)).execute(); 
//		}else if(tab==1){
//			System.out.println("tab 1"); 
//			(new TodaysSportsCourseCategoryLoader(rootView)).execute(); 
//		}
		
//		if(SportsFragment.TAB_SELECTED==SportsFragment.AZ){
//			System.out.println("tab 0"); 
//			(new SportsCourseCategoryLoader(rootView)).execute(); 
//		}else if(SportsFragment.TAB_SELECTED==SportsFragment.TODAY){
//			System.out.println("tab 1"); 
//			(new TodaysSportsCourseCategoryLoader(rootView)).execute(); 
//		}
		
		return rootView; 
 
	}

//	protected class SportsCourseCategoryLoader extends
//			AsyncDataLoader<List<SportsCourseCategory>> {
//		SportsCourseCategoryLoader(View rootView) {
//			super(rootView);
//		}
//
//		@Override
//		protected List<SportsCourseCategory> doInBackground(Void... unused) {
//			AccessFacade accessFacade = new AccessFacade();
//
//			List<SportsCourseCategory> sportsCourseCategories = accessFacade
//					.getSportsCourseAccess().getSportsCourses();
//
//			if (sportsCourseCategories == null) {
//				publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
//				sportsCourseCategories = accessFacade.getSportsCourseAccess()
//						.getSportsCourses();
//			}
//
//			if (sportsCourseCategories == null) {
//				sportsCourseCategories = Collections
//						.unmodifiableList(new ArrayList<SportsCourseCategory>());
//			}
//
//			return sportsCourseCategories;
//		}
//
//		@Override
//		protected void onPostExecute(
//				List<SportsCourseCategory> sportsCourseCategories) {
//			ListView sportsoffer = (ListView) rootView
//					.findViewById(R.id.categories);
//
//			final ArrayList<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();
//
//			for (SportsCourseCategory category : sportsCourseCategories) {
//
//				HashMap<String, String> temp1 = new HashMap<String, String>();
//				temp1.put("id", category.getId());
//				temp1.put("title", category.getTitle());
//				categoryList.add(temp1);
//			}
//
//			SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
//					categoryList, R.layout.custom_row_view,
//					new String[] { "title", }, new int[] { R.id.title });
//
//			sportsoffer.setAdapter(adapter);
//
//			sportsoffer.setOnItemClickListener(new OnItemClickListener() {
//
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view,
//						int position, long id) {
//					System.out.println("click");
//					System.out.println(position);
//					displaySportsCourses(categoryList.get(position).get("id"));
//				}
//
//			});
//
//		}
//
//		private void displaySportsCourses(String categoryId) {
//			Intent intent = new Intent(rootView.getContext(),
//					DisplaySportCoursesActivity.class);
//			intent.putExtra("categoryId", categoryId);
//			startActivity(intent);
//
//		}
//	}
//
//	protected class TodaysSportsCourseCategoryLoader extends
//			AsyncDataLoader<List<SportsCourseCategory>> {
//		private TodaysSportsCourseCategoryLoader(View rootView) {
//			super(rootView);
//		}
//
//		@Override
//		protected List<SportsCourseCategory> doInBackground(Void... unused) {
//			AccessFacade accessFacade = new AccessFacade();
//
//			List<SportsCourseCategory> sportsCourseCategories = accessFacade
//					.getSportsCourseAccess().getTodaysSportsCourses();
//
//			if (sportsCourseCategories == null) {
//				publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
//				sportsCourseCategories = accessFacade.getSportsCourseAccess()
//						.getTodaysSportsCourses();
//			}
//
//			if (sportsCourseCategories == null) {
//				sportsCourseCategories = Collections
//						.unmodifiableList(new ArrayList<SportsCourseCategory>());
//			}
//
//			return sportsCourseCategories;
//		}
//
//		@Override
//		protected void onPostExecute(
//				List<SportsCourseCategory> sportsCourseCategories) {
//			ListView sportsoffer = (ListView) rootView
//					.findViewById(R.id.categories);
//
//			final ArrayList<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();
//
//			for (SportsCourseCategory category : sportsCourseCategories) {
//
//				HashMap<String, String> temp1 = new HashMap<String, String>();
//				temp1.put("id", category.getId());
//				temp1.put("title", category.getTitle());
//				categoryList.add(temp1);
//			}
//
//			SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
//					categoryList, R.layout.custom_row_view,
//					new String[] { "title", }, new int[] { R.id.title });
//
//			sportsoffer.setAdapter(adapter);
//
//			sportsoffer.setOnItemClickListener(new OnItemClickListener() {
//
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view,
//						int position, long id) {
//					System.out.println("click");
//					System.out.println(position);
////					displaySportsCourses(categoryList.get(position).get("id"));
//				}
//
//			});
//		}
//	}
}
