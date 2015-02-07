package com.brisco.BridgeCalculatorPro.ScoreSheet;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.brisco.BridgeCalculatorPro.BridgeCalculatorProApplication;
import com.brisco.BridgeCalculatorPro.R;
import com.brisco.BridgeCalculatorPro.Settings;
import com.brisco.BridgeCalculatorPro.persistence.DB;
import com.brisco.common.Game.Auction;
import com.brisco.common.Game.Bid;
import com.brisco.common.Game.Board;

public class AuctionActivity extends Activity implements OnClickListener {
	public static final String AUCTION = "AUCTION";
	public static final int GET_AUCTION = 23;
	private static LayoutParams _layoutParams = new LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
	private LayoutInflater _li;
	private Auction _auction;
	private int _boardNr;
	private AuctionViews _views;
	private Settings _settings;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auction);
		_views = new AuctionViews(this);
		_views.Doubled.setOnClickListener(this);
		_views.ReDoubled.setOnClickListener(this);
		_views.OKButton.setOnClickListener(this);
		_li = LayoutInflater.from(this);
		for (TextView tv : _views.Suit) {
			tv.setOnClickListener(this);
		}
		for (TextView tv : _views.Level) {
			tv.setOnClickListener(this);
		}
		_views.DeleteButton.setOnClickListener(this);
		_views.ClearControls();
		_settings = ((BridgeCalculatorProApplication) getApplication())
				.GetSettings(this);
		Intent i = getIntent();
		_boardNr = i.getExtras().getInt(BoardActivity.BOARDNUMBER);
		_views.Vulnerability.setBoardNumber(_boardNr);
		if (i.hasExtra(AUCTION)) {
			_auction = (Auction) DB.GetObjectFromByteArray(i
					.getByteArrayExtra(AUCTION));
		}
		if (_auction == null) {
			_auction = new Auction(Board.GetDealer(_boardNr));
		}
		UpdateBidsTable();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		Bid b = null;
		switch (id) {
		case R.id.contractDeleteBtn:
			if (_auction.Bids.size() > 0) {
				_auction.Bids.remove(_auction.Bids.size() - 1);
			}
			UpdateBidsTable();
			break;
		case R.id.auctionOKBtn:
			Intent resultIntent = new Intent();
			try {
				byte[] byteArray = DB.GetByteArrayFromObject(_auction);
				resultIntent.putExtra(AUCTION, byteArray);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			;
			setResult(RESULT_OK, resultIntent);
			finish();
			break;
		case R.id.ContractLevelPass:
			b = new Bid();
			b.Pass = true;
			break;
		case R.id.ContractDoubled:
			b = new Bid();
			b.Double = true;
			break;
		case R.id.ContractReDoubled:
			b = new Bid();
			b.ReDouble = true;
			break;
		case R.id.ContractSuitClubs:
		case R.id.ContractSuitDiamonds:
		case R.id.ContractSuitHearts:
		case R.id.ContractSuitSpades:
		case R.id.ContractSuitNT:
			_views.ToggleToggleButtons(_views.Suit, id);
			b = new Bid();
			b.Level = _views.GetLevel();
			// TODO: issue warning if suit pressed before level...
			b.Suit = _views.GetSuit();
			break;
		case R.id.ContractLevel1:
		case R.id.ContractLevel2:
		case R.id.ContractLevel3:
		case R.id.ContractLevel4:
		case R.id.ContractLevel5:
		case R.id.ContractLevel6:
		case R.id.ContractLevel7:
			_views.ToggleToggleButtons(_views.Level, id);
			break;
		}
		if (b != null) {
			_auction.Bids.add(b);
			UpdateBidsTable();
			_views.ClearControls();
		}
	}

	private void UpdateBidsTable() {
		_views.BidsTable.removeViews(1, _views.BidsTable.getChildCount() - 1);
		if (_auction.Bids == null || _auction.Bids.size() < 1)
			return;

		// Make "balanced" array of bids:
		ArrayList<Bid> bids = new ArrayList<Bid>();
		switch (_auction.Dealer) {
		case South:
			bids.add(null);
		case East:
			bids.add(null);
		case North:
			bids.add(null);
		case West:
		}
		bids.addAll(_auction.Bids);

		for (int i = 0; i < (bids.size() / 4) + 1; i++) {
			int ix = i * 4;
			TableRow tr = GetAuctionTableRow();
			AddBid(tr, bids, ix, R.id.auctionWestCell);
			AddBid(tr, bids, ix + 1, R.id.auctionNorthCell);
			AddBid(tr, bids, ix + 2, R.id.auctionEastCell);
			AddBid(tr, bids, ix + 3, R.id.auctionSouthCell);
			_views.BidsTable.addView(tr);
		}
	}

	private void AddBid(TableRow tr, ArrayList<Bid> bids, int i,
			int directionViewID) {
		if (i >= bids.size())
			return;

		Bid b = bids.get(i);

		if (b == null)
			return;
		LinearLayout ll = (LinearLayout) tr.findViewById(directionViewID);
		String bidText = Mapping.GetBidString(b, _settings);
		ll.addView(GetTextView(Html.fromHtml(bidText)));
	}

	private TableRow GetAuctionTableRow() {
		return (TableRow) _li.inflate(R.layout.auction_row, null);
	}

	private TextView GetTextView(Spanned text) {
		TextView tv = GetTextView();
		tv.setText(text);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
		return tv;
	}

	private TextView GetTextView() {
		TextView tv = new TextView(this);
		tv.setLayoutParams(_layoutParams);
		return tv;
	}

}