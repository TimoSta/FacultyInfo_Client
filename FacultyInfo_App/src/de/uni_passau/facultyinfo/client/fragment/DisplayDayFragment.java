
package de.uni_passau.facultyinfo.client.fragment;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.activity.DisplayTimeTableEntryActivity;
import de.uni_passau.facultyinfo.client.activity.EditTimeTableActivity;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.TimetableEntry;
import de.uni_passau.facultyinfo.client.util.ColorHelper;

public class DisplayDayFragment extends Fragment {

	public static final String ARG_DAY = "day";
	private View rootView;
	private int timeslotId;
	private int dayId;
	private int dayC;

	public DisplayDayFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_display_day, container,
				false);
		Bundle args = getArguments();
		dayId = args.getInt(ARG_DAY);

		if (dayId == 1) {
			dayC = TimetableEntry.MONDAY;
		} else if (dayId == 2) {
			dayC = TimetableEntry.TUESDAY;
		} else if (dayId == 3) {
			dayC = TimetableEntry.WEDNESDAY;
		} else if (dayId == 4) {
			dayC = TimetableEntry.THURSDAY;
		} else if (dayId == 5) {
			dayC = TimetableEntry.FRIDAY;
		}

		OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				editEventTT(v);
				return true;
			}
		};
		OnClickListener onClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showEventTT(v);
			}
		};

		TextView td810 = (TextView) rootView.findViewById(R.id.td810);
		td810.setOnLongClickListener(onLongClickListener);
		td810.setOnClickListener(onClickListener);
		td810.setText(System.getProperty("line.separator"));

		TextView td1012 = (TextView) rootView.findViewById(R.id.td1012);
		td1012.setOnLongClickListener(onLongClickListener);
		td1012.setOnClickListener(onClickListener);
		td1012.setText(System.getProperty("line.separator"));
		

		TextView td1214 = (TextView) rootView.findViewById(R.id.td1214);
		td1214.setOnLongClickListener(onLongClickListener);
		td1214.setOnClickListener(onClickListener);
		td1214.setText(System.getProperty("line.separator"));

		TextView td1416 = (TextView) rootView.findViewById(R.id.td1416);
		td1416.setOnLongClickListener(onLongClickListener);
		td1416.setOnClickListener(onClickListener);
		td1416.setText(System.getProperty("line.separator"));

		TextView td1618 = (TextView) rootView.findViewById(R.id.td1618);
		td1618.setOnLongClickListener(onLongClickListener);
		td1618.setOnClickListener(onClickListener);
		td1618.setText(System.getProperty("line.separator"));

		TextView td1820 = (TextView) rootView.findViewById(R.id.td1820);
		td1820.setOnLongClickListener(onLongClickListener);
		td1820.setOnClickListener(onClickListener);
		td1820.setText(System.getProperty("line.separator"));

		return rootView;

	}

	@Override
	public void onResume() {
		(new TimetableEntryLoader()).execute();
		super.onResume();
	}

	protected void showEventTT(View v) {

		if (v.getId() == (rootView.findViewById(R.id.td810)).getId()) {
			timeslotId = TimetableEntry.FROM_08_TO_10;
		} else if (v.getId() == (rootView.findViewById(R.id.td1012)).getId()) {
			timeslotId = TimetableEntry.FROM_10_TO_12;
		} else if (v.getId() == (rootView.findViewById(R.id.td1214)).getId()) {
			timeslotId = TimetableEntry.FROM_12_TO_14;
		} else if (v.getId() == (rootView.findViewById(R.id.td1416)).getId()) {
			timeslotId = TimetableEntry.FROM_14_TO_16;
		} else if (v.getId() == (rootView.findViewById(R.id.td1618)).getId()) {
			timeslotId = TimetableEntry.FROM_16_TO_18;
		} else if (v.getId() == (rootView.findViewById(R.id.td1820)).getId()) {
			timeslotId = TimetableEntry.FROM_18_TO_20;
		}

		(new TimetableEntrySearcher(false)).execute();
	}

	protected void editEventTT(View v) {
		if (v.getId() == (rootView.findViewById(R.id.td810)).getId()) {
			timeslotId = TimetableEntry.FROM_08_TO_10;
		} else if (v.getId() == (rootView.findViewById(R.id.td1012)).getId()) {
			timeslotId = TimetableEntry.FROM_10_TO_12;
		} else if (v.getId() == (rootView.findViewById(R.id.td1214)).getId()) {
			timeslotId = TimetableEntry.FROM_12_TO_14;
		} else if (v.getId() == (rootView.findViewById(R.id.td1416)).getId()) {
			timeslotId = TimetableEntry.FROM_14_TO_16;
		} else if (v.getId() == (rootView.findViewById(R.id.td1618)).getId()) {
			timeslotId = TimetableEntry.FROM_16_TO_18;
		} else if (v.getId() == (rootView.findViewById(R.id.td1820)).getId()) {
			timeslotId = TimetableEntry.FROM_18_TO_20;
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

		private AccessFacade accessFacade;

		@Override
		protected List<TimetableEntry> doInBackground(Void... unused) {
			accessFacade = new AccessFacade();

			List<TimetableEntry> timetableEntries = accessFacade
					.getTimetableAccess().getTimetableEntries();

			return timetableEntries;
		}

		@Override
		protected void onPostExecute(List<TimetableEntry> timetableEntries) {

			for (TimetableEntry timetableEntry : timetableEntries) {
				if (timetableEntry.getDayOfWeek() == dayC) {
					if (timetableEntry.getTime() == TimetableEntry.FROM_08_TO_10) {
						prepareTextElement(R.id.td810, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_10_TO_12) {
						prepareTextElement(R.id.td1012, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_12_TO_14) {
						prepareTextElement(R.id.td1214, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_14_TO_16) {
						prepareTextElement(R.id.td1416, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_16_TO_18) {
						prepareTextElement(R.id.td1618, timetableEntry);
					} else if (timetableEntry.getTime() == TimetableEntry.FROM_18_TO_20) {
						prepareTextElement(R.id.td1820, timetableEntry);
					}
				}
			}
		}

		private void prepareTextElement(int id, TimetableEntry entry) {
			ColorHelper colorHelper = new ColorHelper();
			TextView view = (TextView) rootView.findViewById(id);
			view.setText(entry.getTitle()+ System.getProperty("line.separator") + entry.getLocation());
			view.setBackgroundColor(colorHelper.getColor(entry.getColor())
					.getBackgroundColor());
			view.setTextColor(colorHelper.getColor(entry.getColor())
					.getFontColor());
		}

		// boolean search(int time, int day) {
		// List<TimetableEntry> timetableEntries = accessFacade
		// .getTimetableAccess().getTimetableEntries();
		//
		// for (TimetableEntry timetableEntry : timetableEntries) {
		// if (timetableEntry.getTime() == time
		// && timetableEntry.getDayOfWeek() == day) {
		// return true;
		// }
		// }
		// return false;
		//
		// }
	}

	protected class TimetableEntrySearcher extends
			AsyncTask<Void, Void, List<TimetableEntry>> {

		boolean edit = false;

		private TimetableEntrySearcher(boolean edit) {
			super();
			this.edit = edit;
		}

		@Override
		protected List<TimetableEntry> doInBackground(Void... unused) {
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
//				CreateEventTT dialog = new CreateEventTT();
//				dialog.setAttributes(rootView, timeslotId, dayC);
//				dialog.show(DisplayDayFragment.this.getFragmentManager(),
//						"createEventTT");
				Intent intent = new Intent(rootView.getContext(),
						EditTimeTableActivity.class);
				intent.putExtra("timeslotId", timeslotId);
				intent.putExtra("dayId", dayC);
				intent.putExtra("new", false);
				intent.putExtra("toOverview", true);
				startActivity(intent);
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