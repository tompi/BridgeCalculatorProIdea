package com.brisco.BridgeCalculatorPro.ScoreSheet;

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

import com.brisco.BridgeCalculatorPro.BridgeCalculatorProApplication;
import com.brisco.BridgeCalculatorPro.R;
import com.brisco.BridgeCalculatorPro.Settings;
import com.brisco.BridgeCalculatorPro.Contract.ContractActivity;

public class ScoreSheetListViewActivity extends ListActivity implements
		OnClickListener {
	private ResultAdapter _adapter;
	private Event _event;
	private Settings _settings;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scoresheet);
		((LinearLayout) findViewById(R.id.scoresheetAdd))
				.setOnClickListener(this);

		BridgeCalculatorProApplication app = (BridgeCalculatorProApplication) getApplication();
		_event = (Event) app.GetCompetition();
		_settings = app.GetSettings(this);
		if (_event.Description == null) {
			_event.Description = "Scoresheet";
		}
		PopulateList();
		registerForContextMenu(getListView());
	}

	private void PopulateList() {
		_adapter = new ResultAdapter(this, _settings, R.layout.competition,
				_event.GetResults());
		setListAdapter(_adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.scoresheetAdd:
			Intent i = new Intent(this, BoardActivity.class);
			i.putExtra(BoardActivity.BOARDNUMBER,
					_event.GetFirstUnregisteredBoardnr());
			startActivityForResult(i, ContractActivity.GET_CONTRACT);
			break;
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Result result = _adapter.getItem(position);
		Intent i = new Intent(this, BoardActivity.class);
		i.putExtra(BoardActivity.BOARDNUMBER, result.BoardNumber);
		startActivityForResult(i, ContractActivity.GET_CONTRACT);
	}

	@Override
	protected void onResume() {
		super.onResume();
		_adapter.notifyDataSetChanged();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			_event.SortResults();
			try {
				((BridgeCalculatorProApplication) getApplication())
						.SaveCompetition();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		View list = getListView();
		if (v.getId() == list.getId()) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			Result result = _adapter.getItem(info.position);
			menu.setHeaderTitle("Board" + result.BoardNumber);
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
		final Result result = _adapter.getItem(info.position);
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
									_event.deleteBoard(result.BoardNumber);
									try {
										((BridgeCalculatorProApplication) getApplication())
												.SaveCompetition();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									_adapter.notifyDataSetChanged();
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

}