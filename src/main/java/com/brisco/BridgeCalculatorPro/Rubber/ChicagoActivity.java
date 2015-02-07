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

public class ChicagoActivity extends Activity implements OnClickListener {
	private Chicago _chicago;
	private RubberViews _views;

	private static LayoutParams scoreLayoutParams = new LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		BridgeCalculatorProApplication application = ((BridgeCalculatorProApplication) getApplication());
		_chicago = (Chicago) application.GetCompetition();
		if (_chicago.Description == null) {
			GetChicagoDescription();
			if (_chicago.Description == null) {
				_chicago.Description = "Chicago";
			}
		}
		application.SetIAddContractHandler(_chicago);

		setContentView(R.layout.rubber);

		_views = new RubberViews(this);
		_views.AddButton.setOnClickListener(this);
		_views.RemoveButton.setOnClickListener(this);

		DisplayChicago();
	}

	private void GetChicagoDescription() {
		LayoutInflater vi = LayoutInflater.from(this);
		View view = vi.inflate(R.layout.rubber_meta_info, null);

		((TextView) view.findViewById(R.id.rubberMetaLabel))
				.setText("Chicago description:");
		final EditText input = (EditText) view
				.findViewById(R.id.rubberMetaDescription);
		new AlertDialog.Builder(this).setView(view)
				.setPositiveButton("OK", null)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					    String val = input.getText().toString()
                        .trim();
					    if (val != null && val.length()>0) {
					        _chicago.Description = val;
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
			int size = _chicago.GetContractsSize();
			if (size > 0) {
				Contract contract = _chicago.GetContract(size - 1);

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
										_chicago.DeleteLastGame();
										ClearResultViews();
										DisplayChicago();
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
				DisplayChicago();
				try {
					((BridgeCalculatorProApplication) getApplication())
							.SaveCompetition();
				} catch (IOException e) {
					new AlertDialog.Builder(this)
							.setMessage("Error saving Chicago!")
							.setPositiveButton("OK", null).show();
				}
			}
		}
	}

	private void DisplayChicago() {
		ArrayList<Integer> results = _chicago.GetScores();

		// Set up vulnerability colors:
		Vulnerability vulnerability = _chicago.GetVulnerability();
		MarkLayoutVulnerability(_views.StatusWe,
				vulnerability.IsVulnerable(Direction.North));
		MarkLayoutVulnerability(_views.StatusThem,
				vulnerability.IsVulnerable(Direction.East));

		// Total score:
		int totalScoreWe = 0;
		int totalScoreThem = 0;
		for (int score : results) {
			if (score > 0) {
				totalScoreWe += score;
			} else {
				totalScoreThem += -1 * score;
			}
		}
		_views.StatusTextWe.setTextSize(30);
		_views.StatusTextThem.setTextSize(30);
		_views.StatusLegTextWe.setVisibility(View.GONE);
		_views.StatusLegTextThem.setVisibility(View.GONE);
		_views.StatusScoreTextWe.setTextSize(25);
		_views.StatusScoreTextThem.setTextSize(25);
		_views.StatusScoreTextWe.setText("Total: "
				+ Integer.toString(totalScoreWe));
		_views.StatusScoreTextThem.setText("Total: "
				+ Integer.toString(totalScoreThem));

		// Disable add button if rubber has ended
		if (results.size() > 3) {
			_views.AddButton.setEnabled(false);
			new AlertDialog.Builder(this)
					.setView(GetResultView(totalScoreWe, totalScoreThem))
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									Toast.makeText(
											getBaseContext(),
											"Press back when you are finished reviewing the chicago.",
											Toast.LENGTH_LONG).show();
								}
							}).show();
		} else {
			_views.AddButton.setEnabled(true);
		}

		if (results.size() > 0) {
			Contract c = _chicago.GetContract(0);
			AddScoreToLayout(c, results.get(0), _views.AboveLineWe, false, true);
			AddScoreToLayout(c, results.get(0), _views.AboveLineThem, false,
					false);
			if (results.size() > 1) {
				c = _chicago.GetContract(1);
				AddScoreToLayout(c, results.get(1), _views.BelowLineWe1, true,
						true);
				AddScoreToLayout(c, results.get(1), _views.BelowLineThem1,
						false, false);
				if (results.size() > 2) {
					c = _chicago.GetContract(2);
					AddScoreToLayout(c, results.get(2), _views.BelowLineWe2,
							false, true);
					AddScoreToLayout(c, results.get(2), _views.BelowLineThem2,
							true, false);
					if (results.size() > 3) {
						c = _chicago.GetContract(3);
						AddScoreToLayout(c, results.get(3),
								_views.BelowLineWe3, true, true);
						AddScoreToLayout(c, results.get(3),
								_views.BelowLineThem3, true, false);
					}
				}
			}
		}

	}

	private void ClearResultViews() {
		_views.AboveLineWe.removeAllViews();
		_views.AboveLineThem.removeAllViews();
		_views.BelowLineWe1.removeAllViews();
		_views.BelowLineThem1.removeAllViews();
		_views.BelowLineWe2.removeAllViews();
		_views.BelowLineThem2.removeAllViews();
		_views.BelowLineWe3.removeAllViews();
		_views.BelowLineThem3.removeAllViews();
	}

	private void AddScoreToLayout(Contract c, int score, LinearLayout layout,
			boolean vulnerable, boolean us) {
		MarkLayoutVulnerability(layout, vulnerable);
		layout.removeAllViews();
		LinearLayout l = new LinearLayout(this);
		l.setLayoutParams(scoreLayoutParams);

		String contract = null;
		if ((c.Player == Direction.North) ^ !us) {
			contract = Mapping.GetContractString(c, this);
		}
		String scoreString = null;
		if (score != 0) {
			if ((score > 0) ^ !us) {
				scoreString = Integer.toString(Math.abs(score));
			}
		}
		TextView tv1 = _views.CreateTextView(contract);
		tv1.setTextSize(20);
		tv1.setGravity(Gravity.LEFT);
		TextView tv2 = _views.CreateTextView(scoreString);
		tv2.setTextSize(20);
		tv2.setGravity(Gravity.RIGHT);
		tv2.setLayoutParams(scoreLayoutParams);
		l.addView(tv1);
		l.addView(tv2);
		layout.addView(l);
	}

	private View GetResultView(int totalScoreWe, int totalScoreThem) {
		// Total score:
		Integer diff = Math.abs(totalScoreThem - totalScoreWe);

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
				.setText(GetSideResult("We", totalScoreWe));
		((TextView) ret.findViewById(R.id.rubberEndedThem))
				.setText(GetSideResult("They", totalScoreThem));

		return ret;
	}

	private String GetSideResult(String name, Integer totalScore) {
		return name + " scored " + totalScore.toString() + " points.";
	}

	private void MarkLayoutVulnerability(View view, boolean isVulnerable) {
		view.setBackgroundColor(_views.GetColor(isVulnerable ? R.color.red
				: R.color.green));
	}

}