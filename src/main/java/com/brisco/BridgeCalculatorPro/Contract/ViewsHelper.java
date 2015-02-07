package com.brisco.BridgeCalculatorPro.Contract;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.brisco.BridgeCalculatorPro.R;

public abstract class ViewsHelper {
	private Activity _activity;

	public ViewsHelper(Activity activity) {
		_activity = activity;
	}

	public View Find(int id) {
		return _activity.findViewById(id);
	}

	public int GetColor(int resourceColor) {
		return _activity.getResources().getColor(resourceColor);
	}

	public TextView CreateTextView(String text) {
		TextView tv = new TextView(_activity);
		tv.setText(text);
		tv.setTextColor(GetColor(R.color.white));
		return tv;
	}
}
