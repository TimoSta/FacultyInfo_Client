package de.uni_passau.facultyinfo.client.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.R.layout;
import de.uni_passau.facultyinfo.client.R.menu;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.Faq;
import de.uni_passau.facultyinfo.client.model.dto.FaqCategory;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class DisplayFAQActivity extends Activity {
	private String faqId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_faq);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);

		Intent intent = getIntent();
		faqId = intent.getStringExtra("faqId");

		setTitle(intent.getStringExtra("category"));
		
		(new FaqLoader()).execute(); 

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_faq, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected class FaqLoader extends AsyncDataLoader<Faq> {


		@Override
		protected Faq doInBackground(Void... unused) {
			AccessFacade accessFacade = new AccessFacade();
//			Faq faq = null;

			Faq faq = accessFacade.getFaqAccess().getFaq(faqId);

//			if (faq == null) {
//				publishProgress(AsyncDataLoader.NO_CONNECTION_PROGRESS);
//				faq = accessFacade.getFaqAccess().get();
//			}
//
//			if (faq == null) {
//				faq = new ArrayList<FaqCategory>();
//			}

			return faq;
		}

		@Override
		protected void onPostExecute(Faq faq) {
			TextView headingView = (TextView) findViewById(R.id.faqHeading);
			System.out.println(faq.getTitle()); 
			headingView.setText(faq.getTitle());

			TextView textView = (TextView) findViewById(R.id.faqText);
			System.out.println(faq.getText()); 
			textView.setMovementMethod(new ScrollingMovementMethod());
			textView.setText(faq.getText());

		}
	}

}
