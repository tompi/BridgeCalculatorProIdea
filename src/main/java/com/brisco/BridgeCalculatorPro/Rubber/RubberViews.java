package com.brisco.BridgeCalculatorPro.Rubber;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brisco.BridgeCalculatorPro.R;
import com.brisco.BridgeCalculatorPro.Contract.ViewsHelper;

public class RubberViews extends ViewsHelper {
	public RubberViews(Activity activity) {
		super(activity);
		MainView = ((LinearLayout) Find(R.id.rubberMainView));
		AddButton = ((LinearLayout) Find(R.id.rubberAddContract));
		RemoveButton = ((LinearLayout) Find(R.id.rubberRemoveContract));
		StatusTextWe = ((TextView) Find(R.id.rubberStatusWeLabel));
		StatusTextThem = ((TextView) Find(R.id.rubberStatusThemLabel));
		StatusScoreTextWe = ((TextView) Find(R.id.rubberStatusScoreWe));
		StatusScoreTextThem = ((TextView) Find(R.id.rubberStatusScoreThem));
		StatusLegTextWe = ((TextView) Find(R.id.rubberStatusLegWe));
		StatusLegTextThem = ((TextView) Find(R.id.rubberStatusLegThem));
		StatusWe = ((LinearLayout) Find(R.id.rubberStatusWe));
		StatusThem = ((LinearLayout) Find(R.id.rubberStatusThem));
		AboveLineWe = ((LinearLayout) Find(R.id.rubberAboveLineWe));
		AboveLineThem = ((LinearLayout) Find(R.id.rubberAboveLineThem));
		BelowLineWe1 = ((LinearLayout) Find(R.id.rubberBelowLine1We));
		BelowLineWe2 = ((LinearLayout) Find(R.id.rubberBelowLine2We));
		BelowLineWe3 = ((LinearLayout) Find(R.id.rubberBelowLine3We));
		BelowLineThem1 = ((LinearLayout) Find(R.id.rubberBelowLine1Them));
		BelowLineThem2 = ((LinearLayout) Find(R.id.rubberBelowLine2Them));
		BelowLineThem3 = ((LinearLayout) Find(R.id.rubberBelowLine3Them));
	}

	public LinearLayout MainView;
	public LinearLayout AddButton;
	public LinearLayout RemoveButton;

	public TextView StatusTextWe;
	public TextView StatusTextThem;
	public TextView StatusScoreTextWe;
	public TextView StatusScoreTextThem;
	public TextView StatusLegTextWe;
	public TextView StatusLegTextThem;

	public LinearLayout StatusWe;
	public LinearLayout StatusThem;

	public LinearLayout AboveLineWe;
	public LinearLayout AboveLineThem;

	public LinearLayout BelowLineWe1;
	public LinearLayout BelowLineWe2;
	public LinearLayout BelowLineWe3;
	public LinearLayout BelowLineThem1;
	public LinearLayout BelowLineThem2;
	public LinearLayout BelowLineThem3;

}
