package com.brisco.BridgeCalculatorPro.Rubber;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brisco.BridgeCalculatorPro.BridgeCalculatorProApplication;
import com.brisco.BridgeCalculatorPro.R;
import com.brisco.BridgeCalculatorPro.Contract.ContractActivity;
import com.brisco.BridgeCalculatorPro.Contract.Mapping;
import com.brisco.common.Game.Contract;
import com.brisco.common.Game.Direction;
import com.brisco.common.Game.Vulnerability;

public class RubberActivity extends Activity implements OnClickListener {
	private Rubber _rubber;
	private RubberViews _views;

	private static LayoutParams scoreLayoutParams = new LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		BridgeCalculatorProApplication application = ((BridgeCalculatorProApplication) getApplication());
		_rubber = (Rubber) application.GetCompetition();
		if (_rubber.Description == null) {
			GetRubberDescription();
			if (_rubber.Description == null) {
				_rubber.Description = "Rubber";
			}
		}
		application.SetIAddContractHandler(_rubber);

		setContentView(R.layout.rubber);

		_views = new RubberViews(this);
		_views.AddButton.setOnClickListener(this);
		_views.RemoveButton.setOnClickListener(this);
		DisplayRubber();
	}

	private void GetRubberDescription() {
		LayoutInflater vi = LayoutInflater.from(this);
		View view = vi.inflate(R.layout.rubber_meta_info, null);

		((TextView) view.findViewById(R.id.rubberMetaLabel))
				.setText("Rubber description:");
		final EditText input = (EditText) view
				.findViewById(R.id.rubberMetaDescription);
		new AlertDialog.Builder(this).setView(view)
				.setPositiveButton("OK", null)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
                        String val = input.getText().toString()
                        .trim();
                        if (val != null && val.length()>0) {
                            _rubber.Description = val;
                        }
					}
				}).show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rubberAddContract:
			Intent i = new Intent(this, ContractActivity.class);
			startActivityForResult(i, ContractActivity.GET_CONTRACT);
			break;
		case R.id.rubberRemoveContract:
			int size = _rubber.GetContractsSize();
			if (size > 0) {
				Contract contract = _rubber.GetContract(size - 1);

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage(
						"Are you sure you want to delete '"
								+ Mapping.GetContractString(contract, this)
								+ "' ?")
						.setCancelable(false)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										_rubber.DeleteLastGame();
										DisplayRubber();
									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			}
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
		super.onActivityResult(reqCode, resultCode, data);
		if (reqCode == ContractActivity.GET_CONTRACT) {
			if (resultCode == Activity.RESULT_OK) {
				DisplayRubber();
				try {
					((BridgeCalculatorProApplication) getApplication())
							.SaveCompetition();
				} catch (IOException e) {
					new AlertDialog.Builder(this)
							.setMessage("Error saving rubber!")
							.setPositiveButton("OK", null).show();
				}
			}
		}
	}

	private void DisplayRubber() {
		RubberResult result = _rubber.GetRubberResult();

		// Total score:
		int totalScoreWe = result.NorthSouth.Score;
		int totalScoreThem = result.EastWest.Score;
		_views.StatusScoreTextWe.setText("Total: "
				+ Integer.toString(totalScoreWe));
		_views.StatusScoreTextThem.setText("Total: "
				+ Integer.toString(totalScoreThem));

		// Set up vulnerability colors:
		Vulnerability vulnerability = result.GetVulnerability();
		MarkLayoutVulnerability(_views.StatusWe,
				vulnerability.IsVulnerable(Direction.North));
		MarkLayoutVulnerability(_views.StatusThem,
				vulnerability.IsVulnerable(Direction.East));

		// Disable add button if rubber has ended
		if (result.HasRubberEnded()) {
			_views.AddButton.setEnabled(false);
			new AlertDialog.Builder(this)
					.setView(GetResultView(result))
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									Toast.makeText(
											getBaseContext(),
											"Press back when you are finished reviewing the rubber.",
											Toast.LENGTH_LONG).show();
								}
							}).show();
		} else {
			_views.AddButton.setEnabled(true);
			int currentRubber = result.GetCurrentGame();
			_views.StatusLegTextWe.setText("Leg: "
					+ result.NorthSouth.GetLeg(currentRubber));
			_views.StatusLegTextThem.setText("Leg: "
					+ result.EastWest.GetLeg(currentRubber));
		}

		// Add a text-view per score:
		AddScoresToLayout(result.NorthSouth.AboveLine, _views.AboveLineWe, true);
		AddScoresToLayout(result.EastWest.AboveLine, _views.AboveLineThem, true);
		AddScoresToLayout(result.NorthSouth.BelowLine1, _views.BelowLineWe1,
				false);
		AddScoresToLayout(result.NorthSouth.BelowLine2, _views.BelowLineWe2,
				false);
		AddScoresToLayout(result.NorthSouth.BelowLine3, _views.BelowLineWe3,
				false);
		AddScoresToLayout(result.EastWest.BelowLine1, _views.BelowLineThem1,
				false);
		AddScoresToLayout(result.EastWest.BelowLine2, _views.BelowLineThem2,
				false);
		AddScoresToLayout(result.EastWest.BelowLine3, _views.BelowLineThem3,
				false);

	}

	private View GetResultView(RubberResult result) {
		// Total score:
		Integer totalScoreWe = result.NorthSouth.Score;
		Integer totalScoreThem = result.EastWest.Score;
		Integer diff = Math.abs(totalScoreThem - totalScoreWe);

		Integer rubbersWonWe = result.NorthSouth.GetGamesWon();
		Integer rubbersWonThem = result.EastWest.GetGamesWon();

		boolean weWon = totalScoreWe > totalScoreThem;
		boolean tie = totalScoreThem == totalScoreWe;

		LayoutInflater vi = LayoutInflater.from(this);
		View ret = vi.inflate(R.layout.rubber_ended_message, null);

		String message = "It's a tie!";
		if (!tie) {
			message = weWon ? "We" : "They";
			message += " won by " + diff.toString() + " points! ";
		}
		((TextView) ret.findViewById(R.id.rubberEndedWon)).setText(message);
		((TextView) ret.findViewById(R.id.rubberEndedWe))
				.setText(GetSideResult("We", totalScoreWe, rubbersWonWe));
		((TextView) ret.findViewById(R.id.rubberEndedThem))
				.setText(GetSideResult("They", totalScoreThem, rubbersWonThem));

		return ret;
	}

	private String GetSideResult(String name, Integer totalScore,
			Integer gamesWon) {
		String ret = name + " scored " + totalScore.toString() + " points";
		if (gamesWon == 1) {
			ret += "(won 1 game)";
		} else if (gamesWon == 2) {
			ret += "(won 2 games)";
		}
		ret += ".";
		return ret;
	}

	private void MarkLayoutVulnerability(LinearLayout layout,
			boolean isVulnerable) {
		layout.setBackgroundColor(_views.GetColor(isVulnerable ? R.color.red
				: R.color.green));
	}

	private void AddScoresToLayout(ArrayList<RubberScore> scores,
			LinearLayout layout, boolean reverse) {

		int ix = 0;
		layout.removeAllViews();
		for (RubberScore score : scores) {
			LinearLayout l = new LinearLayout(this);
			l.setLayoutParams(scoreLayoutParams);

			String contract = score.ContractNumber < 0 ? "Bonus" : Mapping
					.GetContractString(
							_rubber.GetContract(score.ContractNumber), this);
			TextView tv1 = _views.CreateTextView(contract);
			tv1.setGravity(Gravity.LEFT);
			TextView tv2 = _views.CreateTextView(Integer.toString(score.Score));
			tv2.setGravity(Gravity.RIGHT);
			tv2.setLayoutParams(scoreLayoutParams);
			l.addView(tv1);
			l.addView(tv2);
			layout.addView(l, reverse ? 0 : ix);
			ix++;
		}
	}

}