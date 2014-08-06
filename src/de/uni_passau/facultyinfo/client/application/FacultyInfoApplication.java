package de.uni_passau.facultyinfo.client.application;

import android.app.Application;
import android.content.Context;

public class FacultyInfoApplication extends Application {
	
	private static FacultyInfoApplication instance;

	public FacultyInfoApplication() {
		instance = this;
	}
	
	public static Context getContext(){
		return instance;
	}

}
