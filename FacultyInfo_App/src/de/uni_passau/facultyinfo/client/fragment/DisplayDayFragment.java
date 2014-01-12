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
import android.view.ViewGroup;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.activity.DisplayTimeTableEntryActivity;
import de.uni_passau.facultyinfo.client.activity.EditTimeTableActivity;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.TimetableEntry;
import de.uni_passau.facultyinfo.client.util.ColorHelper;

//import android.app.DialogFragment;

public class DisplayDayFragment extends Fragment {

	public static final String ARG_DAY = "day";
	private View rootView;
	private int timeslotId;
	private int dayId;
	private int dayC;
	private TimetableEntryLoader timetableEntryLoader;

	public DisplayDayFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// View rootView = inflater.inflate(R.layout.fragment_display_day,
		// container, false);
		rootView = inflater.inflate(R.layout.fragment_display_day, container,
				false);
		Bundle args = getArguments();
		dayId = args.getInt(ARG_DAY);
		String day;

		if (dayId == 1) {
			System.out.println("DisplayDayFragment -> Montag");
			day = "Montag";
			dayC = TimetableEntry.MONDAY;
		} else if (dayId == 2) {
			System.out.println("DisplayDayFragment -> Dienstag");
			day = "Dienstag";
			dayC = TimetableEntry.TUESDAY;
		} else if (dayId == 3) {
			System.out.println("DisplayDayFragment -> Mittwoch");
			day = "Mittwoch";
			dayC = TimetableEntry.WEDNESDAY;
		} else if (dayId == 4) {
			System.out.println("DisplayDayFragment -> Donnerstag");
			day = "Donnerstag";
			dayC = TimetableEntry.THURSDAY;
		} else if (dayId == 5) {
			System.out.println("DisplayDayFragment -> Freitag");
			day = "Freitag";
			dayC = TimetableEntry.FRIDAY;
		}

		// TextView textview = (TextView)rootView.findViewById(R.id.text1);
		// textview.setText(day);
		// ((TextView) rootView.findViewById(R.id.text1)).setText(
		// getString(args.getInt(ARG_DAY)));

		TextView td810 = (TextView) rootView.findViewById(R.id.td810);
		// td810.setText("get - " + dayId + " - 0810");
		td810.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				editEventTT(v);
				return true;
			}
		});
		td810.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showEventTT(v);
			}
		});

		TextView td1012 = (TextView) rootView.findViewById(R.id.td1012);
		// td1012.setText("get - " + dayId + " - 1012");
		td1012.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				editEventTT(v);
				return true;
			}
		});
		td1012.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showEventTT(v);
			}
		});

		TextView td1214 = (TextView) rootView.findViewById(R.id.td1214);
		// td1214.setText("get - " + dayId + " - 1214");
		td1214.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				editEventTT(v);
				return true;
			}
		});
		td1214.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showEventTT(v);
			}
		});

		TextView td1416 = (TextView) rootView.findViewById(R.id.td1416);
		// td1416.setText("get - " + dayId + " - 1416");
		td1416.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				editEventTT(v);
				return true;
			}
		});
		td1416.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showEventTT(v);
			}
		});

		TextView td1618 = (TextView) rootView.findViewById(R.id.td1618);
		// td1618.setText("get - " + dayId + " - 1618");
		td1618.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				editEventTT(v);
				return true;
			}
		});
		td1618.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showEventTT(v);
			}
		});

		TextView td1820 = (TextView) rootView.findViewById(R.id.td1820);
		// td1820.setText("get - " + dayId + " - 1820");
		td1820.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				editEventTT(v);
				return true;
			}
		});
		td1820.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showEventTT(v);
			}
		});

		return rootView;

	}

	@Override
	public void onResume() {
		timetableEntryLoader = new TimetableEntryLoader();
		timetableEntryLoader.execute();
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
		// if(timetableEntryLoader.search(timeslotId, dayId)){
		Intent intent = new Intent(rootView.getContext(),
				DisplayTimeTableEntryActivity.class);
		intent.putExtra("dayId", dayC);
		intent.putExtra("timeslotId", timeslotId);
		startActivity(intent);
		// }

	}

	protected void editEventTT(View v) {
		System.out.println("longClick -> showEventTT" + v.getId());

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

		final class CreateEventTT extends DialogFragment {
			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
				// Use the Builder class for convenient dialog construction
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setMessage(R.string.create_event_TT_dialog)
						.setPositiveButton(R.string.create,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										System.out
												.println("Veranstaltung anlegen");
										Intent intent = new Intent(rootView
												.getContext(),
												EditTimeTableActivity.class);
										intent.putExtra("timeslotId",
												timeslotId);
										intent.putExtra("dayId", dayC);
										intent.putExtra("new", true);
										startActivity(intent);
									}
								})
						.setNegativeButton(R.string.cancel,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										System.out.println("Abbruch");
										// User cancelled the dialog
									}
								});
				// Create the AlertDialog object and return it
				return builder.create();
			}
		}
		int day;

		if (!timetableEntryLoader.search(timeslotId, dayC)) {
			CreateEventTT dialog = new CreateEventTT();
			dialog.show(this.getFragmentManager(), "createEventTT");
		} else {
			System.out.println("Daten laden");
			// EditTimeTable, aber Daten laden!!
			Intent intent = new Intent(rootView.getContext(),
					EditTimeTableActivity.class);
			intent.putExtra("timeslotId", timeslotId);
			intent.putExtra("dayId", dayC);
			intent.putExtra("new", false);
			startActivity(intent);
		}

	}

	protected class TimetableEntryLoader extends
			AsyncTask<Void, Void, List<TimetableEntry>> {

		private AccessFacade accessFacade;

		@Override
		protected List<TimetableEntry> doInBackground(Void... unused) {
			// AccessFacade accessFacade = new AccessFacade();
			accessFacade = new AccessFacade();

			List<TimetableEntry> timetableEntries = accessFacade
					.getTimetableAccess().getTimetableEntries();

			return timetableEntries;
		}

		@Override
		protected void onPostExecute(List<TimetableEntry> timetableEntries) {

			// DATENTYP onLongClickListener
			// onLongClickListener = new View.OnLongClickListener() {
			// @Override
			// public boolean onLongClick(View v) {
			// showEventTT(v);
			// return true;
			// }
			// };

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
			view.setText(entry.getTitle());
			view.setBackgroundColor(colorHelper.getColor(entry.getColor())
					.getBackgroundColor());
			view.setTextColor(colorHelper.getColor(entry.getColor())
					.getFontColor());
		}

		boolean search(int time, int day) {
			List<TimetableEntry> timetableEntries = accessFacade
					.getTimetableAccess().getTimetableEntries();

			for (TimetableEntry timetableEntry : timetableEntries) {
				if (timetableEntry.getTime() == time
						&& timetableEntry.getDayOfWeek() == day) {
					return true;
				}
			}
			return false;

		}
	}
}
