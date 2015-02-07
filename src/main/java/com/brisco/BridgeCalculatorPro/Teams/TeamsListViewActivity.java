package com.brisco.BridgeCalculatorPro.Teams;

import java.io.IOException;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.brisco.BridgeCalculatorPro.BridgeCalculatorProApplication;
import com.brisco.BridgeCalculatorPro.R;
import com.brisco.BridgeCalculatorPro.Settings;
import com.brisco.BridgeCalculatorPro.Contract.ContractActivity;

public class TeamsListViewActivity extends ListActivity implements
		OnClickListener {
	private TeamsResultAdapter _adapter;
	private TeamsEvent _event;
	private RadioButton _radioNS;
	private RadioButton _radioEW;
	private Settings _settings;
	private int _session = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teams_listview);
		((LinearLayout) findViewById(R.id.teamslistviewAdd))
				.setOnClickListener(this);
		_radioNS = (RadioButton) findViewById(R.id.teamslistHeaderNS);
		_radioNS.setOnClickListener(this);
		_radioEW = (RadioButton) findViewById(R.id.teamslistHeaderEW);
		_radioEW.setOnClickListener(this);

		BridgeCalculatorProApplication app = (BridgeCalculatorProApplication) getApplication();
		_settings = app.GetSettings(this);
		_event = (TeamsEvent) app.GetCompetition();
		if (_event.Description == null) {
			_event.Description = "Teams match";
		}
		PopulateList();
		updateHeaderStats();
		registerForContextMenu(getListView());
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		View list = getListView();
		if (v.getId() == list.getId()) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			TeamsResult result = _adapter.getItem(info.position);
			menu.setHeaderTitle("Board no " + result.BoardNumber);
			String[] menuItems = getResources().getStringArray(
					R.array.boardListMenu);
			for (int i = 0; i < menuItems.length; i++) {
				menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		final TeamsResult result = _adapter.getItem(info.position);
		switch (item.getItemId()) {
		case 0:
			// Delete
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(
					"Are you sure you want to delete board "
							+ result.BoardNumber + " ?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									_event.deleteBoard(result.BoardNumber,
											_session);
									saveEvent();
									PopulateList();
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
			break;
		}
		return true;
	}

	private void PopulateList() {
		_adapter = new TeamsResultAdapter(this, R.layout.teams_listview,
				_event, _session);
		setListAdapter(_adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.teamslistviewAdd:
			Intent i = new Intent(this,
					com.brisco.BridgeCalculatorPro.Teams.BoardActivity.class);
			i.putExtra(BoardActivity.BOARDNUMBER, _event
					.GetFirstUnregisteredBoardnr(_session,
							_settings.LookDownWardsForNextFreeBoardNumber,
							_settings.LastChosenBoardNumber));
			startActivityForResult(i, ContractActivity.GET_CONTRACT);
			break;
		case R.id.teamslistHeaderNS:
			_event.OurTeamNSinOpenRoom = true;
			updateHeaderStats();
			PopulateList();
			saveEvent();
			break;
		case R.id.teamslistHeaderEW:
			_event.OurTeamNSinOpenRoom = false;
			updateHeaderStats();
			PopulateList();
			saveEvent();
			break;
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		TeamsResult result = _adapter.getItem(position);
		Intent i = new Intent(this, BoardActivity.class);
		i.putExtra(BoardActivity.BOARDNUMBER, result.BoardNumber);
		startActivityForResult(i, ContractActivity.GET_CONTRACT);
	}

	@Override
	protected void onResume() {
		super.onResume();
		_adapter.notifyDataSetChanged();
		updateHeaderStats();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			_event.SortResults(_session);
			saveEvent();
		}
		updateHeaderStats();
		_adapter.notifyDataSetChanged();
	}

	private void saveEvent() {
		try {
			((BridgeCalculatorProApplication) getApplication())
					.SaveCompetition();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateHeaderStats() {
		// Set up radiobuttons state
		if (_event.OurTeamNSinOpenRoom) {
			_radioNS.setChecked(true);
		} else {
			_radioEW.setChecked(true);
		}

		TeamsEventStats stats = new TeamsEventStats(_event);
		boolean winning = true;
		int color = R.color.green;
		if (stats.IMP < 0) {
			winning = false;
			color = R.color.red;
		}
		int androidColor = getResources().getColor(color);

		// IMP
		TextView tv = GetTV(R.id.teamslistHeaderIMP);
		tv.setText(Integer.toString(stats.IMP));
		tv.setTextColor(androidColor);

		// VP
		String text = null;
		if (winning) {
			text = Integer.toString(stats.VP.WinnerVP) + "-"
					+ Integer.toString(stats.VP.LoserVP);
		} else {
			text = Integer.toString(stats.VP.LoserVP) + "-"
					+ Integer.toString(stats.VP.WinnerVP);
		}
		tv = GetTV(R.id.teamslistHeaderVP);
		tv.setText(text);
		tv.setTextColor(androidColor);

		// Scored
		text = stats.ScoredBoards + "/" + stats.TotalBoards;
		GetTV(R.id.teamslistHeaderScored).setText(text);

		// Statistics
		text = stats.WonBoards + "/" + stats.TiedBoards + "/"
				+ stats.LostBoards + "/" + stats.PlayedPercentage + "%";
		GetTV(R.id.teamslistHeaderStats).setText(text);
	}

	private TextView GetTV(int id) {
		return (TextView) findViewById(id);
	}
}