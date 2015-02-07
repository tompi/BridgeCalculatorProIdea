package com.brisco.BridgeCalculatorPro.Teams;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.brisco.BridgeCalculatorPro.R;
import com.brisco.BridgeCalculatorPro.Contract.Mapping;
import com.brisco.BridgeCalculatorPro.ScoreSheet.BoardActivity;
import com.brisco.common.Game.Contract;
import com.brisco.common.Score.Calculator;
import com.brisco.common.Score.IMPCalculator;

public class TeamsResultAdapter extends ArrayAdapter<TeamsResult> {
	public TeamsResultAdapter(Context context, int textViewResourceId,
			TeamsEvent event, int session) {
		super(context, textViewResourceId, event.GetResults(session));
		_results = event.GetResults(session);
		_ourTeamIsNSInOpenRoom = event.OurTeamNSinOpenRoom;
		_context = context;
	}

	private ArrayList<TeamsResult> _results;
	private Context _context;
	private boolean _ourTeamIsNSInOpenRoom;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = LayoutInflater.from(_context);
			v = vi.inflate(R.layout.teams_row, null);
		}

		TeamsResult result = _results.get(position);
		TextView tv = setText(v, R.id.teamsrowBoardnr,
				Integer.toString(result.BoardNumber));
		tv.setBackgroundDrawable(_context.getResources().getDrawable(
				BoardActivity.getVulnerabilityDrawable(result.BoardNumber)));
		if (result != null) {
			FillRoomInfo(v, R.id.teamsrowOpen, result.OpenContract,
					result.BoardNumber);
			FillRoomInfo(v, R.id.teamsrowClosed, result.ClosedContract,
					result.BoardNumber);
			FillImpInfo(v, result);
		}

		return v;
	}

	private void FillImpInfo(View v, TeamsResult result) {
		if (result.OpenContract != null && result.ClosedContract != null) {
			int imps = IMPCalculator.GetNorthSouthIMP(result.BoardNumber,
					result.OpenContract, result.ClosedContract);
			if (!_ourTeamIsNSInOpenRoom) {
				imps = imps * -1;
			}
			TextView tv = setText(v, R.id.teamsrowIMPs, Integer.toString(imps));
			int color = R.color.green;
			if (imps < 0) {
				color = R.color.red;
			}
			tv.setTextColor(v.getResources().getColor(color));
		} else {
			setText(v, R.id.teamsrowIMPs, "");
		}
	}

	private void FillRoomInfo(View v, int roomId, Contract contract,
			int boardNumber) {
		View room = v.findViewById(roomId);
		if (contract != null) {
			setText(room, R.id.teamsrowContract,
					Mapping.GetContractStringWithoutTricks(contract, _context));
			setText(room, R.id.teamsrowDeclarer,
					Mapping.GetStringFromDirection(contract.Player, _context));
			setText(room, R.id.teamsrowResult,
					Integer.toString(contract.Tricks));
			setText(room, R.id.teamsrowScore, Integer.toString(Calculator
					.GetNorthSouthPoints(contract, boardNumber)));
		} else {
			setText(room, R.id.teamsrowContract, "");
			setText(room, R.id.teamsrowDeclarer, "");
			setText(room, R.id.teamsrowResult, "");
			setText(room, R.id.teamsrowScore, "");
		}
	}

	private TextView setText(View v, int targetViewID, String string) {
		TextView tv = (TextView) v.findViewById(targetViewID);
		tv.setText(string);
		return tv;
	}
}