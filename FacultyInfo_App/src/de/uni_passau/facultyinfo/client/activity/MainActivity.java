package de.uni_passau.facultyinfo.client.activity;

import java.net.URL;
import java.util.ArrayList;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.News;

public class MainActivity extends Activity implements OnNavigationListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(false);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.spinner_menu,
				android.R.layout.simple_spinner_item);
		getActionBar().setListNavigationCallbacks(adapter, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void loadNews(View view) {
		NewsLoader newsLoader = new NewsLoader();
		newsLoader.execute();
	}

	private class NewsLoader extends AsyncTask<URL, Void, ArrayList<News>> {

		@Override
		protected ArrayList<News> doInBackground(URL... urls) {
			AccessFacade accessFacade = new AccessFacade();
			ArrayList<News> news = accessFacade.getNewsAccess().getNews();
			return news;
		}

		@Override
		protected void onPostExecute(ArrayList<News> news) {
			if (news != null && news.size() > 0) {
//				TextView textView = (TextView) findViewById(R.id.editText1);
//				textView.setText(news.get(0).getTitle() + "\n\n"
//						+ news.get(0).getDescription());
			}

		}
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		
		
		System.out.println("MainActivity->onNavigationItemSelected->itemId"); 
		System.out.println(itemId); 
		
	
		if (itemId==1){
			System.out.println("itemId==1"); 
			Intent intent = new Intent (this, NewsActivity.class);
			startActivity(intent);
			return true; 
		}else if (itemId==2){
			Intent intent = new Intent (this, TimetableActivity.class);
			startActivity(intent);
			return true; 
		}else if (itemId==3){
			Intent intent = new Intent (this, BuslinesActivity.class);
			startActivity(intent);
			return true; 
		}else if (itemId==4){
			Intent intent = new Intent (this, SportsActivity.class);
			startActivity(intent);
			return true; 
		}else if (itemId==5){
			Intent intent = new Intent (this, CafeteriaActivity.class);
			startActivity(intent);
			return true; 
		}else if (itemId==6){
			Intent intent = new Intent (this, ContactsActivity.class);
			startActivity(intent);
			return true; 
		}else if (itemId==7){
			Intent intent = new Intent (this, FAQsActivity.class);
			startActivity(intent);
			return true; 
		}
		return false;
	}

	
	
	
}


