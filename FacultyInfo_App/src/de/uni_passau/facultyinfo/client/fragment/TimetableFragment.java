package de.uni_passau.facultyinfo.client.fragment;

import java.util.List;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.activity.DisplayTimeTableEntryActivity;
import de.uni_passau.facultyinfo.client.activity.EditTimeTableActivity;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.TimetableEntry;
import de.uni_passau.facultyinfo.client.util.ColorHelper;
import android.support.v4.app.FragmentManager;

//import android.widget.TableRow;

public class TimetableFragment extends Fragment {

	private View rootView;

	private int dayC;
	private int timeslotId;

	OnLongClickListener onLongClickListener;

	public TimetableFragment() {
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_timetable_test,
				container, false);

		getActivity().getActionBar().setTitle(
				getActivity().getApplicationContext().getString(
						R.string.title_timetable));
		getActivity().getActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_STANDARD);

		// TableRow th = (TableRow) rootView.findViewById(R.id.th);
		// th.setPadding(0, 0, 0, 0);

		onLongClickListener = new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				System.out.println("onLongClick"); 
				editEventTT(v);
				return true;
			}
		};

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

		System.out.println("secondRow");

		// 10:00-12:00
		TextView tr1012 = (TextView) rootView.findViewById(R.id.t1012);
		tr1012.setText("10:00-12:00");

		// 12:00-14:00
		TextView tr1214 = (TextView) rootView.findViewById(R.id.t1214);
		tr1214.setText("12:00-14:00");

		// 14:00-16:00
		TextView tr1416 = (TextView) rootView.findViewById(R.id.t1416);
		tr1416.setText("14:00-16:00");

		// 16:00-18:00
		TextView tr1618 = (TextView) rootView.findViewById(R.id.t1618);
		tr1618.setText("16:00-18:00");

		// 18:00-20:00
		TextView tr1820 = (TextView) rootView.findViewById(R.id.t1820);
		tr1820.setText("18:00-20:00");

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

		((TextView) rootView.findViewById(R.id.mo810))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.mo1012))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.mo1214))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.mo1416))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.mo1618))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.mo1820))
				.setOnLongClickListener(onLongClickListener);

		((TextView) rootView.findViewById(R.id.di810))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.di1012))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.di1214))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.di1416))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.di1618))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.di1820))
				.setOnLongClickListener(onLongClickListener);

		((TextView) rootView.findViewById(R.id.mi810))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.mi1012))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.mi1214))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.mi1416))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.mi1618))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.mi1820))
				.setOnLongClickListener(onLongClickListener);

		((TextView) rootView.findViewById(R.id.don810))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.don1012))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.don1214))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.don1416))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.don1618))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.don1820))
				.setOnLongClickListener(onLongClickListener);

		((TextView) rootView.findViewById(R.id.fr810))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.fr1012))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.fr1214))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.fr1416))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.fr1618))
				.setOnLongClickListener(onLongClickListener);
		((TextView) rootView.findViewById(R.id.fr1820))
				.setOnLongClickListener(onLongClickListener);

		((TextView) rootView.findViewById(R.id.mo810))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.mo1012))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.mo1214))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.mo1416))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.mo1618))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.mo1820))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));

		((TextView) rootView.findViewById(R.id.di810))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.di1012))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.di1214))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.di1416))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.di1618))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.di1820))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));

		((TextView) rootView.findViewById(R.id.mi810))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.mi1012))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.mi1214))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.mi1416))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.mi1618))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.mi1820))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));

		((TextView) rootView.findViewById(R.id.don810))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.don1012))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.don1214))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.don1416))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.don1618))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.don1820))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));

		((TextView) rootView.findViewById(R.id.fr810))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.fr1012))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.fr1214))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.fr1416))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.fr1618))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));
		((TextView) rootView.findViewById(R.id.fr1820))
				.setBackgroundColor(android.graphics.Color
						.parseColor("#FFFFFF"));

		new TimetableEntryLoader().execute();
		super.onResume();
	}

	protected void editEventTT(View v) {
		System.out.println("editEventTT");

		if (v.getId() == (rootView.findViewById(R.id.mo810).getId())
				|| v.getId() == (rootView.findViewById(R.id.mo1012).getId())
				|| v.getId() == (rootView.findViewById(R.id.mo1214).getId())
				|| v.getId() == (rootView.findViewById(R.id.mo1416).getId())
				|| v.getId() == (rootView.findViewById(R.id.mo1618).getId())
				|| v.getId() == (rootView.findViewById(R.id.mo1820).getId())) {
			dayC = TimetableEntry.MONDAY;
		} else if (v.getId() == (rootView.findViewById(R.id.di810).getId())
				|| v.getId() == (rootView.findViewById(R.id.di1012).getId())
				|| v.getId() == (rootView.findViewById(R.id.di1214).getId())
				|| v.getId() == (rootView.findViewById(R.id.di1416).getId())
				|| v.getId() == (rootView.findViewById(R.id.di1618).getId())
				|| v.getId() == (rootView.findViewById(R.id.di1820).getId())) {
			dayC = TimetableEntry.TUESDAY;
		} else if (v.getId() == (rootView.findViewById(R.id.mi810).getId())
				|| v.getId() == (rootView.findViewById(R.id.mi1012).getId())
				|| v.getId() == (rootView.findViewById(R.id.mi1214).getId())
				|| v.getId() == (rootView.findViewById(R.id.mi1416).getId())
				|| v.getId() == (rootView.findViewById(R.id.mi1618).getId())
				|| v.getId() == (rootView.findViewById(R.id.mi1820).getId())) {
			dayC = TimetableEntry.WEDNESDAY;
		} else if (v.getId() == (rootView.findViewById(R.id.don810).getId())
				|| v.getId() == (rootView.findViewById(R.id.don1012).getId())
				|| v.getId() == (rootView.findViewById(R.id.don1214).getId())
				|| v.getId() == (rootView.findViewById(R.id.don1416).getId())
				|| v.getId() == (rootView.findViewById(R.id.don1618).getId())
				|| v.getId() == (rootView.findViewById(R.id.don1820).getId())) {
			dayC = TimetableEntry.THURSDAY;
		} else if (v.getId() == (rootView.findViewById(R.id.fr810).getId())
				|| v.getId() == (rootView.findViewById(R.id.fr1012).getId())
				|| v.getId() == (rootView.findViewById(R.id.fr1214).getId())
				|| v.getId() == (rootView.findViewById(R.id.fr1416).getId())
				|| v.getId() == (rootView.findViewById(R.id.fr1618).getId())
				|| v.getId() == (rootView.findViewById(R.id.fr1820).getId())) {
			dayC = TimetableEntry.FRIDAY;
		}

		if (v.getId() == (rootView.findViewById(R.id.mo810).getId())
				|| v.getId() == (rootView.findViewById(R.id.di810).getId())
				|| v.getId() == (rootView.findViewById(R.id.mi810).getId())
				|| v.getId() == (rootView.findViewById(R.id.don810).getId())
				|| v.getId() == (rootView.findViewById(R.id.fr810).getId())){
			timeslotId=TimetableEntry.FROM_08_TO_10; 
		}else if (v.getId() == (rootView.findViewById(R.id.mo1012).getId())
				|| v.getId() == (rootView.findViewById(R.id.di1012).getId())
				|| v.getId() == (rootView.findViewById(R.id.mi1012).getId())
				|| v.getId() == (rootView.findViewById(R.id.don1012).getId())
				|| v.getId() == (rootView.findViewById(R.id.fr1012).getId())){
			timeslotId=TimetableEntry.FROM_10_TO_12; 
		}else if (v.getId() == (rootView.findViewById(R.id.mo1214).getId())
				|| v.getId() == (rootView.findViewById(R.id.di1214).getId())
				|| v.getId() == (rootView.findViewById(R.id.mi1214).getId())
				|| v.getId() == (rootView.findViewById(R.id.don1214).getId())
				|| v.getId() == (rootView.findViewById(R.id.fr1214).getId())){
			timeslotId=TimetableEntry.FROM_12_TO_14; 
		}else if (v.getId() == (rootView.findViewById(R.id.mo1416).getId())
				|| v.getId() == (rootView.findViewById(R.id.di1416).getId())
				|| v.getId() == (rootView.findViewById(R.id.mi1416).getId())
				|| v.getId() == (rootView.findViewById(R.id.don1416).getId())
				|| v.getId() == (rootView.findViewById(R.id.fr1416).getId())){
			timeslotId=TimetableEntry.FROM_14_TO_16; 
		}else if (v.getId() == (rootView.findViewById(R.id.mo1618).getId())
				|| v.getId() == (rootView.findViewById(R.id.di1618).getId())
				|| v.getId() == (rootView.findViewById(R.id.mi1618).getId())
				|| v.getId() == (rootView.findViewById(R.id.don1618).getId())
				|| v.getId() == (rootView.findViewById(R.id.fr1618).getId())){
			timeslotId=TimetableEntry.FROM_16_TO_18; 
		}else if (v.getId() == (rootView.findViewById(R.id.mo1820).getId())
				|| v.getId() == (rootView.findViewById(R.id.di1820).getId())
				|| v.getId() == (rootView.findViewById(R.id.mi1820).getId())
				|| v.getId() == (rootView.findViewById(R.id.don1820).getId())
				|| v.getId() == (rootView.findViewById(R.id.fr1820).getId())){
			timeslotId=TimetableEntry.FROM_18_TO_20; 
		}

			(new TimetableEntrySearcher(true)).execute();
	}

	public static final class CreateEventTT extends DialogFragment {
		private View rootView;
		private int timeslotId;
		private int dayC;

		public CreateEventTT() {
			super();
		}

		private void setAttributes(View rootView, int timeslotId, int dayC) {
			this.rootView = rootView;
			this.timeslotId = timeslotId;
			this.dayC = dayC;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			if (rootView != null) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setMessage(R.string.create_event_TT_dialog)
						.setPositiveButton(R.string.create,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										Intent intent = new Intent(rootView
												.getContext(),
												EditTimeTableActivity.class);
										intent.putExtra("timeslotId",
												timeslotId);
										intent.putExtra("dayId", dayC);
										intent.putExtra("new", true);
										intent.putExtra("toOverview", true);
										intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										startActivity(intent);
									}
								})
						.setNegativeButton(R.string.cancel,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
									}
								});
				return builder.create();
			} else {
				throw new NullPointerException();
			}
		}
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
				} else if (timetableEntry.getDayOfWeek() == TimetableEntry.WEDNESDAY) {
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
				} else if (timetableEntry.getDayOfWeek() == TimetableEntry.THURSDAY) {
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

	protected class TimetableEntrySearcher extends
			AsyncTask<Void, Void, List<TimetableEntry>> {

		boolean edit = false;

		private TimetableEntrySearcher(boolean edit) {
			super();
			this.edit = edit;
			System.out.println("TimetableEntrySearcher(boolean edit)");
		}

		@Override
		protected List<TimetableEntry> doInBackground(Void... unused) {
			System.out.println("TimetableEntrySearcher->doInBackground");
			AccessFacade accessFacade = new AccessFacade();

			List<TimetableEntry> timetableEntries = accessFacade
					.getTimetableAccess().getTimetableEntries();

			return timetableEntries;
		}

		@Override
		protected void onPostExecute(List<TimetableEntry> timetableEntries) {
			boolean exists = false;

			for (TimetableEntry timetableEntry : timetableEntries) {
				if (timetableEntry.getTime() == timeslotId
						&& timetableEntry.getDayOfWeek() == dayC) {
					exists = true;
					break;
				}
			}
			if (!exists) {
				System.out.println("Anlegen");
				CreateEventTT dialog = new CreateEventTT();
				dialog.setAttributes(rootView, timeslotId, dayC);
				 dialog.show(TimetableFragment.this.getFragmentManager(),
				 "createEventTT");
			} else {
				if (edit) {
					Intent intent = new Intent(rootView.getContext(),
							EditTimeTableActivity.class);
					intent.putExtra("timeslotId", timeslotId);
					intent.putExtra("dayId", dayC);
					intent.putExtra("new", false);
					intent.putExtra("toOverview", true);
					startActivity(intent);
				} else {
					Intent intent = new Intent(rootView.getContext(),
							DisplayTimeTableEntryActivity.class);
					intent.putExtra("dayId", dayC);
					intent.putExtra("timeslotId", timeslotId);
					startActivity(intent);
				}
			}
		}
	}

}
