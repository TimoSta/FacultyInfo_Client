package de.uni_passau.facultyinfo.client.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.activity.EditTimeTableActivity;
//import android.app.DialogFragment;

public class DisplayDayFragment extends Fragment {

	public static final String ARG_DAY = "day";
	private View rootView; 
	private int timeslotId; 
	private int dayId; 

	public DisplayDayFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		View rootView = inflater.inflate(R.layout.fragment_display_day,
//				container, false);
		rootView = inflater.inflate(R.layout.fragment_display_day,
				container, false);
		Bundle args = getArguments();
		dayId = args.getInt(ARG_DAY);
		String day;

		if (dayId == 0) {
			System.out.println("DisplayDayFragment -> Montag");
			day = "Montag";
		} else if (dayId == 1) {
			System.out.println("DisplayDayFragment -> Dienstag");
			day = "Dienstag";
		} else if (dayId == 2) {
			System.out.println("DisplayDayFragment -> Mittwoch");
			day = "Mittwoch";
		} else if (dayId == 3) {
			System.out.println("DisplayDayFragment -> Donnerstag");
			day = "Donnerstag";
		} else if (dayId == 4) {
			System.out.println("DisplayDayFragment -> Freitag");
			day = "Freitag";
		}

		// TextView textview = (TextView)rootView.findViewById(R.id.text1);
		// textview.setText(day);
		// ((TextView) rootView.findViewById(R.id.text1)).setText(
		// getString(args.getInt(ARG_DAY)));

		TextView td810 = (TextView) rootView.findViewById(R.id.td810);
		td810.setText("get - " + dayId + " - 0810");
		td810.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				showEventTT(v);
				return true;
			}
		});

		TextView td1012 = (TextView) rootView.findViewById(R.id.td1012);
		td1012.setText("get - " + dayId + " - 1012");
		td1012.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				showEventTT(v);
				return true;
			}
		});

		TextView td1214 = (TextView) rootView.findViewById(R.id.td1214);
		td1214.setText("get - " + dayId + " - 1214");
		td1214.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				showEventTT(v);
				return true;
			}
		});

		TextView td1416 = (TextView) rootView.findViewById(R.id.td1416);
		td1416.setText("get - " + dayId + " - 1416");
		td1416.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				showEventTT(v);
				return true;
			}
		});

		TextView td1618 = (TextView) rootView.findViewById(R.id.td1618);
		td1618.setText("get - " + dayId + " - 1618");
		td1618.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				showEventTT(v);
				return true;
			}
		});

		TextView td1820 = (TextView) rootView.findViewById(R.id.td1820);
		td1820.setText("get - " + dayId + " - 1820");
		td1820.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				showEventTT(v);
				return true;
			}
		});

		return rootView;

	}

	protected void showEventTT(View v) {
		System.out.println("longClick -> showEventTT" + v.getId()); 
		
		
		if(v.getId()==(rootView.findViewById(R.id.td810)).getId()){
			timeslotId=810; 
		}else if(v.getId()==(rootView.findViewById(R.id.td1012)).getId()){
			timeslotId=1012; 
		}else if(v.getId()==(rootView.findViewById(R.id.td1214)).getId()){
			timeslotId=1214; 
		}else if(v.getId()==(rootView.findViewById(R.id.td1416)).getId()){
			timeslotId=1416; 
		}else if(v.getId()==(rootView.findViewById(R.id.td1618)).getId()){
			timeslotId=1618; 
		}else if(v.getId()==(rootView.findViewById(R.id.td1820)).getId()){
			timeslotId=1820; 
		}
		
		final class CreateEventTT extends DialogFragment {
		    @Override
		    public Dialog onCreateDialog(Bundle savedInstanceState) {
		        // Use the Builder class for convenient dialog construction
		        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		        builder.setMessage(R.string.create_event_TT_dialog)
		               .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                	   System.out.println("Veranstaltung anlegen"); 
		                	   Intent intent = new Intent(rootView.getContext(), EditTimeTableActivity.class); 
		                	   intent.putExtra("timeslotId", timeslotId); 
		                	   intent.putExtra("dayId", dayId); 
		                	   startActivity(intent); 
		                   }
		               })
		               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                	   System.out.println("Abbruch"); 
		                       // User cancelled the dialog
		                   }
		               });
		        // Create the AlertDialog object and return it
		        return builder.create();
		    }
		}
		CreateEventTT dialog = new CreateEventTT(); 
		dialog.show(this.getFragmentManager(), "createEventTT"); 
		
	}

}
