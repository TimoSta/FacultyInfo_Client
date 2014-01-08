package de.uni_passau.facultyinfo.client.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.Toast;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.activity.DisplaySportCoursesActivity;
import de.uni_passau.facultyinfo.client.fragment.NewsFragment.NewsLoader;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.SportsCourseCategory;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

public class SportsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
	
	private View rootView; 

	public SportsFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_sports, container,
				false);

		getActivity().setTitle(R.string.title_sports);
		
//		Switch slider = (Switch) rootView.findViewById(R.id.timePeriod); 
//		slider.setTextOn("A-Z"); 
//		slider.setTextOff("heute"); 
		
		Switch s = (Switch) rootView.findViewById(R.id.timePeriod);
        if (s != null) {
            s.setOnCheckedChangeListener(this);
        }
        
        SportsCourseCategoryLoader sportsCategoryLoader = new SportsCourseCategoryLoader(rootView);
		sportsCategoryLoader.execute();
        

		return rootView;

	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_search:
			System.out.println("Search");
			

			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	 @Override
	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	     if(isChecked){
	    	 System.out.println("A-Z"); 
	    	 SportsCourseCategoryLoader sportsCategoryLoader = new SportsCourseCategoryLoader(rootView);
	 		sportsCategoryLoader.execute();
	     }else{
	    	 System.out.println("heute"); 
	    	 TodaysSportsCourseCategoryLoader sportsCategoryLoader = new TodaysSportsCourseCategoryLoader(rootView);
	 		sportsCategoryLoader.execute();
	     }
//		 
//		 Toast.makeText(rootView.getContext(), "Monitored switch is " + (isChecked ? "on" : "off"),
//	               Toast.LENGTH_SHORT).show();
	    }

	protected class SportsCourseCategoryLoader extends AsyncDataLoader<List<SportsCourseCategory>> {
		private SportsCourseCategoryLoader(View rootView) {
			super(rootView);
		}

		@Override
		protected List<SportsCourseCategory> doInBackground(Void... unused) {
			AccessFacade accessFacade = new AccessFacade();

			List<SportsCourseCategory> sportsCourseCategories = accessFacade.getSportsCourseAccess().getSportsCourses(); 

			if (sportsCourseCategories == null) {
				publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
				sportsCourseCategories = accessFacade.getSportsCourseAccess().getSportsCourses(); 
			}

			if (sportsCourseCategories == null) {
				sportsCourseCategories = Collections
						.unmodifiableList(new ArrayList<SportsCourseCategory>());
			}

			return sportsCourseCategories;
		}

		@Override
		protected void onPostExecute(List<SportsCourseCategory> sportsCourseCategories) {
			ListView sportsoffer = (ListView) rootView.findViewById(R.id.sportsoffer);
			
			final ArrayList<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();

			for (SportsCourseCategory category: sportsCourseCategories) {
				
				HashMap<String, String> temp1 = new HashMap<String, String>();
				temp1.put("id", category.getId());
				temp1.put("title", category.getTitle());
				categoryList.add(temp1);
			}

			SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
					categoryList, R.layout.custom_row_view, new String[] { "title",
							}, new int[] { R.id.title });			
			
			sportsoffer.setAdapter(adapter); 
			
			sportsoffer.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					System.out.println("click");
					System.out.println(position);
					displaySportsCourses(categoryList.get(position).get("id"));
				}

				
			});
		}
	}
	
	private void displaySportsCourses(String categoryId) {		
		Intent intent = new Intent(rootView.getContext(), DisplaySportCoursesActivity.class); 
		intent.putExtra("categoryId", categoryId); 
		startActivity(intent); 
		
	}
	
	
	protected class TodaysSportsCourseCategoryLoader extends AsyncDataLoader<List<SportsCourseCategory>> {
		private TodaysSportsCourseCategoryLoader(View rootView) {
			super(rootView);
		}

		@Override
		protected List<SportsCourseCategory> doInBackground(Void... unused) {
			AccessFacade accessFacade = new AccessFacade();

			List<SportsCourseCategory> sportsCourseCategories = accessFacade.getSportsCourseAccess().getTodaysSportsCourses(); 

			if (sportsCourseCategories == null) {
				publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
				sportsCourseCategories = accessFacade.getSportsCourseAccess().getTodaysSportsCourses(); 
			}

			if (sportsCourseCategories == null) {
				sportsCourseCategories = Collections
						.unmodifiableList(new ArrayList<SportsCourseCategory>());
			}

			return sportsCourseCategories;
		}

		@Override
		protected void onPostExecute(List<SportsCourseCategory> sportsCourseCategories) {
			ListView sportsoffer = (ListView) rootView.findViewById(R.id.sportsoffer);
			
			final ArrayList<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();

			for (SportsCourseCategory category: sportsCourseCategories) {
				
				HashMap<String, String> temp1 = new HashMap<String, String>();
				temp1.put("id", category.getId());
				temp1.put("title", category.getTitle());
				categoryList.add(temp1);
			}

			SimpleAdapter adapter = new SimpleAdapter(rootView.getContext(),
					categoryList, R.layout.custom_row_view, new String[] { "title",
							}, new int[] { R.id.title });			
			
			sportsoffer.setAdapter(adapter); 
			
			sportsoffer.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					System.out.println("click");
					System.out.println(position);
					displaySportsCourses(categoryList.get(position).get("id"));
				}

				
			});
		}
	}
	
	
}

