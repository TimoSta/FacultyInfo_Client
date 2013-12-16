package de.uni_passau.facultyinfo.client.activity;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;

public class TimetableActivity extends Activity implements OnNavigationListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timetable);
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(false);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.spinner_menu_timetable,
				android.R.layout.simple_spinner_item);
		getActionBar().setSelectedNavigationItem(1);
		getActionBar().setListNavigationCallbacks(adapter, this);
		
		
		TableLayout timetable = (TableLayout) findViewById (R.id.time); 

		
		 TableRow th = (TableRow) findViewById(R.id.th); th.setPadding(0, 0,
		 0, 0);
		 
		

		TextView mo = (TextView) findViewById(R.id.mo);
		mo.setText("Mo");

		TextView di = (TextView) findViewById(R.id.di);
		di.setText("Di");

		TextView mi = (TextView) findViewById(R.id.mi);
		mi.setText("Mi");

		TextView don = (TextView) findViewById(R.id.don);
		don.setText("Do");

		TextView fr = (TextView) findViewById(R.id.fr);
		fr.setText("Fr");

		System.out.println("firstRow");

		// 8:00-10:00
		TextView tr810 = (TextView) findViewById(R.id.t810);
		tr810.setText("8:00-10:00");

		TextView mo810 = (TextView) findViewById(R.id.mo810);
		mo810.setText("Mo810");

		TextView di810 = (TextView) findViewById(R.id.di810);
		di810.setText("Di810");

		TextView mi810 = (TextView) findViewById(R.id.mi810);
		mi810.setText("Mi810");

		TextView don810 = (TextView) findViewById(R.id.don810);
		don810.setText("Do810");

		TextView fr810 = (TextView) findViewById(R.id.fr810);
		fr810.setText("Fr810");

		System.out.println("secondRow");

		// 10:00-12:00
		TextView tr1012 = (TextView) findViewById(R.id.t1012);
		tr1012.setText("10:00-12:00");

		TextView mo1012 = (TextView) findViewById(R.id.mo1012);
		mo1012.setText("Mo810");

		TextView di1012 = (TextView) findViewById(R.id.di1012);
		di1012.setText("Di810");

		TextView mi1012 = (TextView) findViewById(R.id.mi1012);
		mi1012.setText("Mi810");

		TextView don1012 = (TextView) findViewById(R.id.don1012);
		don1012.setText("Do810");

		TextView fr1012 = (TextView) findViewById(R.id.fr1012);
		fr1012.setText("Fr810");

		System.out.println("thirdRow");

		// 12:00-14:00
		TextView tr1214 = (TextView) findViewById(R.id.t1214);
		tr1214.setText("12:00-14:00");

		TextView mo1214 = (TextView) findViewById(R.id.mo1214);
		mo1214.setText("Mo1214");

		TextView di1214 = (TextView) findViewById(R.id.di1214);
		di1214.setText("Di1214");

		TextView mi1214 = (TextView) findViewById(R.id.mi1214);
		mi1214.setText("Mi1214");

		TextView don1214 = (TextView) findViewById(R.id.don1214);
		don1214.setText("Do1214");

		TextView fr1214 = (TextView) findViewById(R.id.fr1214);
		fr1214.setText("Fr1214");

		// 14:00-16:00
		TextView tr1416 = (TextView) findViewById(R.id.t1416);
		tr1416.setText("14:00-16:00");

		TextView mo1416 = (TextView) findViewById(R.id.mo1416);
		mo1416.setText("Mo1416");

		TextView di1416 = (TextView) findViewById(R.id.di1416);
		di1416.setText("Di1416");

		TextView mi1416 = (TextView) findViewById(R.id.mi1416);
		mi1416.setText("Mi1416");

		TextView don1416 = (TextView) findViewById(R.id.don1416);
		don1416.setText("Do1416");

		TextView fr1416 = (TextView) findViewById(R.id.fr1416);
		fr1416.setText("Fr1416");

		// 16:00-18:00
		TextView tr1618 = (TextView) findViewById(R.id.t1618);
		tr1618.setText("16:00-18:00");

		TextView mo1618 = (TextView) findViewById(R.id.mo1618);
		mo1618.setText("Mo1618");

		TextView di1618 = (TextView) findViewById(R.id.di1618);
		di1618.setText("Di1618");

		TextView mi1618 = (TextView) findViewById(R.id.mi1618);
		mi1618.setText("Mi1618");

		TextView don1618 = (TextView) findViewById(R.id.don1618);
		don1618.setText("Do1618");

		TextView fr1618 = (TextView) findViewById(R.id.fr1618);
		fr1618.setText("Fr1618");

		// 18:00-20:00
		TextView tr1820 = (TextView) findViewById(R.id.t1820);
		tr1820.setText("18:00-20:00");

		TextView mo1820 = (TextView) findViewById(R.id.mo1820);
		mo1820.setText("Mo1820");

		TextView di1820 = (TextView) findViewById(R.id.di1820);
		di1820.setText("Di1820");

		TextView mi1820 = (TextView) findViewById(R.id.mi1820);
		mi1820.setText("Mi1820");

		TextView don1820 = (TextView) findViewById(R.id.don1820);
		don1820.setText("Do1820");

		TextView fr1820 = (TextView) findViewById(R.id.fr1820);
		fr1820.setText("Fr1820");
		

		/**
		 * System.out.println("test"); TableLayout timeTable = (TableLayout)
		 * findViewById(R.id.time); System.out.println("table");
		 * 
		 * TableRow th = (TableRow) (findViewById(R.id.zeit)); //TableRow th =
		 * new TableRow (this);
		 * 
		 * System.out.println("th");
		 * 
		 * th.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		 * LayoutParams.WRAP_CONTENT));
		 * 
		 * System.out.println("setLayoutParams");
		 * 
		 * for (int i = 0; i < 5; i++) { System.out.println("for"); TextView td
		 * = new TextView(this); switch (i) { case 0: td.setText("Mo");
		 * System.out.println("Mo"); break; case 1: td.setText("Di");
		 * System.out.println("Di"); break; case 2: td.setText("Mi");
		 * System.out.println("Mi"); break; case 3: td.setText("Do");
		 * System.out.println("Do"); break; case 4: td.setText("Fr");
		 * System.out.println("Fr"); System.out.println(td.getText()); break; }
		 * /*td.setLayoutParams(new LayoutParams( LayoutParams.FILL_PARENT,
		 * LayoutParams.WRAP_CONTENT));
		 */
		/**
		 * th.addView(td);
		 * 
		 * 
		 * } System.out.println("endFor"); // timeTable.addView(th);
		 * //timeTable.removeAllViews(); //timeTable.addView(th);
		 * //timeTable.addView(th, new TableLayout.LayoutParams(
		 * //LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		 * 
		 * for (int j = 0; j < 6; j++) { //TableRow tr = (TableRow)
		 * (findViewById(R.id.zeit)); TableRow tr = new TableRow (this); for
		 * (int i = 0; i < 7; i++) { TextView td = new TextView(this); if (i ==
		 * 0 && j == 0) { td.setText("8:00-10:00"); } else if (i == 0 && j == 1)
		 * { td.setText("10:00-12:00"); } else if (i == 0 && j == 2) {
		 * td.setText("12:00-14:00"); } else if (i == 0 && j == 3) {
		 * td.setText("14:00-16:00"); } else if (i == 0 && j == 4) {
		 * td.setText("16:00-18:00"); } else if (i == 0 && j == 5) {
		 * td.setText("18:00-20:00"); }else{ td.setText("test"); }
		 * td.setLayoutParams(new LayoutParams( LayoutParams.FILL_PARENT,
		 * LayoutParams.WRAP_CONTENT)); tr.addView(td);
		 * 
		 * } //timeTable.removeAllViews(); timeTable.addView(tr);
		 * //timeTable.addView(tr, new TableLayout.LayoutParams(
		 * //LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT)); }
		 **/

		// final ArrayList<String> timeList = new ArrayList<String>();

		// SimpleAdapter timeAdapter = new SimpleAdapter(this, timeList,
		// R.layout.activity_timetable, )

		// LinearLayout linearMain = (LinearLayout) findViewById(R.id.time);

		// linearMain.removeAllViews();
		// linearMain.invalidate();

		// AddLayout();

		// TableLayout tableContainer = new
		// TableLayout(getApplicationContext());
		// TableLayout timeTable = (TableLayout) findViewById(R.id.test2);
		// TableLayout.LayoutParams tab_lp = new
		// TableLayout.LayoutParams(LayoutParams.FILL_PARENT,
		// LayoutParams.FILL_PARENT);
		// timeTable.setLayoutParams(tab_lp);

		/**
		 * TableRow row = new TableRow(this);
		 * 
		 * final TextView tvProduct = (TextView) findViewById(R.id.test2);
		 * 
		 * // LinearLayout.LayoutParams lp_l3 = new //
		 * LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, //
		 * (LayoutParams.WRAP_CONTENT)); // tvProduct.setLayoutParams(lp_l3);
		 * tvProduct.setText("test");
		 * 
		 * row.addView(tvProduct, new TableRow.LayoutParams(1));
		 * 
		 * TableRow row2 = new TableRow(this);
		 * 
		 * final TextView tvProduct2 = (TextView) findViewById(R.id.test2);
		 * 
		 * // LinearLayout.LayoutParams lp_l3 = new //
		 * LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, //
		 * (LayoutParams.WRAP_CONTENT)); // tvProduct.setLayoutParams(lp_l3);
		 * tvProduct2.setText("test2");
		 * 
		 * row2.addView(tvProduct, new TableRow.LayoutParams(1));
		 * 
		 * timeTable.addView(row, new TableLayout.LayoutParams());
		 **/

		// linearMain.addView(timeTable);

		/*
		 * final ArrayList<HashMap<String, String>> list = new
		 * ArrayList<HashMap<String, String>>();
		 * 
		 * GridView gridView = (GridView) findViewById(R.id.gridView1);
		 * 
		 * SimpleAdapter timeAdapter = new SimpleAdapter(this, list,
		 * R.layout.custom_row_view, new String[] { "title", "time", }, new
		 * int[] { R.id.title, R.id.time}
		 * 
		 * );
		 * 
		 * HashMap<String, String> temp0 = new HashMap<String, String>();
		 * temp0.put("title", ""); list.add(temp0);
		 * 
		 * HashMap<String, String> temp1 = new HashMap<String, String>();
		 * temp1.put("title", "Mo"); list.add(temp1);
		 * 
		 * HashMap<String, String> temp2 = new HashMap<String, String>();
		 * temp2.put("title", "Di"); list.add(temp2);
		 * 
		 * HashMap<String, String> temp3 = new HashMap<String, String>();
		 * temp3.put("title", "Mi"); list.add(temp3);
		 * 
		 * HashMap<String, String> temp4 = new HashMap<String, String>();
		 * temp4.put("title", "Do"); list.add(temp4);
		 * 
		 * HashMap<String, String> temp5 = new HashMap<String, String>();
		 * temp5.put("title", "Fr"); list.add(temp5);
		 * 
		 * HashMap<String, String> temp6 = new HashMap<String, String>();
		 * temp6.put("time", "8:00-10:00"); list.add(temp6);
		 * 
		 * for(int i=0; i<5; i++){ HashMap<String, String> temp7 = new
		 * HashMap<String, String>(); temp7.put("time", ""); list.add(temp7); }
		 * 
		 * HashMap<String, String> temp10 = new HashMap<String, String>();
		 * temp10.put("time", "10:00-12:00"); list.add(temp10);
		 * 
		 * for(int i=0; i<5; i++){ HashMap<String, String> temp7 = new
		 * HashMap<String, String>(); temp7.put("time", ""); list.add(temp7); }
		 * 
		 * HashMap<String, String> temp11 = new HashMap<String, String>();
		 * temp11.put("time", "12:00-14:00"); list.add(temp11);
		 * 
		 * for(int i=0; i<5; i++){ HashMap<String, String> temp7 = new
		 * HashMap<String, String>(); temp7.put("time", ""); list.add(temp7); }
		 * 
		 * HashMap<String, String> temp12 = new HashMap<String, String>();
		 * temp12.put("time", "14:00-16:00"); list.add(temp12);
		 * 
		 * for(int i=0; i<5; i++){ HashMap<String, String> temp7 = new
		 * HashMap<String, String>(); temp7.put("time", ""); list.add(temp7); }
		 * 
		 * HashMap<String, String> temp13 = new HashMap<String, String>();
		 * temp13.put("time", "16:00-18:00"); list.add(temp13);
		 * 
		 * for(int i=0; i<5; i++){ HashMap<String, String> temp7 = new
		 * HashMap<String, String>(); temp7.put("time", ""); list.add(temp7); }
		 * 
		 * HashMap<String, String> temp14 = new HashMap<String, String>();
		 * temp14.put("time", "18:00-20:00"); list.add(temp14);
		 * 
		 * for(int i=0; i<5; i++){ HashMap<String, String> temp7 = new
		 * HashMap<String, String>(); temp7.put("time", ""); list.add(temp7); }
		 * 
		 * 
		 * gridView.setAdapter(timeAdapter);
		 */

		/**
		 * für GridView
		 * 
		 * 
		 * <RelativeLayout
		 * xmlns:android="http://schemas.android.com/apk/res/android"
		 * xmlns:tools="http://schemas.android.com/tools"
		 * android:layout_width="match_parent"
		 * android:layout_height="match_parent"
		 * android:paddingBottom="@dimen/activity_vertical_margin"
		 * android:paddingLeft="@dimen/activity_horizontal_margin"
		 * android:paddingRight="@dimen/activity_horizontal_margin"
		 * android:paddingTop="@dimen/activity_vertical_margin"
		 * tools:context=".TimetableActivity" >
		 * 
		 * <GridView android:id="@+id/gridView1"
		 * android:layout_width="match_parent"
		 * android:layout_height="wrap_content"
		 * android:layout_alignParentLeft="true"
		 * android:layout_alignParentTop="true" android:numColumns="6" >
		 * 
		 * 
		 * </GridView>
		 * 
		 * </RelativeLayout>
		 * 
		 */

	}
	
	void onClick(View view){
		System.out.println(view.getId()); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timetable, menu);
		return true;
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {

		System.out.println("NewsActivity->onNavigationItemSelected->itemId");
		System.out.println(itemId);

		if (itemId == 1) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			return true;
		} else if (itemId == 2) {
			Intent intent = new Intent(this, NewsActivity.class);
			startActivity(intent);
			return true;
		} else if (itemId == 3) {
			Intent intent = new Intent(this, BuslinesActivity.class);
			startActivity(intent);
			return true;
		} else if (itemId == 4) {
			Intent intent = new Intent(this, SportsActivity.class);
			startActivity(intent);
			return true;
		} else if (itemId == 5) {
			Intent intent = new Intent(this, CafeteriaActivity.class);
			startActivity(intent);
			return true;
		} else if (itemId == 6) {
			Intent intent = new Intent(this, ContactsActivity.class);
			startActivity(intent);
			return true;
		} else if (itemId == 7) {
			Intent intent = new Intent(this, FAQsActivity.class);
			startActivity(intent);
			return true;
		}
		return false;
	}

}
