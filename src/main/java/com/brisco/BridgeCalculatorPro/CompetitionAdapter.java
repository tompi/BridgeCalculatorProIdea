package com.brisco.BridgeCalculatorPro;

import java.util.ArrayList;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.brisco.BridgeCalculatorPro.persistence.CompetitionView;

public class CompetitionAdapter extends ArrayAdapter<CompetitionView> {
	public CompetitionAdapter(Context context, int textViewResourceId,
			ArrayList<CompetitionView> competitions) {
		super(context, textViewResourceId, competitions);
		_competitions = competitions;
		_timeFormat = DateFormat.getTimeFormat(context);
		_dateFormat = DateFormat.getDateFormat(context);
	}

	private java.text.DateFormat _timeFormat;
	private java.text.DateFormat _dateFormat;
	private ArrayList<CompetitionView> _competitions;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.competition, null);
		}

		CompetitionView cv = _competitions.get(position);
		if (cv != null) {
			TextView description = (TextView) v
					.findViewById(R.id.competitionDescription);
			description.setText(cv.Description);
			TextView date = (TextView) v.findViewById(R.id.competitionDate);
			date.setText(_dateFormat.format(cv.Date) + " "
					+ _timeFormat.format(cv.Date));
		}

		return v;
	}
}