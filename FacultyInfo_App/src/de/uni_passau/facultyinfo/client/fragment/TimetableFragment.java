package de.uni_passau.facultyinfo.client.fragment;

import java.util.List;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.TimetableEntry;
import de.uni_passau.facultyinfo.client.model.dto.util.Color;
import de.uni_passau.facultyinfo.client.util.ColorHelper;
//import android.widget.TableRow;

public class TimetableFragment extends Fragment {

	private View rootView;

	public TimetableFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_timetable_test, container,
				false);

		getActivity().getActionBar().setTitle(
				getActivity().getApplicationContext().getString(
						R.string.title_timetable));
		getActivity().getActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_STANDARD);

//		TableRow th = (TableRow) rootView.findViewById(R.id.th);
//		th.setPadding(0, 0, 0, 0);

		TextView mo = (TextView) rootView.findViewById(R.id.mo);
		mo.setText("Mo");

		TextView di = (TextView) rootView.findViewById(R.id.di);
		di.setText("Di");

		TextView mi = (TextView) rootView.findViewById(R.id.mi);
		mi.setText("Mi");

		TextView don = (TextView) rootView.findViewById(R.id.don);
		don.setText("Do");

		TextView fr = (TextView) rootView.findViewById(R.id.fr);
		fr.setText("Fr");

		System.out.println("firstRow");

		// 8:00-10:00
		TextView tr810 = (TextView) rootView.findViewById(R.id.t810);
		tr810.setText("8:00-10:00");

		// TextView mo810 = (TextView) rootView.findViewById(R.id.mo810);
		// mo810.setText("Mo810");
		//
		// TextView di810 = (TextView) rootView.findViewById(R.id.di810);
		// di810.setText("Di810");
		//
		// TextView mi810 = (TextView) rootView.findViewById(R.id.mi810);
		// mi810.setText("Mi810");
		//
		// TextView don810 = (TextView) rootView.findViewById(R.id.don810);
		// don810.setText("Do810");
		//
		// TextView fr810 = (TextView) rootView.findViewById(R.id.fr810);
		// fr810.setText("Fr810");

		System.out.println("secondRow");

		// 10:00-12:00
		TextView tr1012 = (TextView) rootView.findViewById(R.id.t1012);
		tr1012.setText("10:00-12:00");

		// TextView mo1012 = (TextView) rootView.findViewById(R.id.mo1012);
		// mo1012.setText("Mo810");
		//
		// TextView di1012 = (TextView) rootView.findViewById(R.id.di1012);
		// di1012.setText("Di810");
		//
		// TextView mi1012 = (TextView) rootView.findViewById(R.id.mi1012);
		// mi1012.setText("Mi810");
		//
		// TextView don1012 = (TextView) rootView.findViewById(R.id.don1012);
		// don1012.setText("Do810");
		//
		// TextView fr1012 = (TextView) rootView.findViewById(R.id.fr1012);
		// fr1012.setText("Fr810");
		//
		// System.out.println("thirdRow");

		// 12:00-14:00
		TextView tr1214 = (TextView) rootView.findViewById(R.id.t1214);
		tr1214.setText("12:00-14:00");

		// TextView mo1214 = (TextView) rootView.findViewById(R.id.mo1214);
		// mo1214.setText("Mo1214");
		//
		// TextView di1214 = (TextView) rootView.findViewById(R.id.di1214);
		// di1214.setText("Di1214");
		//
		// TextView mi1214 = (TextView) rootView.findViewById(R.id.mi1214);
		// mi1214.setText("Mi1214");
		//
		// TextView don1214 = (TextView) rootView.findViewById(R.id.don1214);
		// don1214.setText("Do1214");
		//
		// TextView fr1214 = (TextView) rootView.findViewById(R.id.fr1214);
		// fr1214.setText("Fr1214");

		// 14:00-16:00
		TextView tr1416 = (TextView) rootView.findViewById(R.id.t1416);
		tr1416.setText("14:00-16:00");

		// TextView mo1416 = (TextView) rootView.findViewById(R.id.mo1416);
		// mo1416.setText("Mo1416");
		//
		// TextView di1416 = (TextView) rootView.findViewById(R.id.di1416);
		// di1416.setText("Di1416");
		//
		// TextView mi1416 = (TextView) rootView.findViewById(R.id.mi1416);
		// mi1416.setText("Mi1416");
		//
		// TextView don1416 = (TextView) rootView.findViewById(R.id.don1416);
		// don1416.setText("Do1416");
		//
		// TextView fr1416 = (TextView) rootView.findViewById(R.id.fr1416);
		// fr1416.setText("Fr1416");

		// 16:00-18:00
		TextView tr1618 = (TextView) rootView.findViewById(R.id.t1618);
		tr1618.setText("16:00-18:00");

		// TextView mo1618 = (TextView) rootView.findViewById(R.id.mo1618);
		// mo1618.setText("Mo1618");
		//
		// TextView di1618 = (TextView) rootView.findViewById(R.id.di1618);
		// di1618.setText("Di1618");
		//
		// TextView mi1618 = (TextView) rootView.findViewById(R.id.mi1618);
		// mi1618.setText("Mi1618");
		//
		// TextView don1618 = (TextView) rootView.findViewById(R.id.don1618);
		// don1618.setText("Do1618");
		//
		// TextView fr1618 = (TextView) rootView.findViewById(R.id.fr1618);
		// fr1618.setText("Fr1618");

		// 18:00-20:00
		TextView tr1820 = (TextView) rootView.findViewById(R.id.t1820);
		tr1820.setText("18:00-20:00");

		// TextView mo1820 = (TextView) rootView.findViewById(R.id.mo1820);
		// mo1820.setText("Mo1820");
		//
		// TextView di1820 = (TextView) rootView.findViewById(R.id.di1820);
		// di1820.setText("Di1820");
		//
		// TextView mi1820 = (TextView) rootView.findViewById(R.id.mi1820);
		// mi1820.setText("Mi1820");
		//
		// TextView don1820 = (TextView) rootView.findViewById(R.id.don1820);
		// don1820.setText("Do1820");
		//
		// TextView fr1820 = (TextView) rootView.findViewById(R.id.fr1820);
		// fr1820.setText("Fr1820");

		// Load timetable entries

		return rootView;

	}

	@Override
	public void onResume() {
		((TextView) rootView.findViewById(R.id.mo810)).setText(null); 
		((TextView) rootView.findViewById(R.id.mo1012)).setText(null); 
		((TextView) rootView.findViewById(R.id.mo1214)).setText(null); 
		((TextView) rootView.findViewById(R.id.mo1416)).setText(null); 
		((TextView) rootView.findViewById(R.id.mo1618)).setText(null); 
		((TextView) rootView.findViewById(R.id.mo1820)).setText(null); 
		
		((TextView) rootView.findViewById(R.id.di810)).setText(null); 
		((TextView) rootView.findViewById(R.id.di1012)).setText(null); 
		((TextView) rootView.findViewById(R.id.di1214)).setText(null); 
		((TextView) rootView.findViewById(R.id.di1416)).setText(null); 
		((TextView) rootView.findViewById(R.id.di1618)).setText(null); 
		((TextView) rootView.findViewById(R.id.di1820)).setText(null); 
		
		((TextView) rootView.findViewById(R.id.mi810)).setText(null); 
		((TextView) rootView.findViewById(R.id.mi1012)).setText(null); 
		((TextView) rootView.findViewById(R.id.mi1214)).setText(null); 
		((TextView) rootView.findViewById(R.id.mi1416)).setText(null); 
		((TextView) rootView.findViewById(R.id.mi1618)).setText(null); 
		((TextView) rootView.findViewById(R.id.mi1820)).setText(null); 
		
		((TextView) rootView.findViewById(R.id.don810)).setText(null); 
		((TextView) rootView.findViewById(R.id.don1012)).setText(null); 
		((TextView) rootView.findViewById(R.id.don1214)).setText(null); 
		((TextView) rootView.findViewById(R.id.don1416)).setText(null); 
		((TextView) rootView.findViewById(R.id.don1618)).setText(null); 
		((TextView) rootView.findViewById(R.id.don1820)).setText(null); 
		
		((TextView) rootView.findViewById(R.id.fr810)).setText(null); 
		((TextView) rootView.findViewById(R.id.fr1012)).setText(null); 
		((TextView) rootView.findViewById(R.id.fr1214)).setText(null); 
		((TextView) rootView.findViewById(R.id.fr1416)).setText(null); 
		((TextView) rootView.findViewById(R.id.fr1618)).setText(null); 
		((TextView) rootView.findViewById(R.id.fr1820)).setText(null); 
		
		((TextView) rootView.findViewById(R.id.mo810)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.mo1012)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.mo1214)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.mo1416)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.mo1618)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.mo1820)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		
		((TextView) rootView.findViewById(R.id.di810)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.di1012)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.di1214)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.di1416)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.di1618)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.di1820)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		
		((TextView) rootView.findViewById(R.id.mi810)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.mi1012)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.mi1214)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.mi1416)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.mi1618)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.mi1820)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		
		((TextView) rootView.findViewById(R.id.don810)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.don1012)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.don1214)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.don1416)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.don1618)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.don1820)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		
		((TextView) rootView.findViewById(R.id.fr810)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.fr1012)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.fr1214)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.fr1416)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.fr1618)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		((TextView) rootView.findViewById(R.id.fr1820)).setBackgroundColor(android.graphics.Color.parseColor("#FFFFFF")); 
		
		new TimetableEntryLoader().execute();
		super.onResume();
	}

	protected class TimetableEntryLoader extends
			AsyncTask<Void, Void, List<TimetableEntry>> {

		@Override
		protected List<TimetableEntry> doInBackground(Void... unused) {
			System.out.println("doInBackground"); 
			AccessFacade accessFacade = new AccessFacade();

			List<TimetableEntry> timetableEntries = accessFacade
					.getTimetableAccess().getTimetableEntries();

			return timetableEntries;
		}

		@Override
		protected void onPostExecute(List<TimetableEntry> timetableEntries) {
			System.out.println("onPostExecute"); 
			
			
			

			for (TimetableEntry timetableEntry : timetableEntries) {
				System.out.println("for"); 
				if (timetableEntry.getDayOfWeek() == TimetableEntry.MONDAY) {
					if (timetableEntry.getTime() == TimetableEntry.FROM_08_TO_10) {
						prepareCell(R.id.mo810, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_10_TO_12) {
						prepareCell(R.id.mo1012, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_12_TO_14) {
						prepareCell(R.id.mo1214, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_14_TO_16) {
						prepareCell(R.id.mo1416, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_16_TO_18) {
						prepareCell(R.id.mo1618, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_18_TO_20) {
						prepareCell(R.id.mo1820, timetableEntry);
					}
				} else if (timetableEntry.getDayOfWeek() == TimetableEntry.TUESDAY) {
					if (timetableEntry.getTime() == TimetableEntry.FROM_08_TO_10) {
						prepareCell(R.id.di810, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_10_TO_12) {
						prepareCell(R.id.di1012, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_12_TO_14) {
						prepareCell(R.id.di1214, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_14_TO_16) {
						prepareCell(R.id.di1416, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_16_TO_18) {
						prepareCell(R.id.di1618, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_18_TO_20) {
						prepareCell(R.id.di1820, timetableEntry);
					}
				} else if (timetableEntry.getDayOfWeek() == TimetableEntry.WEDNESDAY){
					if (timetableEntry.getTime() == TimetableEntry.FROM_08_TO_10) {
						prepareCell(R.id.mi810, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_10_TO_12) {
						prepareCell(R.id.mi1012, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_12_TO_14) {
						prepareCell(R.id.mi1214, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_14_TO_16) {
						prepareCell(R.id.mi1416, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_16_TO_18) {
						prepareCell(R.id.mi1618, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_18_TO_20) {
						prepareCell(R.id.mi1820, timetableEntry);
					}
				}else if (timetableEntry.getDayOfWeek() == TimetableEntry.THURSDAY) {
					if (timetableEntry.getTime() == TimetableEntry.FROM_08_TO_10) {
						prepareCell(R.id.don810, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_10_TO_12) {
						prepareCell(R.id.don1012, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_12_TO_14) {
						prepareCell(R.id.don1214, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_14_TO_16) {
						prepareCell(R.id.don1416, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_16_TO_18) {
						prepareCell(R.id.don1618, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_18_TO_20) {
						prepareCell(R.id.don1820, timetableEntry);
					}
				} else if (timetableEntry.getDayOfWeek() == TimetableEntry.FRIDAY) {
						if (timetableEntry.getTime() == TimetableEntry.FROM_08_TO_10) {
							prepareCell(R.id.fr810, timetableEntry);
						} else if (timetableEntry.getTime() == TimetableEntry.FROM_10_TO_12) {
							prepareCell(R.id.fr1012, timetableEntry);
						} else if (timetableEntry.getTime() == TimetableEntry.FROM_12_TO_14) {
							prepareCell(R.id.fr1214, timetableEntry);
						} else if (timetableEntry.getTime() == TimetableEntry.FROM_14_TO_16) {
							prepareCell(R.id.fr1416, timetableEntry);
						} else if (timetableEntry.getTime() == TimetableEntry.FROM_16_TO_18) {
							prepareCell(R.id.fr1618, timetableEntry);
						} else if (timetableEntry.getTime() == TimetableEntry.FROM_18_TO_20) {
							prepareCell(R.id.fr1820, timetableEntry);
						}
				}
			}

		}

		private void prepareCell(int id, TimetableEntry entry) {
			System.out.println("prepareCell"); 
			ColorHelper colorHelper = new ColorHelper();
			TextView view = (TextView) rootView.findViewById(id);
			view.setText(entry.getTitle());
			view.setBackgroundColor(colorHelper.getColor(entry.getColor())
					.getBackgroundColor());
			view.setTextColor(colorHelper.getColor(entry.getColor())
					.getFontColor());
		}
	}

}
