package com.brisco.BridgeCalculatorPro;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}

	@Override
	protected void onDestroy() {
		if (isFinishing()) {
			BridgeCalculatorProApplication app = (BridgeCalculatorProApplication) getApplication();
			app.InValidateSettings();
		}
		super.onDestroy();
	}

}