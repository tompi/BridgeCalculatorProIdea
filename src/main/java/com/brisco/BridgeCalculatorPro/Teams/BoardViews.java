package com.brisco.BridgeCalculatorPro.Teams;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brisco.BridgeCalculatorPro.R;
import com.brisco.BridgeCalculatorPro.Contract.ViewsHelper;

public class BoardViews extends ViewsHelper {
	public BoardViews(Activity activity) {
		super(activity);
		OpenRoomLayout = (LinearLayout) Find(R.id.teamsBoardOpenRoom);
		ClosedRoomLayout = (LinearLayout) Find(R.id.teamsBoardClosedRoom);

		BoardNumberButton = (Button) Find(R.id.teamsBoardNumberBtn);
		// NextButton = (Button) Find(R.id.teamsBoardNextBtn);
		// PrevButton = (Button) Find(R.id.teamsBoardPrevBtn);
		OKButton = (LinearLayout) Find(R.id.teamsBoardOKBtn);
		SwapButton = (LinearLayout) Find(R.id.teamsBoardSwapBtn);
		OpenDeleteButton = (LinearLayout) Find(R.id.teamsBoardOpenDeleteBtn);
		ClosedDeleteButton = (LinearLayout) Find(R.id.teamsBoardClosedDeleteBtn);
		ScoreTextView = (TextView) Find(R.id.teamsIMP);
		DealerTextView = (TextView) Find(R.id.teamsBoardDealer);
	}

	public Button BoardNumberButton;
	// public Button NextButton;
	// public Button PrevButton;
	public LinearLayout OKButton;
	public LinearLayout SwapButton;
	public LinearLayout OpenDeleteButton;
	public LinearLayout ClosedDeleteButton;
	public LinearLayout OpenRoomLayout;
	public LinearLayout ClosedRoomLayout;
	public TextView ScoreTextView;
	public TextView DealerTextView;

	public ImageView GetRoomIconView(boolean openRoom) {
		return (ImageView) GetRoom(openRoom).findViewById(
				R.id.teamsBoardRoomIcon);
	}

	public TextView GetRoomNameView(boolean openRoom) {
		return GetRoomTextView(openRoom, R.id.teamsBoardRoomName);
	}

	public TextView GetRoomContractView(boolean openRoom) {
		return GetRoomTextView(openRoom, R.id.teamsBoardContract);
	}

	public TextView GetRoomDeclarerView(boolean openRoom) {
		return GetRoomTextView(openRoom, R.id.teamsBoardDeclarer);
	}

	public TextView GetRoomTricksView(boolean openRoom) {
		return GetRoomTextView(openRoom, R.id.teamsBoardTricks);
	}

	public TextView GetRoomScoreView(boolean openRoom) {
		return GetRoomTextView(openRoom, R.id.teamsBoardScore);
	}

	private TextView GetRoomTextView(boolean openRoom, int viewId) {
		return (TextView) GetRoom(openRoom).findViewById(viewId);
	}

	private View GetRoom(boolean openRoom) {
		return Find(openRoom ? R.id.teamsBoardOpenRoom
				: R.id.teamsBoardClosedRoom);
	}
}
