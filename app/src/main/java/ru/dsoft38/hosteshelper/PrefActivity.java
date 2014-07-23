package ru.dsoft38.hosteshelper;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PrefActivity extends PreferenceActivity {
	  @SuppressWarnings("deprecation")
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      addPreferencesFromResource(R.layout.pref);
	    }
}
