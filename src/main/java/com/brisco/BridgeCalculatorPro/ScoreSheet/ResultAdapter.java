package com.brisco.BridgeCalculatorPro.ScoreSheet;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.brisco.BridgeCalculatorPro.R;
import com.brisco.BridgeCalculatorPro.Settings;
import com.brisco.BridgeCalculatorPro.Contract.Mapping;
import com.brisco.common.Score.Calculator;

public class ResultAdapter extends ArrayAdapter<Result> {
	public ResultAdapter(Context context, Settings settings,
			int textViewResourceId, ArrayList<Result> results) {
		super(context, textViewResourceId, results);
		_results = results;
		_context = context;
		_settings = settings;
	}

	private ArrayList<Result> _results;
	private Context _context;
	private Settings _settings;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = LayoutInflater.from(_context);
			v = vi.inflate(R.layout.scoresheetrow, null);
		}

		Result result = _results.get(position);
		if (result != null) {
			TextView tv = setText(v, R.id.scoresheetBoardnr,
					Integer.toString(result.BoardNumber));
			tv.setBackgroundDrawable(_context.getResources().getDrawable(
					BoardActivity.getVulnerabilityDrawable(result.BoardNumber)));
			setText(v, R.id.scoresheetContract,
					Mapping.GetContractStringWithoutTricks(result.Contract,
							_context));
			setText(v, R.id.scoresheetDeclarer, Mapping.GetStringFromDirection(
					result.Contract.Player, _context));
			setText(v, R.id.scoresheetLead,
					com.brisco.BridgeCalculatorPro.ScoreSheet.Mapping
							.GetStringFromCard(result.Contract.Lead, _settings));
			setText(v, R.id.scoresheetResult,
					Integer.toString(result.Contract.Tricks));
			setText(v, R.id.scoresheetScore, Integer.toString(Calculator
					.GetNorthSouthPoints(result.Contract, result.BoardNumber)));
		}

		return v;
	}

	private TextView setText(View v, int targetViewID, String string) {
		TextView tv = (TextView) v.findViewById(targetViewID);
		tv.setText(string);
		return tv;
	}
}