package de.uni_passau.facultyinfo.client.activity;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import de.uni_passau.facultyinfo.client.R;
import de.uni_passau.facultyinfo.client.R.id;
import de.uni_passau.facultyinfo.client.R.layout;
import de.uni_passau.facultyinfo.client.R.menu;
import de.uni_passau.facultyinfo.client.model.access.AccessFacade;
import de.uni_passau.facultyinfo.client.model.dto.BusinessHours;
import de.uni_passau.facultyinfo.client.model.dto.BusinessHoursFacility;
import de.uni_passau.facultyinfo.client.util.AsyncDataLoader;

public class DisplayBusinessHoursActivity extends Activity {

	private String facilityId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_business_hours);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);

		Intent intent = getIntent();
		facilityId = intent.getStringExtra("facilityId");
		String name = intent.getStringExtra("name");

		setTitle(name);

		(new BusinessHoursLoader()).execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_business_hours, menu);
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

	protected class BusinessHoursLoader extends
			AsyncDataLoader<BusinessHoursFacility> {

		@Override
		protected BusinessHoursFacility doInBackground(Void... unused) {
			System.out.println("doInBackground");
			AccessFacade accessFacade = new AccessFacade();

			BusinessHoursFacility facility = accessFacade
					.getBusinessHoursAccess().getFacility(facilityId);

			// if (list == null) {
			// // publishProgress(NewsLoader.NO_CONNECTION_PROGRESS);
			// list = accessFacade.getBusinessHoursAccess()
			// .getFacilityFromCache(facilityId).;
			// }

			// if (list == null) {
			// list = Collections
			// .unmodifiableList(new ArrayList<BusinessHoursFacility>());
			// }

			return facility;
		}

		@Override
		protected void onPostExecute(BusinessHoursFacility facility) {
			System.out.println("onPostExecute");
			// for (int i = 0; i < 2; i++) {
			// int phase;
			// if (i == 0) {
			// phase = BusinessHours.PHASE_SEMESTER;
			// } else {
			// phase = BusinessHours.PHASE_BREAK;
			// }
			// for (int j = 0; j < 7; j++) {
			// int day;
			// if (j == 0) {
			// day = BusinessHours.MONDAY;
			// } else if (j == 1) {
			// day = BusinessHours.TUESDAY;
			// } else if (j == 2) {
			// day = BusinessHours.WEDNESDAY;
			// } else if (j == 3) {
			// day = BusinessHours.THURSDAY;
			// } else if (j == 4) {
			// day = BusinessHours.FRIDAY;
			// } else if (j == 0) {
			// day = BusinessHours.SATURDAY;
			// } else {
			// day = BusinessHours.SUNDAY;
			// }
			// SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",
			// Locale.GERMAN);
			// if (phase == BusinessHours.PHASE_SEMESTER
			// && day == BusinessHours.MONDAY) {
			//
			// String text = "Mo " + facility.getBusinessHours(phase, day)
			// .getOpeningTime().toString()
			// + "-"
			// + facility.getBusinessHours(phase, day)
			// .getClosingTime().toString();
			// System.out.println(text);
			// TextView textView = (TextView) findViewById(R.id.semester_mo);
			// textView.setText(text);
			//
			// }
			// }
			//
			// }

			if (facility.getBusinessHours(BusinessHours.PHASE_BREAK,
					BusinessHours.MONDAY) == null) {
				TextView semester = (TextView) findViewById(R.id.semester);
				semester.setText("");
			}

			TextView semester_mo = (TextView) findViewById(R.id.semester_mo);
			BusinessHours businessHours_mo = facility.getBusinessHours(
					BusinessHours.PHASE_SEMESTER, BusinessHours.MONDAY);
			if (businessHours_mo.getStatus() == BusinessHours.STATUS_CLOSED) {
				semester_mo.setText("geschlossen");
			} else if (businessHours_mo.getStatus() == BusinessHours.STATUS_OPEN) {
				semester_mo.setText(businessHours_mo.getOpeningTime()
						.toString()
						+ "-"
						+ businessHours_mo.getClosingTime().toString());
			}

			TextView semester_di = (TextView) findViewById(R.id.semester_di);
			BusinessHours businessHours_di = facility.getBusinessHours(
					BusinessHours.PHASE_SEMESTER, BusinessHours.TUESDAY);
			if (businessHours_di.getStatus() == BusinessHours.STATUS_CLOSED) {
				semester_di.setText("geschlossen");
			} else if (businessHours_di.getStatus() == BusinessHours.STATUS_OPEN) {
				semester_di.setText(businessHours_di.getOpeningTime()
						.toString()
						+ "-"
						+ businessHours_di.getClosingTime().toString());
			}

			TextView semester_mi = (TextView) findViewById(R.id.semester_mi);
			BusinessHours businessHours_mi = facility.getBusinessHours(
					BusinessHours.PHASE_SEMESTER, BusinessHours.WEDNESDAY);
			if (businessHours_mi.getStatus() == BusinessHours.STATUS_CLOSED) {
				semester_mi.setText("geschlossen");
			} else if (businessHours_mi.getStatus() == BusinessHours.STATUS_OPEN) {
				semester_mi.setText(businessHours_mi.getOpeningTime()
						.toString()
						+ "-"
						+ businessHours_mi.getClosingTime().toString());
			}

			TextView semester_do = (TextView) findViewById(R.id.semester_do);
			BusinessHours businessHours_do = facility.getBusinessHours(
					BusinessHours.PHASE_SEMESTER, BusinessHours.THURSDAY);
			if (businessHours_do.getStatus() == BusinessHours.STATUS_CLOSED) {
				semester_do.setText("geschlossen");
			} else if (businessHours_do.getStatus() == BusinessHours.STATUS_OPEN) {
				semester_do.setText(businessHours_do.getOpeningTime()
						.toString()
						+ "-"
						+ businessHours_do.getClosingTime().toString());
			}

			TextView semester_fr = (TextView) findViewById(R.id.semester_fr);
			BusinessHours businessHours_fr = facility.getBusinessHours(
					BusinessHours.PHASE_SEMESTER, BusinessHours.FRIDAY);
			if (businessHours_fr.getStatus() == BusinessHours.STATUS_CLOSED) {
				semester_fr.setText("geschlossen");
			} else if (businessHours_fr.getStatus() == BusinessHours.STATUS_OPEN) {
				semester_fr.setText(businessHours_fr.getOpeningTime()
						.toString()
						+ "-"
						+ businessHours_fr.getClosingTime().toString());
			}

			TextView semester_sa = (TextView) findViewById(R.id.semester_sa);
			BusinessHours businessHours_sa = facility.getBusinessHours(
					BusinessHours.PHASE_SEMESTER, BusinessHours.SATURDAY);
			if (businessHours_sa.getStatus() == BusinessHours.STATUS_CLOSED) {
				semester_sa.setText("geschlossen");
			} else if (businessHours_sa.getStatus() == BusinessHours.STATUS_OPEN) {
				semester_sa.setText(businessHours_sa.getOpeningTime()
						.toString()
						+ "-"
						+ businessHours_sa.getClosingTime().toString());
			}

			TextView semester_so = (TextView) findViewById(R.id.semester_so);
			BusinessHours businessHours_so = facility.getBusinessHours(
					BusinessHours.PHASE_SEMESTER, BusinessHours.SUNDAY);
			if (businessHours_so.getStatus() == BusinessHours.STATUS_CLOSED) {
				semester_so.setText("geschlossen");
			} else if (businessHours_so.getStatus() == BusinessHours.STATUS_OPEN) {
				semester_so.setText(businessHours_so.getOpeningTime()
						.toString()
						+ "-"
						+ businessHours_so.getClosingTime().toString());
			}

			TextView break_mo = (TextView) findViewById(R.id.break_mo);
			BusinessHours businessHoursBreak_mo = facility.getBusinessHours(
					BusinessHours.PHASE_BREAK, BusinessHours.MONDAY);
			if (businessHoursBreak_mo != null) {
				System.out.println("businessHoursBreak_mo!=null");
				if (businessHoursBreak_mo.getStatus() == BusinessHours.STATUS_CLOSED) {
					System.out.println("status_closed");
					break_mo.setText("geschlossen");
				} else if (businessHoursBreak_mo.getStatus() == BusinessHours.STATUS_OPEN) {
					System.out.println("status_open");
					break_mo.setText(businessHoursBreak_mo.getOpeningTime()
							.toString()
							+ "-"
							+ businessHoursBreak_mo.getClosingTime().toString());
				}
			} else {
				TextView break_business_hours = (TextView) findViewById(R.id.break_business_hours);
				break_business_hours.setText("");

				TextView break_mo_h = (TextView) findViewById(R.id.break_mo_h);
				break_mo_h.setText("");
			}

			TextView break_di = (TextView) findViewById(R.id.break_di);
			BusinessHours businessHoursBreak_di = facility.getBusinessHours(
					BusinessHours.PHASE_BREAK, BusinessHours.TUESDAY);
			if (businessHoursBreak_di != null) {
				if (businessHoursBreak_di.getStatus() == BusinessHours.STATUS_CLOSED) {
					break_di.setText("geschlossen");
				} else if (businessHoursBreak_di.getStatus() == BusinessHours.STATUS_OPEN) {
					break_di.setText(businessHoursBreak_di.getOpeningTime()
							.toString()
							+ "-"
							+ businessHoursBreak_di.getClosingTime().toString());
				}
			} else {
				TextView break_di_h = (TextView) findViewById(R.id.break_di_h);
				break_di_h.setText("");
			}

			TextView break_mi = (TextView) findViewById(R.id.break_mi);
			BusinessHours businessHoursBreak_mi = facility.getBusinessHours(
					BusinessHours.PHASE_BREAK, BusinessHours.WEDNESDAY);
			if (businessHoursBreak_mi != null) {
				if (businessHoursBreak_mi.getStatus() == BusinessHours.STATUS_CLOSED) {
					break_mi.setText("geschlossen");
				} else if (businessHoursBreak_mi.getStatus() == BusinessHours.STATUS_OPEN) {
					break_mi.setText(businessHoursBreak_mi.getOpeningTime()
							.toString()
							+ "-"
							+ businessHoursBreak_mi.getClosingTime().toString());
				}
			} else {
				TextView break_mi_h = (TextView) findViewById(R.id.break_mi_h);
				break_mi_h.setText("");
			}

			TextView break_do = (TextView) findViewById(R.id.break_do);
			BusinessHours businessHoursBreak_do = facility.getBusinessHours(
					BusinessHours.PHASE_BREAK, BusinessHours.THURSDAY);
			if (businessHoursBreak_do != null) {
				if (businessHoursBreak_do.getStatus() == BusinessHours.STATUS_CLOSED) {
					break_do.setText("geschlossen");
				} else if (businessHoursBreak_do.getStatus() == BusinessHours.STATUS_OPEN) {
					break_do.setText(businessHoursBreak_do.getOpeningTime()
							.toString()
							+ "-"
							+ businessHoursBreak_do.getClosingTime().toString());
				}
			} else {
				TextView break_do_h = (TextView) findViewById(R.id.break_do_h);
				break_do_h.setText("");
			}

			TextView break_fr = (TextView) findViewById(R.id.break_fr);
			BusinessHours businessHoursBreak_fr = facility.getBusinessHours(
					BusinessHours.PHASE_BREAK, BusinessHours.FRIDAY);
			if (businessHoursBreak_fr != null) {
				if (businessHoursBreak_fr.getStatus() == BusinessHours.STATUS_CLOSED) {
					break_fr.setText("geschlossen");
				} else if (businessHoursBreak_fr.getStatus() == BusinessHours.STATUS_OPEN) {
					break_fr.setText(businessHoursBreak_fr.getOpeningTime()
							.toString()
							+ "-"
							+ businessHoursBreak_fr.getClosingTime().toString());
				}
			} else {
				TextView break_fr_h = (TextView) findViewById(R.id.break_fr_h);
				break_fr_h.setText("");
			}

			TextView break_sa = (TextView) findViewById(R.id.break_sa);
			BusinessHours businessHoursBreak_sa = facility.getBusinessHours(
					BusinessHours.PHASE_BREAK, BusinessHours.SATURDAY);
			if (businessHoursBreak_sa != null) {
				if (businessHoursBreak_sa.getStatus() == BusinessHours.STATUS_CLOSED) {
					break_sa.setText("geschlossen");
				} else if (businessHoursBreak_sa.getStatus() == BusinessHours.STATUS_OPEN) {
					break_sa.setText(businessHoursBreak_sa.getOpeningTime()
							.toString()
							+ "-"
							+ businessHoursBreak_sa.getClosingTime().toString());
				}
			} else {
				TextView break_sa_h = (TextView) findViewById(R.id.break_sa_h);
				break_sa_h.setText("");
			}

			TextView break_so = (TextView) findViewById(R.id.break_so);
			BusinessHours businessHoursBreak_so = facility.getBusinessHours(
					BusinessHours.PHASE_BREAK, BusinessHours.SUNDAY);
			if (businessHoursBreak_so != null) {
				if (businessHoursBreak_so.getStatus() == BusinessHours.STATUS_CLOSED) {
					break_so.setText("geschlossen");
				} else if (businessHoursBreak_so.getStatus() == BusinessHours.STATUS_OPEN) {
					break_so.setText(businessHoursBreak_so.getOpeningTime()
							.toString()
							+ "-"
							+ businessHoursBreak_so.getClosingTime().toString());
				}
			} else {
				TextView break_so_h = (TextView) findViewById(R.id.break_so_h);
				break_so_h.setText("");
			}

		}
	}

}
