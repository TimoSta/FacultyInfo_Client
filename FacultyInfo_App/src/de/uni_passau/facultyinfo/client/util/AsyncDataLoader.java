package de.uni_passau.facultyinfo.client.util;

import de.uni_passau.facultyinfo.client.application.FacultyInfoApplication;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

abstract public class AsyncDataLoader<T> extends AsyncTask<Void, Integer, T> {
	protected static final int NO_CONNECTION_PROGRESS = 1;
	protected View rootView = null;

	public AsyncDataLoader(View rootView) {
		super();
		this.rootView = rootView;
	}

	public AsyncDataLoader() {
		super();
	}

	@Override
	abstract protected T doInBackground(Void... unused);

	@Override
	protected final void onProgressUpdate(Integer... status) {
		if (status[0] != null && status[0] == NO_CONNECTION_PROGRESS) {
			Toast toast = Toast.makeText(FacultyInfoApplication.getContext(),
					"Keine Verbindung zum Server.", Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	@Override
	abstract protected void onPostExecute(T result);
	
}