package com.brisco.BridgeCalculatorPro;

import java.text.ParseException;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.brisco.BridgeCalculatorPro.Rubber.Chicago;
import com.brisco.BridgeCalculatorPro.Rubber.ChicagoActivity;
import com.brisco.BridgeCalculatorPro.Rubber.Rubber;
import com.brisco.BridgeCalculatorPro.Rubber.RubberActivity;
import com.brisco.BridgeCalculatorPro.Rubber.RussianActivity;
import com.brisco.BridgeCalculatorPro.Rubber.RussianChicago;
import com.brisco.BridgeCalculatorPro.ScoreSheet.Event;
import com.brisco.BridgeCalculatorPro.ScoreSheet.ScoreSheetListViewActivity;
import com.brisco.BridgeCalculatorPro.Teams.TeamsEvent;
import com.brisco.BridgeCalculatorPro.Teams.TeamsListViewActivity;
import com.brisco.BridgeCalculatorPro.persistence.CompetitionView;
import com.brisco.BridgeCalculatorPro.persistence.DB;
import com.brisco.BridgeCalculatorPro.persistence.File;
import com.brisco.common.PBN.Parser;

public class main extends ListActivity implements OnClickListener {
	private CompetitionAdapter _adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		((LinearLayout) findViewById(R.id.newBtn)).setOnClickListener(this);
		((LinearLayout) findViewById(R.id.newRubberBtn))
				.setOnClickListener(this);
		((LinearLayout) findViewById(R.id.newChicagoBtn))
				.setOnClickListener(this);
		((LinearLayout) findViewById(R.id.newRussianChicagoBtn))
				.setOnClickListener(this);
		((LinearLayout) findViewById(R.id.newTeamsBtn))
				.setOnClickListener(this);
		((LinearLayout) findViewById(R.id.newPersonalScoreBtn))
				.setOnClickListener(this);
		try {
			PopulateList();
		} catch (ParseException e) {
			new AlertDialog.Builder(this)
					.setMessage("Error loading list of rubbers!")
					.setPositiveButton("OK", null).show();
		}
		registerForContextMenu(getListView());
	}

	private void PopulateList() throws ParseException {

		_adapter = new CompetitionAdapter(this, R.layout.competition,
				((BridgeCalculatorProApplication) getApplication()).GetDB()
						.GetAllCompetitions());
		setListAdapter(_adapter);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		View list = getListView();
		if (v.getId() == list.getId()) {
			AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
			CompetitionView compettitionView = _adapter.getItem(info.position);
			menu.setHeaderTitle(compettitionView.Description);
			String[] menuItems = getResources().getStringArray(
					R.array.competitionListMenu);
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
		final CompetitionView competitionView = _adapter.getItem(info.position);
		switch (item.getItemId()) {
		case 0:
			// Mail as html
			DB db = ((BridgeCalculatorProApplication) getApplication()).GetDB();
			Competition competition = db.GetCompetition(competitionView.ID);
			StringBuilder html = new StringBuilder();
			competition.AppendHTML(html);
			SendFileAsMail(
					html,
					competition.Description,
					"Score attached.\n\nSent from Bridge Calculator Pro on my android phone.",
					"score.html");
			break;
		case 1:
			// Share PBN
			db = ((BridgeCalculatorProApplication) getApplication()).GetDB();
			competition = db.GetCompetition(competitionView.ID);
			StringBuilder pbn = Parser.WritePBN(competition.GetPBNGames());
			SendFileAsMail(
					pbn,
					competition.Description,
					"If your computer can't open the attached PBN-file,\n"
							+ "try installing this program: http://www.bridgebase.com/download/bbo_setup.exe\n\n"
							+ "Sent from Bridge Calculator Pro on my android phone.",
					"score.pbn");
			break;
		case 2:
			// Share CSV
			db = ((BridgeCalculatorProApplication) getApplication()).GetDB();
			competition = db.GetCompetition(competitionView.ID);
			StringBuilder csv = com.brisco.common.CSV.Mapping.Parser
					.WriteCSV(competition.GetPBNGames());
			SendFileAsMail(csv, competition.Description,
					"Sent from Bridge Calculator Pro on my android phone.",
					"score.csv");
			break;
		case 3:
			// Delete
			java.text.DateFormat timeFormat = DateFormat.getTimeFormat(this);
			java.text.DateFormat dateFormat = DateFormat.getDateFormat(this);
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(
					"Are you sure you want to delete '"
							+ competitionView.Description + "' ("
							+ dateFormat.format(competitionView.Date) + " "
							+ timeFormat.format(competitionView.Date) + ") ?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									DB db = ((BridgeCalculatorProApplication) getApplication())
											.GetDB();
									db.delete(competitionView.ID);
									_adapter.remove(competitionView);
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

	private void SendFileAsMail(StringBuilder content, String subject,
			String msg, String fileName) {

		if (!StorageReady()) {
			return;
		}
		Uri file = File.SaveStringBuilderToFile(content, fileName);
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.setType("text/plain");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
				new String[] { "" });
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, msg);
		emailIntent.putExtra(Intent.EXTRA_STREAM, file);
		startActivity(Intent.createChooser(emailIntent, "Send pbn-file with:"));
	}

	private boolean StorageReady() {
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states, but
			// all we need
			// to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		boolean ready = mExternalStorageAvailable && mExternalStorageWriteable;
		if (!ready) {
			Toast toast = Toast.makeText(getBaseContext(),
					"Sorry, could not store file...", Toast.LENGTH_SHORT);
			toast.show();
		}
		return ready;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.newBtn:
			ToggleNewActivitiesVisible();
			break;
		case R.id.newRubberBtn:
			BridgeCalculatorProApplication application = ((BridgeCalculatorProApplication) getApplication());
			application.SetCompetition(new Rubber());
			application.ClearCompetitionID();
			Intent i = new Intent(this, RubberActivity.class);
			ToggleNewActivitiesVisible();
			startActivity(i);
			break;
		case R.id.newChicagoBtn:
			application = ((BridgeCalculatorProApplication) getApplication());
			application.SetCompetition(new Chicago());
			application.ClearCompetitionID();
			i = new Intent(this, ChicagoActivity.class);
			ToggleNewActivitiesVisible();
			startActivity(i);
			break;
		case R.id.newRussianChicagoBtn:
			application = ((BridgeCalculatorProApplication) getApplication());
			application.SetCompetition(new RussianChicago());
			application.ClearCompetitionID();
			i = new Intent(this, RussianActivity.class);
			ToggleNewActivitiesVisible();
			startActivity(i);
			break;
		case R.id.newPersonalScoreBtn:
			application = ((BridgeCalculatorProApplication) getApplication());
			application.SetCompetition(new Event());
			application.ClearCompetitionID();
			i = new Intent(this, ScoreSheetListViewActivity.class);
			ToggleNewActivitiesVisible();
			startActivity(i);
			break;
		case R.id.newTeamsBtn:
			application = ((BridgeCalculatorProApplication) getApplication());
			application.SetCompetition(new TeamsEvent());
			application.ClearCompetitionID();
			i = new Intent(this, TeamsListViewActivity.class);
			ToggleNewActivitiesVisible();
			startActivity(i);
			break;
		default:
			break;
		}
	}

	private void ToggleNewActivitiesVisible() {
		LinearLayout actionsView = (LinearLayout) findViewById(R.id.newActions);
		boolean visible = actionsView.getVisibility() == View.VISIBLE;
		actionsView.setVisibility(visible ? View.GONE : View.VISIBLE);
		LinearLayout newBtn = (LinearLayout) findViewById(R.id.newBtn);
		newBtn.setVisibility(visible ? View.VISIBLE : View.GONE);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		CompetitionView competitionView = _adapter.getItem(position);
		BridgeCalculatorProApplication app = (BridgeCalculatorProApplication) getApplication();
		app.set_competitionID(competitionView.ID);
		Competition c = app.GetCompetition();
		Intent i = null;
		if (c instanceof Rubber) {
			i = new Intent(this, RubberActivity.class);
		} else if (c instanceof RussianChicago) {
			i = new Intent(this, RussianActivity.class);
		} else if (c instanceof Chicago) {
			i = new Intent(this, ChicagoActivity.class);
		} else if (c instanceof Event) {
			i = new Intent(this, ScoreSheetListViewActivity.class);
		} else if (c instanceof TeamsEvent) {
			i = new Intent(this, TeamsListViewActivity.class);
		}
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_exit:
			finish();
			return true;
		case R.id.menu_about:
			new AlertDialog.Builder(this).setView(GetAboutView(null))
					.setPositiveButton(getText(R.string.OK), null).show();
			return true;
		case R.id.menu_help:
			Intent browserIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://sites.google.com/a/bridgecalculator.com/bridge-calculator/"));
			startActivity(browserIntent);
			return true;
		case R.id.menu_settings:
			startActivity(new Intent(this, Preferences.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private View GetAboutView(ViewGroup root) {
		LayoutInflater vi = LayoutInflater.from(this);
		View ret = vi.inflate(R.layout.about, root);
		return ret;
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			PopulateList();
			_adapter.notifyDataSetChanged();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}